# Plano de checkup e estabilização do backend

Documento de execução futura. Este plano registra a revisão geral combinada para a próxima etapa do projeto.

## Resumo

Executar uma revisão completa do backend, preservando alterações já existentes no worktree, e entregar:

- correções de estabilidade e consistência;
- idempotência nos fluxos críticos;
- testes unitários e de integração proporcionais ao risco;
- cobertura mínima de 85% de linhas e 75% de branches, excluindo código gerado;
- revisão arquitetural, de segurança, persistência e transações;
- documentação alinhada ao comportamento real;
- remoção apenas de código e documentação comprovadamente obsoletos.

Baseline registrado: `./mvnw test` passou com 44 testes. O projeto ainda não possui JaCoCo nem quality gate de cobertura. O worktree contém muitas alterações preexistentes, que deverão ser preservadas.

## Alterações previstas

### 1. Auditoria estrutural e de domínio

Revisar autenticação, compras, estoque, produção, vendas, custos, financeiro, fechamento e pagamentos, verificando:

- responsabilidades entre delegate, mapper, service, domínio e repository;
- regras duplicadas ou espalhadas;
- transações incompletas;
- consultas com risco de N+1;
- entidades com invariantes frágeis;
- uso de dinheiro, quantidades, datas e arredondamentos;
- dependências cíclicas ou abstrações sem benefício;
- código morto, temporário ou incompatível com o contrato OpenAPI.

As correções devem ser pequenas e locais, sem introdução de microserviços, mensageria ou novas camadas genéricas.

### 2. Idempotência e consistência

Mapear operações com risco de retry, timeout ou clique duplicado e aplicar proteção específica por caso de uso:

- webhook e reconciliação de pagamento: identificação única do evento ou transação do provedor;
- baixa de estoque e pedido: execução única por pedido e registro do resultado;
- compras, produção, perdas e lançamentos críticos: chave de operação ou restrição única quando houver risco concreto de repetição;
- fechamento financeiro: impedir fechamento duplicado do mesmo período;
- operações administrativas de atualização: semântica determinística e concorrência avaliada.

Cada mecanismo deverá ter comportamento definido para repetição, resposta consistente, constraint ou índice quando necessário e testes de repetição e concorrência relevante. Não criar uma plataforma genérica de idempotência sem necessidade comprovada.

### 3. Segurança e autorização

Alinhar a implementação à matriz documentada:

- `ADMIN`, `EMPLOYEE` e `CUSTOMER` sem aliases antigos;
- `EMPLOYEE` limitado aos módulos permitidos;
- financeiro, dashboard, operações, sócios e usuários exclusivos de `ADMIN`;
- separação explícita entre fluxos internos e e-commerce;
- remoção ou isolamento de rotas temporárias após confirmar consumidores;
- validação de que preço, identidade, role e tipo de cliente não são controláveis pelo cliente;
- revisão de CORS, JWT, Actuator e logs sensíveis.

Adicionar testes de `401`, `403`, acesso permitido e acesso negado por papel.

### 4. Estratégia de testes e cobertura

Adicionar JaCoCo ao Maven e quality gate de:

- 85% de cobertura de linhas;
- 75% de cobertura de branches;
- exclusão somente de classes geradas pelo OpenAPI e código comprovadamente não testável;
- relatório local e publicação no CI.

Priorizar testes de calculadores, rateios, custos, quantidades, arredondamentos, invariantes, compras, estoque, produção, vendas, perdas, rollback, idempotência, autenticação, autorização e repositories com queries customizadas.

Usar testes unitários sem Spring para regras puras e testes de integração para persistência, segurança, migrations, constraints e transações. Evitar testes cerimoniais e mocks de domínio puro.

### 5. Persistência e transações

Revisar:

- fronteira transacional de cada caso de uso;
- rollback sem persistência parcial;
- locks ou atualização atômica apenas onde houver read-modify-write crítico;
- fetches e quantidade de queries;
- constraints de unicidade, valores positivos e relacionamentos;
- índices baseados em consultas reais;
- compatibilidade entre H2 de teste e PostgreSQL de produção.

Adicionar cenários de falha no meio de compras, produção, vendas, baixa de estoque e fechamento financeiro.

### 6. Documentação e limpeza

Comparar código, OpenAPI, migrations, testes e os documentos de desenvolvimento, roadmap, pendências, regras de negócio, workflow e deploy.

Atualizar o status de itens já implementados, separar histórico de pendências vigentes e remover somente:

- controllers temporários comprovadamente substituídos;
- documentação duplicada;
- instruções incompatíveis com a configuração atual;
- código sem referências, contrato ou consumidor;
- nomes documentais antigos ainda usados indevidamente.

Qualquer remoção com impacto em contrato ou frontend dependerá da confirmação dos consumidores. Na ausência dos repositórios consumidores, registrar como pendência em vez de apagar por inferência.

### 7. CI e evolução futura

Fortalecer o pipeline com build limpo, testes, quality gate de cobertura, validação do OpenAPI, geração reproducível, verificação de migrations e relatório claro de falhas.

Registrar, sem implementar infraestrutura antecipada, as evoluções futuras reais: separação definitiva das superfícies interna e externa, estoque vendável, auditoria administrativa, concorrência de estoque, decisões de preço e cliente PJ e integração completa com os frontends.

## Critérios de aceitação

- `./mvnw verify` executa com sucesso;
- cobertura atende 85% de linhas e 75% de branches no código manual;
- existem testes críticos de segurança, transação e idempotência;
- repetição de operações críticas não duplica estoque, financeiro, produção ou pagamento;
- nenhum endpoint permitido viola a matriz de autorização;
- OpenAPI, migrations, código e documentação estão coerentes;
- nenhuma migration histórica foi alterada;
- nenhum código gerado foi editado manualmente;
- o diff final contém somente mudanças justificadas;
- limitações externas e decisões abertas ficam documentadas.

## Premissas

- O backend continuará como monólito modular com transações locais.
- Mudanças públicas de contrato serão feitas somente com revisão dos consumidores.
- As alterações atualmente presentes no worktree pertencem ao proprietário e não serão revertidas.
- A limpeza será conservadora: somente itens comprovadamente obsoletos serão removidos.
