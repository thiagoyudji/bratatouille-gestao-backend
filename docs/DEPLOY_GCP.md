# Deploy de produção no Google Cloud

O workflow `.github/workflows/deploy-prod.yml` executa os testes e faz deploy automático no Cloud Run após push na `master`.

## 1. Preparar o projeto GCP

Execute localmente após autenticar no `gcloud`:

```bash
gcloud auth login
gcloud config set project project-4c4e2300-94cd-4054-992

gcloud services enable \
  artifactregistry.googleapis.com \
  run.googleapis.com \
  iamcredentials.googleapis.com \
  sts.googleapis.com

gcloud artifacts repositories create bratatouille \
  --repository-format=docker \
  --location=southamerica-east1 \
  --description="Bratatouille container images"
```

Se o repositório `bratatouille` já existir, o último comando pode ser ignorado.

## 2. Criar a identidade usada pelo GitHub Actions

```bash
PROJECT_ID=project-4c4e2300-94cd-4054-992
PROJECT_NUMBER=$(gcloud projects describe "$PROJECT_ID" --format="value(projectNumber)")
SERVICE_ACCOUNT=github-deployer
SERVICE_ACCOUNT_EMAIL="$SERVICE_ACCOUNT@$PROJECT_ID.iam.gserviceaccount.com"

gcloud iam service-accounts create "$SERVICE_ACCOUNT" \
  --project="$PROJECT_ID" \
  --display-name="GitHub production deployer"

gcloud projects add-iam-policy-binding "$PROJECT_ID" \
  --member="serviceAccount:$SERVICE_ACCOUNT_EMAIL" \
  --role="roles/run.admin"

gcloud projects add-iam-policy-binding "$PROJECT_ID" \
  --member="serviceAccount:$SERVICE_ACCOUNT_EMAIL" \
  --role="roles/artifactregistry.writer"

gcloud iam service-accounts add-iam-policy-binding \
  "$PROJECT_NUMBER-compute@developer.gserviceaccount.com" \
  --member="serviceAccount:$SERVICE_ACCOUNT_EMAIL" \
  --role="roles/iam.serviceAccountUser"
```

## 3. Configurar Workload Identity Federation

```bash
POOL=github
PROVIDER=github
REPOSITORY=thiagoyudji/bratatouille-gestao-backend

gcloud iam workload-identity-pools create "$POOL" \
  --project="$PROJECT_ID" \
  --location=global \
  --display-name="GitHub Actions"

gcloud iam workload-identity-pools providers create-oidc "$PROVIDER" \
  --project="$PROJECT_ID" \
  --location=global \
  --workload-identity-pool="$POOL" \
  --display-name="Bratatouille backend repository" \
  --issuer-uri="https://token.actions.githubusercontent.com" \
  --attribute-mapping="google.subject=assertion.sub,attribute.repository=assertion.repository" \
  --attribute-condition="assertion.repository == '$REPOSITORY'"

gcloud iam service-accounts add-iam-policy-binding "$SERVICE_ACCOUNT_EMAIL" \
  --project="$PROJECT_ID" \
  --role="roles/iam.workloadIdentityUser" \
  --member="principalSet://iam.googleapis.com/projects/$PROJECT_NUMBER/locations/global/workloadIdentityPools/$POOL/attribute.repository/$REPOSITORY"

echo "GCP_WIF_PROVIDER=projects/$PROJECT_NUMBER/locations/global/workloadIdentityPools/$POOL/providers/$PROVIDER"
echo "GCP_SERVICE_ACCOUNT=$SERVICE_ACCOUNT_EMAIL"
```

No GitHub, crie estas repository variables em **Settings → Secrets and variables → Actions → Variables**:

```text
GCP_WIF_PROVIDER
GCP_SERVICE_ACCOUNT
```

Use exatamente os valores impressos pelos dois `echo` acima.

## 4. Secrets da aplicação no GitHub

Em **Settings → Secrets and variables → Actions → Secrets**, configure:

```text
DATABASE_URL=jdbc:postgresql://host:5432/database?sslmode=require
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=<database-password>
AUTH_JWT_SECRET=<valor-gerado-localmente>
```

```bash
openssl rand -base64 48
```

Não commite os valores reais nem os imprima nos logs do workflow.

## 5. Dependências

O repositório possui Dependabot para atualizações do Maven e das GitHub Actions. O workflow `dependency-review.yml` analisa alterações de dependências em PRs e falha quando uma vulnerabilidade de severidade alta ou crítica é introduzida.

## 6. Executar

Após o merge na `master`, o workflow `Deploy production` executa automaticamente.

Cada execução publica uma imagem versionada pelo SHA do commit e atualiza o serviço `bratatouille-backend-prod` na região `southamerica-east1`.
