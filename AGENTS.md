# Repository Guidelines

## Project Structure & Module Organization
- `src/main/java` hosts Spring Boot components under `fyi.dslab.car.maintenance.*`; OpenAPI generation writes interface scaffolding to `...tracker.user.api`.
- Configuration and SQL migrations belong in `src/main/resources/` (see `application.yml`); mirror the package structure for modular boundaries.
- `src/test/java` is reserved for JUnit 5 + Testcontainers suites; keep fixtures beside the code they exercise.
- Contracts live in `contracts/api.yaml`; regenerate client/server stubs with the OpenAPI plugin before committing changes.
- Local infrastructure scripts sit in `docker/` (PostgreSQL compose file); Maven outputs land in `target/`.

## Build, Test, and Development Commands
- `mvn clean verify` compiles sources, runs the OpenAPI generator, executes unit/integration tests, and assembles the artifact.
- `mvn spring-boot:run` launches the application using local profiles; start the database first with `docker compose -f docker/docker-compose.yml up cmt-db`.
- `mvn generate-sources` refreshes OpenAPI-generated DTOs and controllers when `contracts/api.yaml` changes.
- `mvn dependency:tree` helps troubleshoot dependency and platform alignment issues.

## Coding Style & Naming Conventions
- Use Java 25 features thoughtfully; default to four-space indentation and the standard Spring Boot formatting profile.
- Name classes with PascalCase, methods and fields in camelCase, and constants in UPPER_SNAKE_CASE.
- Place modulith boundaries at the package level; avoid cross-module imports that bypass the intended `fyi.dslab.car.maintenance.<module>` structure.
- Leverage Lombok only for boilerplate (getters, constructors) and MapStruct for DTO/entity mapping; keep manual mappers in `mapper` subpackages.

## Testing Guidelines
- Prefer JUnit Jupiter with `@SpringBootTest` or `@DataJdbcTest` and isolate DB-dependent cases behind Testcontainers-based slices.
- Name test classes `<Subject>Test` and methods `should<Behavior>`; colocate reusable fixtures under `src/test/java/.../support`.
- Ensure new functionality has happy-path and failure-path coverage; integration tests that touch the database should launch the `postgresql` Testcontainer or rely on the compose service.

## Commit & Pull Request Guidelines
- Follow the existing history: concise, imperative summaries such as `add openapi maven generator plugin`; keep to ≤72 characters when possible.
- Reference issues with `Fixes #123` in the body when applicable and summarize scope, risks, and verification.
- Before opening a PR, run `mvn clean verify`, regenerate OpenAPI artifacts if contracts changed, and attach screenshots or sample requests for API-facing work.
- PR descriptions should call out schema changes, new endpoints, and any manual steps (e.g., database migrations or compose updates).
