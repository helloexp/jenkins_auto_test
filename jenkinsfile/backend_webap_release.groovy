pipeline {
    agent any
    environment {
        PROJECT_ID = sh (script:"curl -s http://metadata/computeMetadata/v1/project/project-id -H 'Metadata-Flavor:Google'", returnStdout: true).trim()
    }
    parameters {
        choice(name: 'REGION', choices: ['asia-northeast1'], description: 'target region')
        choice(name: 'APPLICATION', choices: ['backend-webap'], description: 'target application')
        string(name: 'BRANCH', defaultValue: 'default', description: 'target branch or tag (Depending on the environment, default is either of master/staging/devlop. When the tag is specified, the format is "refs/tags/{TAG}")')    
        string(name: 'VERSION', defaultValue: 'latest', description: 'target version')
    }
    options {
        ansiColor('xterm')
        timestamps()
    }

    stages {
        stage('Prepare release'){
            steps {
                script {

                    configs = readJSON file: "./jenkinsfile/config.json"

                    // Environment Config
                    env_config = configs.env_config."${PROJECT_ID}"
                    environment = env_config.environment
                    default_branch = env_config.default_branch
                    gcp_svc_account = env_config.service_account
                    cluster = env_config."${REGION}".cluster_name
                    application_nsp = env_config."${REGION}".application_namespace
                    external_secret_nsp = env_config."${REGION}".external_secret_namespace
                    kube_state_metrics_nsp = env_config."${REGION}".kube_state_metrics_namespace

                    // App Config
                    app_config = configs.app_config."${APPLICATION}"
                    git_url = app_config.git_url
                    git_credential = app_config.git_credential
                    chart_path= app_config.chart_path
                    value_filepath = "config/${environment}/${REGION}/${APPLICATION}.yaml"

                    // Region Config
                    region_config = configs.region_config."${REGION}"
                    region_short =  region_config.region_short
                    gcr_location =  region_config.gcr_location

                    // Release Config
                    app_name = "${region_short}-${APPLICATION}"
                    image_name = "${gcr_location}/${PROJECT_ID}/${app_name}"

                    if ("${BRANCH}" == "default"){
                        BRANCH = "${default_branch}"
                    }
                    sh "echo \"target branch :${BRANCH}\""

                    if ( "${VERSION}" == "latest" ){
                        VERSION = sh(script: "gcloud container images list-tags ${image_name} --format json --filter \"tags=latest\" | jq -r '.[].tags-[\"latest\"]' | jq -r .[0]", returnStdout:true)
                    }

                    release_work_dir = "${app_name}/app"
                    helm_work_dir = "${release_work_dir}/${chart_path}"

                    sh "rm -rf ${release_work_dir}/*"
                }
            }
        }

        stage('Git clone'){
            steps {
                script {
                    dir("${release_work_dir}") {
                       checkout([$class: 'GitSCM', userRemoteConfigs: [[url: "${git_url}", credentialsId: "${git_credential}"]], branches: [[name: "${BRANCH}"]]])
                    }
                    sh "git log --oneline --graph --decorate -10"
                }
            }
        }

        stage('Prepare Release'){
            steps {
                script {
                    dir("${helm_work_dir}") {
                        
                        // Get credentials
                        sh "gcloud container clusters get-credentials --region ${REGION} ${cluster}"

                        // Check cluster config
                        ansiColor('xterm') {
                            sh "kubectl config current-context"
                            sh "kubectl cluster-info"
                        }

                        // External Secrets
                        try {
                            // execute normally on the first time
                            sh "helm repo add external-secrets https://external-secrets.github.io/kubernetes-external-secrets/"
                            sh "gcloud iam service-accounts add-iam-policy-binding ${gcp_svc_account} --role roles/iam.workloadIdentityUser --member \"serviceAccount:${PROJECT_ID}.svc.id.goog[${external_secret_nsp}/external-secret-sa]\" --project ${PROJECT_ID}"
                            sh "helm install external-secret external-secrets/kubernetes-external-secrets --values config/${environment}/${REGION}/external-secrets.yaml --create-namespace -n ${external_secret_nsp}"
                        } catch (err) {
                            // do nothing
                            sh "echo \"Skip Install External Secrets\""
                        }

                        // Kube-state-metrics
                        try {
                            // execute normally on the first time

                            sh "helm repo add prometheus-community https://prometheus-community.github.io/helm-charts"

                            // install kube-state-metrics
                            sh "helm install kube-state-metrics prometheus-community/kube-state-metrics --values config/${environment}/${REGION}/kube-state-metrics.yaml --create-namespace -n ${kube_state_metrics_nsp}"

                            // install prometheus-to-sd
                            sh "gcloud iam service-accounts add-iam-policy-binding ${gcp_svc_account} --role roles/iam.workloadIdentityUser --member \"serviceAccount:${PROJECT_ID}.svc.id.goog[${kube_state_metrics_nsp}/default]\" --project ${PROJECT_ID}"
                            sh "helm install prometheus-to-sd prometheus-community/prometheus-to-sd --values config/${environment}/${REGION}/prometheus-to-sd.yaml --create-namespace -n ${kube_state_metrics_nsp}"
                            sh "kubectl annotate serviceaccount default iam.gke.io/gcp-service-account=${gcp_svc_account} -n ${kube_state_metrics_nsp}"

                        } catch (err) {
                            // do nothing
                            sh "echo \"Skip Install Kube-state-metrics\""
                        }
                    }
                }
            }
        }
        
        stage('Dry run'){
            steps {
                script {
                    dir("${helm_work_dir}") {

                        // Application
                        sh "helm lint --values ${value_filepath} --set image.repository=${image_name} --set image.tag=${VERSION}"
                        sh "helm install ${app_name} . --debug --dry-run --values ${value_filepath} --create-namespace -n ${application_nsp} --set image.repository=${image_name} --set image.tag=${VERSION}"

                        apply = true
                        if ("${environment}" == 'production') {
                            try {
                                input message: 'Apply changes?', ok: 'Apply'
                            } catch (err) {
                                apply = false
                                currentBuild.result = 'UNSTABLE'
                            }
                        }
                    }
                }
            }
        }

        stage('Release')  {
            when {
                expression { return apply }
            }
            steps {
                script {
                    dir("${helm_work_dir}") {

                        sh "gcloud iam service-accounts add-iam-policy-binding ${gcp_svc_account} --role roles/iam.workloadIdentityUser --member \"serviceAccount:${PROJECT_ID}.svc.id.goog[${application_nsp}/default]\" --project ${PROJECT_ID}"

                        try {
                            // Check if app was already installed
                            sh "helm status ${app_name} -n ${application_nsp}"
                            // Upgrade
                            sh "helm upgrade ${app_name} . --debug --values ${value_filepath} --create-namespace -n ${application_nsp} --set image.repository=${image_name} --set image.tag=${VERSION}"
                         } catch (err) {
                            // Initial install
                            sh "helm install ${app_name} . --debug --values ${value_filepath} --create-namespace -n ${application_nsp} --set image.repository=${image_name} --set image.tag=${VERSION}"
                        }

                        sh "kubectl annotate serviceaccount default iam.gke.io/gcp-service-account=${gcp_svc_account} -n ${application_nsp} --overwrite"

                    }
                }
            }
        }
    }

    post {
        success {
            script{
                build job: 'slack_notify', parameters: [string(name: 'status', value: "SUCCESS"), string(name: 'message', value: "[${PROJECT_ID}] ${app_name}:${VERSION} DEPLOY SUCCESS \nJenkins URL:${env.BUILD_URL}")]
            }
        }
        failure {
            script {
                 build job: 'slack_notify', parameters: [string(name: 'status', value: "FAILURE"), string(name: 'message', value: "[${PROJECT_ID}] ${app_name}:${VERSION} DEPLOY FAILURE \nJenkins URL:${env.BUILD_URL}")]
            }
        }
    }
}