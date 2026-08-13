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
