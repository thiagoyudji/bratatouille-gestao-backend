# Testes e Revisão

## Princípio

O projeto ainda pode ter cobertura pequena. Isso não justifica criar suíte cerimonial nem ignorar regressões. O teste deve ser proporcional ao risco da mudança.

## Prioridade

1. regras puras de domínio e cálculos financeiros;
2. compra, estoque, produção e custo;
3. transações e rollback;
4. autenticação e autorização;
5. repositories com queries customizadas;
6. delegates/mapeamento somente quando agregarem valor real.

## Tipos de teste

### Unitário sem Spring

Preferido para:

- calculadores;
- validações de domínio;
- rateios;
- médias e custos;
- transformação de estado pura.

### Integração com Spring/JPA

Usar para:

- transações;
- repositories;
- constraints;
- fetches;
- persistência de agregados;
- segurança de endpoints.

### Teste de API

Usar quando houver:

- contrato crítico;
- autenticação/autorização;
- validação HTTP;
- serialização relevante;
- regressão de endpoint.

## Regras

- toda correção de bug relevante deve ganhar teste de regressão;
- não mockar classes de domínio puras;
- evitar teste que apenas repete implementação;
- não buscar cobertura percentual artificial;
- fixtures devem ser legíveis e mínimas;
- cenários financeiros precisam conferir valores exatos e arredondamento;
- testes de transação devem validar ausência de persistência parcial.

## Revisão final do agente

Antes de concluir, revise:

- contrato OpenAPI alterado ou preservado;
- compatibilidade de enums;
- transação e rollback;
- queries e N+1;
- autorização;
- dinheiro e arredondamento;
- datas e timezone;
- logs e dados sensíveis;
- imports e código morto;
- nomes alinhados ao domínio;
- testes executados e eventuais limitações.

## Formato da resposta final

Seja conciso e técnico. Informe:

- o que mudou;
- decisões relevantes;
- testes executados;
- riscos ou pendências reais.

Não escreva tutorial básico nem descrição linha a linha do código.
