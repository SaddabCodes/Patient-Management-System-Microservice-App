# Patient Management System - Microservice App

Spring Boot microservice workspace for managing patients and billing integration. The repository currently contains a REST-based `patient-service` backed by PostgreSQL and a `billing-service` that exposes a gRPC endpoint.

## Services

### `patient-service`

- Spring Boot `4.0.5`
- Java `21`
- REST API for patient CRUD operations
- PostgreSQL persistence with Spring Data JPA
- Jakarta Validation
- OpenAPI UI via Springdoc
- gRPC client for billing account creation

Default local ports:

- HTTP: `4000`

Main endpoints:

- `GET /patients`
- `POST /patients`
- `PUT /patients/{id}`
- `DELETE /patients/{id}`

### `billing-service`

- Spring Boot `4.0.6`
- Java `21`
- WebMVC starter included
- gRPC server for billing account creation

Default local ports:

- HTTP: `4001`
- gRPC: `9001`

Current gRPC implementation:

- `createBillingAccount`

## Repository Layout

```text
.
|-- patient-service/
|-- billing-service/
|-- api-request/
|   `-- patient-service/
|-- grpc-request/
|   `-- billing-service/
|-- AGENTS.md
`-- README.md
```

Useful paths:

- `patient-service/src/main/java/com/sadcodes/patientservice`
- `billing-service/src/main/java/com/sadcodes/billingservice`
- `api-request/patient-service`
- `grpc-request/billing-service`

## Requirements

- Java `21`
- Docker Desktop or Docker Engine for container-based startup
- PostgreSQL if running `patient-service` locally without Docker

Use the Maven Wrapper included in each service instead of a global Maven install.

## Run Locally

### Windows PowerShell

Start `patient-service`:

```powershell
cd .\patient-service
.\mvnw.cmd spring-boot:run
```

Start `billing-service`:

```powershell
cd .\billing-service
.\mvnw.cmd spring-boot:run
```

### macOS / Linux

Start `patient-service`:

```sh
cd patient-service
./mvnw spring-boot:run
```

Start `billing-service`:

```sh
cd billing-service
./mvnw spring-boot:run
```

## Configuration

### `patient-service`

Default datasource settings:

- URL: `jdbc:postgresql://localhost:5432/db`
- Username: `postgres`
- Password: `1234`

Environment variable overrides:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `BILLING_SERVICE_ADDRESS`
- `BILLING_SERVICE_GRPC_PORT`

### `billing-service`

Default ports:

- `server.port=4001`
- `grpc.server.port=9001`

## Run With Docker

The compose file is located at the repository root in `docker-compose.yml` and starts:

- PostgreSQL `18`
- `patient-service`
- `billing-service`

Run it from the repository root:

```powershell
docker compose up --build
```

Or on macOS / Linux:

```sh
docker compose up --build
```

## API Requests

Sample requests are included here:

- REST samples: `api-request/patient-service`
- gRPC sample: `grpc-request/billing-service/create-billing-account.http`

## Testing

Run tests per service.

### Windows PowerShell

```powershell
cd .\patient-service
.\mvnw.cmd test
```

```powershell
cd .\billing-service
.\mvnw.cmd test
```

### macOS / Linux

```sh
cd patient-service
./mvnw test
```

```sh
cd billing-service
./mvnw test
```

Note: `patient-service` depends on PostgreSQL. If the database is not available, application startup or some test scenarios may fail.
