## Rent Store Service Database

### Setup Docker Oracle Database Container
`docker-compose up -d rent-store-service-db`

### Flyway schema
In order to be able to run the flyway scripts on your local desktop, you would need to refer to 
`flyway.url=jdbc:oracle:thin:@localhost:1521:domain`.

### Clean
`./gradlew flywayClean -Dflyway.configFiles=../flyway/{ENV}/{SCRIPT}.properties`

### Migrate
`./gradlew flywayMigrate -Dflyway.configFiles=../flyway/{ENV}/{SCRIPT}.properties`

### Local Docker Oracle Migrate
`./gradlew flywayMigrate -Dflyway.configFiles=../flyway/local/docker-oracle.properties`

### Example flyway properties file
```
flyway.url = "db url"
flyway.user = "db user"
flyway.password = "db password"
flyway.driver = "jdbc driver"
flyway.schemas = "db schemas list separated by comma"
