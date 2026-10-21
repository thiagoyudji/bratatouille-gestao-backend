# Proposta em discussão — preparar integrações futuras sem overengineering

**Estado:** investigação/discovery; o usuário ainda vai explicar quais são as duas APIs e os fluxos pretendidos. Não implementar integração sem especificação.

## Premissa

O ERP deve poder integrar provedores externos sem acoplar estoque, receitas, vendas ou financeiro aos SDKs externos.

## Preparação incremental recomendada

- Documentar eventos de domínio *que já existem na operação*: compra confirmada, produção concluída, baixa de estoque, pedido aprovado, expedição, custo/recebimento conciliado. **Não criar event bus agora.**
- Definir IDs estáveis, `occurredAt` com timezone, idempotency key, status e origem de requisição onde exigido.
- Usar interfaces pequenas nas bordas quando houver integração concreta (gateway/adapter), preservando regras de negócio independentes.
- Se publicação assíncrona se tornar necessária, avaliar transactional outbox e retries/dead-letter **após** um caso real.
- Contratos OpenAPI versionados/testados entre backend e dashboard, autorização mínima e dados pessoais estritamente necessários.
- Integradores nunca atualizam `stocks.quantity` diretamente: devem entrar pelos casos de uso com validação, transação e movimento auditável.
- Dados financeiros externos passam por conciliação, não equivalem automaticamente a pagamento confirmado.

## Decisões pendentes

1. Quais são as duas APIs/plataformas?
2. Fluxo de entrada, saída ou bidirecional?
3. Quais entidades estarão sincronizadas: catálogo, estoque, lotes, pedidos, recebíveis, NF-e, logística?
4. Qual lado é autoridade para cada campo?
5. Frequência, tolerância a atraso e volume de eventos?
6. Limites de segurança/PII, credenciais e autorização?

Só depois disso escrever RFC específico, contrato e plano de migração. Este arquivo **não autoriza trabalho de integração**.
