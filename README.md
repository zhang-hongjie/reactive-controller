# reactive controller

### Prerequisites

Java 8

### Build from source

cd api  
./gradlew clean build

### Run with java

java -jar -Dspring.profiles.active=dev build/libs/reactive-controller-1.0-SNAPSHOT.jar


### SpringBoot 2.1.6 bug:

<b>Bug context:</b> springboot 2.1.6 + webflux + tomcat(tested on 8.5.31/9.0.21/9.0.22) + gzip
 
<b>Bug description:</b> when the endpoint returns POJO/Collection<POJO>/Mono<POJO>/Mono<Collection<POJO>> `(not FLUX<POJO>)`, The unit test passed, but failed when call api by postman or curl

<b>Cause:</b>
 - in the response, contains both CONTENT_LENGTH and TRANSFER_ENCODING, so the response is not valid
 - in the response, the value of CONTENT_LENGTH is the one before compression, so the `status` is `failed` in chrome

<b>Solution:</b>  
Clean the invalid headers using the `NEW` rule of http 1.1 protocol [RFC7230](https://tools.ietf.org/html/rfc7230#section-3.3), ~~ignore the `old` rules of obsoleted [RFC2616](https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html)~~

 - Rule 1: A sender MUST NOT send a Content-Length header field in any message
             that contains a Transfer-Encoding header field.
 - Rule 2: a server MUST NOT send Content-Length in a response unless its field-value equals the
             decimal number of octets that would have been sent in the payload
             body of a response.
             for example, we should remove Content-Length if the compressed content length is unknown (Ref org.apache.coyote.CompressionConfig)