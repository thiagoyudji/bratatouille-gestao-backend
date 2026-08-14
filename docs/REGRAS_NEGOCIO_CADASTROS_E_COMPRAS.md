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
- Esse cadastro deve definir quais itens entram no site, disponibilidade/quantidade virtual e preços PF/PJ do produto vendável.
- A quantidade do Estoque fake pode ser maior que o estoque físico atual. Ela representa uma oferta/projeção comercial e não deve ser apresentada como saldo físico confirmado.
- O objetivo do Estoque fake é ampliar a capacidade comercial e permitir vender além do saldo físico atual, calculando o que pode ser atendido imediatamente e o que exigirá produção.
- Nenhuma baixa do estoque físico deve ocorrer antes da confirmação administrativa do pedido. Na confirmação, o sistema deve calcular o consumo do estoque existente e a necessidade de produção do excedente.
- Ao confirmar administrativamente um pedido, a quantidade correspondente deve ser retirada da oferta do Estoque fake. Se o estoque físico não cobrir toda a quantidade, o pedido deve assumir status “em produção” para a parcela faltante, sem permitir envio antes da conclusão.
- A leitura necessária ao e-commerce pode permanecer pública ou autenticada conforme o contrato externo; a escrita administrativa deve exigir `ADMIN`.
- Para produto final, os preços PF e PJ são relevantes nesse cadastro. A política de preço continua sob autoridade do backend.
- A oferta pode ser configurada como infinita (`infinite=true`), representando disponibilidade comercial sem limite numérico físico.
- A flag `active` controla a publicação: quando inativa, a oferta não deve aparecer no e-commerce.
- A flag de fora de estoque controla a compra: quando fora de estoque, a oferta continua aparecendo, mas deve ser exibida como indisponível e não pode ser comprada.
- Desativar a condição infinita não deve necessariamente ocultar o produto: a oferta pode passar a trabalhar com quantidade finita ou ficar fora de estoque, conforme a operação administrativa.

### Escopo de receita

- Nesta regra, “receita” significa exclusivamente a `Recipe` de produção: produto final, insumos, quantidades e rendimento (`yieldPercentage`).
- O cálculo de insumos do Estoque fake deve reutilizar essa receita de produção e não deve ser confundido com receita financeira, faturamento ou prestação de contas.

## Integração entre estoque vendável, estoque físico e produção

- A aba Estoque fake deve permitir vincular a oferta vendável ao estoque físico original.
- Estoque fake deve ser tratado como quantidade virtual/ofertada, não como segundo saldo físico. A venda precisa deixar explícito se será atendida pelo estoque físico disponível ou por produção necessária.
- Quando a quantidade fake for maior que a quantidade física disponível, o sistema deve calcular a diferença que precisa ser produzida. A baixa do estoque físico ocorre somente após o `ADMIN` confirmar o pedido; a parte excedente deve gerar necessidade de produção e não pode ser expedida antes da produção.
- Para uma quantidade virtual de produto final ou `X` pedidos, o sistema deve apresentar a necessidade estimada de cada insumo com base na `Recipe` ativa do produto.
- A estimativa deve incluir o custo previsto dos insumos necessários para produzir a diferença entre a oferta fake e o estoque físico aproveitável, sem confundir esse custo estimado com custo já realizado.
- O custo estimado dos insumos deve usar custo médio ponderado. A compra, entretanto, deve preservar o preço efetivamente pago naquela aquisição para histórico e prestação de contas.
- O cálculo deve respeitar a regra já usada na produção: `usableQuantity = recipeItem.quantity * producedQuantity` e `consumedQuantity = usableQuantity / yieldPercentage`. O resultado deve ser comparado ao estoque físico e mostrar eventuais déficits por insumo.
- A previsão deve deixar visíveis, separadamente, quantidade fake ofertada, estoque físico aproveitável, quantidade comprometida/reservada, déficit de produto final, necessidade de produção, necessidade de cada insumo e custo estimado. Não deve criar uma produção automaticamente sem decisão explícita.
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

### Decisões técnicas ainda abertas

- Definir se a quantidade fake exibida funciona como limite comercial recorrente ou se é reposta manualmente após cada confirmação de pedido.
- O planejamento de produção/compras usa somente a quantidade `X` informada manualmente pelo usuário; não deve incorporar pedidos ou o saldo fake automaticamente.
- Como tratar arredondamento, lote mínimo e quantidade fracionária de receita?
- Deve existir somente uma `Recipe` ativa por produto final; falta definir a modelagem de vigência/histórico das receitas antigas.
- Gás é custo da produção; luz e aluguel são despesas mensais e devem entrar no período entre abertura e fechamento mensal.
- Definir a fórmula de rateio do gás por produção e como exibir a participação de luz/aluguel no custo estimado sem perder o lançamento integral dessas despesas no fechamento mensal.
- Definir se custos indiretos entram no custo estimado do produto, na prestação de contas ou em ambos.
- Definir se custos indiretos entram no custo estimado do produto, na prestação de contas ou em ambos.
- O vínculo deve ser configurado por produto final, e não por ingrediente/embalagem, ou haverá exceções?
