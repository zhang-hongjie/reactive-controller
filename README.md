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
