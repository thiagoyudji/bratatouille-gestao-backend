# Bratatouille — auditoria técnica e roadmap (backend)

**Data:** 2026-10-21
**Repositório:** \`thiagoyudji/bratatouille-gestao-backend\`
**Baseline lida:** \`master\` no commit \`fb124e13649d85cfd105d2597c7dee598a2e337e\`.
**Estado:** diagnóstico e backlog; **não** é evidência de implementação concluída.

## Método e escopo

Leitura do código fonte remoto no GitHub, documentos \`AGENTS.md\`, \`DEVELOPMENT_RULEBOOK.md\`, \`docs/PENDENCIAS_TECNICAS.md\`, contratos OpenAPI, serviços de produção/simulação, estoque, custos, compras, receitas, vendas, pagamento, segurança, fechamento financeiro e estrutura de testes/CI. Inspeção estática **amostral**, não auditoria de todas as linhas. Não houve execução local do Maven, acesso a segredos, consulta ao Supabase, teste de carga ou avaliação da infraestrutura produtiva nesta auditoria.

Classificação:
- **Confirmado no código:** trecho e fluxo encontrados na \`master\` analisada.
- **Risco a confirmar:** comportamento plausível que exige teste, consulta ou investigação.
- **Melhoria proposta:** mudança de produto ou engenharia ainda não decidida.

Este conjunto complementa, sem substituir, as regras do \`AGENTS.md\`, o rulebook e as pendências técnicas existentes.

## Resultado executivo

Há módulos bem separados por domínio, OpenAPI contract-first, controle transacional de estoque, bloqueio pessimista para saídas e testes de integração relevantes. O ponto mais urgente é **confiabilidade operacional e autorização**, não introduzir novas tecnologias ou grandes padrões.

| Ordem | ID | Prioridade | Achado | Evidência |
|---|---|---|---|---|
| 1 | BE-001 | P0 | EMPLOYEE alcança APIs administrativas que deveriam ser ADMIN | \`auth/security/SecurityConfig.java\`; \`docs/PENDENCIAS_TECNICAS.md\` |
| 2 | BE-002 | P0 | Simulação por insumos expõe quantidade digitada como \`currentStock\` | \`productionSimulation/service/ProductionSimulationService.java\` |
| 3 | BE-003 | P0/P1 | API e UX não separam saldo real, disponibilidade hipotética e plano executável | Mesmo service; OpenAPI de simulação |
| 4 | BE-004 | P1 | Contrato do planejamento por potes está em PR separado e a \`master\` ainda exige \`inputs\` | OpenAPI de simulação; PR #31 |
| 5 | BE-005 | P1 | Criação do checkout persiste pedido antes de chamar gateway externo | \`payment/service/InfinitePayCheckoutService.java\` |
| 6 | BE-006 | P1 | Estoque inicial sem linha ainda exige análise de concorrência na criação | \`stock/service/StockService.java\`, \`stock/repository/StockRepository.java\` |
| 7 | BE-007 | P1 | Custo médio com entradas sem custo pode ocultar incompletude histórica | \`cost/service/CostService.java\` |
| 8 | BE-008 | P1 | Baixa por produção mistura captura de consumo, regra de embalagem, custo e persistência | \`production/service/ProductionService.java\` |
| 9 | BE-009 | P1 | Segurança e ciclo de vida do bootstrap temporário merecem remoção/controlos | \`tempbootstrap/controller/TempBootstrapController.java\`, \`auth/service/AuthService.java\` |
| 10 | BE-010 | P2 | Consultas/serializações em coleção têm potencial de N+1 e custos de lista não paginada | Services e repositories de receitas, vendas, estoque e custos |
| 11 | BE-011 | P2 | Padronização de erros, testes, métricas e auditoria de ajustes | \`stock/service/StockService.java\`, \`common/error/**\` |

**Documento detalhado:** [01_BACKLOG_BACKEND.md](01_BACKLOG_BACKEND.md).
**Padrões sugeridos:** [02_ENGINEERING_STANDARDS.md](02_ENGINEERING_STANDARDS.md).
**Reprodução conceitual do bug da simulação:** [03_SIMULATION_STOCK_TRUTH.md](03_SIMULATION_STOCK_TRUTH.md).

## Atenção ao inventário inicial

O último inventário informado ao projeto registrou 31 saldos, 29 entradas/movimentações positivas, 68 produtos acabados e 9 receitas. Entre ingredientes, as entradas positivas iniciais foram tomate seco, alcaparra, xerém de caju e açúcar. **Isso não prova que o saldo atual permaneça igual**: consultar \`stocks\` para saber a situação presente.

Não assumir que itens sem registro de \`stocks\` têm saldo positivo. Tampouco usar entrada inicial sem custo como preço de mercado.

## Dependências e sequência sugerida

1. P0: corrigir matriz de autorização + testes HTTP de papéis.
2. P0: corrigir verdade do estoque na simulação + mensagens explícitas de cenário hipotético, em PRs coordenadas de backend/frontend.
3. Confirmar semântica dos dois modos (\`INPUTS\` hipotético vs \`TARGET\` planejado), status da PR #31 e alinhar contrato gerado.
4. Fortalecer idempotência do checkout e tratamento do retorno do gateway.
5. Revisar modelo de embalagem/receita e integridade/concor­rência do estoque.
6. Melhorar qualidade (testes de borda, N+1, limites de consulta, logging/auditoria).
7. Só então evoluir features comerciais/financeiras maiores.

Cada item deve virar PR pequena e focada com testes. Sem manipular dados históricos ou migrations já aplicadas.

## Gates de qualidade

- O PR documenta regra atual, regra desejada, riscos e consumidores impactados.
- Testes unitários e/ou integração passam em \`./mvnw test\` em ambiente controlado, com OpenAPI gerado e fixtures coerentes.
- Mudanças de contrato incluem sincronização de tipos/frontend e verificação das rotas.
- Testes de autorização, concorrência e rollback são obrigatórios para operações sensíveis.
- Evidências por tarefa em \`docs/research/handoffs/<TASK_ID>/\`, com \`00_SUMMARY.md\`, comandos e resultados reais.
- Nada de declarar produção validada só porque o PR compilou. Não executar SQL em produção sem plano e autorização.

## Referência para agentes

Ler \`AGENTS.md\`, \`DEVELOPMENT_RULEBOOK.md\` e os documentos por domínio em \`.agents\`. **Não alterar a arquitetura de monólito modular** sem razão comprovada. As prioridades aqui são propostas de backlog, não autorização para disparar tudo de uma vez.
