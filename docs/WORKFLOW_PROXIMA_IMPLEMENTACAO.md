# Workflow prioritário — Estoque fake, produção e pedidos

Documento de execução para a próxima sessão de código. As regras estão em `docs/REGRAS_NEGOCIO_CADASTROS_E_COMPRAS.md`.

## Fase 1 — Backend: Estoque fake

1. Revisar OpenAPI, entidades, service, repository e fluxo atual de `SellableStock`.
2. Separar `active`, `infinite`, `outOfStock` e quantidade fake finita.
3. Restringir escrita administrativa a `ADMIN`.
4. Garantir que o e-commerce distinga item oculto, visível fora de estoque, infinito e finito.
5. Adicionar testes para as transições dessas flags.

## Fase 2 — Backend: confirmação do pedido

1. Ao confirmar o pedido pelo `ADMIN`, reduzir a oferta fake.
2. Baixar o estoque físico somente nessa confirmação.
3. Se faltar produto físico, marcar o pedido como `EM_PRODUCAO`.
4. Impedir envio enquanto houver produção pendente.
5. Garantir transação, concorrência e idempotência.

## Fase 3 — Backend: pré-produção e compras

1. Criar caso de uso que receba manualmente `X` unidades de produto final.
2. Calcular proporcionalmente os insumos da `Recipe` ativa.
3. Comparar necessidade com estoque e apontar faltas.
4. Estimar custos diretos com rastreabilidade das compras e custo médio para projeção.
5. Incluir gás por produção e luz/aluguel como despesas mensais do período.
6. Deixar rendimento/perdas por peso e planejamento de compras como evolução posterior.

## Fase 4 — Dashboard de gestão

1. Criar aba “Estoque fake”, exclusiva de `ADMIN`, separada de Vendas.
2. Exibir ativo/inativo, infinito/finito e disponível/fora de estoque com semântica distinta.
3. Exibir déficit e status `EM_PRODUCAO`.
4. Criar tela de pré-produção para X unidades com insumos, faltas e custos.
5. Integrar abertura/fechamento mensal e despesas de luz/aluguel.

## Fase 5 — E-commerce normal

1. Revisar serviços, tipos gerados, catálogo, carrinho e checkout.
2. Corrigir leitura dos estados de publicação e fora de estoque.
3. Garantir PF/PJ no preço retornado pelo backend.
4. Revisar produtos aguardando produção e indisponibilidade.
5. Implementar após fechar o contrato do backend.

## Ordem de execução

Começar pelo inventário do fluxo atual de `SellableStock` e pedido no backend. A primeira fatia vertical será: flags do Estoque fake, confirmação administrativa do pedido e testes. Depois seguir para pré-produção e planejamento de compras.
