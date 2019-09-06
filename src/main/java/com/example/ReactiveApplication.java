package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude = { ErrorWebFluxAutoConfiguration.class })
@EnableJpaRepositories
@EnableSwagger2WebFlux
public class ReactiveApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ReactiveApplication.class);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    @PostConstruct
    void started() {
        // default time zone utc
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
