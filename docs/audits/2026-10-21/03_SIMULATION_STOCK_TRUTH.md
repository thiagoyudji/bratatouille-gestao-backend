# BE-002 / BE-003 — Estoque real x cenário hipotético na simulação

**Status:** bug confirmado em leitura da \`master\` no snapshot da auditoria. Este documento descreve correção **futura**, não afirma que foi implementada.

## Reprodução conceitual

\`ProductionSimulationService.simulateFromInputs(request)\` lê \`request.inputs\` (quantidades fornecidas manualmente), calcula fator mínimo por ingrediente e chama:

\`\`\`java
// productionSimulation/service/ProductionSimulationService.java
response.setCurrentStock(supplied);
response.setMissingQuantity(required.subtract(supplied).max(BigDecimal.ZERO));
\`\`\`

O método \`simulateInputItem\` **não chama \`stockRepository\`**. Assim, enviar \`1000000 g\` de um ingrediente sem estoque pode resultar em \`currentStock=1000000\` na resposta. É uma violação de significado do campo \`currentStock\`.

**Atenção:** a função original de simulação manual pode representar disponibilidade *hipotética*; permitir cenário arbitrário não é por si só errado. O erro é declarar cenário como saldo físico real e não marcar a simulação como não executável sem conferir estoque.

## Contrato semântico a decidir antes de codar

| Campo/conceito | Significado |
|---|---|
| \`physicalStock\` / \`currentStock\` | saldo persistido em \`stocks\`; ausente = 0 |
| \`scenarioAvailableQuantity\` | quantidade digitada pelo operador, explicitamente hipotética |
| \`requiredQuantity\` | consumo estimado pela formulação para o plano |
| \`missingPhysicalQuantity\` | \`max(0, requiredQuantity - physicalStock)\` |
| \`hypotheticalMissingQuantity\` | falta dentro do cenário informado, quando útil |
| \`estimatedFinishedQuantity\` | somente com rendimento conhecido (ou como meta, rotulada) |
| \`completeness\` | completa/parcial; rendimento desconhecido não vira zero inventado |

Não reaproveitar os nomes anteriores com semântica silenciosamente alterada. Atualizar contratos e testes do frontend juntamente.

## UX prevista (dashboard)

- Mostrar **Estoque real: 0 g / 500 g / ...** junto de cada ingrediente, inclusive para item sem linha em \`stocks\`.
- Modo principal \`ESTOQUE_REAL\`: preencher dados a partir do servidor; quantidade digitada > estoque bloqueia execução **real**, mas mantém capacidade de planejar compras.
- Modo \`HIPOTETICO\`: entradas maiores permitidas com aviso persistente **"Cenário hipotético: você não possui necessariamente estes insumos"**.
- Modo \`META_POTES\`: comparar necessidade com estoque físico e mostrar faltas, inclusive embalagem. Sem rendimento: exibir **estimativa parcial** sem declarar custo total dos ingredientes.
- Nunca mostrar \`currentStock\` da API como quantidade real se a API antiga ainda a calcula a partir do body.
- Aviso por item fora do estoque e botão "Usar estoque real" (somente quando os saldos realmente forem consultados).
- Dados financeiros podem ser zero por ausência de histórico; sinalizar custo incompleto, não "gratuito" automaticamente.

## Casos de teste obrigatórios

| Caso | Estoque físico | Cenário digitado | Esperado |
|---|---:|---:|---|
| Sem estoque | 0 g | 1.000.000 g | Físico 0; nunca apresentar cenário como real |
| Saldo parcial | 150 g | 500 g | Físico 150; avisar divergência e necessidade |
| Saldo suficiente | 2.000 g | 1.000 g | Diferenciar cenário 1.000 de saldo 2.000 |
| Item sem row em \`stocks\` | ausente | 100 g | Tratar como 0 físico |
| Embalagem sem saldo | 0 un | 20 potes | 20 lacres faltantes se a receita consumir 1/pote |
| Unidade alternativa | 1.500 g | 2 kg | Canonical 2.000 g; comparar dimensionalmente |
| Rendimento desconhecido | qualquer | 500 g | Não inventar potes nem custo total |
| Validade estrutural | — | negativo/NaN/unidade trocada | 4xx/validação visível |
| Modo hipotético | 0 g | 9.999 g | Pode simular, com aviso e físico 0 |
| Concorrência | saldo muda entre simulação e produção | — | Execução revalida em transação, sem confiar em prévia |

## Riscos de compatibilidade

- A alteração de significado de \`currentStock\` exige atualizar frontend que hoje possa esperar \`supplied\`.
- \`ProductionSimulationResponse\` atualmente não possui campo próprio de origem/cenário.
- A PR de backend #31 (planejamento por potes) estava aberta durante esta auditoria. Reavaliar \`master\` após merge e aplicar correção sobre a nova base, preservando testes.
- Não travar simulação hipotética por regra de estoque real sem oferecer alternativa clara.
- Um resultado de simulação **não reserva estoque**; produção real sempre revalida no momento da escrita.

## Critério de pronto

A experiência passa a responder, separadamente e de modo correto:
1. "Quanto tenho?"
2. "Quanto eu poderia produzir com o que tenho?"
3. "Quanto precisaria comprar para produzir X?"
4. "O que aconteceria se eu tivesse Y?"

Sem qualquer alteração automática no inventário.
