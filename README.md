# reactive controller

### Prerequisites

Java 8, Docker

### Start prototype dev database with docker 

cd api  
docker-compose up -d

### Build from source

cd api  
./gradlew clean build

### Run with java

java -jar -Dspring.profiles.active=dev build/libs/reactive-controller-1.0-SNAPSHOT.jar

### Features
- springboot 2.1.6
- databbase server: docker postgres
- liquibase: jpa entity-> database DDL changelog -> migrate `DDL changelog` & `data fixture` to database server postgres
- hibernate/JPA/Envers
- Reactive WebFlux API
- Spring actuator API http://localhost:8080/api/actuator
- API security config
- generate swagger documentation http://localhost:8080/swagger-ui.html