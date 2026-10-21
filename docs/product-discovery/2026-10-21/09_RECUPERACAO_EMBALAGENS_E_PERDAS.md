# Regra operacional — vencimento, perda de produto e recuperação de potes

**Status:** premissas de negócio comunicadas; detalhamento sanitário, custo e UI pendentes de aprovação.

## Regra declarada

- Os produtos atuais usam validade cadastrada de **6 meses**; manter o padrão existente. Pode ser configurável no futuro, sem alteração imediata sem necessidade.
- Quando o alimento é retirado da venda por vencimento, o **pote e a tampa são potencialmente recuperáveis**; o **rótulo não é recuperado**. Distinguir fisicamente o alimento, o rótulo e a embalagem recuperada.
- Recuperável **não significa automaticamente disponível para envasar novamente**. Exige inspeção e higienização adequadas, sob política sanitária aprovada. Nenhuma sugestão de consumir alimento vencido ou reaproveitar embalagem sem avaliação.
- Registrar **valor da perda** com origem e grau de confiabilidade do custo (custo conhecido vs desconhecido). Reduzir estoque de produto acabado não implica dar entrada imediata de embalagem recuperada.

## Fluxo proposto

1. Selecionar `Lot` e quantidade fisicamente retirada, motivo (`EXPIRED`, `QUALITY`, outro), data, operador, observação e custo histórico disponível.
2. Gerar baixa do produto acabado por lote e movimentação rastreável, sem dupla contagem no estoque físico.
3. Desmontagem: registrar quantidade de rótulos descartados, potes e tampas recuperados fisicamente, perdas/danos; não exigir recuperação teórica de 100% se peças estiverem danificadas.
4. Armazenar recuperados em estado `PENDING_INSPECTION/SANITIZATION`, **fora de estoque disponível** para produção; depois de aprovação e quantidade efetiva reutilizável, registrar entrada nas embalagens corretas com origem na baixa.
5. Custos: separar perda do conteúdo, descarte do rótulo, outros componentes não recuperados, custo de higienização e valor residual do que foi reaproveitado — método de valoração requer política aprovada, não creditar custo de pote "gratuito" automaticamente.
6. Impedir repetição da recuperação no mesmo lote/evento; idempotência e restrição por quantidade disponível.

## Exemplo hipotético

10 potes vencidos, 10 potes + tampas recuperados e aprovados, 10 rótulos descartados:
- `finished_product`: -10 unidades;
- perdas: 10 unidades de conteúdo e rótulos, **com avaliação de custo adequada**;
- potes/tampas: +10 unidades reutilizáveis só **após** conferência/higienização e regras sanitárias, evitando reaproveitamento duplicado;
- pedido/venda futura não pode usar o lote vencido.

## Testes

- 10 potes vencidos e apenas 8 recuperados aptos => 8 embalagens reutilizáveis, 2 perdas de embalagem.
- Falha de higienização => sem reentrada em stock utilizável.
- Baixa já processada => não duplicar materiais recuperados ou prejuízo.
- Lote antigo sem saldo remanescente confiável => registrar pendência de conciliação antes de converter automaticamente em perda.
- Custo histórico desconhecido => perda com valor não apurado, sem afirmar R$ 0,00.
