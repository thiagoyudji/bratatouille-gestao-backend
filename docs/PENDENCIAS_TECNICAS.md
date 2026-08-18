# Pendências técnicas

## Alinhar autorização do papel `EMPLOYEE`

**Status:** pendente de implementação.

**Decisão de negócio:** `EMPLOYEE` deve acessar somente Itens, Receitas, Compras, Produção, Estoque e Vendas. Dashboard, Financeiro, Operações, Sócios e Usuários são exclusivos de `ADMIN`.

**Divergência atual:** o frontend já aplica essa matriz na navegação e nas rotas. Entretanto, o `SecurityConfig` do backend ainda permite `EMPLOYEE` nos grupos `/api/dashboard/**`, `/api/partners/**`, `/api/operational-costs/**`, `/api/operational-losses/**` e `/api/zero-cost-entries/**`.

**Risco:** a proteção do frontend melhora a experiência, mas não constitui uma barreira de segurança. Enquanto o backend não for alinhado, um usuário `EMPLOYEE` autenticado pode chamar diretamente essas APIs.

**Implementação futura:**

- separar os matchers exclusivos de `ADMIN` dos endpoints operacionais compartilhados;
- manter Financeiro e criação de usuários exclusivos de `ADMIN`;
- preservar Itens, Receitas, Compras, Produção, Estoque e Vendas para `ADMIN` e `EMPLOYEE`;
- revisar se endpoints de relatórios expõem dados financeiros antes de liberar acesso;
- adicionar testes de integração para respostas `200/403` por papel e grupo de endpoint;
- manter `.agents/AUTH.md`, `AGENTS.md`, rulebook e consumidores alinhados ao papel `EMPLOYEE`.

Não alterar o OpenAPI para simular autorização: a regra deve ser aplicada pelo Spring Security e documentada de forma coerente.

## Reestruturar cadastro de sócios, itens e preços

**Status:** decisão de negócio registrada; implementação pendente.

Direção definida pelo proprietário:

- somente usuários `ADMIN` podem ser cadastrados/associados como sócios;
- a associação não é automática e deve permitir rastrear compras e contas pelo usuário do dashboard;
- o item deve nascer no cadastro de compra em lote, com nome único;
- o cadastro isolado de item deve ser removido ou descontinuado mediante atualização coordenada do contrato e dos consumidores;
- embalagem e produto final devem usar unidade de contagem no frontend, não grama/ml;
- preço de aquisição deve ser informado na compra;
- produto final possui preço PF e PJ; os demais tipos possuem um único preço, com PF como mapeamento provisório recomendado no modelo atual.

Detalhamento, impactos e decisões abertas: `docs/REGRAS_NEGOCIO_CADASTROS_E_COMPRAS.md`.

## Vínculo imutável entre ADMIN e sócio

**Status:** regra definida; implementação pendente.

- associação obrigatória 1:1 entre usuário `ADMIN` e `Partner` quando o sócio for vinculado a uma conta;
- um `ADMIN` não pode ser associado a mais de um sócio;
- um sócio não pode ser associado a mais de um `ADMIN`;
- a associação não pode ser alterada depois de criada;
- inativação da conta ou do sócio não deve apagar o histórico financeiro.

Ainda falta decidir como tratar sócios/compras existentes sem vínculo.

## Perdas e fechamento financeiro

**Status:** revisão de regra e UX pendente.

- perdas de insumos devem compor custos/prestação de contas, além de movimentar estoque;
- revisar a inclusão de perdas nos resumos e fechamentos financeiros;
- exigir confirmação explícita antes de fechar um período;
- exibir `startDate` e `endDate` completos no fluxo de confirmação e no resultado do fechamento.

## Cadastro administrativo de estoque vendável

**Status:** conceito existente (`SellableStock`); revisão de acesso e UX pendente.

- criar uma aba exclusiva de `ADMIN` para cadastrar o que será vendido no site;
- manter preços PF/PJ nesse cadastro para produtos finais;
- revisar a autorização atual de escrita, pois o `SecurityConfig` ainda permite `EMPLOYEE` em `/api/sellable-stocks/**`;
- usar “Estoque fake” como nome da aba no dashboard; preservar `SellableStock` como nome técnico do conceito existente.

## Vincular estoque vendável ao estoque físico e planejar produção

**Status:** regra desejada; implementação pendente.

O backend hoje mantém quantidade em `SellableStock` e a venda reduz essa quantidade. O Estoque fake, porém, deve poder ofertar quantidade maior que o estoque físico: ele é uma projeção comercial voltada a ampliar vendas e lucro, não um saldo físico. Nenhuma baixa física ocorre antes da confirmação administrativa do pedido.

Direção desejada:

- a aba administrativa deve exibir/configurar o vínculo entre produto vendável, oferta fake e estoque físico;
- deve distinguir quantidade fake ofertada, estoque físico aproveitável, quantidade reservada/comprometida e déficit de produto final;
- quando não houver produto final suficiente, deve informar que é necessário produzir;
- para uma demanda de `X` pedidos/quantidades, deve calcular a necessidade de insumos usando a `Recipe` ativa e o `yieldPercentage` já aplicado pelo fluxo de produção;
- o resultado deve indicar a falta de cada insumo e não apenas a falta do produto final;
- o resultado deve estimar o custo dos insumos necessários para produzir o déficit, identificando que se trata de previsão e definindo a política de custo utilizada;
- a baixa do estoque físico deve ocorrer somente após o `ADMIN` confirmar o pedido/envio;
- a parcela excedente ao estoque físico deve ser tratada como necessidade de produção, sem permitir expedição antes da produção;
- não deve haver baixa física antecipada apenas porque a quantidade foi ofertada no Estoque fake;
- ao confirmar o pedido, a quantidade deve ser retirada da oferta do Estoque fake;
- se o estoque físico não cobrir a quantidade confirmada, o pedido deve ficar “em produção” e não pode ser enviado antes da conclusão;
- a quantidade fake deve ter sua regra de redução definida separadamente, pois sua finalidade é comercial e não representa estoque real;
- deve existir um fluxo de pré-produção que calcule, para a quantidade solicitada, a proporção exata de cada insumo, o que está disponível e o que falta;
- a produção confirmada deve exigir 100% dos insumos necessários;
- a estimativa de insumos deve usar custo médio ponderado, enquanto o histórico da compra preserva o preço efetivamente pago;
- o relatório deve detalhar custo de ingredientes, gás por produção, luz mensal, aluguel mensal, rateios, custo total estimado e custo por unidade;
- custos estimados não podem ser registrados como custo realizado antes da compra/consumo efetivo;
- preservar o detalhamento por compra, consumo, custo direto, custo indireto, faturamento e margem; custo médio serve para estimativa, não substitui o preço efetivamente pago;
- registrar como evolução futura o peso bruto, peso aproveitável, percentual de perda, rendimento médio e valor financeiro da perda;
- criar futuramente planejamento de compras por quantidade de produto final, usando receita, rendimento médio, estoque disponível/comprometido, embalagem, déficit e custo estimado.
- o frontend não deve duplicar a fórmula: o backend deve ser a autoridade do cálculo e retornar os dados necessários para a tela;
- a previsão não deve criar ordem de produção automaticamente até que essa transição seja decidida.

Revisar também concorrência, reservas, arredondamento/lote mínimo e comportamento de venda sem estoque. Detalhamento das regras e decisões abertas: `docs/REGRAS_NEGOCIO_CADASTROS_E_COMPRAS.md`.

### Consolidação final da regra

O desenho aprovado substitui as referências anteriores a quantidade fake finita, `outOfStock`, redução da oferta fake e status público `EM_PRODUCAO`:

- o modelo fake terá somente `active` e `infinite`;
- a baixa será uma ação manual, única e idempotente do `ADMIN` sobre o estoque físico;
- a baixa poderá ser parcial e registrará faltas por produto final;
- faltas, receitas, insumos e compra estimada aparecem somente no dashboard;
- não haverá bloqueio do pedido nem mudança do status visual do cliente por falta de estoque;
- edição após a baixa física será bloqueada e exigirá ajuste específico;
- alterações terão motivo e log antes/depois.
