# Roadmap consolidado — 3 repositórios e decisões pendentes

**Status:** proposta para aprovação; docs-only, sem implementação, merge ou deploy.
**Repositórios:** `bratatouille-frontend` (site), `bratatouille-gestao-backend` (núcleo de negócio), `bratatouille-gestao-frontend` (ERP). `bratatouille-backend` isolado não é autoridade observada para operações atuais.

## Mapa de jornadas

```text
Site / Cadastro interno / Mercado Livre futuro
      |
      v
Pedido único (ID ERP, origem, cliente, itens e preço authoritative)
      +--> tentativa de pagamento (InfinitePay / Inter / MP opcional)
      |       +--> título -> recebimento -> conciliação -> caixa
      +--> confirmação comercial -> demanda firme
              +--> Trello (integração eventual idempotente)
              +--> estoque físico / reservado / apto por lote
                       +--> disponibilidade e plano de produção
                       +--> BOM: ingredientes e embalagens
                       +--> lista de compras sugerida
                       +--> execução real -> custo por lote -> perdas / recuperação de pote
              +--> expedição / rastreio / entrega
      |
      v
Gestão: vista operacional + balancete + indicadores
```

## Estado confirmado versus plano

- Site `bratatouille-frontend`: mocks para identidade e catálogo; checkout sem envio; não pronto para operação real integrada.
- Gestão frontend: venda manual já chama `POST /api/sales-orders` (ADMIN/EMPLOYEE), mas sem ciclo operacional/recebível.
- Backend: pedido precifica PF/PJ e salva snapshot, InfinitePay pode iniciar cobrança, porém não há ingestão multi-canal/fulfillment completo; estoque de oferta comercial é virtual.
- Produção/lotes/custo existem, mas yield `NULL` para receitas iniciais e valores históricos desconhecidos tornam algumas estimativas incompletas.
- Gás por leitura por produção e despesas compartilhadas mensais são requisitos de negócio informados, ainda não implementados como custeio gerencial consolidado.
- Validade dos produtos atuais é seis meses (regra adotada); potes/tampas recuperáveis condicionados a inspeção/sanitização, rótulos descartados.

## Prioridades e dependências

| Ordem | Feature | Por quê | Dependências / PRs coordenadas |
|---|---|---|---|
| P0-1 | Verdade de estoque e segurança/contratos | Não prometer atendimento/custo fictício | BE-002, auditorias |
| P0-2 | Autenticação real no site | Dados e preços PJ mock/localStorage inseguro | SITE-01 + endpoint CUSTOMER |
| P0-3 | Pedido canônico, identidade, origem, estados e idempotência | Base de tudo | OR-01/02 |
| P0-4 | Checkout site -> pedido real e visualização no Gestão | Capturar pedidos de verdade | SITE-02/03, UX-ORDER-01 |
| P1-1 | Demanda firme x estoque físico x reserva x faltas de potes | Priorizar produção | PL-02/03, lotes |
| P1-2 | BOM e necessidade de compra por pedido | Avisar faltas reais | PL-04/05 + histórico yield |
| P1-3 | Recebíveis B2B, contas a pagar e fluxo de caixa unificado | Balancete confiável | Financeiro + Payments |
| P1-4 | Gás por produção e despesas mensais rateadas | Custo por pote e fechamento | Costs/Payables |
| P1-5 | Trello criado após pedido aceito, com retry | Retirar lançamento manual redundante | INT-01 + OR-01 |
| P1-6 | Controle por lote, perdas e recuperação | Disponibilidade/validade confiáveis | INV-BE-03/04 e doc 09 |
| P2-1 | Loggi/Correios, coleta e frete | Facilitar entrega | Fulfillment e contratos externos |
| P2-2 | Banco Inter cobranças + conciliação, Mercado Pago opcional | Vários meios e watcher | Recebível unificado |
| P2-3 | Mercado Livre como canal seller | Vender em marketplace | OR-01, SKU mapping e OAuth |
| P2-4 | Rentabilidade por produto, cliente e lote | Gestão e planejamento avançados | Custo/recebimentos/lotes confiáveis |

As ordens P1/P2 podem mudar pelo valor de negócio. **Não começar pelo Trello antes de haver transição comercial confiável**: evita spam/duplicação de cards.

## Design sem overengineering

- Monólito modular; sem introduzir Kafka, CQRS ou serviços distribuídos sem evidência.
- API authoritative para preço PF/PJ, estoque, custo, recebimento, roles e estados; sites são clientes.
- `Order` canônico com source/externalId, transições independentes; não duplicar modelos Site vs Gestão vs Mercado Livre.
- Adapters por provedor de pagamento/logística somente quando houver caso/conta habilitados.
- Idempotência e outbox durável para integração externa quando a primeira integração for aprovada, sem infraestrutura adicional.
- Migrations incrementais Flyway, eventuais dados históricos em migração separada/opt-in.
- UI/Backend PRs cruzadas referenciam contrato OpenAPI e deploy compatível.
- Nunca alterar saldo de produção sem operação real confirmada; simulação não reserva nem compra.
- Handoffs em `docs/research/handoffs/<TASK_ID>/00_SUMMARY.md` com testes efetivamente executados.

## Decisões para a próxima conversa (ainda abertas)

1. **Trello:** criar card após pedido confirmado comercialmente (B2B a prazo) e após pagamento verificado (site checkout), ou em fase anterior?
2. **Estoque:** quando reservar os potes para um pedido confirmado? Admitir reserva parcial? Como tratar entrega parcial?
3. **Rendimento:** sugerir consumo por pote só após quantas produções comparáveis? Quem aprova a referência?
4. **Financeiro:** qual conta principal e provedor preferido para PF, PJ primeira compra, PJ recorrente? Mercado Pago será utilizado?
5. **Gás/contas:** unidade do medidor, divisão exata de água/luz/aluguel, forma de repartir custos indiretos entre produções.
6. **Banco/boletos:** começar só com cadastramento manual e conciliação ou emissão por API depois de habilitar credenciais?
7. **Mercado Livre:** escopo inicial somente importar pedidos ou também publicar/sincronizar anúncios?
8. **Validade/recuperação:** como registrar inspeção e higienização de pote e tampa antes de retornar ao inventário?

Decisões abertas não são impedimento para **documentar**, mas devem ser resolvidas antes do respectivo PR de implementação. 
