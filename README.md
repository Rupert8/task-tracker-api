# Task Tracker API

A modular RESTful API application for managing projects, tasks, and task states. Built with **Java 17** and **Spring Boot 3.5.0**.

## 🛠 Tech Stack

* **Language:** Java 17
* **Framework:** Spring Boot 3.5.0
* **Database:** PostgreSQL (H2 for testing environment)
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Gradle (Multi-module architecture)
* **Libraries:** Lombok, MapStruct, QueryDSL

## 🏗 Project Structure

The application follows a multi-module architecture to separate concerns and ensure maintainability:

* `task-tracker-api-boot`: Main application entry point and configuration (`application.yml`).
* `task-tracker-api-web`: REST API layer containing the endpoints (`ProjectController`, `TaskController`, `TaskStateController`).
* `task-tracker-api-core`: Core business logic, services, and global exception handling (`CustomExceptionHandler`).
* `task-tracker-api-miscellaneous`:
    * `/store`: Database layer containing JPA Repositories and Entities.
    * `/data`: Data Transfer Objects (DTOs) and MapStruct mappers.
* `task-tracker-api-shared`: Shared utilities, configurations, and QueryDSL predicates.
* `task-tracker-api-integration-tests`: Integration tests suite for the application services.

## 🚀 Getting Started

### Prerequisites

* **JDK 17** or higher
* **PostgreSQL** running on default port `5432`
* **Gradle**

### Database Configuration

Create a PostgreSQL database named `task-tracker`. The application uses the following default credentials (configured in `application.yml`):

```properties
URL: jdbc:postgresql://localhost:5432/task-tracker
Username: postgres
Password: 1111
```

*Note: The application uses `ddl-auto: update`, so database tables will be generated automatically upon the first startup.*

### Running the Application

1. Clone the repository and navigate to the project root directory.
2. Build the project using the Gradle wrapper:

```bash
./gradlew clean build
```

3. Run the application:

```bash
./gradlew :task-tracker-api-boot:bootRun
```

The server will start and be available on `http://localhost:8081`.

## 🧪 Testing

The project includes an integration testing module using JUnit 5 and an in-memory H2 database. To run the tests:

```bash
./gradlew test
```