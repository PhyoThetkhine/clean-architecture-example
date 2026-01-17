# Clean Architecture — Book Management Example

This repository is a small, educational Spring Boot application that demonstrates how to apply the Clean Architecture (ports & adapters) pattern to a simple Book management system using JDBC and Thymeleaf.

## Purpose

- Explain Clean Architecture concepts.
- Separate domain entities, application (use-case) logic, and infrastructure adapters.
- Demonstrate a JDBC outbound adapter, a web inbound adapter, DTOs at the boundary, and a simple application concern (audit logging).

## Key Principles (short)

- Dependency Rule: source code dependencies point inwards — outer layers depend on inner layers, never the opposite.
- Entities & Use-cases are core and framework-agnostic. Frameworks and databases are implemented as adapters.
- Ports are interfaces defined by the inner layers; adapters implement those interfaces.

## Project mapping (folders -> layers)

- Presentation (web / UI): `src/main/java/com/clean/clean_architecture_example/adapter/in/web`
  - `BookController` — Spring MVC controller; handles HTTP requests and binds `BookDto`.
  - `BookMapper` — maps between `BookDto` and `Book` domain.
- Application (use-cases / services): `src/main/java/com/clean/clean_architecture_example/application`
  - `BookService` — orchestrates use-cases and contains application business logic (e.g., audit orchestration).
- Domain (entities / business rules): `src/main/java/com/clean/clean_architecture_example/domain`
  - `Book` — core entity
  - `AuditLog` — audit record created by application logic
- Infrastructure (adapters / persistence / external): `src/main/java/com/clean/clean_architecture_example/adapter/out/persistence`
  - `JdbcBookRepository` — `BookRepository` implemented with `JdbcTemplate` (infrastructure)
  - `JdbcAuditRepository` — `AuditRepository` implemented with `JdbcTemplate` (infrastructure)
- Ports (interfaces / boundaries): `src/main/java/com/clean/clean_architecture_example/port`
  - `BookRepository`, `AuditRepository` — interfaces that the application layer relies on
- DTOs: `src/main/java/com/clean/clean_architecture_example/dto`
  - `BookDto` — data transfer object used at the presentation boundary for safety and validation
- Resources and SQL: `src/main/resources`
  - `application.properties` — app configuration / datasource
  - `schema.sql` — demo schema (creates `books` and `audit_logs` tables)

## How this demonstrates Clean Architecture

- The `BookService` (application layer) expresses business rules and orchestrates steps — for example, when creating a book the service saves the book via the `BookRepository` port and then records an audit entry via the `AuditRepository` port. The service does not know how these ports are implemented; it just calls the interfaces.
- Persistence details (SQL, JDBC) live in outbound adapters. If you replace `JdbcBookRepository` with a JPA adapter, `BookService` remains unchanged.
- The web layer (controller) uses `BookDto` to avoid binding HTTP input directly to domain entities. Mapping is explicit (via `BookMapper`).

## Example: application logic and audit flow

1. Client (browser) submits a new book form.
2. `BookController` receives a `BookDto`, maps it to `Book` domain object, and calls `BookService.create(book)`.
3. `BookService` calls `BookRepository.save(book)` to persist the entity.
4. `BookService` then creates an `AuditLog` and calls `AuditRepository.save(audit)` to persist the audit record.

This sequence is important: the service coordinates the use-case and enforces policy (that audit entries are written after business changes). The repositories are implementation details.

## DTOs and boundary protection

- DTOs (`BookDto`) protect internal domain representation and allow validation/sanitization before mapping to domain objects.
- Use `@Valid` on controller method params and JSR-380 annotations on DTO fields to validate input.
