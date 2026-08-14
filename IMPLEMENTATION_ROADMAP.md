# Roadmap de consolidação do backend

Este documento preserva o diagnóstico realizado em 2026-08-05. Ele é histórico e não deve ser lido sozinho como retrato atual do sistema. Pendências vigentes devem ser mantidas em `docs/PENDENCIAS_TECNICAS.md` e decisões de negócio em `docs/REGRAS_NEGOCIO_CADASTROS_E_COMPRAS.md`.

As novas regras de cadastros, compras e preços registradas em 2026-08-14 estão consolidadas em `docs/REGRAS_NEGOCIO_CADASTROS_E_COMPRAS.md` e devem ser tratadas como decisão de negócio antes das etapas de contrato e persistência relacionadas.

## Estado inicial observado

- O worktree contém uma reestruturação extensa ainda não consolidada.
- Antes da etapa 1, a aplicação principal usava `spring.jpa.hibernate.ddl-auto=create-drop` em conjunto com Flyway.
- Antes da etapa 1, existia somente uma migration parcial para metadados de pagamento, que pressupunha a existência de `sales_orders`.
- O webhook da InfinitePay é público e não possui assinatura criptográfica documentada; o backend confirma o pagamento via `payment_check`, valida valor, invoice/NSU e evita reutilização de transação.
- A criação do checkout reserva estoque antes da chamada externa e não possui recuperação explícita para falha ou timeout.
- Permanecem dois fluxos de bootstrap administrativo, incluindo um controller temporário.
- O OpenAPI não declara Bearer Security nem as respostas padronizadas de erro nas operações.
- As APIs interna e externa ainda compartilham parte da superfície `/api/**`; a separação completa de superfícies continua pendente.
- Pedidos externos não estão vinculados de forma suficiente à identidade autenticada.
- Listagens e cálculos usam várias consultas não paginadas e precisam de revisão de fetch/N+1.

## Ordem de implementação registrada na auditoria

### 1. Banco e Flyway

Objetivo: tornar o schema reproduzível, versionado e seguro para ambientes persistentes.

- [x] Confirmar se existe banco com dados reais: não existe.
- [x] Confirmar se a migration V1 já foi aplicada em algum ambiente compartilhado: não foi aplicada.
- [x] Definir estratégia de baseline: substituir a V1 incompleta por uma baseline integral.
- [x] Criar migration para o schema completo, constraints e índices necessários.
- [x] Manter configuração de teste separada e validar seu schema via Flyway.
- [x] Usar `ddl-auto=validate` em ambiente persistente.
- [x] Validar criação a partir de banco vazio; upgrade não se aplica porque não existe banco persistente nem V1 aplicada.

### 2. Segurança e reconciliação do webhook InfinitePay

- [ ] Confirmar o mecanismo oficial de autenticação/assinatura do provedor — aguardando documentação ou credencial privada a ser obtida pelo proprietário.
- [x] Confirmar ativamente o pagamento via `payment_check` antes de abrir a transação que altera o pedido.
- [x] Validar valor, pedido, invoice/NSU e transação.
- [x] Implementar idempotência com constraint única para `transaction_nsu` e lock pessimista do pedido.
- [x] Cobrir duplicidade, adulteração, pagamento não confirmado e reutilização de transação.

Pendência registrada em 2026-08-05: a documentação pública consultada não descreve assinatura do webhook e recomenda confirmação ativa via `payment_check`. Como pode existir documentação privada da conta, uma eventual validação criptográfica será adicionada antes do `payment_check` quando esse material for fornecido; nenhuma assinatura será presumida.

### 3. Ciclo de checkout e reserva de estoque

- [ ] Definir estados e transições do checkout.
- [ ] Definir comportamento para falha, timeout, retry e expiração.
- [ ] Evitar pedido duplicado por retry/clique duplo.
- [ ] Garantir liberação única de estoque em cancelamento/expiração.
- [ ] Testar rollback e ausência de persistência parcial.

Ponto de parada registrado em 2026-08-05. Antes de implementar esta etapa, o proprietário deve decidir:

1. Se erro definitivo da criação do checkout, como HTTP `4xx`, deve cancelar o pedido e liberar imediatamente o estoque reservado.
2. Se timeout ou HTTP `5xx` deve manter o pedido em estado intermediário, como `CHECKOUT_UNCERTAIN`, sem liberar estoque, e qual será o prazo de expiração dessa reserva.
3. Se o contrato público pode exigir `Idempotency-Key`, gerado pelo frontend e persistido com constraint única, para impedir pedidos duplicados por retry ou clique duplo.

Recomendação técnica registrada: liberar estoque em falha definitiva; manter a reserva em resultado incerto até reconciliação ou expiração; exigir uma chave de idempotência fornecida pelo cliente.

### 4. Bootstrap e configuração por ambiente

- [ ] Remover o controller temporário.
- [ ] Manter um único mecanismo de bootstrap, condicionado por configuração/profile.
- [ ] Tornar o bootstrap concorrente seguro.
- [ ] Remover segredo JWT e credenciais inseguras como defaults de produção.
- [ ] Desabilitar logs DEBUG e SQL fora do ambiente local.

### 5. Identidade do cliente e autorização de pedidos

- [ ] Derivar PF/PJ do perfil autenticado no fluxo externo.
- [ ] Vincular pedido ao cliente preservando snapshots necessários.
- [ ] Definir comportamento de guest.
- [ ] Impedir acesso de cliente a pedidos de terceiros.

### 6. Contrato OpenAPI

- [ ] Declarar Bearer Security e operações públicas.
- [ ] Declarar respostas `400/401/403/404/409/422/500` conforme o padrão existente.
- [ ] Revisar campos obrigatórios e códigos HTTP de criação.
- [ ] Documentar requisitos do webhook.
- [ ] Validar e gerar o contrato no build.

### 7. Separação das superfícies interna e externa

- [ ] Definir estratégia de compatibilidade para paths existentes.
- [ ] Atualizar primeiro o OpenAPI.
- [ ] Separar autorização e rotas internas/externas.
- [ ] Validar consumidores antes de remover paths antigos.

### 8. Paginação, consultas e concorrência

- [ ] Paginar listagens operacionais crescentes.
- [ ] Filtrar períodos financeiros no banco.
- [ ] Revisar fetch dos agregados antes dos mappers.
- [ ] Medir e eliminar N+1 relevante.
- [ ] Testar criação concorrente de estoque e estoque vendável.

## Regras de execução

- Não iniciar uma etapa com decisão de negócio ou estado de banco em aberto.
- Não alterar contrato público silenciosamente.
- Não editar migration já aplicada.
- Para cada etapa: registrar fluxo atual, invariantes, transação, autorização, plano, testes e riscos residuais.
- Atualizar este documento ao concluir ou bloquear cada etapa.

## Validações executadas

- Em 2026-08-05, o Flyway validou e aplicou a baseline V1 em banco H2 vazio durante `TestDatabaseSeederIT`.
- O Hibernate iniciou com `ddl-auto=validate`, sem divergência entre a baseline e as entidades mapeadas.
- Compilação de produção e testes concluída com Java 21.
- Resultado da suíte: 41 testes executados, 40 aprovados e 1 falha preexistente em `FinancialClosingServiceTest.getClosedSummaryRejectsMissingClosing`; o teste espera `IllegalArgumentException`, enquanto o serviço lança `NoSuchElementException`. A falha não percorre banco nem foi causada pela baseline.
- Etapa 2: 10 testes de integração de webhook, checkout, dashboard e relatórios aprovados; 2 testes do client `payment_check` aprovados; execução adicional do webhook após adequação da resposta de retry com 7 testes aprovados.

## Retomada histórica

O trabalho foi pausado por solicitação do proprietário em 2026-08-05, após a implementação da baseline e da reconciliação InfinitePay via `payment_check`. A assinatura privada do webhook continua pendente apenas se a documentação/credencial do provedor exigir esse mecanismo; não deve ser tratada como pré-condição para o `payment_check` sem evidência.
