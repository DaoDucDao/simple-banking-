# Bank Account Service

A growing banking-style API — started as a Spring Boot learning project (Java 17, Maven), now expanding into a fuller multi-feature banking platform (not a real production bank, but enough breadth to actually show off).

## Stack
- Spring Boot 3.5 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`)
- PostgreSQL 16 (via Docker)
- Lombok

## Done

- [x] `entity/Account.java` — JPA entity mapped to the `account` table
- [x] `repository/AccountRepository.java` — JPA repository (data access layer)
- [x] `service/AccountService.java` — business logic: create account, deposit, withdraw, get balance
- [x] `controller/AccountController.java` — REST endpoints exposing the service
- [x] Centralized error handling (custom exceptions + global exception handler)
- [x] Request validation (Bean Validation on DTOs)
- [x] Build and run the app, verified endpoints end-to-end with Postman
- [x] Swap H2 for a real persistent database (PostgreSQL via Docker)
- [x] Transaction history — every deposit/withdrawal recorded as an auditable `Transaction`, queryable per account

## Roadmap

### Core banking features
- [x] User accounts & authentication (Spring Security + JWT) — each user can own multiple bank accounts
- [x] Multiple account types (CHECKING, SAVINGS) per user
- [x] Account-to-account transfers (not just isolated deposit/withdraw)
- [x] Transaction history — every deposit/withdrawal/transfer recorded as an auditable `Transaction`, queryable per account
- [x] Paginated & filterable transaction history endpoint
- [x] Scheduled interest accrual on savings accounts (Spring `@Scheduled`)
- [x] Account freeze/close functionality

### Infrastructure & polish
- [x] Swap H2 for a real persistent database (PostgreSQL)
- [ ] Configure HTTPS (TLS certificate)
- [x] API documentation (Swagger/OpenAPI via springdoc)
- [ ] Unit & integration tests (JUnit, Mockito)
- [ ] Dockerized app (Dockerfile + docker-compose with Postgres)
- [ ] Structured logging
