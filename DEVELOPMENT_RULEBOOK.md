# BRATATOUILLE — DEVELOPMENT RULEBOOK (SOURCE OF TRUTH)

---

# 0. PURPOSE

You are working on an operational system for a gourmet food production company.

This system is NOT:
- a generic CRUD
- a startup showcase
- a marketing dashboard

This system IS:
- a production system
- a stock system
- a financial system
- a cost system
- an operational control system

The system MUST support:
- operational clarity
- financial accuracy
- production traceability
- scalable operations
- low operational friction

---

# 0.1 PRODUCT PHILOSOPHY

System MUST feel:
- operational
- modern
- premium
- calm
- responsive
- frictionless

Users SHOULD feel:
- productive
- organized
- confident
- in control

System MUST reduce operational stress.

Operational systems already create cognitive load.

UI and workflows MUST compensate by being:
- clear
- breathable
- predictable
- lightweight

---

# 0.2 PRIMARY BUSINESS GOALS

System MUST:
- guarantee real cost accuracy
- ensure stock consistency
- provide financial visibility
- support production planning
- preserve operational traceability
- support future scalability

---

# 0.3 GLOBAL RULES

### MUST:

- responses MUST be in brazilian portuguese
- AI MUST read existing source before implementing
- AI MUST follow existing patterns
- AI MUST preserve user changes and report the files actually changed
- AI MUST respect OpenAPI contract

### MUST NOT:

- invent architecture
- invent contracts
- invent naming
- introduce random conventions

---

# 0.4 RULE DEFINITIONS

- MUST → mandatory
- MUST NOT → forbidden
- SHOULD → recommended
- MAY → optional

---

# 0.5 RULE PRIORITY ORDER

When rules conflict, follow this order:

1. Business rule confirmed by the product owner
2. OpenAPI contract for the technical API shape
3. Domain consistency and security invariants
4. Intentional existing behavior and tests
5. Rulebook architecture rules
6. UX/UI rules

OpenAPI is not authority for deciding business policy. A technically valid contract that delegates price, identity, authorization or critical state transitions to a client MUST be reported and corrected through a coordinated contract change.

---

# 1. CORE PRINCIPLES

1. cost must be real
2. stock must be accurate
3. production must be traceable
4. financial must close
5. simplicity over abstraction
6. operational clarity first
7. avoid unnecessary complexity
8. follow existing patterns
9. domain owns rules
10. frontend reflects backend reality
11. operational simplicity is a feature
12. user energy is precious

---

# 2. BACKEND ARCHITECTURE

## Tech Stack

- Java
- Spring Boot
- Monolith modularized by domain
- OpenAPI contract-first
- Controllers GENERATED
- Delegates IMPLEMENTED manually
- DTOs GENERATED
- JPA entities per domain

---

## 2.1 LAYER RESPONSIBILITIES

### Controller

MUST NOT:
- contain business logic

### Delegate

MUST NOT:
- contain business logic

### Mapper

MUST NOT:
- contain business logic

### Service

MUST:
- orchestrate use cases

MUST NOT:
- contain persistence hacks
- become god class

### Domain / Entity

MUST:
- contain business rules

---

## 2.2 FORBIDDEN

### MUST NOT:

- duplicate business rules
- create random architecture patterns
- bypass domain validation
- modify infrastructure without need
- spread business rules across layers

---

# 3. CODE STYLE

## MUST:

1. read existing code
2. follow exact patterns
3. preserve conventions
4. preserve naming
5. preserve architecture consistency

---

## 3.1 ENUM RULE

### MUST:

- follow OpenAPI EXACTLY
- preserve enum values
- preserve enum naming

### MUST NOT:

- rename enums
- "improve" enums semantically
- refactor enum contracts

Reason:
- DB consistency
- API consistency
- frontend consistency
- contract integrity

---

## 3.2 STANDARD CONVENTIONS

### MUST:

- ID = Long
- jakarta.persistence
- @GeneratedValue(strategy = IDENTITY)
- LocalDateTime
- @CreationTimestamp
- @UpdateTimestamp
- operationId MUST match delegate
- delegates MUST use @Override

---

# 4. OPENAPI CONTRACT RULE

OpenAPI is the ABSOLUTE technical source of truth.

Rulebook defines:
- business philosophy
- business concepts
- operational rules
- architectural constraints

OpenAPI defines:
- DTOs
- enums
- request payloads
- response payloads
- field names
- API contracts

If both differ:
- OpenAPI wins technically
- Rulebook wins conceptually

---

# 5. ACCESS CONTROL

## ADMIN

MUST:
- have full access

---

## EMPLOYEE

MUST manage:
- items
- recipes
- purchases
- production
- stock

MUST NOT access:
- financial summaries
- partner balances
- profit visibility

`EDITOR` and `OPERADOR` are obsolete names. `EMPLOYEE` MUST NOT access dashboard overview, partners, operational costs/losses, zero-cost entries, users or any financial view. The frontend already applies this matrix; the backend `SecurityConfig` still requires alignment.

---

# 6. ITEM DOMAIN

Item represents any manageable operational entity.

Examples:
- ingredient
- packaging
- sellable product

---

## 6.1 ITEM RULES

### MUST:

- maintain unit consistency
- preserve unit after creation
- preserve traceability

### MUST NOT:

- change unit after creation
- mix incompatible units
- perform automatic conversion

---

## 6.2 UNIT CONVERSION

Current system version DOES NOT support conversion.

Example:
- purchase in KG
- usage in GRAM

This MUST be manually standardized operationally.

---

## 6.3 FUTURE CONVERSION SUPPORT

System MAY support conversion in the future ONLY IF:
- fully traceable
- fully validated
- operationally safe

---

# 7. PARTNER DOMAIN

Partners represent operational financial ownership.

---

## 7.1 PARTNER SPLIT

Partner split is a GROUP operation.

### MUST:

- use active partners
- total active split = EXACTLY 100%

### MUST NOT:

- active split > 100%
- active split < 100%

---

## 7.2 PARTNER MANAGEMENT

### MUST:

- support editing
- support inactivation
- support redistribution

### MUST:

- require redistribution when partner becomes inactive

---

# 8. PURCHASE DOMAIN

Purchase represents financial stock entry.

---

## 8.1 PURCHASE RULES

### MUST:

- totalValue > 0
- totalAmount > 0
- represent real financial transaction
- increase stock
- affect financial system
- affect cost history

### MUST NOT:

- exist without financial impact
- allow zero total amount

---

## 8.2 PURCHASE SPLIT

Purchase SHOULD automatically use active default partner split.

Custom split is EXCEPTION flow.

### UI SHOULD:

- hide custom split complexity
- expose custom split ONLY when enabled

---

# 9. STOCK ENTRY TYPES

Stock entry is NOT equal to purchase.

---

## Types

- PURCHASE
- ZERO_COST_ENTRY

---

## 9.1 ZERO COST ENTRY

### MUST:

- affect stock
- preserve traceability

### MUST NOT:

- generate financial split
- affect partner balances
- affect financial system
- reuse Purchase domain

---

# 10. RECIPE DOMAIN

Recipe defines production structure.

---

## 10.1 RECIPE RULES

### MUST:

- define output item
- define outputQuantity > 0
- contain at least 1 input
- prevent duplicate inputs
- prevent output as input
- require active recipe

### MAY:

- represent production batch
- omit loss calculation

---

# 11. PRODUCTION DOMAIN

Production is HIGH RISK operation.

---

## 11.1 PRODUCTION RULES

### MUST:

- validate stock
- use cost history
- affect stock
- generate lot
- preserve traceability

### MUST NOT:

- silently mutate history
- manually override cost

---

## 11.2 PRODUCTION EXECUTION UX

System MUST introduce friction BEFORE irreversible execution.

### MUST:

- show confirmation summary
- expose:
  - inputs
  - outputs
  - quantities
  - stock impact
  - estimated costs

### MUST:

- require explicit confirmation

---

## 11.3 PRODUCTION EDITING

### Normal users MUST NOT:

- edit production
- delete production
- rollback production

### Admin MAY:

- edit production ONLY with:
  - audit logging
  - reason tracking
  - historical traceability

System MUST NEVER silently mutate production history.

---

## 11.4 ERROR CORRECTION

Operational errors WILL happen.

System MUST correct via:
- compensation events
- adjustment entries
- traceable corrections

System MUST NOT:
- silently rewrite past events

---

# 12. COST SYSTEM

---

## 12.1 COST FORMULA

Weighted average:

```txt
SUM(totalValue) / SUM(quantity)
```

---

## 12.2 COST RULES

### MUST:

- use weighted average
- use historical data only

### MUST NOT:

- use FIFO
- use fixed cost
- allow manual override

---

## 12.3 ZERO COST IMPACT

Zero-cost entries MAY distort average cost.

### System MUST:

- expose distortion clearly
- explain zero-cost impact
- highlight risk visually

---

# 13. STOCK SYSTEM

### MUST:

- track all movements
- prevent negative stock
- preserve traceability

---

## Stock movements

- purchase → add
- production input → remove
- production output → add
- zero-cost entry → add
- adjustment → manual

---

# 14. SELLABLE STOCK

Sellable stock is independent from real stock.

### MAY:

- exceed real stock

### MUST NOT:

- block sales

### MUST:

- alert operational inconsistency

---

# 15. PRODUCTION SIMULATION

### MUST:

- not persist
- not affect stock
- not affect financial
- not generate lot

### MUST:

- use same production logic

---

# 16. LOSS SYSTEM

---

## 16.1 PRODUCTION LOSS

### MAY:

- have zero cost

---

## 16.2 OPERATIONAL LOSS

### MUST:

- quantity > 0
- derive cost automatically
- affect stock

### MUST NOT:

- accept manual cost

---

# 17. SALES DOMAIN

### MUST:

- quantity > 0
- unitPrice > 0
- affect stock
- affect sellable stock

### MAY:

- have unitCost = 0

### MUST:

- expose costIncomplete

---

# 18. FINANCIAL SYSTEM

---

## 18.1 FINANCIAL TRACKING

### MUST track:

- total spent
- payments
- balances
- cash flow

### MUST include:

- purchases
- operational costs

### MUST NOT include:

- production
- sales
- zero-cost entries

---

## 18.2 CASH FLOW

System MUST differentiate:
- incurred cost
- actual payment

Financial visibility MUST reflect:
- real money movement
- not theoretical totals only

---

# 19. DASHBOARD

---

## MUST show:

- cost per product
- stock alerts
- financial alerts
- profit estimation

---

## MUST highlight:

- incomplete cost
- negative margin
- margin anomaly
- stock inconsistency
- production issues
- zero-cost distortion

---

# 20. PRICING

### MUST support:

- PF (retail/B2C) price
- PJ (business customer) price

### MUST:

- derive PF/PJ from authenticated customer identity whenever authentication exists
- hide PJ price from PF customers when the commercial rule requires it
- keep `Partner` pricing and financial ownership completely separate from customer pricing
- treat backend-calculated price and total as authoritative

---

# 21. AI INTEGRATION

### MUST:

- require human validation
- never persist directly

---

# 22. FRONTEND ARCHITECTURE + UX/UI

Frontend is NOT independent from backend.

Frontend MUST mirror backend reality.

---

# 22.1 FRONTEND STACKS

The two frontends are independent and MUST preserve their established stacks:

### External e-commerce (`bratatouille-frontend`)

- Next.js App Router
- React and TypeScript
- Tailwind CSS 4 plus existing CSS patterns
- native `fetch` through a centralized API layer

### Internal dashboard (`bratatouille-gestao-frontend`)

- Vite, React and TypeScript
- React Router
- TanStack Query
- Tailwind CSS and Radix components
- OpenAPI-generated TypeScript types

Agents MUST NOT migrate either frontend or force a shared stack without an explicit product decision.

---

# 22.2 FRONTEND PRINCIPLES

### MUST:

- follow OpenAPI exactly
- follow existing patterns
- reuse components
- keep visual consistency
- separate API from UI
- keep pages thin
- centralize types

### MUST NOT:

- invent DTOs
- rename API fields
- duplicate business logic
- invent conventions
- create frontend-only contracts

---

# 22.3 FRONTEND UX PHILOSOPHY

Frontend SHOULD feel:
- modern
- clean
- lightweight
- operational
- premium
- calm

NOT:
- flashy
- noisy
- overanimated
- corporate-heavy

---

# 22.4 OPERATIONAL DOPAMINE

Frontend SHOULD create operational satisfaction.

Interactions SHOULD feel:
- smooth
- responsive
- intentional
- rewarding

Animations SHOULD:
- reinforce responsiveness
- reinforce flow
- reinforce confidence

Animations MUST NOT:
- interrupt workflows
- create delay
- create friction

---

# 22.5 LOW COGNITIVE LOAD

UI MUST:
- reduce mental effort
- reduce unnecessary decisions
- reduce operational fatigue

### MUST:

- keep flows obvious
- keep labels explicit
- keep actions predictable

### MUST NOT:

- rely on hidden interactions
- create ambiguous actions
- use icon-only critical actions

---

# 22.6 FORM UX

Forms are operational flows.

### MUST:

- feel fast
- feel breathable
- preserve user input
- expose validation clearly
- group related sections visually

### MUST NOT:

- compress layouts
- create giant unreadable forms
- silently fail

---

# 22.7 MODAL UX

Complex modals SHOULD:
- feel spacious
- feel comfortable
- avoid horizontal compression
- maintain visual hierarchy

### MUST NOT:

- create tiny dialogs
- create modal-within-modal
- create horizontal scrolling

---

# 22.8 TABLE UX

Tables are operational tools.

### MUST:

- remain readable
- support scanning
- align values consistently
- support loading state
- support empty state
- support error state

### SHOULD:

- support future filtering
- support future search
- support future pagination

---

# 22.9 DESIGN LANGUAGE

Design SHOULD feel:
- minimal
- modern
- premium
- trustworthy
- operational

### MUST:

- maintain spacing consistency
- maintain typography consistency
- maintain semantic colors

### MUST NOT:

- overload colors
- overload visual noise
- create decorative-only UI

---

# 22.10 RESPONSIVENESS

System MUST:
- remain usable on notebook screens
- avoid horizontal overflow
- support vertical scrolling properly

Operational systems are often used:
- side-by-side with spreadsheets
- on small notebooks
- on operational monitors

---

# 22.11 FEEDBACK RULES

User MUST always understand:
- loading state
- success state
- failure state
- validation problems

### MUST NOT:

- fail silently
- hide operational problems

---

# 22.12 ERROR PREVENTION

### MUST:

- prevent impossible states
- validate dangerous actions
- prevent invalid financial splits
- prevent invalid quantities

### SHOULD:

- preload defaults
- reduce repetitive typing
- optimize repetitive flows

---

# 22.13 API LAYER

### MUST:

- centralize HTTP client
- centralize auth handling
- centralize API error handling

### MUST NOT:

- scatter fetch logic
- duplicate endpoint paths

---

# 22.14 STATE MANAGEMENT

### MUST:

- separate UI state from server state
- keep local state local
- isolate async handling

### MUST NOT:

- duplicate loading logic
- duplicate error handling

---

# 22.15 HIBERNATE / FETCH RULES

Repositories MUST fetch required relations BEFORE mapping.

### MUST NOT rely on:

- FetchType.EAGER workaround
- accidental lazy loading
- transactional lazy loading side effects

Services MUST orchestrate explicit fetch strategy.

---

# 23. FINAL PRODUCT PRINCIPLE

Operational simplicity is a FEATURE.

Every unnecessary:
- click
- field
- modal
- animation
- abstraction
- confirmation
- visual element

creates operational fatigue.

System exists to:
- support operations
- reduce friction
- improve clarity
- improve execution speed
- improve operational confidence
