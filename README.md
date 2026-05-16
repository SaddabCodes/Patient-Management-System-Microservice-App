# Patient Management System - Microservice App

Spring Boot microservice workspace for patient management, billing integration, gateway routing, and event-driven analytics.

The repository currently contains:

- `patient-service`: REST CRUD service backed by PostgreSQL
- `billing-service`: gRPC billing service
- `api-gateway`: Spring Cloud Gateway entrypoint for patient APIs
- `analytics-service`: Kafka consumer service for patient events

## Services

### `patient-service`

- Spring Boot `4.0.5`
- Java `21`
- REST API for patient CRUD operations
- PostgreSQL persistence with Spring Data JPA
- Jakarta Validation
- OpenAPI UI via Springdoc
- gRPC client for billing account creation
- Kafka producer for patient events

Default local port:

- HTTP: `4000`

Main endpoints:

- `GET /patients`
- `POST /patients`
- `PUT /patients/{id}`
- `DELETE /patients/{id}`

### `billing-service`

- Spring Boot `4.0.6`
- Java `21`
- Web MVC support
- gRPC server for billing account creation

Default local ports:

- HTTP: `4001`
- gRPC: `9001`

Current gRPC operation:

- `createBillingAccount`

### `api-gateway`

- Spring Boot `4.0.6`
- Java `21`
- Spring Cloud Gateway Server WebFlux
- Route forwarding for patient REST traffic
- Route forwarding for patient OpenAPI docs

Default local port:

- HTTP: `4004`

Current routes:

- `/api/patients/**` -> `patient-service` with `StripPrefix=1`
- `/api-docs/patients` -> `patient-service` `/v3/api-docs`

### `analytics-service`

- Spring Boot `4.0.6`
- Java `21`
- Spring for Apache Kafka consumer
- Consumes patient events from Kafka topic `patient`

Current configuration notes:

- Kafka bootstrap servers default to `localhost:9094`
- Consumer group id is `analytics-service`
- `docker-compose.yml` maps host port `4002`, but the service config does not currently declare `server.port`

## Repository Layout

```text
.
|-- patient-service/
|-- billing-service/
|-- api-gateway/
|-- analytics-service/
|-- api-request/
|   `-- patient-service/
|-- grpc-request/
|   `-- billing-service/
|-- AGENTS.md
|-- docker-compose.yml
`-- README.md
```

Useful paths:

- `patient-service/src/main/java/com/sadcodes/patientservice`
- `billing-service/src/main/java/com/sadcodes/billingservice`
- `api-gateway/src/main/java/com/sadcodes/apigateway`
- `analytics-service/src/main/java/com/sadcodes/analyticsservice`
- `api-request/patient-service`
- `grpc-request/billing-service`

## Requirements

- Java `21`
- Docker Desktop or Docker Engine for container-based startup
- PostgreSQL if running `patient-service` locally without Docker
- Kafka if running the event-driven flow locally without Docker

Use the Maven Wrapper included in each service instead of a global Maven install.

## Run Locally

Run commands from the target service directory.

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

Start `api-gateway`:

```powershell
cd .\api-gateway
.\mvnw.cmd spring-boot:run
```

Start `analytics-service`:

```powershell
cd .\analytics-service
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

Start `api-gateway`:

```sh
cd api-gateway
./mvnw spring-boot:run
```

Start `analytics-service`:

```sh
cd analytics-service
./mvnw spring-boot:run
```

## Configuration

### `patient-service`

Default datasource settings:

- URL: `jdbc:postgresql://localhost:5432/db`
- Username: `postgres`
- Password: `1234`

Default application settings:

- `server.port=4000`
- `SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9094`
- `PATIENT_EVENTS_TOPIC=patient`

Environment variable overrides:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `BILLING_SERVICE_ADDRESS`
- `BILLING_SERVICE_GRPC_PORT`
- `SPRING_KAFKA_BOOTSTRAP_SERVERS`
- `PATIENT_EVENTS_TOPIC`

### `billing-service`

Default ports:

- `server.port=4001`
- `grpc.server.port=9001`

### `api-gateway`

Default port:

- `server.port=4004`

Configured routes:

- `/api/patients/**` forwards to `http://patient-service:4000`
- `/api-docs/patients` rewrites to `/v3/api-docs` on `http://patient-service:4000`

### `analytics-service`

Default application settings:

- `SPRING_KAFKA_BOOTSTRAP_SERVERS=localhost:9094`
- `PATIENT_EVENTS_TOPIC=patient`
- Kafka consumer group id: `analytics-service`

## Run With Docker

The compose file is located at the repository root in `docker-compose.yml`.

It currently starts:

- Kafka
- PostgreSQL `18`
- `patient-service`
- `billing-service`
- `analytics-service`

Current compose notes:

- `patient-service` depends on Kafka, PostgreSQL, and `billing-service`
- `api-gateway` exists in the repository but is not currently included in `docker-compose.yml`
- `analytics-service` is included in compose with host port mapping `4002:4002`

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

```powershell
cd .\api-gateway
.\mvnw.cmd test
```

```powershell
cd .\analytics-service
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

```sh
cd api-gateway
./mvnw test
```

```sh
cd analytics-service
./mvnw test
```

Notes:

- `patient-service` depends on PostgreSQL for normal application startup.
- Event-driven flows depend on Kafka availability.
- `api-gateway` route verification is separate from downstream service tests.
