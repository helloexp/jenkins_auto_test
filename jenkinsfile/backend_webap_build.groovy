pipeline {
    agent any
    environment {
        PROJECT_ID = sh (script:"curl -s http://metadata/computeMetadata/v1/project/project-id -H 'Metadata-Flavor:Google'", returnStdout: true).trim()
    }
    parameters {
        choice(name: 'REGION', choices: ['asia-northeast1'], description: 'target region')
        choice(name: 'APPLICATION', choices: ['backend-webap'], description: 'target application')
        string(name: 'BRANCH', defaultValue: 'default', description: 'target branch or tag (Depending on the environment, default is either of master/staging/devlop. When the tag is specified, the format is "refs/tags/{TAG}")')    
    }
    options {
        timestamps()
    }

    stages {
        stage('Prepare build'){
            steps {
                script {

                    configs = readJSON file: "./jenkinsfile/config.json"

                    // Environment Config
                    env_config = configs.env_config."${PROJECT_ID}"
                    environment = env_config.environment
                    default_branch = env_config.default_branch

                    // App Config
                    app_config = configs.app_config."${APPLICATION}"
                    git_url = app_config.git_url
                    git_credential = app_config.git_credential
                    build_home= app_config.build_home
                    dockerfile_path= app_config.dockerfile_path

                    // Region Config
                    region_config = configs.region_config."${REGION}"
                    gcr_location =  region_config.gcr_location
                    region_short =  region_config.region_short

                    // Build Config
                    app_name = "${region_short}-${APPLICATION}"
                    image_name = "${gcr_location}/${PROJECT_ID}/${app_name}"

                    if ("${BRANCH}" == "default"){
                        BRANCH = "${default_branch}"
                    }
                    sh "echo \"target branch :${BRANCH}\""
                    version = "${BRANCH}".replace("/", "_")

                    work_dir = "${app_name}/app"
                    build_work_dir = "${work_dir}/${build_home}"

                    sh "rm -rf ${work_dir}/*"
                }
            }
        }

        stage('Git clone'){
            steps {
                script {
                    dir("${work_dir}") {
                       checkout([$class: 'GitSCM', userRemoteConfigs: [[url: "${git_url}", credentialsId: "${git_credential}"]], branches: [[name: "${BRANCH}"]]])
                    }
                    sh "git log --oneline --graph --decorate -10"
                    git_hash = sh(script: "git rev-parse --short HEAD", returnStdout: true) 

                    // Version
                    version = "${version}_${git_hash}".replace("\n", "")
                }
            }
        }

        stage('Unit test'){
            when {
                environment name: 'environment', value: 'development'
            }
            steps {
                script {
                    dir("${build_work_dir}") {
                        try {
                            sh "chmod 755 ./*"
                            sh "mvn clean test"
                        } finally {
                            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                        }
                    }
                }
            }
        }

        stage('Build'){
            steps {
                script {
                    dir("${build_work_dir}") {
                        sh "chmod 755 ./*"
                        sh "docker build -t ${app_name} --target build ."
                    }
                }
            }
        }

        stage('Push image'){
            steps {
                script {
                    sh "docker image tag ${app_name} ${image_name}:latest"
                    sh "docker image push ${image_name}:latest"
                    sh "gcloud container images add-tag ${image_name}:latest ${image_name}:${version}"
                    echo "Application:Version ${app_name}:${version}"
                 }
            }
        }
    }

    post {
        success {
            script{
                build job: 'slack_notify', parameters: [string(name: 'status', value: "SUCCESS"), string(name: 'message', value: "[${PROJECT_ID}] ${app_name}:${version} BUILD SUCCESS \nJenkins URL:${env.BUILD_URL}")]
            }
        }
        failure {
            script {
                 build job: 'slack_notify', parameters: [string(name: 'status', value: "FAILURE"), string(name: 'message', value: "[${PROJECT_ID}] ${app_name}:${version} BUILD FAILURE \nJenkins URL:${env.BUILD_URL}")]
            }
        }
    }
}