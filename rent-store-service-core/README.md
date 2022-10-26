## Rent Store service Core

### Build
Gradle command to build
`./gradlew :rent-store-service-core:build`

### Local run
- Run
  `run com/rent/store/RentStoreServiceApplication.main()`
- Check health check endpoint is UP
  `http://localhost:80/api/v1/actuator/health`
