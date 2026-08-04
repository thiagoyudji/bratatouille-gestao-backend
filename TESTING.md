# Testing Convention

## Scope
- Keep tests close to the production package they exercise.
- Use unit tests for business rules and validation.
- Add integration tests only when persistence, web wiring, or security filters are being verified end to end.

## Package Layout
- Mirror the main source tree under `src/test/java`.
- Example: `src/main/java/br/com/.../financial/service/FinancialService.java` maps to `src/test/java/br/com/.../financial/service/FinancialServiceTest.java`.
- Shared test utilities live under `br.com.bratatouille.management.support`.

## Naming
- Test classes end with `Test`.
- Use one test class per production class whenever possible.
- Prefer method names that describe the rule being verified, such as `rejectsDuplicateUsername` or `validateNotClosedAllowsOpenPeriod`.

## Fixtures
- Put reusable builders and object factories in `support`.
- Keep fixture methods small and explicit.
- Avoid duplicating `ReflectionTestUtils` or object construction across tests.

## First Targets
- `auth`: login, bootstrap, and access rules.
- `financial`: balance calculation and settlement.
- `financialClosing`: open-period validation and snapshot behavior.
