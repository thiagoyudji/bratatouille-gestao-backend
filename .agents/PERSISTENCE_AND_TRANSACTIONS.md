# Persistência, Fetch e Transações

## Fronteira transacional

A transação deve ficar no service que representa o caso de uso completo.

Operações compostas críticas devem ser atômicas. Exemplos:

### Compra

- registrar compra;
- registrar itens e rateios;
- aumentar estoque;
- criar movimentos;
- refletir no histórico de custo e financeiro.

### Produção

- validar disponibilidade;
- consumir entradas;
- registrar movimentos;
- gerar saída e lote;
- preservar custo histórico.

### Venda

- registrar pedido e itens;
- atualizar estoque aplicável;
- preservar custo e indicadores necessários.

Falha em qualquer etapa deve provocar rollback do caso de uso, salvo regra explícita de compensação.

## Concorrência

Não adicionar locking preventivamente sem analisar o conflito real.

Ao alterar estoque ou saldo:

- identificar se há read-modify-write;
- avaliar concorrência esperada;
- preferir atualização atômica ou optimistic locking quando suficiente;
- usar pessimistic locking somente quando a contenção e o risco justificarem;
- sempre testar cenário de conflito quando mecanismo de lock for introduzido.

Para o volume atual e poucos usuários internos, simplicidade vence, mas consistência de estoque não pode depender de sorte.

## Idempotência

Idempotência só é obrigatória quando houver risco concreto de repetição, como:

- clique duplo;
- retry do cliente;
- timeout com resultado desconhecido;
- reenvio de comando crítico.

Não criar plataforma genérica de idempotência. Quando necessária, usar uma chave de operação ou restrição única adequada ao caso de uso e definir claramente o comportamento de repetição.

## Fetch

- repositories devem buscar as relações necessárias antes do mapper;
- não usar `FetchType.EAGER` como remendo;
- evitar lazy loading acidental dentro de mapper;
- usar `join fetch`, `EntityGraph`, projection ou consulta específica conforme o caso;
- revisar número de queries em operações com coleções;
- evitar `findById` dentro de loop quando puder buscar em lote.

## JPA e entidades

- preservar invariantes no domínio;
- evitar setters públicos indiscriminados;
- cuidado com `equals/hashCode` em entidades JPA;
- não expor entidade diretamente pela API;
- cascades e orphan removal devem ser deliberados;
- relações bidirecionais só quando realmente necessárias.

## Banco e migrations

Quando migrations forem adicionadas:

- usar mecanismo definido pelo projeto;
- nunca editar migration já aplicada;
- criar constraints no banco para invariantes estruturais importantes;
- criar índices com base em consultas reais;
- manter precisão e escala explícitas para dinheiro e quantidade;
- alterações destrutivas exigem plano de migração.
