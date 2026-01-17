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
