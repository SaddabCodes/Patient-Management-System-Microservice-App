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

Use Context7 MCP to fetch current documentation whenever a task asks about a library, framework, SDK, API, CLI tool, or cloud service. This includes Spring Boot, Spring Data JPA, Jakarta Validation, Maven, PostgreSQL, Lombok, and related setup, configuration, API usage, migration, or debugging questions.

Do not use Context7 for ordinary refactoring, writing project-specific business logic, code review, or general programming concepts.

When Context7 is needed:

1. Start with `resolve-library-id` using the library or tool name and the user's question, unless the user gives an exact `/org/project` library ID.
2. Select the best match by exact name, relevance, source reputation, snippet count, and benchmark score.
3. Use `query-docs` with the selected library ID and the full user question.
4. Base the answer or implementation on the fetched documentation.

## Project Shape

This repository is a Spring Boot microservice workspace. The only service currently present is:

- `patient-service`: Spring Boot 4.0.5 application for patient management.

The service uses:

- Java 21.
- Maven Wrapper (`mvnw`, `mvnw.cmd`).
- Spring Boot starters for Web MVC, Spring Data JPA, and Validation.
- PostgreSQL runtime driver.
- Lombok as an annotation processor.
- JUnit/Spring Boot test support.

## Repository Layout

- Root `AGENTS.md`: guidance for agents working in this repository.
- `patient-service/pom.xml`: Maven project definition.
- `patient-service/src/main/java/com/sadcodes/patientservice`: application source.
- `patient-service/src/main/resources/application.yaml`: service configuration.
- `patient-service/src/test/java/com/sadcodes/patientservice`: tests.

Current domain code includes a `Patient` JPA entity under `com.sadcodes.patientservice.model`. Keep package names consistent with the existing code unless the task explicitly asks for package cleanup or renaming.

## Build And Run

Run commands from `patient-service` unless a task explicitly targets the workspace root.

On Windows PowerShell:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

On Unix-like shells:

```sh
./mvnw test
./mvnw spring-boot:run
```

Use the Maven Wrapper instead of a globally installed Maven whenever possible.

## Local Configuration

`application.yaml` currently expects PostgreSQL at:

- URL: `jdbc:postgresql://localhost:5433/patient_management_system`
- Username: `postgres`
- Password: `1234`

Do not assume the database is running during tests or local verification. If a task requires application startup or integration testing, confirm whether PostgreSQL is available or document the database dependency clearly.

## Coding Conventions

- Follow the existing Java package root: `com.sadcodes.patientservice`.
- Prefer constructor/service/controller/repository layers that match common Spring Boot practices when adding behavior.
- Use Jakarta imports (`jakarta.persistence`, `jakarta.validation`) consistently with Spring Boot 4.
- Keep validation annotations on API/domain boundaries where they enforce required patient data.
- Avoid hard-coding new credentials, ports, or environment-specific values unless the task explicitly requires it.
- Keep changes scoped to the requested service or module.

## Testing Guidance

- For code changes, run `.\mvnw.cmd test` from `patient-service` on Windows when feasible.
- If tests require a live PostgreSQL instance and it is unavailable, report that limitation instead of hiding the failure.
- Add focused tests for new service logic, validation behavior, repositories, or controllers when the change affects behavior.

## Git Safety

- Do not revert user changes unless explicitly requested.
- Treat generated `target/` output as build artifacts; do not edit it by hand.
- Keep commits and patches focused on the requested change.

## Commit Messages

- When the user asks for a git commit message, provide it in a professional conventional-commit style.
- Prefer prefixes such as `feat:`, `fix:`, `docs:`, `refactor:`, `test:`, `chore:`, `build:`, and `ci:` where appropriate.
- Keep commit messages concise, specific to the actual change, and ready to use without rewriting.
