# Padronização proposta — backend

## Objetivo

Preservar o monólito modular Spring Boot/PostgreSQL, minimizar duplicações de regra e permitir manutenção por uma equipe pequena. Este documento **não substitui** \`AGENTS.md\`, \`DEVELOPMENT_RULEBOOK.md\` e \`.agents/**\`.

## Regras de projeto por PR

1. **Uma responsabilidade por método.** Não misturar validação HTTP, regra de negócio, persistência, mapeamento, logging, chamadas externas e transição de estado no mesmo método. Extrair por etapa de negócio quando houver ganho real de leitura/teste; não pulverizar métodos triviais.
2. **Controller gerado fica gerado.** OpenAPI -> geração -> delegate (adaptação) -> caso de uso (orquestração) -> domínio (invariantes) -> repository (consulta/gravação). Mapper sem regra de decisão financeira.
3. **Erros tipados.** Não usar resposta genérica \`500\` para dados inválidos. Padronizar erro de validação, não encontrado, conflito e acesso negado preservando \`ApiErrorCode\`.
4. **Cálculo preciso.** Dinheiro e quantidades no backend com \`BigDecimal\`. Definir unidade canônica (\`G/ML/UN\`) e conversões permitidas (\`KG->G\`, \`L->ML\`), precisão do banco e regras de arredondamento por operação.
5. **Não converter massa em volume** sem densidade explicitamente modelada por item — e não assumir densidade 1. Ingredientes cadastrados em \`G\` e \`ML\` podem representar itens distintos.
6. **Fonte de verdade única.** Backend calcula saldo/custo/preço/margens e valida privilégios; frontend apenas apresenta e auxilia preenchimento. Nunca usar valor digitado como saldo existente no banco.
7. **Transação explícita e curta.** Compra, produção e baixa de estoque devem gravar mudanças e movimentos em uma transação. Jamais manter transação JPA aberta enquanto aguarda checkout HTTP de terceiro.
8. **Concorrência observável.** Ao alterar saldo, usar estratégia atômica e teste de disputa entre chamadas. Lock em registro existente não resolve automaticamente corrida de criação de registro.
9. **Idempotência.** Webhook e confirmação de pagamento devem aceitar repetição sem duplicar efeitos; chave de transação externa única. Checkout precisa política explícita de retry.
10. **Histórico não reescrito.** Corrigir lançamento por ajuste rastreável, com usuário, data, motivo, estado antes/depois; migrar por novas Flyway, nunca editar versões já aplicadas.
11. **Modelos com semântica explícita.** Diferenciar ingrediente/batelada, embalagem/pote, potes produzidos, perda física e consumo em simulação hipotética.
12. **Módulos estáveis.** Evitar microserviços, event sourcing, brokers ou camadas artificiais. Domain service pequeno apenas quando uma regra pura compartilhada justifica.
13. **Queries monitoradas.** Não corrigir N+1 por \`EAGER\`. Preferir query por caso, projection, batch ou fetch join com testes SQL count.
14. **Configuração segura.** Credenciais por variáveis/secret manager; nunca commitar \`.env\` com segredos; logs sem token, senha, endereço pessoal ou payload completo de pagamento.
15. **Testes proporcionais.** Para entradas inválidas, roles, arredondamento, duplicidade, retry, estoque zero, ausência de registro, múltiplas operações concorrentes e falha no meio da transação.
16. **API versionada conscientemente.** Quando mudar \`required\`, enum, unidade ou campo, atualizar OpenAPI, geração Java, types TypeScript, docs de release e consumidores, com compatibilidade temporária quando necessária.

## Checklist obrigatório

- [ ] Confirmar \`master\` e regras em \`AGENTS.md\`.
- [ ] Mapear contrato, delegate, service, entity, repository, testes e telas consumidoras.
- [ ] Descrever condição inicial, invariantes e efeito colateral.
- [ ] Escolher menor desenho coerente e riscos de rollback.
- [ ] Não permitir entrada negativa ou unidade incompatível; validar limites \`NUMERIC(19,3)\`.
- [ ] Testes de sucesso, erro, concorrência e rollback onde aplicável.
- [ ] Rodar \`./mvnw test\` e informar resultado real; não afirmar green sem evidência.
- [ ] Verificar schema OpenAPI efetivamente publicado e geração.
- [ ] Revisar SQL/índices/queries com evidências ao otimizar.
- [ ] Separar refactor estrutural de feature e alteração operacional de dados.
- [ ] Handoff em \`docs/research/handoffs/<TASK_ID>/00_SUMMARY.md\` com arquivos alterados, testes, pendências e instrução de reversão.

## Convenções de commit

\`fix(stock): ...\`, \`feat(production): ...\`, \`refactor(financial): ...\`, \`test(auth): ...\`, \`docs(audit): ...\`; PRs referenciam BE-XXX. Branches curtas; sem merge se CI falha; deploy de produção somente após verificação apropriada.

## Antipadrões a eliminar com cuidado

- Vários \`findByItemId\` em laço sobre centenas de itens;
- \`findAll\` sem paginação para listas ilimitadas;
- uso de \`null\` para significar simultaneamente "desconhecido", "zero" e "não se aplica";
- custo financeiro incompleto apresentado como custo comprovado;
- \`currentStock\` derivado de uma entrada HTTP hipotética;
- contratuais \`required\` divergentes entre OpenAPI/servidor/TypeScript;
- classe service gigante fazendo tudo e dependência cruzada desnecessária;
- duplicar entidade \`Partner\` para representar cliente \`PJ\`.
