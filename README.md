## Rent Store Service

### Setup database
[./database/README.md](database/README.md)

### Build and run
[./rent-store-service-core/README.md](rent-store-service-core/README.md)

### Local run
- Run database container
  `docker-compose up -d rent-store-service-db`
- Run database migrations
  `./gradlew flywayMigrate -Dflyway.configFiles=../flyway/{ENV}/{SCRIPT}.properties`
- Run application local in IDE
  `run com/rent/store/RentStoreServiceApplication.main()`
- Check health check endpoint is UP
  `http://localhost:80/api/v1/actuator/health`
