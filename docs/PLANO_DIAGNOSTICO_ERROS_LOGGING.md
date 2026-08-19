# Plano de diagnóstico claro de erros e logging correlacionado

Documento de implementação futura. O objetivo é eliminar respostas genéricas como `BUSINESS_RULE_VIOLATION` sem contexto e permitir diagnóstico usando apenas o JSON retornado e seu `traceId`.

## Contrato de erro

- Manter `traceId`, `status`, `path`, `method` e `code`.
- Tornar `code` específico e estável por regra, por exemplo `RECIPE_ITEMS_REQUIRED`, `RECIPE_OUTPUT_MUST_BE_FINISHED_PRODUCT`, `RECIPE_INPUT_CANNOT_BE_FINISHED_PRODUCT` e `RECIPE_INPUT_ITEM_INACTIVE`.
- Usar `message` como detalhe público claro e traduzido, sem stack trace ou mensagem técnica bruta.
- Usar `fieldErrors` para campos diretamente relacionados, como `items`, `outputItemId` e `itemId`.
- Adicionar metadados estruturados opcionais apenas quando ajudarem o diagnóstico, como domínio, operação, campo e regra.
- Nunca incluir senha, JWT, payload sensível ou entidades completas.
- Atualizar o schema OpenAPI e os testes do contrato.

Exemplo esperado:

```json
{
  "status": 422,
  "error": "Regra de negócio",
  "code": "RECIPE_ITEMS_REQUIRED",
  "message": "A receita deve conter ao menos um item.",
  "path": "/api/recipes",
  "method": "POST",
  "traceId": "26c294e9801d4087a3db0e70beb618e9",
  "fieldErrors": [
    {
      "field": "items",
      "code": "AT_LEAST_ONE_ITEM",
      "message": "Informe ao menos um item na receita."
    }
  ]
}
```

## Tratamento interno

- Criar uma exceção de negócio estruturada com código estável, mensagem pública, status HTTP, campo opcional e detalhes seguros opcionais.
- Migrar progressivamente os `IllegalArgumentException` dos fluxos de negócio para essa exceção, priorizando receitas, compras, estoque, produção, vendas, financeiro e pagamentos.
- Manter tratamento de transição para exceções antigas, mas impedir que novas regras relevantes retornem somente `BUSINESS_RULE_VIOLATION`.
- Remover a dependência de tradução frágil baseada em busca textual de mensagens em inglês.
- Preservar mensagens genéricas somente para erros não classificados ou inesperados.

## Logging e correlação

- Criar filtro `OncePerRequestFilter` para aceitar `X-Request-Id` ou `X-Correlation-Id` após validação, gerar `traceId` quando ausente, colocar o ID no MDC, devolver o mesmo ID no header `X-Trace-Id` e limpar o MDC ao final.
- Fazer o `ApiErrorResponseFactory` reutilizar o ID criado pelo filtro.
- Logs de erro de negócio devem conter `traceId`, método, rota, status, código estável, exceção, domínio/operação, usuário autenticado quando seguro, campo e identificador de recurso quando disponíveis.
- Erros esperados não devem ter stack trace; erros inesperados devem ter stack trace completo.
- Não registrar body completo, senha, token, dados financeiros sensíveis ou informações pessoais desnecessárias.
- Ajustar logging para remover níveis `DEBUG` excessivos em produção e manter contexto suficiente para investigação.

## Testes

Adicionar ou atualizar testes para:

- cada regra de receita retornar código, mensagem e campo corretos;
- nenhum erro classificado retornar fallback genérico;
- geração e propagação segura de `traceId`;
- mesmo `traceId` no body, header e MDC;
- rejeição ou regeneração de IDs inválidos ou excessivamente longos;
- ocultação de detalhes internos em erros inesperados;
- logs de erro de negócio com código, rota e traceId;
- logs inesperados com stack trace;
- formato consistente para respostas `400`, `401`, `403`, `404`, `409`, `422` e `500`;
- contrato OpenAPI refletir códigos e detalhes novos.

## Critérios de aceitação

- Apenas com o body do erro será possível identificar domínio, regra, campo afetado e traceId.
- O traceId permitirá localizar a requisição completa nos logs.
- Nenhum erro de negócio crítico retornará somente `BUSINESS_RULE_VIOLATION` com mensagem genérica.
- Mensagens públicas permanecerão em português e sem vazamento de informação sensível.
- O contrato e os consumidores serão atualizados explicitamente quando necessário.
- `./mvnw test` e posteriormente `./mvnw verify` passarão com os novos testes.

## Premissas

- O contrato existente será evoluído de forma explícita.
- `message` continuará sendo o detalhe humano principal; códigos e `fieldErrors` serão usados para integração e diagnóstico determinístico.
- Não será introduzida dependência externa de observabilidade apenas para resolver correlação de erros.
- A migração de exceções será feita por domínio, sem refatoração massiva não relacionada.
