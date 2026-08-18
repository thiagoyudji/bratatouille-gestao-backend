# Regras de negócio — cadastros, compras e preços

Documento criado em 2026-08-14 a partir de decisões do proprietário. Registra a direção desejada antes de alterações de contrato, banco ou frontend.

## Usuário ADMIN e sócio

- Um usuário `ADMIN` do dashboard pode ser cadastrado/associado como sócio (`Partner`).
- Ser `ADMIN` não cria automaticamente um sócio.
- Um usuário que não seja `ADMIN` não pode ser cadastrado como sócio.
- A associação do sócio ao usuário do dashboard deve permitir rastrear compras, pagamentos e contas por identidade administrativa.
- A associação não deve reutilizar `Partner` para representar cliente PJ.

Decisão do proprietário: a associação é 1:1 e não pode ser alterada depois de criada. Um `ADMIN` só pode estar associado a um `Partner`, e cada `Partner` só pode estar associado a um `ADMIN`.

### Decisões ainda abertas

- Como migrar sócios/compras existentes sem vínculo.
- Se exclusão deve ser proibida e somente a inativação permitida.
- Como tratar a inativação do usuário ou do sócio sem apagar histórico.

## Cadastro de itens por compra

- O fluxo público de cadastro de item isolado não deve existir como operação principal.
- O item deve ser criado dentro do cadastro de uma compra.
- O cadastro de compra deve aceitar uma coleção de itens.
- O nome do item deve ser único, sem permitir dois itens com o mesmo nome.
- Se o nome já existir, o fluxo deve reutilizar o item existente ou rejeitar a compra conforme decisão técnica posterior; não deve criar duplicidade silenciosamente.
- A remoção ou descontinuação do endpoint/tela de itens exige atualização coordenada do OpenAPI, backend e dashboard.

## Unidades por tipo de item

Regra de experiência do dashboard, inicialmente aplicada no frontend:

- `PACKAGING` não pode ser cadastrado em gramas ou mililitros; deve usar unidade compatível com contagem, normalmente `UN`.
- `FINISHED_PRODUCT` não pode ser cadastrado em gramas ou mililitros; deve usar unidade compatível com contagem, normalmente `UN`.
- Ingredientes continuam podendo usar as unidades de massa/volume previstas no contrato, conforme o caso.

Essa restrição não deve ser considerada proteção suficiente no backend. Se a unidade impactar estoque, produção ou custo, a mesma invariante deverá ser avaliada para validação no domínio e no banco durante a implementação.

## Preços

- O preço de aquisição deve ser informado na compra, e não no cadastro isolado do item.
- Para `FINISHED_PRODUCT`, devem existir preços de venda distintos para PF e PJ.
- Para ingredientes e embalagens, deve existir somente um preço aplicável.
- Como compatibilidade com o modelo atual que possui `pricePf` e `pricePj`, o preço único pode ser exposto temporariamente no campo PF; o campo PJ não deve representar uma segunda política de preço para esses tipos.
- A política de preço de venda de produto final continua sendo determinada pelo tipo do cliente autenticado; o frontend não pode enviar preço final como autoridade.

### Decisões ainda abertas

- Diferenciar explicitamente preço de aquisição na compra de preço de venda do produto final.
- Definir se o preço de venda do produto final nasce na compra, em uma etapa posterior, ou em ambos os fluxos.
- Definir o comportamento para alterações de preço e preservação do histórico.
- Definir se o preço único deve permanecer no campo PF por compatibilidade ou receber um campo neutro em novo contrato.

## Impactos esperados

- Revisar entidades e vínculo `Partner`/`AuthUser`.
- Revisar o agregado de compra e o cadastro em lote de itens.
- Criar constraint/validação de nome único de item.
- Revisar schema OpenAPI, migrations, services, mappers e consumidores.
- Atualizar telas, tipos gerados e serviços do dashboard antes de remover o cadastro isolado de item.
- Adicionar testes de autorização, unicidade, unidades válidas e cálculo/exposição de preços.

## Perdas, financeiro e fechamento

- A perda de item/insumo deve ser tratada como informação de custo e prestação de contas, não somente como ajuste operacional de estoque.
- O fluxo de perdas deve alimentar as visões financeiras e os cálculos do período com quantidade, custo unitário, custo total, motivo e data.
- O fechamento de período deve exigir confirmação explícita antes da operação irreversível.
- A interface e o resultado do fechamento devem exibir claramente o período completo, do início ao fim (`startDate` até `endDate`), antes e depois da confirmação.
- Deve ser revisada a integração entre perdas, custos, vendas, compras e fechamento para evitar que a prestação de contas omita perdas já registradas.

## Estoque fake — estoque disponível para venda (`SellableStock`)

- O cadastro do estoque/oferta que será exposto no e-commerce deve ficar em uma aba acessível somente a `ADMIN`.
- “Estoque fake” é o nome da aba e da funcionalidade no dashboard. O conceito técnico existente no backend é `SellableStock`.
- Esse cadastro deve definir quais itens entram no site, disponibilidade virtual e preços PF/PJ do produto vendável.
- O Estoque fake não possui quantidade finita. `infinite=true` significa que o produto pode ser vendido; `infinite=false` significa que aparece fora de estoque.
- O objetivo do Estoque fake é ampliar a capacidade comercial; ele não representa saldo físico.
- Nenhuma baixa do estoque físico deve ocorrer antes da confirmação administrativa do pedido. Na confirmação, o sistema deve calcular o consumo do estoque existente e a necessidade de produção do excedente.
- O pedido aceito pelo Estoque fake não reduz essa oferta. Enquanto `infinite=true`, ele continua vendável; a redução é uma operação separada sobre o estoque físico.
- A leitura necessária ao e-commerce pode permanecer pública ou autenticada conforme o contrato externo; a escrita administrativa deve exigir `ADMIN`.
- Para produto final, os preços PF e PJ são relevantes nesse cadastro. A política de preço continua sob autoridade do backend.
- A flag `active` controla a publicação: quando inativa, a oferta não deve aparecer no e-commerce.
- A flag `infinite` controla a disponibilidade comercial: quando falsa, a oferta continua aparecendo, mas fica fora de estoque e não pode ser comprada.
- O catálogo público deve receber somente o estado fake e os dados comerciais; estoque físico nunca é exposto.

### Escopo de receita

- Nesta regra, “receita” significa exclusivamente a `Recipe` de produção: produto final, insumos, quantidades e rendimento (`yieldPercentage`).
- O cálculo de insumos do Estoque fake deve reutilizar essa receita de produção e não deve ser confundido com receita financeira, faturamento ou prestação de contas.

## Integração entre estoque vendável, estoque físico e produção

- A aba Estoque fake deve permitir vincular a oferta vendável ao estoque físico original.
- Estoque fake deve ser tratado como quantidade virtual/ofertada, não como segundo saldo físico. A venda precisa deixar explícito se será atendida pelo estoque físico disponível ou por produção necessária.
- Quando o `ADMIN` acionar “Confirmar saída do estoque”, o sistema baixa somente a quantidade física disponível e registra a falta restante por produto final. A falta não bloqueia o pedido nem altera o status visual do cliente.
- A ação de baixa física é idempotente e executável uma única vez por pedido, registrando usuário, data, itens baixados e quantidades faltantes.
- Para uma quantidade virtual de produto final ou `X` pedidos, o sistema deve apresentar a necessidade estimada de cada insumo com base na `Recipe` ativa do produto.
- A estimativa deve incluir o custo previsto dos insumos necessários para produzir a diferença entre a oferta fake e o estoque físico aproveitável, sem confundir esse custo estimado com custo já realizado.
- O custo estimado dos insumos deve usar custo médio ponderado. A compra, entretanto, deve preservar o preço efetivamente pago naquela aquisição para histórico e prestação de contas.
- O cálculo deve respeitar a regra já usada na produção: `usableQuantity = recipeItem.quantity * producedQuantity` e `consumedQuantity = usableQuantity / yieldPercentage`. O resultado deve ser comparado ao estoque físico e mostrar eventuais déficits por insumo.
- A previsão deve deixar visíveis estoque físico aproveitável, déficit por produto final, necessidade de produção, estoque de insumos, déficit de insumos, quantidade de compra e custo estimado. Não deve criar produção automaticamente.
- O relatório de pré-produção deve detalhar a regra de proporção da receita, os insumos disponíveis, os insumos faltantes, o custo de cada insumo e os custos indiretos rateados da produção.
- A produção só pode ser confirmada quando houver quantidade suficiente de todos os insumos necessários; a pré-produção pode apenas simular e apontar déficits.
- O custo indireto estimado deve considerar gás por produção e rateio mensal de luz e aluguel, com critérios de rateio explícitos e auditáveis.
- O dashboard deve preservar o máximo de detalhe possível sobre gastos e lucros: preço de cada compra, consumo efetivo, custos diretos, custos indiretos, pedidos, faturamento, margem estimada e margem realizada.

## Rendimento, perdas e planejamento de compras

**Direção futura; não é requisito da primeira implementação do Estoque fake.**

- Para insumos com preparação/perda relevante, como pimentão, poderá ser registrado o peso bruto antes do preparo e o peso aproveitável após descascar/limpar.
- Exemplo: entrada bruta de `5.000 g` e peso aproveitável de `4.352 g` resultam em perda de `648 g` e rendimento de `87,04%`.
- O sistema poderá calcular percentual de perda, percentual de rendimento e valor financeiro perdido com base no custo do insumo.
- A média histórica de rendimento poderá ser usada na estimativa para converter a necessidade líquida da receita em necessidade bruta de compra. A receita deve continuar representando a quantidade efetivamente aproveitada/consumida na produção.
- Essa medição deve diferenciar perda de preparo, perda operacional e sobra reaproveitável, para não transformar toda diferença de peso em desperdício automaticamente.
- Deve existir futuramente uma aba de planejamento de compras em que o usuário informe uma quantidade de produto final e receba a quantidade necessária de cada insumo bruto, o estoque disponível, o déficit de compra e o custo estimado.
- O planejamento deve considerar receitas, rendimento médio, estoque comprometido por pedidos “em produção”, estoque disponível e embalagem necessária, permitindo comprar o mais próximo possível da necessidade real.

## Pedido, baixa física e auditoria

- Pedido PF pode aguardar pagamento da InfinitePay; pedido PJ pode seguir por condição de crédito/boleto de prazo longo, sem pagamento imediato.
- Pedido recusado, cancelado ou expirado antes da baixa física não consome estoque e deve ser apenas cancelado.
- A alteração de pedido exige confirmação explícita do `ADMIN`, recalcula automaticamente o valor e exige motivo.
- Toda alteração deve gerar log administrativo com usuário, data, motivo, estado anterior e estado posterior, incluindo itens adicionados, removidos ou alterados.
- A baixa inicial considera somente produtos finais adquiridos e publicados no Estoque fake. Ingredientes e embalagens são controlados pelo fluxo de produção.
- O pedido não recebe status público “em produção”; faltas são informação operacional exclusiva do dashboard.
- A baixa registra a quantidade retirada e a quantidade não atendida, sem permitir duplicidade.

### Crédito de cliente PJ

- Cliente PJ associado a parceiro pode comprar sem pagamento imediato.
- A condição deve ser representada separadamente do pagamento instantâneo, com método/condição, vencimento e status próprios.
- A integração de boleto/crédito PJ está documentada, mas não faz parte da primeira implementação.

### Decisões técnicas ainda abertas

- O planejamento de produção/compras usa somente a quantidade `X` informada manualmente pelo usuário; não incorpora pedidos nem saldo fake automaticamente.
- Como tratar arredondamento, lote mínimo e quantidade fracionária de receita?
- Deve existir somente uma `Recipe` ativa por produto final; falta definir a modelagem de vigência/histórico das receitas antigas.
- Gás é custo da produção; luz e aluguel são despesas mensais e devem entrar no período entre abertura e fechamento mensal.
- Definir a fórmula de rateio do gás por produção e como exibir a participação de luz/aluguel no custo estimado sem perder o lançamento integral dessas despesas no fechamento mensal.
- Definir se custos indiretos entram no custo estimado do produto, na prestação de contas ou em ambos.
- Definir se custos indiretos entram no custo estimado do produto, na prestação de contas ou em ambos.
- O vínculo deve ser configurado por produto final, e não por ingrediente/embalagem, ou haverá exceções?
