package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MdcDataFilter implements WebFilter {

    private static Logger logger = LoggerFactory.getLogger(MdcDataFilter.class);
    public static String AUDIT_USERNAME = "auditUsrName";
    private Environment environment;

    public MdcDataFilter(Environment environment) {
        this.environment = environment;
        logger.debug("Mdc Data Filter Filter initialized");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.debug("Step in Mdc Data Filter");
        // setting env
//        MDC.put(environment.getActiveProfiles());
//        ServerHttpRequest request = exchange.getRequest();
//        data.putRequestUrl(request.getURI());
//        data.putRequestMethod(request.getMethod());
//        data.putRemoteAddress(request.getRemoteAddress());
        // execute the chain
        Mono<Void> result = chain.filter(exchange).subscriberContext(context -> {
            // setting username
            Optional<Mono<SecurityContext>> securityContext = context.getOrEmpty(SecurityContext.class);
            if (securityContext.isPresent()) {
                // this looks bad, because we block, but the security context is extracted from the Jwt Token
                // there is no IO involved while reading the token, so this call does not block the current thread
                // @see com.renault.digital.pval.shared.technical.security.JwtWebFilter
                Authentication authentication = securityContext.get().block().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    Object user = authentication.getPrincipal();
                    MDC.put(AUDIT_USERNAME, user.toString());
                } else {
                    logger.debug("No authentication available or not authenticated");
                }

            } else {
                logger.debug("Context does not contain security context");
            }
            return context;
        });
        logger.debug("Step out in Mdc Data Filter");
        return result;
    }

}
