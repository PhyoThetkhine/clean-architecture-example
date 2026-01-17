# Clean Architecture — Book Management Example

This repository is a small, educational Spring Boot application that demonstrates how to apply the Clean Architecture (ports & adapters) pattern to a simple Book management system using JDBC and Thymeleaf.

## Purpose

- Explain Clean Architecture concepts to students.
- Separate domain entities, application (use-case) logic, and infrastructure adapters.
- Demonstrate a JDBC outbound adapter, a web inbound adapter, DTOs at the boundary, and a simple application concern (audit logging).

## Key Principles (short)

- Dependency Rule: source code dependencies point inwards — outer layers depend on inner layers, never the opposite.
- Entities & Use-cases are core and framework-agnostic. Frameworks and databases are implemented as adapters.
- Ports are interfaces defined by the inner layers; adapters implement those interfaces.

## Project mapping (folders -> layers)

- Domain (core models): `src/main/java/com/clean/clean_architecture_example/domain`
  - `Book` (entity)
  - `AuditLog` (application event / audit record)
- Ports (interfaces): `src/main/java/com/clean/clean_architecture_example/port`
  - `BookRepository` (port used by use-cases to persist books)
  - `AuditRepository` (port used to record audit events)
- Application / Use-cases: `src/main/java/com/clean/clean_architecture_example/application`
  - `BookService` — implements use-cases and contains application logic (e.g., orchestration and audit recording)
- Outbound adapters (infrastructure): `src/main/java/com/clean/clean_architecture_example/adapter/out/persistence`
  - `JdbcBookRepository` — `BookRepository` implemented with `JdbcTemplate`
  - `JdbcAuditRepository` — `AuditRepository` implemented with `JdbcTemplate`
- Inbound adapters (web/UI): `src/main/java/com/clean/clean_architecture_example/adapter/in/web`
  - `BookController` — Spring MVC controller that receives HTTP requests and uses DTOs at the boundary
  - `BookMapper` — converts between DTOs and domain objects
- DTOs: `src/main/java/com/clean/clean_architecture_example/dto`
  - `BookDto` — data transfer object used at the web boundary for safety and validation
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

## Running the app (quick)

1. Create a MySQL database (or change connection settings in `src/main/resources/application.properties`):

```sql
CREATE DATABASE books_db;
```

2. Update `src/main/resources/application.properties` with MySQL credentials.

3. Run:

```bash
mvn spring-boot:run
```

4. Browse the UI at: `http://localhost:8080/books`

## Schema

- `src/main/resources/schema.sql` creates the `books` and `audit_logs` tables for demo purposes. In production, prefer a migration tool (Flyway/Liquibase).

## Teaching exercises (suggested)

- Unit test `BookService` with mocks for `BookRepository` and `AuditRepository` verifying audit records are written.
- Add validation to `BookDto` and display errors in Thymeleaf forms.
- Replace `JdbcBookRepository` with a JPA adapter — demonstrate adapter swap without changing use-cases.
- Add an audit viewer UI page to show saved `audit_logs`.

## Files to inspect during a walkthrough

- `src/main/java/com/clean/clean_architecture_example/domain/Book.java`
- `src/main/java/com/clean/clean_architecture_example/port/BookRepository.java`
- `src/main/java/com/clean/clean_architecture_example/adapter/out/persistence/JdbcBookRepository.java`
- `src/main/java/com/clean/clean_architecture_example/application/BookService.java`
- `src/main/java/com/clean/clean_architecture_example/adapter/in/web/BookController.java`
- `src/main/resources/schema.sql`

## Further reading

- Uncle Bob Martin — Clean Architecture
- Alistair Cockburn — Hexagonal Architecture (Ports & Adapters)
- Eric Evans — Domain-Driven Design

---

If you want, I can now:

- Add unit tests that assert audit behavior.
- Add `@Valid` validation to `BookDto` and show errors in the template.
- Add an audit viewer endpoint and template.

Tell me which follow-up to implement and I will add it next.
# Clean Architecture Example — Book Management (Spring Boot + Thymeleaf + MySQL)

This small demo shows a simple Clean Architecture layout with:

- Domain model: `Book`
- Port: `BookRepository` interface
- Adapter (outbound): `JdbcBookRepository` using `JdbcTemplate`
- Application/service layer: `BookService` (use-cases)
- Adapter (inbound): `BookController` (Spring MVC + Thymeleaf)

Quick setup

1. Create a MySQL database named `books_db` (or change `spring.datasource.url` in `src/main/resources/application.properties`).

```sql
CREATE DATABASE books_db;
```

2. Update `src/main/resources/application.properties` with your MySQL username/password.

3. Run the application:

```bash
mvn spring-boot:run
```

The application will run on http://localhost:8080 and the books UI is at http://localhost:8080/books

Notes

- The project uses `schema.sql` to create the `books` and `audit_logs` tables on startup.
- `BookService` demonstrates application logic: after repository operations it writes an `AuditLog` via the `AuditRepository`.
- This is intentionally small and focused on demonstrating architecture, JDBC adapter, and Thymeleaf UI.
