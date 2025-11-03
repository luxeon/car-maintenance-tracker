# Car Maintenance Tracker

## Overview
The Car Maintenance Tracker application helps vehicle owners monitor upcoming service intervals and record past maintenance. It keeps a transparent overview of every vehicle a user owns, generates maintenance plans from manufacturer or custom templates, and schedules reminders so nothing falls through the cracks.

## Key Features
- Manage multiple vehicles per user with manufacturer details and odometer tracking.
- Auto-generate maintenance checklists from reusable plan templates and personalize schedules.
- Record completed services with notes and attachments for a full maintenance history.
- Receive reminders for upcoming tasks based on calendar dates, mileage, or custom triggers.
- Expose a typed HTTP API generated from `contracts/api.yaml` for integrations and UI clients.

## Business Logic
- When a user registers a new car, the system proposes service templates based on manufacturer guidance or curated presets.
- Selecting a template copies all maintenance regulations to the vehicle, forming its initial plan.
- Users can refine plans by editing template-derived regulations or adding fully custom ones.
- Each regulation tracks both mileage and time thresholds to determine the next service due date.
- Logging a service record stamps the regulation’s “last completed” data and recalculates the next due interval.
- Users can define custom task types that remain private to their account for bespoke maintenance items.

## Getting Started
### Prerequisites
- Java 25 and Maven 3.9+
- Docker with Docker Compose for local PostgreSQL

### Local Run
1. Start the database: `docker compose -f docker/docker-compose.yml up cmt-db`.
2. Launch the service with hot reload: `mvn spring-boot:run`.
3. The application exposes REST endpoints documented in the OpenAPI contract at `contracts/api.yaml`.

### Build & Test
- `mvn clean verify` compiles sources, regenerates OpenAPI stubs, and runs the full test suite.
- `mvn generate-sources` refreshes DTOs and controller scaffolding when the API contract changes.

## Project Structure
- `src/main/java/fyi/dslab/car/maintenance/**`: Spring Boot components organized by modulith boundaries.
- `src/main/resources`: Configuration (`application.yml`) and SQL migrations.
- `src/test/java`: JUnit 5 and Testcontainers-based tests that mirror the production package layout.
- `contracts/api.yaml`: Source of truth for API schemas and endpoint definitions.
- `docker/`: Local infrastructure scripts, including the PostgreSQL container definition.

## Contributing
- Follow the existing formatting (Java 25, Spring Boot defaults) and use Lombok/MapStruct for boilerplate and DTO mapping.
- Ensure new features include both happy-path and failure-path test coverage.
- Before opening a PR, run `mvn clean verify` and document any schema or manual environment changes in the description.
