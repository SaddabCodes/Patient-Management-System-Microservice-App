<!-- context7 -->
Use Context7 MCP to fetch current documentation whenever the user asks about a library, framework, SDK, API, CLI tool, or cloud service -- even well-known ones like React, Next.js, Prisma, Express, Tailwind, Django, or Spring Boot. This includes API syntax, configuration, version migration, library-specific debugging, setup instructions, and CLI tool usage. Use even when you think you know the answer -- your training data may not reflect recent changes. Prefer this over web search for library docs.

Do not use for: refactoring, writing scripts from scratch, debugging business logic, code review, or general programming concepts.

## Steps

1. Always start with `resolve-library-id` using the library name and the user's question, unless the user provides an exact library ID in `/org/project` format
2. Pick the best match (ID format: `/org/project`) by: exact name match, description relevance, code snippet count, source reputation (High/Medium preferred), and benchmark score (higher is better). If results don't look right, try alternate names or queries (e.g., "next.js" not "nextjs", or rephrase the question). Use version-specific IDs when the user mentions a version
3. `query-docs` with the selected library ID and the user's full question (not single words)
4. Answer using the fetched docs
<!-- context7 -->

---

# Patient Management System - Microservice App

## Documentation Lookup

Use Context7 MCP to fetch current documentation whenever a task asks about a library, framework, SDK, API, CLI tool, or cloud service. This includes Spring Boot, Spring Data JPA, Jakarta Validation, Maven, PostgreSQL, Lombok, Springdoc OpenAPI, Docker, and Docker Compose.

Do not use Context7 for ordinary refactoring, writing project-specific business logic, code review, or general programming concepts.

When Context7 is needed:

1. Start with `resolve-library-id` using the library or tool name and the user's question, unless the user gives an exact `/org/project` library ID.
2. Select the best match by exact name, relevance, source reputation, snippet count, and benchmark score.
3. Use `query-docs` with the selected library ID and the full user question.
4. Base the answer or implementation on the fetched documentation.

## Project Shape

This repository is a Spring Boot microservice workspace with two services:

- `patient-service`: Spring Boot 4.0.5 service for patient CRUD operations backed by PostgreSQL, with a gRPC client for billing integration.
- `billing-service`: Spring Boot 4.0.6 service under the `com.sadcodes.billingservice` package that exposes a gRPC billing endpoint.

Shared characteristics:

- Java 21.
- Maven Wrapper in each service (`mvnw`, `mvnw.cmd`).
- Spring Boot 4.x.
- JUnit / Spring Boot test support.

`patient-service` currently includes:

- Spring Web MVC, Spring Data JPA, and Jakarta Validation.
- PostgreSQL runtime driver.
- Lombok annotation processing.
- Springdoc OpenAPI UI.
- gRPC client integration to call `billing-service`.
- Docker assets (`Dockerfile`, `docker-compose.yml`).

`billing-service` currently includes:

- Spring Web MVC.
- gRPC server support on port `9001`.
- Protobuf definition and generated-service build tooling.
- A basic application test.

## Repository Layout

- Root `AGENTS.md`: workspace guidance for agents.
- Root `README.md`: workspace overview, setup, and run instructions.
- `patient-service/`: main patient management microservice.
- `billing-service/`: billing microservice.
- `api-request/patient-service/`: HTTP request samples for patient endpoints.
- `grpc-request/billing-service/`: gRPC request samples for billing-service.

Important paths:

- `patient-service/pom.xml`
- `patient-service/src/main/java/com/sadcodes/patientservice`
- `patient-service/src/main/resources/application.yaml`
- `patient-service/src/test/java/com/sadcodes/patientservice`
- `patient-service/docker-compose.yml`
- `patient-service/Dockerfile`
- `billing-service/pom.xml`
- `billing-service/src/main/java/com/sadcodes/billingservice`
- `billing-service/src/main/resources/application.yaml`
- `billing-service/src/main/proto/billing_service.proto`
- `billing-service/src/test/java/com/sadcodes/billingservice`
- `grpc-request/billing-service/create-billing-account.http`

Current patient domain code includes controllers, DTOs, mapping, repository, service, exception handling, and a `Patient` JPA entity under `com.sadcodes.patientservice.model`.

## Build And Run

Run commands from the target service directory unless a task explicitly targets the workspace root.

Windows PowerShell examples:

```powershell
cd .\patient-service
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

```powershell
cd .\billing-service
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

Unix-like examples:

```sh
cd patient-service
./mvnw test
./mvnw spring-boot:run
```

```sh
cd billing-service
./mvnw test
./mvnw spring-boot:run
```

Use the Maven Wrapper instead of a globally installed Maven whenever possible.

## Local Configuration

`patient-service/src/main/resources/application.yaml` currently defaults to:

- URL: `jdbc:postgresql://localhost:5432/db`
- Username: `postgres`
- Password: `1234`
- Port: `4000`

These can be overridden with:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

`patient-service/docker-compose.yml` provisions:

- PostgreSQL 18 on `localhost:5432`
- `patient-service` on port `4000`
- `billing-service` on ports `4001` and `9001`

`billing-service/src/main/resources/application.yaml` currently sets:

- HTTP port `4001`
- gRPC port `9001`

Do not assume PostgreSQL is running during tests or local verification. If a task requires application startup or integration testing, confirm whether the database is available or document the dependency clearly.

## Coding Conventions

- Keep package names consistent with the target service: `com.sadcodes.patientservice` for patient-service code and `com.sadcodes.billingservice` for billing-service code.
- Prefer standard Spring layering: controller, service, repository, DTO/mapper, and exception handling where appropriate.
- Use Jakarta imports (`jakarta.persistence`, `jakarta.validation`) consistently in Spring Boot 4 code.
- Keep validation annotations on request or domain boundaries where they enforce required data.
- Avoid hard-coding new credentials, ports, hosts, or environment-specific values unless the task explicitly requires it.
- Keep changes scoped to the requested service or module.
- Do not move code between services unless the task explicitly asks for cross-service refactoring.

## Testing Guidance

- For code changes, run the relevant service tests when feasible: `patient-service` with `.\mvnw.cmd test`, and `billing-service` with `.\mvnw.cmd test`.
- If tests require a live PostgreSQL instance and it is unavailable, report that limitation instead of hiding the failure.
- Add focused tests for new controller, service, repository, validation, or exception behavior when the change affects behavior.
- Treat `billing-service` as a separate service for verification; do not assume patient-service tests cover it.

## API Notes

- `patient-service` exposes REST endpoints under `/patients`.
- Request examples already exist under `api-request/patient-service/`; update them when endpoint contracts change.
- `patient-service` includes Springdoc OpenAPI UI support, so documentation-related tasks should preserve or intentionally update that integration.
- `billing-service` exposes a gRPC `createBillingAccount` operation; keep the `.proto` contract and sample requests in sync when changing the gRPC API.

## Git Safety

- Do not revert user changes unless explicitly requested.
- Treat generated `target/` output as build artifacts; do not edit it by hand.
- Keep commits and patches focused on the requested change.

## Commit Messages

- When the user asks for a git commit message, provide it in a professional conventional-commit style.
- Prefer prefixes such as `feat:`, `fix:`, `docs:`, `refactor:`, `test:`, `chore:`, `build:`, and `ci:` where appropriate.
- Keep commit messages concise, specific to the actual change, and ready to use without rewriting.
