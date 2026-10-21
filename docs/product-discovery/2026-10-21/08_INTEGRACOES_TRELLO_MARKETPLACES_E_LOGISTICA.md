# Discovery — Trello, Mercado Livre, logística e integrações sem acoplamento excessivo

**Status:** intenção futura; pesquisar capacidades/licenças/contratos antes de implementar.
**Princípio:** Gestão e backend são autoridade do pedido; integrações externas representam projeções e respostas de provedores.

## Trello (curto prazo, após pedidos confiáveis)

- A API oficial suporta `POST /1/cards` e atualização: https://developer.atlassian.com/cloud/trello/rest/api-group-cards/
- Criar **um card por pedido aceito**, usar `salesOrderId` como chave estável, gravar `trelloCardId`, `boardId/listId` configuráveis.
- Card: número do pedido, canal, itens/resumo, prazo, alerta de separação/falta, URL interna do ERP. Evitar CPF/CNPJ, telefones e endereços completos sem necessidade.
- Atualizar card em confirmação, separação, envio e cancelamento conforme mapeamento de listas aprovado. Se alguém mover card manualmente, definir regra de autoridade para não criar loop de duas vias; começar ERP -> Trello.
- Retry/backoff/idempotência/deduplicação com `integration_events` ou tabela outbox **apenas quando a primeira integração concreta justificar**; sem Kafka/broker novo.
- Falha Trello nunca deve desfazer pedido válido; estado `SYNC_PENDING/FAILED/SYNCED` visível no Gestão.

## Mercado Livre (futuro, canal distinto)

- Integração seller por OAuth e associação anúncio/SKU -> `Item` de produto acabado.
- Importar eventos de **pedido confirmado**, pagamentos e envios; preservar `externalOrderId`, timestamps, status e taxas/comissões próprias do marketplace.
- Documentação oficial: https://developers.mercadolivre.com.br/pt_br/produto-consulta-de-usuarios/produto-receba-notificacoes (tópico `orders_v2`) e https://developers.mercadolivre.com.br/pt_br/gerenciamento-de-vendas/pedidos-e-opinioes (consulta pedidos).
- Integração do Mercado Livre **não é igual à integração com Mercado Pago**: são sistemas relacionados comercialmente, mas possuem objetos e callbacks distintos.
- Não duplicar pedido ao receber notificações repetidas/out-of-order. Consultar o recurso completo após notificação e usar sync/reconciliação periódica.
- Futuramente avaliar sincronização de preço/anúncio/estoque com regras comerciais: estoque virtual do Gestão NÃO pode virar disponibilidade física fictícia; política por canal.

## Frete/transportadora (futuro)

- Avaliar Loggi, Correios/Sedex e motoboy local de acordo com contratos, disponibilidade regional, embalagens/fragilidade/alimentos e políticas de coleta.
- Simular fretes, selecionar serviço e custo; somente **após aprovação**, contratar/coletar, gerar etiqueta/ID/tracking e gravar eventos de entrega.
- Custo do envio pertence a frete/faturamento/resultado conforme regra aprovada; repasse ao comprador não deve apagar custo efetivo ou mascarar margem.
- Manual fallback sempre disponível quando API, região ou serviço não suportar pedido.
- Nunca acionar coleta/cobrança externa em retry duplicado por botão ou webhook.

## Integração saudável em monólito modular

- Service de domínio decide quando evento de negócio ocorreu; adapter cuida de HTTP/API de terceiro; persistência guarda ID externo e logs mínimos sem segredos.
- Jobs internos duráveis/execução agendada são opção quando justificáveis; **não adicionar novos deployments ou brokers** inicialmente.
- Evitar ligação "site frontend -> Trello" diretamente: o backend dispara integração após transição comercial confirmada, não depende de aba aberta.
- Comunicação site/dashboard -> backend com contratos OpenAPI e testes de idempotência/autorização.
- Credenciais apenas no servidor. Aplicar menor privilégio, rotation, validação de assinatura/consulta authoritative conforme provedor.

## Perguntas abertas

- Trello: quais listas e quando o card deve existir?
- Pedido PJ a prazo: quem confirma que é firme?
- Mercado Livre: previsão de estreia, SKUs, logística Mercado Envios ou própria?
- Fretes: API por contrato, etiquetas, motoboy sob demanda ou postagem manual?
- Qual canal assume custo de frete e em que momento baixa itens de estoque físico?

## PRs quando aprovadas

INT-01 adapter Trello e sincronização unidirecional; INT-02 contratos/logística de envio; INT-03 Mercado Livre seller importação de pedidos; INT-04 sincronização de oferta e financeiro de marketplace; INT-05 automação de cobrança pós-conciliação.
