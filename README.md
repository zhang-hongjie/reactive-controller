# reactive controller

### Prerequisites

Java 8

### Build from source

cd api  
./gradlew clean build

### Run with java

java -jar -Dspring.profiles.active=dev build/libs/reactive-controller-1.0-SNAPSHOT.jar


### SpringBoot 2.1.6 bug:

tomcat + webflux + gzip  
=> problem of CONENT_LENGTH+TRANSFER_ENCODING for <b>Mono/POJO</b>, not for <b>FLUX</b>

-  compile("org.apache.tomcat.embed:tomcat-embed-core:8.5.31") // NOK
-  compile("org.apache.tomcat.embed:tomcat-embed-core:9.0.21") // NOK
-  compile("org.apache.tomcat.embed:tomcat-embed-core:9.0.22") // NOK

The unit test passed, 
<b>but failed test by postman or curl