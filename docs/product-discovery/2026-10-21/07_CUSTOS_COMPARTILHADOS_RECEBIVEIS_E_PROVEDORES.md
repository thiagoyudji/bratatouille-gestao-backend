# Discovery — gás por produção, contas mensais, boletos e provedores

**Status:** proposta para discussão, **não implementada**. Dados do negócio declarados em 2026-10-21.

## Custos de produção e rateio de espaço

### Decisões operacionais descritas

- **Gás:** medição via relógio/medidor **por produção**; guardar leitura anterior/posterior, unidade de leitura, consumo e preço por unidade (com fonte e período). Não presumir m³, kg ou kWh até conferir o medidor e a fatura.
- **Água e eletricidade:** despesas mensais compartilhadas com outro produtor que ocupa o espaço. O rateio costuma ser aproximadamente 50/50, mas deve ser **configurável** e auditável por período; não assumir divisão fixa perpetuamente.
- **Aluguel:** despesa recorrente, com política real de quem paga e divisão; não inferir que o aluguel também seja meio a meio só porque água e luz são.
- **Outros compromissos:** parcelas mensais do acordo com ex-sócio Mathieu até prazo definido, com classificação financeira própria; sem duplicar aluguel/compra no resultado.

### Dois registros, finalidades distintas

1. **Obrigação/pagamento de caixa:** fatura/conta com vencimento, valor total, valor da Bratatouille, valor de terceiros, responsável, pagamento e conciliação bancária.
2. **Apropriação de custo gerencial:** distribuição do valor da Bratatouille a produtos/lotes/produções segundo critério escolhido; não criar uma segunda saída de caixa.

**Custo por lote:** matéria-prima + embalagem + gás efetivamente mensurado + eventual rateio aprovado de energia/água/aluguel/mão de obra. Exibir custo **direto** vs **indireto rateado** e qualidade de dados. Para despesa mensal, oferecer cálculo gerencial a partir de potes produzidos, tempo, horas, volume ou outro direcionador aprovado. Rateio por 2 é divisão de **fatura entre empresas**, e NÃO define alocação dos custos aos lotes.

### Modelos conceituais

- `UtilityBill` ou `Payable`: categoria, competência, vencimento, valor total, fração Bratatouille, conta de origem, pagamentos.
- `SharedExpenseAllocation`: regra e vigência, quota negociada, parceiro externo opcional, revisão manual.
- `GasMeterReading`: produção, readingBefore/After, unit, measuredQuantity, pricePerUnit, version/date, totalCost, eventual revisão.
- `ProductionCostComponent`: lote, fonte do custo, classe DIRECT/ALLOCATED, valor, origem e cobertura.
- `CostAllocationPolicy`: critério de rateio e vigência; fechamento passado não muda sem ajuste explícito.

Reusar `operational_costs`, `productions`, `purchases` quando possível; não criar tabela abstrata só por nome proposto.

## Financeiro: contas a receber e provedores

**Meios possíveis:** InfinitePay já implementado parcialmente; Banco Inter pode ser usado para boleto/cobrança quando habilitado; Mercado Pago é possível porém conta e estratégia ainda **não confirmadas**. Mercado Livre é marketplace/canal, não simplesmente mais um provedor de checkout.

Separar:
- **Pedido** comercial confirmado;
- **Título a receber**: valor devido, vencimento, status, documento e cliente;
- **Tentativa de cobrança**: `provider`, `externalChargeId`, link/boleto/pix, estado, vencimento, eventos;
- **Pagamento verificado**: valor real, data de liquidação, conta, tarifas, identificação bancária;
- **Lançamento financeiro**: conciliação com contas/saldo sem duplicidade.

**Watcher**: preferir webhook autenticado/verificado quando existente; complementar com consulta periódica para eventos atrasados/perdidos. Não concluir "não pagou" apenas porque vencimento passou; tolerar compensação, feriado/fim de semana bancário e títulos contestados. Alertar/gerar cobrança por e-mail somente com regra de tolerância, limite de envio, opt-out/pausa, idempotência, template aprovado e rastreio. Começar com sugestão/revisão manual, automação opcional posteriormente.

**Integração bancária:** primeiro leitura/conciliar extrato e títulos; nunca habilitar pagamentos ou transferências automaticamente como consequência de detectar uma dívida.

## Fluxo de pagamento futuro

Pedido criado -> escolher provedor/canal (política negócio) -> gerar *uma* tentativa idempotente -> verificar status -> conciliar valor líquido/taxas -> atualizar recebível -> atualizar indicadores. Permitidas tentativas subsequentes em outro provedor com rastreio claro, sem cobrança em duplicidade ou conciliação cruzada.

**Um pedido pode ter recebimento parcial**, parcelamento, crédito/estorno. `SalesOrder.paymentStatus` sozinho não modela completamente esse cenário; estender com compatibilidade.

## Testes e riscos

- Gás lido 103,20 para 104,15 unidades => 0,95 unidade real; sem preço não reportar custo zero.
- Conta de luz R$ 200, rateio Bratatouille 50% => obrigação gerencial Bratatouille R$ 100; pagar R$ 200 pela Bratatouille exige registrar R$ 100 a recuperar, não despesa integral definitiva.
- Gas duplicado na produção e novamente rateado da fatura mensal => **bloquear dupla contagem**.
- Conta do Mathieu com 5 pagamentos mensais remanescentes => gerar apenas as obrigações correspondentes à regra aprovada; não considerar automaticamente aluguel ou custo produtivo.
- Parcela quitada após vencimento => não enviar nova cobrança.
- Webhook repetido => um pagamento/liquidação; falha de envio de e-mail não causa nova cobrança financeira.
- Mercado Pago/Inter/InfinitePay retornam eventos diferentes; domínio normaliza resultado sem perder payload/id original para auditoria.

## Decisões pendentes

- Qual unidade mede o relógio do gás e como é determinado o preço?
- Água, luz e aluguel são pagos integralmente por qual parte? Rateio atual exato e periodicidade?
- Custos indiretos devem entrar na **margem gerencial por pote** ou ficar apenas no resultado mensal? Podemos apresentar ambos.
- Qual a preferência de provedor para venda PF, primeira compra PJ e cliente B2B recorrente?
- Qual prazo e política de cobrança antes de enviar e-mail automático?
- O acordo com Mathieu é parcela de compra/cessão, reembolso, obrigação financeira ou outro? A classificação deve refletir os documentos, sem presunção.

**Referências:** `03_CICLO_FINANCEIRO_REEMBOLSOS_E_LANCAMENTOS.md`, `04_PRECIFICACAO_VALOR_ESTOQUE_E_LOTES.md`.
