# Workflow futuro — Estoque fake, pedidos e produção

Documento de planejamento. Nenhum item abaixo deve ser implementado sem revisão do contrato OpenAPI, migrations e consumidores dos três repositórios.

## Decisões fechadas

- O Estoque fake possui somente `active` e `infinite`.
- `active=false` remove o produto do catálogo; `active=true` mantém a publicação.
- `infinite=true` permite venda; `infinite=false` mantém o produto visível, mas fora de estoque.
- O catálogo público recebe apenas fake stock e dados comerciais. Estoque físico é exclusivo do dashboard.
- O pedido não é bloqueado por falta de produto ou insumo.
- A baixa física é uma ação manual, idempotente e exclusiva de `ADMIN`.
- A baixa retira somente a quantidade física disponível e registra o restante como falta.
- A falta não altera o status visual do cliente.
- Pedido PF pode aguardar confirmação da InfinitePay; pedido PJ pode seguir por crédito/boleto de prazo longo.
- Pedido cancelado, recusado ou expirado antes da baixa não consome estoque.
- Alteração de pedido exige confirmação, motivo, recálculo do valor e log antes/depois.
- Depois da baixa física, a edição comum do pedido é bloqueada.
- A baixa considera somente produtos finais adquiridos e publicados no Estoque fake.

## Fase 1 — Contrato e agregado de Estoque fake

1. Remover do contrato e do domínio `availableQuantity` e `outOfStock`.
2. Renomear `enabled` para `active` de forma coordenada ou definir estratégia de compatibilidade.
3. Separar resposta pública de catálogo e resposta administrativa com estoque físico.
4. Garantir autorização `ADMIN` para escrita administrativa.
5. Atualizar dashboard e e-commerce, tipos gerados, testes e documentação.

## Fase 2 — Pedido e saída física

1. Criar caso de uso administrativo “Confirmar saída do estoque”.
2. Registrar uma única saída por pedido, com usuário, timestamp e itens.
3. Para cada item, calcular solicitado, baixado e faltante.
4. Baixar apenas a quantidade existente no estoque físico.
5. Não alterar o status público por falta.
6. Criar resposta administrativa com faltas agrupadas por produto final.
7. Garantir transação, lock pessimista, idempotência e movimento de estoque auditável.

## Fase 3 — Faltas, receita e compra estimada

1. Para cada produto final faltante, localizar a única `Recipe` ativa.
2. Se não houver receita, registrar aviso “receita não cadastrada” sem bloquear a baixa física.
3. Calcular insumos líquidos pela regra de quantidade e rendimento.
4. Comparar necessidade com estoque de insumos comprado.
5. Mostrar insumos disponíveis, faltantes, quantidade estimada de compra e custo estimado.
6. Usar custo médio ponderado na projeção, preservando preços reais de cada compra.
7. Incluir gás por produção e luz/aluguel como despesas mensais do fechamento.
8. Não criar produção automaticamente.

## Fase 4 — Alteração e auditoria de pedidos

1. Permitir edição somente antes da saída física.
2. Exigir confirmação visual e motivo.
3. Recalcular automaticamente totais e margens.
4. Registrar log append-only com usuário, data, motivo, estado anterior e posterior.
5. Após saída física, direcionar qualquer correção para operação de ajuste específica.

## Fase 5 — Dashboard de gestão

1. Criar aba “Estoque fake”, exclusiva de `ADMIN`.
2. Exibir `active`, `infinite`, produtos fora de estoque, estoque físico e saída pendente.
3. Exibir botão “Confirmar saída do estoque”.
4. Exibir faltas por produto final, receitas, insumos e compra estimada.
5. Exibir logs e motivos de alterações.
6. Integrar despesas de luz/aluguel aos períodos de abertura e fechamento.

## Fase 6 — E-commerce

1. Consumir somente catálogo fake e preços autorizados pelo backend.
2. Ocultar produtos inativos.
3. Exibir produtos ativos e não infinitos como fora de estoque.
4. Não exibir estoque físico, faltas internas, custo ou produção necessária.
5. Priorizar conversão: CTA claro, checkout curto, confiança, recompra e comunicação comercial.
6. Remover a criação direta de pagamento do navegador e usar o fluxo oficial do backend.

## Questões técnicas para a sessão de implementação

- Nome definitivo do novo campo `active` e compatibilidade temporária com `enabled`.
- Nome e contrato da resposta administrativa de saída física.
- Política para item sem receita: aviso persistido ou apenas resposta da operação.
- Modelo de log: tabela própria ou evento/auditoria dentro do agregado do pedido.
- Critério exato de rateio do gás por produção.
