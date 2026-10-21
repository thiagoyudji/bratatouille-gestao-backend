# Proposta em discussão — produção, rendimento e inteligência histórica

**Data:** 2026-10-21 | **Estado:** descoberta de produto; regras não aprovadas, nenhuma implementação nesta PR.
**Baseline observada:** master c2e991acf452cdf7526bd607b2c9f22438777cda. Conferir a master novamente antes de codar.
**Documento relacionado no frontend:** `docs/product-discovery/2026-10-21/01_REDESIGN_GLOBAL_UX.md`.

## Objetivo de negócio

Permitir que a Bratatouille responda com fatos e com graus de certeza explícitos:
- Quais ingredientes/embalagens existem no estoque físico hoje?
- Quanto posso produzir com eles e qual é o gargalo?
- Se produzir uma quantidade-alvo de potes, quais materiais faltam?
- Quanto uma produção realmente consumiu e rendeu?
- Quanto custou de verdade, quanto *poderia* faturar e quanto foi efetivamente vendido?
- O que os históricos recentes sugerem sobre rendimento ou formulação?

**Não é proposta de IA preditiva.** Começar com cálculos determinísticos, histórico auditável e sugestões estatísticas explicáveis.

## O que já existe na master

- `ProductionService.create`: valida receita, registra quantidade produzida, salva produção, baixa ingredientes/embalagens e adiciona acabado ao estoque na transação.
- `Production`, `ProductionItem`: armazenam quantidade final, custo total/unitário, consumo por insumo, custo unitário/total e alguns atributos de perda/rendimento. `Lot` é criado na produção.
- `Recipe`: armazena nome, item de saída, quantidades de referência e `yieldQuantity` opcional.
- `ProductionSimulationService`: para entradas manuais, usa o menor fator `inputQuantity/referenceQuantity`; estima potes só se yield de referência existir. Para meta de potes, estima os ingredientes somente se yield conhecido, e embalagens por unidade final.
- `CostService`: custo médio derivado de compras/produções e quantidades de entradas sem custo; isso NÃO é garantia de custo completo.
- `SalesOrderItem`: snapshot de preço/custo/margem por pedido; ainda não há atribuição de venda a um lote específico.

## Lacunas observadas e riscos

### PD-BE-01 — Simulação não é entidade persistida e não é vinculada à produção

O endpoint simula, mas `Production` não contém vínculo para um cenário/versão de simulação. Hoje é possível saber o consumo final, mas não comparar sistematicamente "previsto vs. realizado" por simulação.

**Proposta:** simulação como `SimulationRun` opcional e imutável (receita/versão, inputs normalizados, modo, meta, resultados, estoque observado e timestamp). Produção confirmada poderia referenciar um `simulationId`, mas execução também poderia ocorrer diretamente, sem simulação prévia.

**Aceite:** simular várias vezes não movimenta estoque; executar uma vez gera exatamente uma produção, com origem rastreável quando existir; históricos ficam intactos após editar receita.

### PD-BE-02 — Rendimento de referência ausente e regra de três

Com `yieldQuantity = NULL`, não há conversão fundamentada entre ingredientes e potes.
Para rendimento conhecido:
- `factor = min(input_i / reference_i)` se cenário usa todas as proporções da receita;
- `pots = floor(factor * referenceYield)` para previsão por insumos;
- `required_i = reference_i * targetPots / referenceYield` para meta por potes;
- embalagens seguem `packagingUnitsPerPot * targetPots`, não a proporção de massa/volume.

**Importante:** atualmente o modo de entradas utiliza a quantidade manual como `currentStock` para ingredientes — BE-002 no relatório de auditoria — e a operação física pode ser impossível. Corrigir a verdade do estoque primeiro.

**Aceite:** quantidades/proporções consistentes em dois sentidos, rounding e potes inteiros; receita com rendimento desconhecido não inventa número final; ingredientes de massa e volume nunca convertidos entre si.

### PD-BE-03 — Aprendizado baseado em produções reais

Proposta de 1ª versão:
1. Registrar toda batelada com materiais efetivamente usados, quantidade realmente produzida, perdas, tamanhos de pote, data, operador e versão da receita.
2. Sugerir consumo típico **por pote**, por insumo: `sum(consumedQuantity) / sum(producedUnits)`, *somente* de lotes comparáveis (mesmo produto, tamanho e versão/normalização).
3. Rendimento típico por batelada de referência só quando as bateladas forem comparáveis; normalizar pelo porte da receita. Exibir número de observações, faixa e variação; nunca calcular confiança artificial.
4. Excluir/rotular bateladas teste, corrigidas, perdidas ou fora do padrão conforme política explícita.
5. Com amostra adequada, gerar **sugestão versionada** de rendimento/consumo em vez de editar a receita silenciosamente.
6. A receita original continua auditável; aprovar uma sugestão cria revisão, com quem/quando/motivo.

**Decisões pendentes:** tamanho mínimo da amostra (ex. 3–5 bateladas reais), janela temporal, influência de outliers, vários tamanhos de embalagem, receitas alteradas, desvio aceito e necessidade de aprovação ADMIN.

### PD-BE-04 — Perda e consumo devem ter significado real

Em `ProductionService.buildProductionItemData`, `usableQuantity=consumedQuantity`, `lossQuantity=0` e `yieldPercentage=1`, inclusive quando embalagem extra é informada para perda. **Isso impede aprendizagem real da perda**.

Proposta: separar `grossConsumed`, `usableApplied`, `lossQty` e motivo. Para embalagem, exemplo: 20 lacres aplicados + 2 danificados => baixa 22, 20 aproveitados, perda 2. Para ingredientes, definir em conjunto se o operador conhece perda em separação/cozimento e como medi-la. Não exigir digitação irrealista.

**Aceite:** total baixado = aproveitado + perdido, se o tipo da operação permitir a decomposição. Não reconstruir perdas antigas inexistentes.

### PD-BE-05 — Custo de produção, receita potencial e lucro realizado

Três resultados não intercambiáveis:
- **Custo apurado:** soma dos custos reais/confiáveis da produção, com flag de informação incompleta. Custo ausente NÃO é zero comprovado.
- **Receita potencial:** potes x preço PF ou PJ (cenários), sujeita a descontos, frete, tributos e canais. Não é faturamento.
- **Lucro realizado:** depende de venda aprovada + custo de mercadoria vendida atribuível ao lote/política de valuation + taxas, perdas e custos atribuídos. Exige decisão de FIFO/lote ou custo médio para CO​GS e rastreio de expedição.

Os valores de venda em `SalesOrderItem` são snapshots comerciais, não prova de qual lote saiu. Proposta: lote/expedição como fonte de verdade de realizado, com atribuição rastreável e regra contábil acordada antes de implementar.

**Aceite:** relatórios separam potencial, aprovado/recebido e realizado; parciais/estimativas visivelmente sinalizados; jamais apresentar margem falsa por entrada histórica sem preço.

### PD-BE-06 — Validade/segurança alimentar por produto

`Lot.DEFAULT_EXPIRATION_MONTHS = 6` é atualmente constante para todo produto. **Revisar urgentemente com responsável técnico pelas regras de validade de cada produto/embalagem/processo.** Nunca assumir que todos os alimentos têm 6 meses de validade. Proposta: parametrizar por produto/formulação e exigir data calculada/confirmada com registro, rótulo e rastreio.

**Aceite:** nenhuma validade é derivada cegamente de uma constante universal no fluxo definitivo; lotes antigos não têm datas alteradas em massa sem revisão.

## Semântica sugerida do ciclo

`RecipeRevision` (referência) -> `SimulationRun` (opcional, imutável) -> `ProductionBatch` (real) + `BatchInputs` + `BatchLosses` -> `Lot` (validade/rastreio) -> `SalesAllocation` (quando vendida/expedida) -> analytics/sugestões.

Esses nomes são **conceituais**. Reusar entidades atuais quando possível, adicionar apenas campos/tabelas justificadas. Monólito modular, SOLID pragmático; um método uma responsabilidade coerente, sem microserviços.

## Fases propostas (PRs separados)

1. PD-01 Correção P0 da verdade do estoque na simulação; contratos cruzados.
2. PD-02 Snapshots imutáveis de simulação e vinculação opcional à execução.
3. PD-03 Perdas reais, rastreabilidade e consistência da baixa por embalagem.
4. PD-04 Histórico de produção por receita e qualidade do custo.
5. PD-05 Sugestão de rendimento por dados históricos, com aprovação versionada.
6. PD-06 Cenários PF/PJ e rentabilidade por lote vendido após definição contábil.
7. PD-07 Validade parametrizada e política de lote/rastreio alimentar, prioritária conforme risco operacional.

## Perguntas abertas para alinhamento

- A produção sempre passa por simulação ou também deve existir modo direto?
- O cálculo de sugestão pode usar só bateladas aprovadas? Quem aprova atualização da receita?
- Como capturar perdas sem complicar a rotina?
- Quais custos entram no "custo por pote" (ingredientes, embalagem, mão de obra, energia, impostos, entrega)?
- Há vários tamanhos/volumes de pote para um mesmo produto?
- Qual regra de validade foi tecnicamente aprovada para cada alimento?
- Qual é a definição operacional de "lucro realizado" no dashboard?

Este documento registra hipóteses para discussão; nenhuma escolha está consolidada.
