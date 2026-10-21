# Proposta de backend — ciclos mensais, balancete gerencial e acertos entre pessoas

**Data:** 2026-10-21. **Estado:** discussão de produto; nenhuma alteração de API/banco/regra nesta PR.
**Contexto:** sócios não têm mais divisão societária por percentual; a empresa precisa de controle de quem pagou/quem deve a quem e ciclos mensais com início frequentemente no dia 20 (ajustável).

## Evidência na master

- `Partner.defaultSplitPercentage` é campo persistido e `PartnerService.create` o recebe no cadastro.
- `Financeiro.tsx` / `Socios.tsx` e Compra/Despesa ainda expõem regras de rateio porcentual.
- `FinancialService.calculateBalances` agrega compras e custos operacionais, `applyPurchaseToBalances`, `applyOperationalCostToBalances`, e gera sugestões Pix via `FinancialSettlementCalculator`.
- `FinancialClosingService` já tem fechamento por período de datas.
- Há separação de compras/despesas e vendas/relatório, mas **não foi confirmado** que haja extrato de conta bancária/fluxo conciliado de caixa com saldos inicial/final.

## PR-BE-FIN-01 — períodos mensais configuráveis

Oferecer no domínio de configuração de negócio:
- Modo `CALENDAR_MONTH`: data 01 até o último dia inclusive.
- Modo `BUSINESS_CYCLE`: marco mensal configurável (ex. dia 20), com rótulo definido por mês de abertura ou fechamento.
- `CUSTOM` por exceção, se exigido por relatórios.
- Armazenar `startDateInclusive` e `endDateExclusive` ou um padrão inclusivo consistente; não usar o dia 20 duas vezes.
- Parâmetro `financialCycleStartDay` não pode reescrever fechamentos anteriores. Guardar datas efetivas de todo período fechado e a configuração utilizada.
- A mudança do marco deve ter data de vigência, usuário e autorização. Definir política para dias 29–31 em meses curtos **antes** de permitir qualquer dia configurável.
- Dashboard, Vendas e Financeiro compartilham função de cálculo e contrato do período; não assumir que toda métrica usa a mesma data de competência se o negócio distinguir pagamento, venda, emissão e entrega.

**Exemplo, rótulo pelo mês de abertura:** `outubro/2026` = 20/10/2026 a 19/11/2026. Se rótulo for por fechamento, outubro pode ser 20/09 a 19/10. Decidir com usuário.

**Aceite:** transição entre meses sem lacuna/sobreposição, períodos de fechamento imutáveis, testes de timezone America/Sao_Paulo e data-only, inclusive mudança de ano e dia 20.

## PR-BE-FIN-02 — acertos sem rateio societário automático

O campo `defaultSplitPercentage` é legado de um modelo operacional que deixou de ser válido. Não utilizá-lo para determinar obrigações futuras automaticamente.

**Modelo mínimo proposto:**
- `expenseId` / `purchaseId` como origem;
- pagador real `paidBy` (empresa/pessoa);
- beneficiário ou devedor explicitamente informado, se houver;
- obrigação `reimbursementAmount` separada de preço da compra;
- status `OPEN/PARTIALLY_SETTLED/SETTLED/CANCELLED` ou enum equivalente com motivo;
- liquidação `paidAt`, `amount`, comprovante e ator (se aplicável).

**Semântica crítica:** gastar R$ 300 do próprio bolso não significa automaticamente que outro participante deve R$ 150 nem R$ 300. A existência/valor da dívida exige regra explícita. Acertos entre pessoas não são necessariamente despesas novas da empresa; reembolsos não devem duplicar custo original.

Não deletar `Partner` nem `PurchaseSplit`/cost split antigos abruptamente. Planejar descontinuação:
1. Levantar quem consome porcentagem no código/relatórios.
2. Desabilitar split automático nas novas operações, mantendo leitura legada.
3. Novo fluxo de reembolso/adiantamento com transição explícita.
4. Migrar parcialmente somente dados que tenham significado conhecido, preservar histórico.
5. Remover campos/rotas antigos apenas em versão compatível com clientes.

**Aceite:** lançar gasto pago por pessoa sem divisor de porcentagem; credor e devedor explicados por documento de origem; abater um reembolso parcial sem duplicar gasto; saldo reproduzível a partir de eventos.

## PR-BE-FIN-03 — balancete gerencial legível sem declarar contabilidade formal

Exibir tabela de lançamentos com:
- data de competência e/ou pagamento, categoria, origem de lançamento, pagador, valor de entrada ou saída, situação e referência;
- grupos e totais de compras, vendas aprovadas, pagamentos recebidos, despesas, reembolsos/adiantamentos;
- saldos inicial/final apenas se existir extrato/ledger de caixa conciliado;
- qualidade do dado: pago, pendente, previsto, histórico não conciliado.

**Não confundir:** receita da venda aprovada ≠ dinheiro recebido; compra registrada ≠ pagamento liquidado; despesa ≠ reembolso; margem/lucro ≠ fluxo de caixa. Se houver pedido de balancete contábil formal, levantar exigências contábeis específicas em vez de simular partidas dobradas com somas de tabela.

**Aceite:** totais reproduzíveis por consulta, lançamentos detalháveis/auditáveis, não duplicar transferência entre contas, fechamento bloqueia alterações retroativas ou oferece ajuste rastreado.

## Dependência UI

`frontend/docs/product-discovery/2026-10-21/04_PERIODO_MENSAL_E_NAVEGACAO_FINANCEIRA.md` descreve seletor e reagrupamento.

## Decisões abertas

1. Ciclo por mês de **abertura** ou mês de **fechamento**?
2. Todos relatórios usam 20–19 ou apenas caixa/financeiro?
3. A empresa tem controle das entradas efetivamente recebidas e saídas pagas, ou somente previsão e despesa incorrida?
4. Quem pode cadastrar/pagar uma obrigação e autorizar reembolso?
5. É necessário saldo por pessoa apenas, ou conta corrente por pessoa/empresa?
