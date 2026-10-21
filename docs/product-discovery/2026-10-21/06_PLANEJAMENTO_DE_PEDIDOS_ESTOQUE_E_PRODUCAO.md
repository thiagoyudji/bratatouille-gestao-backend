# Discovery — demanda consolidada, estoque real, produção e plano de compras

**Status:** design para discussão, sem algoritmo implementado.
**Objetivo:** o ERP indica o que falta para atender pedidos ativos, mostra o plano de produção e calcula materiais sem inventar rendimentos.

## História de usuário

"Tenho 10 potes de geleia de melão aptos a venda. Recebi pedidos confirmados somando 15 potes. Quero saber que faltam 5 potes; o que posso produzir com meu estoque atual e o que devo comprar."

**Primeira resposta inequívoca:** `shortFinishedUnits = max(0, firmDemand - usableUnallocatedFinishedStock - confirmedIncomingStock)`.
No exemplo, ignorando reservas/entradas pendentes, `max(0,15-10)=5 potes`.

**Mas** informar "faltam 487 g de melão" requer:
- receita com `yieldQuantity` validado **ou** consumo por pote observado em lotes comparáveis e uma estimativa declarada;
- estoque físico de melão consultado, com unidade e saldos confiáveis;
- estoque de todos os ingredientes e embalagens, não apenas melão;
- quantidade a produzir de 5 potes, arredondamento da formulação, possíveis perdas e tamanho do pote.

Se todas as 9 receitas continuam com `yieldQuantity=NULL` e sem histórico suficiente, **não é válido produzir 487 g como número certo**. Mostrar `Rendimento indisponível: falta referência ou histórico de produção` e ainda calcular embalagens por pote quando estão vinculadas.

## Três conceitos separados

1. `PhysicalStock`: saldo físico real `stocks` + informação de validade por lote quando modelada.
2. `CommercialDemand`: pedidos firmes/confirmados que consomem obrigação de atendimento; pedidos em carrinho/checkout não consumam demanda firme antes do gatilho definido.
3. `PlanningScenario`: plano hipotético não reserva, não realiza compra e não movimenta estoque.

Outros dados necessários: ordem de prioridade de pedidos, data prometida, expedições parciais, estoque reservado, lote FEFO quando rastreável, recebimento de compras confirmadas e saídas não comerciais.

## Pipeline conceitual

1. **Selecionar pedidos** firmes ativos e não concluídos/cancelados. Consolidar demanda por `finishedItemId`, período e data de entrega.
2. **Consultar saldos** reais de acabados, reservas e lotes aptos para expedição (não confundir com `sellable_stocks.infinite`).
3. **Apurar déficit** por produto: demanda ainda não atendida menos físico apto não reservado e entradas efetivamente confirmadas.
4. **Planejar produção** dos potes faltantes, considerando receita vigente, rendimento e histórico comparável. Rendimento desconhecido => lacuna explícita.
5. **Explodir materiais (BOM)**: ingredientes por rendimento conhecido + embalagens por pote; consolidar necessidades por insumo entre todas receitas, antes de subtrair o estoque dos ingredientes uma única vez (evitar dupla utilização em pedidos concorrentes).
6. **Confrontar disponibilidade física** de ingredientes/embalagens, consumos reservados e pedidos de compra previstos. Identificar gargalos.
7. **Gerar lista sugerida de compras** por quantidade/unit, custo conhecido/desconhecido, fornecedor e prazo quando disponível. Nenhuma compra ou produção é criada automaticamente por essa etapa.
8. **Apresentar timeline** de atendimento por pedido com status, faltas, simulações e operação seguinte.

## Restrições e trade-offs

- `stocks` é por item, `lots` registra quantidade originalmente produzida; alocação por lote e saldo remanescente dependem de melhoria já planejada. Até lá, **rotular** capacidade física agregada sem prometer disponibilidade por vencimento/reserva.
- Não reutilizar `currentStock` de simulação manual se calculado a partir de input do usuário; corrigir verdade de estoque (BE-002) primeiro.
- Se várias encomendas disputam 10 potes, devem somar demanda/reservas sem alocar o mesmo pote duas vezes. Se a política reservar no pagamento ou na confirmação B2B, formalizar.
- Receita deve manter revisão histórica para comparar simulação e execução e não reescrever o passado.
- Não usar `double` para quantidade/custo; unidade-base G/ML/UN, KG/L só na API ou UI com conversão segura.
- Simulação não autoriza automaticamente compra, produção, expedição, pagamento ou cancelamento.

## Resposta futura de API (conceitual, NÃO contrato existente)

```json
{
  "itemId": 55,
  "demandConfirmedUnits": 15,
  "finishedUsableUnits": 10,
  "plannedProductionUnits": 5,
  "materialCoverage": "INCOMPLETE_REFERENCE_YIELD",
  "materialRequirements": [],
  "stockDataAsOf": "timestamp",
  "warnings": ["Não há rendimento suficiente para estimar ingredientes"]
}
```

Quando há referência/histórico confiável:
- item por item: `required`, `physicalAvailable`, `reserved`, `toBuy`, `unit`, `origin`, `estimateReliability`;
- demanda total e por pedido, tempo prometido, gargalo, custo parcial quando necessário;
- preço de venda e receita potencial em seção **distinta**, nunca fingir faturamento ou caixa.

## Testes fundamentais

- 2 pedidos (7+8 potes), físico 10 => faltam 5.
- 2 receitas compartilham 1 insumo; não alocar a mesma grama duas vezes.
- Compra pendente não conta como entregue.
- 15 potes solicitados, 10 virtuais `infinite` e 0 físico => déficit físico 15.
- Cancelamento/estorno reduz demanda e libera reserva.
- Estoque muda depois de simular; produção revalida em transação e não permite saldo negativo.
- Rendimento `NULL` retorna quantidade de potes faltantes, não quantidade inventada de melão.
- Expedição parcial muda apenas demanda ainda pendente.

## Sequência

PL-01 corrigir verdade de estoque; PL-02 fonte de pedidos firmes + status; PL-03 visão de demanda agregada por produto; PL-04 BOM por rendimento + embalagens; PL-05 sugestão de compra; PL-06 conciliação com reservas e lotes; PL-07 dashboard operacional.
