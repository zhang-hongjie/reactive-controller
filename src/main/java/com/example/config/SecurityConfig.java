package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity(order = Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {

        return http.authorizeExchange().pathMatchers("/**").permitAll().and().build();

    }

}
