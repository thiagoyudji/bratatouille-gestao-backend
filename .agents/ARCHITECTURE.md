# Arquitetura do Backend

## Objetivo

Manter um backend único, barato, previsível e simples de operar, sem sacrificar consistência de domínio, estoque, custo e financeiro.

## Estilo arquitetural

- Java + Spring Boot.
- Monólito modularizado por domínio.
- OpenAPI contract-first.
- Controllers e DTOs gerados.
- Delegates implementados manualmente.
- JPA por domínio.
- Um banco compartilhado.

## Organização por domínio

A organização atual por pacotes de domínio deve ser preservada. Novos módulos devem representar capacidades de negócio, não camadas globais genéricas.

Exemplos atuais:

- item;
- purchase;
- stock;
- recipe;
- production;
- lot;
- sales;
- cost;
- financial;
- partner.

Pacotes `common` devem permanecer pequenos. Um código só vai para `common` quando for realmente transversal, estável e sem pertencimento claro a um domínio. Não transformar `common` em depósito de utilitários.

## Dependência entre módulos

- dependências devem seguir o fluxo do caso de uso;
- evitar ciclos entre domínios;
- um módulo não deve manipular diretamente estado interno de outro módulo;
- coordenação entre módulos acontece no service responsável pelo caso de uso;
- quando uma regra pertence a outro domínio, invoque uma operação pública clara desse domínio em vez de duplicá-la.

## Domínios de alto risco

Mudanças nos seguintes fluxos exigem análise explícita de transação, rastreabilidade e regressão:

- compra;
- estoque;
- produção;
- custo;
- financeiro;
- fechamento financeiro;
- perda operacional;
- venda.

## Simplicidade deliberada

Não antecipar escalabilidade hipotética. Só criar nova camada, abstração ou mecanismo quando houver problema concreto no código atual.

Preferir:

- chamada direta;
- transação local;
- SQL/JPA explícito;
- fluxo síncrono;
- logging útil;
- deploy único.

Evitar:

- abstrações de mensageria sem mensageria;
- interfaces com uma implementação sem motivo;
- adapters e ports puramente cerimoniais;
- eventos que escondem sequência crítica;
- pipelines internos difíceis de rastrear.

## OpenAPI

O contrato define:

- paths;
- operationId;
- requests e responses;
- DTOs;
- enums expostos;
- nomes e formatos públicos.

Quando um caso de uso exigir mudança de contrato:

1. explicar a mudança;
2. atualizar o OpenAPI;
3. regenerar fontes;
4. adaptar delegate/mapper/service;
5. validar consumidores.

Nunca modificar diretamente classe gerada para contornar o contrato.
