# Autenticação e Autorização

## Escopo atual desejado

A autenticação deve ser simples e suficiente para o estágio atual.

- Spring Security.
- JWT Bearer Token.
- Senha com `PasswordEncoder` seguro.
- Sem OAuth social.
- Sem Authorization Server próprio.
- Sem refresh token até existir necessidade concreta.

## Naturezas de autenticação

Existem dois fluxos separados:

### Dashboard interno

- login administrativo próprio;
- acesso apenas aos endpoints internos;
- poucos usuários conhecidos;
- papéis implementados: `ADMIN` e `EMPLOYEE`.

### E-commerce

- login de cliente próprio;
- acesso apenas aos endpoints externos permitidos;
- clientes classificados como `PF` ou `PJ`;
- o tipo do cliente define a política de preço;
- cliente nunca recebe role administrativa.

Os fluxos podem usar a mesma tabela de credenciais, mas endpoints, authorities e validações devem permanecer explícitos.

Exemplo conceitual:

- `/api/internal/auth/login`
- `/api/external/auth/login`
- `/api/internal/**`
- `/api/external/**`

Os paths definitivos dependem do OpenAPI.

## Modelo mínimo

A modelagem deve evitar papéis sem necessidade.

Conceitos mínimos:

- conta/credencial;
- papel de usuário: `ADMIN`, `EMPLOYEE` ou `CUSTOMER`;
- tipo de cliente: `PF` ou `PJ`, aplicável somente a cliente;
- estado ativo/inativo, caso necessário.

Não criar `pricingTier` enquanto PF/PJ representar integralmente a regra de preço.

`EDITOR` e `OPERADOR` são nomes antigos. Use `EMPLOYEE`. Esse papel acessa somente Itens, Receitas, Compras, Produção, Estoque e Vendas. Dashboard, Financeiro, Operações, Sócios e Usuários são exclusivos de `ADMIN`; o `SecurityConfig` ainda precisa ser restringido.

## JWT

O token deve conter somente claims necessárias:

- subject estável;
- tipo de usuário;
- tipo de cliente quando aplicável;
- authorities necessárias;
- issued at;
- expiration.

Não colocar no token:

- senha;
- dados financeiros;
- informações pessoais desnecessárias;
- preço calculado;
- objetos inteiros serializados.

## Regras obrigatórias

- o backend é a única autoridade de autorização;
- nunca confiar em role, tipo de cliente ou preço enviados pelo frontend;
- endpoint interno exige autenticação administrativa;
- endpoint externo autenticado exige conta de cliente;
- `401` para credencial ausente, inválida ou expirada;
- `403` para usuário autenticado sem permissão;
- senha nunca é retornada, logada ou armazenada em texto puro;
- chave JWT vem de configuração externa;
- duração do token vem de configuração;
- CORS é configurado por ambiente;
- endpoints públicos devem ser explicitamente permitidos, não liberados por padrão amplo.

## Logout

Com JWT stateless sem refresh token, logout é responsabilidade do cliente ao remover o token. Não criar blacklist ou persistência de token sem requisito real de revogação imediata.

## Separação de Partner

`Partner` é o sócio interno envolvido em rateios financeiros. Não representa usuário cliente PJ e não deve ser reutilizado como entidade de autenticação comercial.

Regra de negócio adicional: somente uma conta de dashboard com papel `ADMIN` pode ser cadastrada ou associada como sócio. Isso não cria um `Partner` automaticamente; a criação/associação é uma operação explícita. O vínculo deve permitir rastrear compras e contas por usuário administrativo. A associação é 1:1 e imutável após criada; inativação não deve apagar histórico.
