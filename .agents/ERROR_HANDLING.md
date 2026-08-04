# Tratamento de Erros

## Objetivo

Fornecer um padrão único, previsível e extensível, sem `if/else` central escolhendo manualmente cada erro.

## Direção arquitetural

- exceções de domínio e aplicação possuem semântica clara;
- um handler global converte exceções em resposta HTTP;
- cada tipo de exceção carrega ou resolve seu código de erro;
- delegates e controllers não repetem `try/catch`;
- detalhes internos ficam em logs, não na resposta pública.

## Estrutura recomendada

Conceitos sugeridos, adaptados aos padrões reais do projeto:

- `ApplicationException` ou `DomainException` como base quando houver ganho real;
- exceções específicas por categoria ou cenário;
- `ErrorCode` estável;
- `ApiError` definido no OpenAPI;
- `@RestControllerAdvice` para mapeamento;
- handlers separados por tipo quando isso remover condicionais e melhorar manutenção.

Não é obrigatório criar uma subclasse para cada mensagem. Crie tipos específicos quando o tratamento HTTP, o código ou a semântica forem distintos.

## Categorias mínimas

- recurso não encontrado → `404`;
- conflito/invariante de estado → `409` ou `422`, conforme contrato;
- validação de entrada → `400`;
- autenticação inválida → `401`;
- autorização insuficiente → `403`;
- erro inesperado → `500` com mensagem pública genérica.

## Resposta de erro

Deve possuir somente campos úteis e estáveis, conforme OpenAPI. Exemplo conceitual:

- timestamp;
- status;
- code;
- message;
- fieldErrors, quando aplicável;
- path;
- correlationId, caso seja adotado.

## Logging

- erro esperado de negócio não precisa de stack trace em nível ERROR;
- falha inesperada deve registrar stack trace;
- nunca logar senha, JWT ou dados sensíveis;
- evitar duplicar o mesmo stack trace em múltiplas camadas;
- mensagem deve incluir contexto suficiente para investigação, sem despejar entidades inteiras.

## Migração do código atual

`IllegalArgumentException` existente não deve ser substituída em massa sem estratégia. Ao tocar um fluxo:

1. verificar sua semântica;
2. migrar para exceção adequada;
3. preservar resposta esperada pelo contrato;
4. adicionar teste do mapeamento.
