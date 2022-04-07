{{- define "api.name" -}}
{{- printf "%s01-%s-%s-mpf" .Values.env .Values.region .Chart.Name }}
{{- end }}

{{- define "api.namespace" -}}
{{- printf "%s01-%s-%s-nsp-mpf" .Values.env .Values.region .Chart.Name }}
{{- end }}

{{- define "api.deployment.name" -}}
{{- printf "%s01-%s-%s-deployment-mpf" .Values.env .Values.region .Chart.Name }}
{{- end }}

{{- define "api.service.name" -}}
{{- printf "%s01-%s-%s-neg-mpf" .Values.env .Values.region .Chart.Name }}
{{- end }}

{{- define "api.ingress.name" -}}
{{- printf "%s01-%s-%s-ingress-mpf" .Values.env .Values.region .Chart.Name }}
{{- end }}

{{- define "api.backend.name" -}}
{{- printf "%s01-%s-%s-backendconfig-mpf" .Values.env .Values.region .Chart.Name }}
{{- end }}

{{- define "api.certificate.name" -}}
{{- printf "%s01-%s-%s-certificate-mpf" .Values.env .Values.region .Chart.Name }}
{{- end }}

{{- define "api.commonLabels" -}}
{{ include "api.selectorLabels" . }}
app.kubernetes.io/version: {{ .Values.image.tag | default .Chart.AppVersion }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
env: {{ .Values.env }}
helm.sh/chart: {{ printf "%s-%s" .Chart.Name .Chart.Version }}
{{- end }}

{{- define "api.selectorLabels" -}}
app.kubernetes.io/name: {{ include "api.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- define "api.healthcheck" -}}
httpGet:
  path: /api/marketingpf/v1/fr/jp/healthcheck
  port: 8080
initialDelaySeconds: 15
periodSeconds: 15
timeoutSeconds: 15
{{- end }}

{{- define "api.url" -}}
{{- printf "api-%s.mkpf.%s" .Values.region .Values.domain }}
{{- end }}

{{- define "api.session" -}}
affinityType: "GENERATED_COOKIE"
affinityCookieTtlSec: 7200
{{- end }}