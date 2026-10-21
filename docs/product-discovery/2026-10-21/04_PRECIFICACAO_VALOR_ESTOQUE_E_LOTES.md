# Proposta de backend — precificação, avaliação de estoque, lotes e validade

**Status:** proposta para discussão; não executar migrations, nem modificar estoque/precificação até decisões aprovadas.

## Evidências no código atual

- `Item` possui `pricePf`, `pricePj` opcionais; `Stock` também possui preços associados/sincronizados; `SellableStock` possui `pricePf/pricePj` próprios para oferta comercial/fake. **Três representações potencialmente divergentes** precisam de contrato de autoridade.
- `StockService.findAll()` retorna saldos por item; não por lote.
- `Lot` tem `productionId`, produto, data, validade e quantidade original produzida, sem campo de saldo remanescente específico por lote.
- `StockMovement` é por item e origem; não há identificação universal do lote consumido/vendido. Uma venda pode reduzir disponibilidade virtual, mas não necessariamente saldo físico até a etapa específica de saída.
- `CostService` calcula custo médio com compras/produções e entradas classificadas como sem custo; no inventário inicial houve entradas com custo histórico DESCONHECIDO. Zero registrado não deve ser vendido como custo conhecido.
- `Lot.DEFAULT_EXPIRATION_MONTHS=6` está fixo para todo produto: precisa validação técnica por alimento e processo; não assumir 6 meses universalmente.

## INV-BE-01 — fonte única de preço e política de disponibilidade

Decidir:
- preço de referência PF/PJ por produto, com opcionais e histórico de alterações;
- eventual preço específico por canal/cliente/período, caso haja caso real;
- `SellableStock` como mecanismo de oferta e sua relação com preço, sem parecer saldo físico;
- snapshot imutável do preço e regra na venda, preservando preço vigente à época.

**Aceite:** UI obtém preço oficial, estoque físico não vira infinito porque há oferta infinita; atualização do preço não reescreve venda histórica; preços omitidos retornam `null`/qualidade de cadastro, não zero fictício.

## INV-BE-02 — visão de estoque valorizado por cenário

Propor endpoint agregado de portfólio:
- `physicalUnits` por produto;
- `eligibleForSaleUnits` somente quando elegibilidade por lote estiver modelada;
- `unitPricePf`, `unitPricePj`, `missingPrice`;
- `potentialRevenuePf = eligibleUnits * pricePf`, `potentialRevenuePj = eligibleUnits * pricePj`, com cobertura;
- `knownInventoryCost` conforme política contábil de custo acordada, `costCoverage` e `incompleteCost`;
- metadados de validade e itens que requerem atenção.

Valor potencial é **simulação do preço de venda** e não é caixa, faturamento nem valor patrimonial contábil. Valores PF/PJ são alternativos, não somam. Não inferir lucro realizado com `potentialRevenue - knownInventoryCost` quando custeio/saídas não estão definidos.

**Aceite:** números reconciliam com saldos reais do banco; consulta agregada sem N+1 severo; preço faltante e custo incompleto sinalizados; produto inativo/vencido/indisponível conforme política.

## INV-BE-03 — livro de lote e remanescente

Definir movimentação de saída alocada a lote:
- produção gera lote e unidades de entrada;
- expedição, perda, amostra, devolução e ajuste movimentam lote identificado, de forma idempotente e auditável;
- saldo remanescente = entradas do lote - saídas do lote + devoluções permitidas;
- soma de lotes por item deve conciliar com saldo físico, ou gerar exceção investigável; não inventar correção retroativa.

Quando não houver vínculo de saídas antigas a lotes, **mostrar a lacuna de atribuição**, não distribuir artificialmente por FIFO sem decisão e migration auditável. Definir FIFO/FEFO para expedição quando houver política comercial/sanitária aprovada; FEFO pode ajudar prazo, mas deve obedecer bloqueios e rastreio.

**Aceite:** 20 fabricados + 8 expedidos do lote => remanescente 12; não permitir duplo envio nem estoque por lote negativo; concorrência transacional; histórico antigo preservado.

## INV-BE-04 — prazo de validade parametrizado com verificação sanitária

Substituir uso cego de `DEFAULT_EXPIRATION_MONTHS = 6` por referência controlada por produto/receita/processo e política de validação/autorização. Dados de validade devem refletir determinação técnica legítima; não atribuir vida útil arbitrária. Lotes históricos precisam revisão separada antes de alterar datas.

**Aceite:** cada lote tem validade rastreável e justificável; vencido/bloqueado não aparece como apto; testes de virada de mês e ano.

## INV-BE-05 — custo de produto, custo do período e lucro efetivo

Distinguir:
1. Custo *da batelada* com composição real de matéria-prima, embalagem, perda, e outros custos aprovados;
2. Custo unitário por pote com cobertura/confiabilidade;
3. Custo do estoque *remanescente*, por política definida (médio, lote, FIFO etc.);
4. Custo dos produtos vendidos (COGS) e lucro realizado, com lote/preço efetivo e momento de apropriação acordado;
5. Receitas **potenciais** PF/PJ (simulação de cenários separada).

Não recalcular custo histórico de insumo de entrada sem preço como zero real; dados antigos sem custo completo ficam explicitamente incompletos.

## Faseamento recomendado

1. Auditar origem/consumidores dos 3 campos PF/PJ e escolher fonte única, sem quebra silenciosa.
2. Expor painel **potencial físico** por produto com avisos de preço e custo, sem prometer aptidão por lote não comprovada.
3. Corrigir rastreabilidade e vencimento por lote.
4. Integrar baixas físicas/expedições a lotes com transação e idempotência.
5. Valoração contábil e lucro realizado após aprovar política financeira e confiabilidade do histórico.

## Discussões necessárias

- PF/PJ fixo no produto basta ou precisa preço por cliente/canal?
- Estoque avaliado em preço de venda, custo histórico contábil ou os dois painéis?
- Validade por produto já está documentada/validada por responsável técnico?
- Há venda/saída parcial por lote, amostras e devoluções?
- Como tratar os potes do inventário inicial cujo lote de origem não foi registrado?
