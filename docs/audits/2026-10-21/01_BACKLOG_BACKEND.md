# Backlog de engenharia — backend

Ler [00_SUMMARY.md](00_SUMMARY.md). Todos os itens abaixo são **propostos**, salvo indicação explícita de comportamento atual confirmado. Não duplicar pendências já resolvidas ao iniciar um PR; conferir a \`master\` naquele momento.

## P0 — integridade, segurança e verdade operacional

### BE-001 — Matriz de autorização EMPLOYEE vs ADMIN

**Confirmado no código:** \`SecurityConfig.java\` concede \`ADMIN,EMPLOYEE\` aos prefixos \`/api/dashboard/**\`, \`/api/operational-costs/**\`, \`/api/operational-losses/**\` e \`/api/zero-cost-entries/**\`. A regra do projeto determina ADMIN para esses módulos. Também revisar \`/api/sales-reports/**\`, que pode expor valores financeiros.

**Implementar:** tornar a matriz HTTP explícita por método/rota; separar e-commerce, dashboard interno e superfícies administrativas; garantir deny-by-default para endpoints novos; revisar o conjunto de paths efetivos no OpenAPI. Sem usar apenas ocultação da sidebar como autorização.

**Aceite:** IT parametrizado com \`ADMIN/EMPLOYEE/CUSTOMER/anonymous\` verificando \`200/201/403/401\` por endpoint; nenhum \`EMPLOYEE\` lê resumos financeiro-administrativos; dashboard operacional permitido conforme decisão aprovada. Não quebrar autorização de webhook público.

### BE-002 — Corrigir \`currentStock\` falso na simulação por insumos

**Confirmado no código:** \`ProductionSimulationService.simulateInputItem(...)\` executa \`response.setCurrentStock(supplied)\` e calcula faltas a partir de \`required - supplied\`, embora \`supplied\` venha do corpo da requisição. A simulação usa uma quantidade **de cenário**, não saldo verificado.

**Implementar:** ler \`stocks\` real, diferenciar semanticamente \`scenarioQuantity\`/quantidade informada de \`currentStock\` físico; faltas contra saldo real quando o resultado se disser operacional. Não usar \`supplied\` como sinônimo de saldo real. Ajustar contrato com compatibilidade e frontend na mesma entrega.

**Aceite:** estoque físico 0, insumo informado 100000 => nunca apresentar 100000 como \`currentStock\`; sinalizar faltas; simulação continua não mutável; receita com rendimento \`NULL\` não inventa potes. Ver [03_SIMULATION_STOCK_TRUTH.md](03_SIMULATION_STOCK_TRUTH.md).

### BE-003 — Distinguir planejamento executável de cenário hipotético

**Confirmado no código:** cenário e estoque real estão misturados nas respostas; modo por insumos não usa autoridade física. **Decisão de produto pendente:** limites não devem inviabilizar a pergunta legítima "quanto preciso comprar?".

**Proposta:** modos inequívocos:
1. **Disponível no estoque** — usa saldos atuais e aponta capacidade/deficiência verificadas.
2. **Cenário hipotético** — permite qualquer quantidade válida, explicitamente rotulada; nunca afirma posse física.
3. **Meta por potes** — usa rendimento quando conhecido, senão calcula somente embalagens e declara a lacuna.

**Aceite:** contrato documenta modo, origem do saldo, quantidade proposta e confiabilidade do resultado; frontend diferencia visualmente; testes cruzados para valores absurdos, estoque ausente, deficiência e \`NULL\`.

### BE-004 — Contrato de meta por potes consistente com frontend

**Confirmado:** na \`master\` auditada, \`ProductionSimulationRequest\` requer \`inputs\` com \`minItems: 1\`; o método \`simulateFromInputs\` rejeita array vazio. O frontend usa \`inputs: []\` no modo por potes. Existe PR de backend **#31** com mudança prevista — verificar merge antes de executar esta atividade.

**Implementar:** sincronizar contrato, implementação, types gerados, testes e deploy; conferir o retorno em receitas sem rendimento; garantir que \`estimatedTotalCost\` parcial não pareça custo total.

**Aceite:** \`POST\` com \`recipeId\` + \`plannedFinishedQuantity\` funciona somente conforme OpenAPI publicado; \`inputs\` opcionais no modo adequado; retorno inclui aviso de cálculo parcial; CI backend e frontend verdes.

## P1 — fluxo de negócio e consistência financeira

### BE-005 — Checkout resiliente, transição e idempotência

**Confirmado:** \`InfinitePayCheckoutService.create\` cria \`SalesOrder\`, chama gateway externo, depois grava metadados. \`InfinitePayPaymentReconciliationService\` já bloqueia ordem, valida valor e confere transaction NSU — **preservar isso**.

**Risco a confirmar:** falha/time-out de checkout depois do pedido persistido pode deixar um \`PENDING\` sem URL; repetição do cliente pode gerar pedidos distintos; fluxos concorrentes e dupla tentativa precisam testes.

**Implementar após decisão:** chave de idempotência e política de retry, estado técnico de checkout, observabilidade de transições; sem manter transação DB aberta na chamada remota.

**Aceite:** timeout, erro 5xx, retry e clique repetido não geram cobrança/pedido duplicado; webhook atrasado/duplicado permanece idempotente; sem aceitar preço vindo do navegador.

### BE-006 — Concorrência no primeiro saldo de um item

**Confirmado:** \`StockService.getOrCreateWithLock\` usa \`findByItemIdForUpdate\`; se não houver linha, constrói \`Stock\` novo. O bloqueio pessimista só protege linha existente. Há unicidade por \`item_id\` no modelo.

**Risco:** duas compras/entradas simultâneas para item sem saldo podem competir na inserção. Requer teste de banco real para entender falha/retry.

**Implementar:** estratégia segura (upsert ou sincronização consistente/retry controlado) compatível com Spring/JPA e PostgreSQL, sem race no primeiro insert.

**Aceite:** duas entradas concorrentes criam exatamente um saldo correto e dois movimentos rastreáveis; nenhuma perda silenciosa.

### BE-007 — Custo histórico, entradas gratuitas e precisão

**Confirmado:** \`CostService\` divide \`SUM(totalValue)\` por \`SUM(purchaseQuantity + zeroCostQuantity)\`; para acabados, inclui produção e \`zeroCostQuantity\`. Isso reduz o custo médio com saldo inicial gratuito, inclusive quando o custo histórico é **desconhecido**, não necessariamente zero.

**Proposta:** separar "custo comprovado", "custo de aquisição desconhecido" e "entrada intencionalmente gratuita"; refletir cobertura de custo em respostas e dashboards. Preservar rastreabilidade das compras. Não recalcular o passado sem política aprovada.

**Aceite:** importação histórica sem preço não transforma valor faltante em custo real zero; margens/valores parciais identificados; testes para compras posteriores e unidades \`KG/L\`.

### BE-008 — Semântica e integridade das embalagens

**Confirmado:** \`ProductionService.buildProductionItems\` trata embalagem como \`recipeItem.quantity * producedQuantity\` e permite override maior por perdas. \`recipe_items.quantity\` não declara formalmente "por pote" vs "por batelada" no banco/contrato.

**Proposta:** explicitar essa semântica no domínio/contrato e na UI; obrigar contagem inteira de potes, rótulos e lacres; garantir que receita tenha somente embalagens compatíveis, sem inventar rendimento. Revisar padronização de receitas antigas e script operacional opt-in \`docs/sql/link-initial-recipe-packaging.sql\`.

**Aceite:** 20 potes -> 20 potes vazios, 20 rótulos, 20 lacres (quando vinculados); 22 lacres se perda informada; falha por estoque insuficiente faz rollback da produção completa; nenhum SQL aplicado automaticamente em produção.

### BE-009 — Bootstrap legado e controle de provisionamento

**Confirmado:** \`SecurityConfig\` permite \`POST /api/auth/bootstrap/admin\` e \`POST /api/_temp/bootstrap/admin\` anonimamente. \`AuthService.bootstrapAdmin\` bloqueia se \`auth_users.count()>0\`; há controller em pacote \`tempbootstrap\` com comentário para removê-lo após o seed.

**Risco:** duplicidade de superfície, corrida de bootstrap em banco vazio e manutenção acidental de endpoint sensível.

**Proposta:** desabilitar o caminho temporário fora de ambiente controlado, consolidar provisionamento e proteger estado inicial de forma atômica. Validar procedimento de disaster recovery.

**Aceite:** banco inicial/teste possui seed autorizado; ambiente produtivo já inicializado não permite criar ADMIN anonimamente; endpoints de recuperação auditados.

### BE-010 — Ajuste manual de estoque auditável

**Confirmado:** \`StockService.adjustManually\` calcula diferença e registra movimento, mas não recebe motivo nem ator na assinatura. Os movimentos podem não fornecer explicação suficiente para auditoria.

**Proposta:** motivo obrigatório para ajustes críticos, usuário autenticado, timestamp e antes/depois; exibir histórico em UI. Não sobrescrever histórico antigo silenciosamente.

**Aceite:** qualquer ajuste novo é explicável e reversível por movimento compensatório; testes de integridade e controle de acesso.

### BE-011 — Consistência de simulação versus execução

**Confirmado:** \`ProductionService\` e \`ProductionSimulationService\` possuem fórmulas de materiais em fluxos distintos. O modo por insumos calcula \`factor=min(supplied/recipeQuantity)\`; o modo de saída calcula escala por rendimento, e há tratamento especial de embalagens.

**Proposta:** extrair regras puras de quantidade/ref. para domínio compartilhado (sem god service nem abstração artificial), mantendo diferenças declaradas entre simulação e baixa real.

**Aceite:** para receita com rendimento conhecido e mesmas entradas, previsão e baixa têm os mesmos requisitos materiais; testes de arredondamento, potes inteiros, falta, ganho/perda e estoque ausente.

### BE-012 — Integridade de dados e consultas

**Evidência:** \`findAll()\` + mapeamento em services de receitas/produção/vendas/estoque/financeiro; \`SellableStockService.toAdminResponse\` chama \`stockRepository.findByItemId\` para cada item; \`CostService\` faz múltiplas agregações por item.

**Proposta:** medir SQL count, EXPLAIN nas consultas quentes e limites/paginação. Evitar N+1 mediante fetch direcionado/batch/projeção, não \`FetchType.EAGER\` indiscriminado.

**Aceite:** medição antes/depois sob dataset representativo, sem regressão de payload ou heap.

## P2 — padronização e evolução operacional

### BE-013 — Erros e validação de entrada consistentes
Revisar \`IllegalArgumentException\`/\`NoSuchElementException\` para mapear \`ApiErrorCode\` estável; @Valid/Bean Validation para request, mensagens úteis, limites numéricos positivos e tipos. Sem vazar stack trace/dados privados. **Aceite:** testes de JSON HTTP (422/400/404/409 conforme convenção definida) e field errors.

### BE-014 — Tempo, datas e rastreabilidade
\`ProductionService\` usa \`LocalDate.now()\` e \`InfinitePayPaymentReconciliationService\` usa UTC explicitamente. Decidir timezone operacional, origem das datas e tratamento de virada de dia, além de mostrar timezone no relatório. **Aceite:** testes próximos da meia-noite e Clock injetável quando relevante.

### BE-015 — Fechamento financeiro e concorrência
\`FinancialClosingService\` verifica sobreposição antes de salvar; revisar lock/constraint de banco para fechamentos concorrentes. Diferenciar despesa incorrida, paga e recebimento efetivo (o cash flow analisado soma compras/custos, sem receita observada nesse fluxo). **Aceite:** dois fechamentos simultâneos não produzem intervalos sobrepostos; sem contaminar resultado.

### BE-016 — Histórico de receita e versão utilizada na produção
Investigar necessidade de snapshot/versão de receita em produção para que editar a receita não reinterprete lotes históricos. **Aceite:** relatório de produção antiga mantém fórmula e custos capturados na data; nenhuma edição altera evidência passada.

### BE-017 — Segurança de APIs externas e webhooks
Revisar rate limiting/limite de body, logging sem segredos, verificação de origem *quando oferecida pelo provedor*, idempotência transacional e resposta apropriada para retry. A chamada de verificação de pagamento existente é positiva e deve ser preservada. **Aceite:** testes de webhook forjado/repetido e carga de requisições.

### BE-018 — Observabilidade e SLO básico
Métricas de pedidos pendentes sem checkout, falhas de pagamento, movimentações anômalas, custos incompletos e número de produções bloqueadas. Logs com IDs de correlação e sem PII desnecessária. Sem adicionar stack distribuída pesada.

## Organização das implementações

Cada ID => branch e PR focadas, título com ID; mudanças cruzando backend/frontend recebem PRs coordenadas, dependências e matriz de contrato. Registrar evidências reais por tarefa em \`docs/research/handoffs/<TASK_ID>/00_SUMMARY.md\`.

Não tratar este backlog como um pedido de executar todas as mudanças de uma vez. Revalidar todos os achados contra a \`master\` antes de iniciar.
