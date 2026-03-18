# Sports Event Calendar Backend (Sportradar Challenge)

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Assumptions and Design Decisions](#assumptions-and-design-decisions)
- [Testing](#testing)
- [Frontend README Reference](#frontend-readme-reference)

## Overview

This application is the backend API for the Sports Event Calendar challenge.
It provides endpoints to:
- Retrieve sports events and related reference data
- Filter events by sport, league, and date
- Create new events with validation
- Manage and query venue records
- Persist data in PostgreSQL with schema versioning via Flyway

The API is designed to support the calendar-based frontend and return fully structured event data (status, season, league, sport, venue, and teams).

## Features

### Event API
- **List Events**: `GET /api/events`
- **Filter Events**: Optional query parameters `sportId`, `leagueId`, and `date`
- **Event Details**: `GET /api/events/{id}`
- **Create Event**: `POST /api/events` with request body validation and relational checks

### Reference Data API
- **Sports**: `GET /api/sports`, `GET /api/sports/{id}`
- **Leagues**: `GET /api/leagues`, `GET /api/leagues/{id}`
- **Seasons**: `GET /api/seasons`, `GET /api/seasons/{id}`
- **Teams**: `GET /api/teams`, `GET /api/teams/{id}`
- **Statuses**: `GET /api/statuses`, `GET /api/statuses/{id}`
- **Venues**: `GET /api/venues`, `GET /api/venues/{id}`, `POST /api/venues`

### Validation and Error Handling
- Bean Validation on DTOs and request parameters
- Positive ID validation for path/query parameters
- Consistent error payloads through global exception handling
- Friendly HTTP status mapping for validation, not found, conflict, and server errors

### Data and Migrations
- Flyway migrations for schema creation and seed data
- Seeded sample data includes sports, leagues, seasons, venues, statuses, teams, and events
- JPA/Hibernate validation mode for schema safety on startup

## Technology Stack

- **Java** (17)
- **Spring Boot** (4.0.3)
- **Spring Web MVC** - REST API layer
- **Spring Data JPA** - Persistence and ORM
- **Spring Validation** - Request validation
- **Flyway** - Database migrations and seed scripts
- **PostgreSQL** - Relational database
- **Maven Wrapper** - Build and dependency management
- **JUnit 5 + Mockito + Spring Test** - Testing framework

## Getting Started

### Prerequisites

- **Java JDK** 17+
- **PostgreSQL** (local or remote)
- **Git**

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/sportradar-BE-challenge.git
   cd sportradar-BE-challenge
   ```

2. **Configure database connection**

   Environment variables (works with `application.properties`)
   ```bash
   DB_HOST=localhost
   DB_PORT=5432
   DB_NAME=sportradar
   DB_USER=postgres
   DB_PASSWORD=your_password_here
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows PowerShell:
   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

4. **API base URL**

   By default, the API runs at:
   ```text
   http://localhost:8080/api
   ```

### Available Commands

#### `./mvnw spring-boot:run`
Runs the backend in development mode.

#### `./mvnw test`
Runs the unit and web layer tests.

#### `./mvnw clean package`
Builds the project and creates the packaged artifact.

## Usage

### Main Endpoints

- `GET /api/events`
- `GET /api/events?sportId=1&leagueId=1&date=2026-04-08`
- `GET /api/events/{id}`
- `POST /api/events`
- `GET /api/sports`
- `GET /api/leagues`
- `GET /api/seasons`
- `GET /api/teams`
- `GET /api/statuses`
- `GET /api/venues`
- `POST /api/venues`

### Create Event Example

```json
{
  "title": "Arsenal vs Chelsea",
  "description": "Premier League fixture",
  "eventDate": "2026-04-12",
  "timeUtc": "15:30:00",
  "sportId": 1,
  "seasonId": 1,
  "venueId": 1,
  "statusId": 1,
  "teams": [
    { "teamId": 10, "role": "HOME", "score": null },
    { "teamId": 11, "role": "AWAY", "score": null }
  ]
}
```

## Project Structure

```text
sportradar-BE-challenge/
├── src/
│   ├── main/
│   │   ├── java/com/example/sportradarbe/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Request/response DTOs
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── exception/       # Global exception handling
│   │   │   ├── repository/      # Spring Data repositories
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/    # Flyway SQL migrations
│   └── test/java/com/example/sportradarbe/
│       ├── exception/           # Exception handler tests
│       └── service/             # Service-layer tests
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Assumptions and Design Decisions

### API Design
- REST-style resource endpoints under `/api`
- Read-heavy reference data endpoints for frontend dropdowns and metadata
- Event creation endpoint validates referential IDs before persistence

### Domain Modeling
- Event aggregates include venue, status, season, league, sport, and teams
- Team participation is represented through an event-team relation with role and optional score
- Statuses are modeled as reference data (`SCHEDULED`, `LIVE`, `FINISHED`, etc.)

### Validation Strategy
- Required fields are validated in DTOs (`title`, `sportId`, `seasonId`, `venueId`, `statusId`)
- Path/query IDs are required to be positive
- Invalid combinations (for example, mismatched `sportId` and season sport) return client errors

### Database and Migrations
- Flyway manages schema and deterministic seed data
- `spring.jpa.hibernate.ddl-auto=validate` prevents silent schema drift
- PostgreSQL is used as the target relational database

### Frontend Integration
- API routes align with frontend expectations:
  - `GET /events`
  - `GET /events/:id`
  - `POST /events`
- Frontend should point to `http://localhost:8080/api` (or proxy `/api` to backend)

## Testing

Run all tests with:

```bash
./mvnw test
```

Key test focus areas:
- Event creation business rules and validation logic
- API request validation and error response shape
- Not-found, conflict, and unexpected error mapping

## Frontend README Reference

This backend is part of the same Sportradar challenge solution as the frontend.

- In a monorepo setup, see the frontend documentation at `../frontend/README.md`.
- If frontend is still in a separate repository, use its repository README (for example, `sportradar-challenge/README.md`) for UI setup and usage details.

## License

This project is licensed under the terms specified in [LICENSE](LICENSE).

---

**Thanks Sportradar**

**I enjoyed building this backend API as a little practice.**
