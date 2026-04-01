# Devices API
A Spring Boot 4 REST API to manage devices, connected to a PostgreSQL database, fully containerized with Docker.

---
## Features
- Create a new device.
- Fully and/or partially update an existing device.
- Fetch a single device.
- Fetch all devices.
- Fetch devices by brand.
- Fetch devices by state.
- Fetch devices by state.

**Validations:**
- Creation time cannot be updated.
- Name and brand properties cannot be updated if the device is in use.
- In use devices cannot be deleted.
---
## Technologies
- Java 21
- Spring Boot 4.0.2
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Maven
- JUnit 5 + Mockito
- Lombok
---
## Running Tests
Run the unit tests with Maven
mvn test 
- unit test class is in src/test/java.
- Mockito is used to mock the repository.
- Tests cover creation, deletion, fetching, updating and exception handling.
---
## Docker Setup
### 1. Build the Spring Boot Jar
Run the Following command in the project route: mvn clean package
### 2. Start the containers
docker-compose up --build
- db container port 54432
- app container port 8080

## Api Endpoint table
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/v1/device` | Create a device |
| GET    | `/api/v1/device/id/{id}` | Get device by ID |
| GET    | `/api/v1/devices` | Get all devices |
| GET    | `/api/v1/devices/brand/{brand}` | Get devices by brand |
| GET    | `/api/v1/devices/state/{state}` | Get devices by state |
| PUT    | `/api/v1/device/{id}` | Update device |
| DELETE | `/api/v1/device/{id}` | Delete device |

---
## Environment Configuration
Springboot connects to PostgreSQL using:
```properties
spring.datasource.url=jdbc:postgresql://db:5432/postgres
spring.datasource.username=nulhart
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```
- db Refers to Docker Compose service name
- ddl-auto=update ensures table are created automatically and persist if you only restart the db or app.
---
## Notes
Application is fully containerized for easy setup and portability
- PostgreSQL data is persisted via Docker volumes
- Java 21 is required to match Spring Boot 4
- Unit tests ensure correctness before containerization  
---
## Optional Commands
### Stop Containers
docker-compose down

### Remove Volumes( reset database)
docker-compose down -v

---
## Future Improvements
- **Pagination & Filtering:** Add pagination and sorting for large datasets (e.g., GET `/devices`)
- **Authentication & Authorization:** Secure the API endpoints with JWT or OAuth2
- **DTO Validation Enhancements:** Add more granular input validation and custom error messages
- **Integration Tests:** Add full integration tests with an in-memory or test PostgreSQL database
- **API Documentation:** Integrate Swagger/OpenAPI for automatic API docs
- **CI/CD Pipeline:** Automate build, test, and Docker deployment using GitHub Actions or Jenkins
- **Error Logging & Monitoring:** Implement centralized logging and monitoring (e.g., ELK stack, Prometheus, Grafana)
