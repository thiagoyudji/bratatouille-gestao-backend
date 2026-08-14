# Deploy de produção no Render

O workflow `.github/workflows/deploy-prod.yml` executa os testes e dispara o deploy do serviço Render após `push` na `master`. Também é possível iniciar o fluxo manualmente em **Actions → Deploy production to Render → Run workflow**.

## Configuração

No Render, abra o serviço em **Settings → Deploy Hook** e copie a URL.

No GitHub, crie o secret de repositório:

```text
RENDER_DEPLOY_HOOK_URL=<URL do Deploy Hook do Render>
```

As variáveis da aplicação, incluindo `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD` e `AUTH_JWT_SECRET`, permanecem configuradas em **Render → Environment**. O workflow não envia esses valores para o GitHub nem para o Render.

Pull requests executam somente os testes. O deploy só é disparado após os testes passarem.
