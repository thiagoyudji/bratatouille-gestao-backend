# Discovery — pedido único, múltiplos canais e ciclo operacional

**Status:** proposta de produto para aprovação; documentação somente, sem migrations/deploy.
**Inspeção:** master do backend em 2026-10-21; repositórios irmãos `bratatouille-frontend` (Next.js) e `bratatouille-gestao-frontend` (Vite/React).

## Decisões comunicadas pelo negócio

- Uma venda pode nascer no **site**, no **dashboard Gestão** (cadastro interno PF/PJ) ou, no futuro, no **Mercado Livre**.
- Todo pedido real deve ser visível e gerenciado no Gestão; um card Trello pode ser criado automaticamente conforme regra de disparo definida.
- Os canais de pagamento possíveis incluem **InfinitePay**, **Banco Inter** (boleto/cobrança), **Mercado Pago** (uso a confirmar) e pagamentos manuais conciliados.
- Pedidos B2B podem ter faturamento e vencimento posteriores ao pedido; aprovação comercial não é quitação.
- A central deve informar faltas de produtos prontos, produção necessária e materiais a comprar.

## Estado atual confirmado no código

- `SalesOrderService.create` persiste `SalesOrder` e `SalesOrderItem`, precifica pelo backend (PF/PJ) e chama `SellableStockService.decreaseAfterSale`, que valida configuração de **estoque virtual comercial**, não realiza baixa física.
- `SecurityConfig` permite `POST /api/sales-orders` a `ADMIN/EMPLOYEE`. O frontend de Gestão usa essa rota em `Vendas.tsx`, portanto **venda manual já é tecnicamente possível**, mas o cadastro é limitado.
- `InfinitePayCheckoutService` persiste um pedido e cria checkout externo via endpoint público; `SalesOrder.paymentStatus` é `PENDING/APPROVED/DECLINED/CANCELED`. Não confundir esse estado com separação/entrega/faturamento.
- **E-commerce Next.js atual:** catálogo em `mockProducts`, carrinho em `localStorage`, checkout sem ação de envio, autenticação também simulada em `localStorage`, e página de pedidos mockada. **Nenhuma transação real deve depender desse código como está.**
- O backend já expõe cadastro/login de cliente e catálogo virtual, mas o site ainda não o consome. Mapear IDs/SKUs e precificação com segurança.
- `SalesOrder` não dispõe de `orderSource`, `externalOrderId`, `fulfillmentStatus`, `deliveryStatus` ou vinculação a recebível múltiplo.

## Modelo conceitual mínimo (nomes sujeitos à revisão)

**Order (existente `SalesOrder`)**
- `id` canônico imutável no ERP; `source` = MANAGEMENT / WEBSITE / MARKETPLACE (futuro);
- `sourceExternalId` opcional, `sourceIdempotencyKey`; restrição UNIQUE por origem/chave quando presente;
- snapshot de cliente, endereço, itens, preço resolvido pelo backend e condições comerciais;
- `commercialStatus` separado de `paymentStatus`, `fulfillmentStatus` e `deliveryStatus`;
- timestamps e atores por transição, sem apagar logs prévios.

**Payment/Receivable**
- pedido pode ter título a receber, tentativas de pagamento, pagamento parcial e conciliação separada; fornecedor e IDs externos.
- InfinitePay, Inter e Mercado Pago são **adaptadores alternativos** sobre o mesmo pedido/título, não sistemas separados de pedido.
- Não permitir múltiplas cobranças não intencionais pelo mesmo débito; retorno idempotente e conciliado.

**Fulfillment**
- pedido comercialmente confirmado -> validação de disponibilidade -> preparação/separação -> expedição -> entrega;
- quantidade do pedido ≠ reserva de estoque físico ≠ baixa física ≠ pagamento;
- definir política de liberação B2B a prazo, sem presumir que `PENDING` significa "aguardando produção".

## Evento que gera Trello (proposta)

Criar card a partir de **pedido aceito comercialmente**:
- B2B a prazo: após confirmação interna/comercial, mesmo sem recebimento;
- checkout antecipado: após confirmação do pagamento;
- não criar card ao abrir carrinho, entrar em checkout ou gerar uma tentativa falha.
Usar `orderId` como chave única, persistir `trelloCardId`, atualizar card sem duplicar, permitir reparação/retry e nunca bloquear o pedido se Trello falhar.

Aprovar esses gatilhos com a operação antes de codar. 

## APIs / segurança

- Nunca confiar em `customerType`, `pricingTier`, valor unitário, `itemId` ou sessão criados/modificados pelo cliente sem validação authoritative.
- E-commerce deve usar login real `CUSTOMER`; dashboard `ADMIN/EMPLOYEE`, cada superfície com autorização própria.
- Implementar criação pública autenticada (ou guest explicitamente autorizado) com política antifraude/rate limit; não simplesmente abrir `POST /api/sales-orders` administrativo para visitantes.
- `idempotencyKey` por tentativa de pedido; transições sem duplicar venda, cobrança, expedição ou Trello.
- Dados do cliente usados no card respeitam mínimo necessário; não colocar CPF/CNPJ, senha ou informação sensível em descrição do Trello.
- Regenerar OpenAPI, SDK/tipos TypeScript dos dois frontends e adicionar testes HTTP por role/origem.

## Cenários obrigatórios

1. Cliente compra no site e paga => 1 pedido no Gestão e 1 card Trello se confirmado; reenvio de webhook não duplica nada.
2. Pedido PJ cadastrado no dashboard a 21 dias => aparece na operação e em Contas a Receber, mesmo sem pagamento; não entra em caixa.
3. Link de pagamento gerado mas abandonado => tentativa PENDING, sem marcar pedido como quitado nem criar card de produção indevido.
4. Mesmo `externalOrderId` recebido duas vezes => upsert idempotente e sem duplicar demanda.
5. Pagamento parcial => título parcial sem alterar quantidade do pedido.
6. Pedido cancelado depois de reservado => reservar/liberar estoque de forma explícita sem desfazer produção física.
7. Falha Trello ou Loggi => pedido permanece válido, integração fica pendente com retry auditável.

## Sequência de PRs sugerida

OR-01 modelo/estados/origem e migrations compatíveis; OR-02 contratos para site e dashboard; OR-03 integração real do site (em PR irmão); OR-04 eventos/retry/Trello; OR-05 fulfillment/expedição; OR-06 recebíveis e conciliação.
**Não implementar em bloco.** Exigir decisão sobre gatilho Trello e fases de pagamento antes do código.
