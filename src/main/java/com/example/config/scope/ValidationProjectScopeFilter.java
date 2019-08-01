package com.example.config.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

public class ValidationProjectScopeFilter implements WebFilter {
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String VALIDATION_PROJECT_SCOPE_HEADER = "x-validation-project-scope";
    private static final String VALIDATION_PROJECT_SCOPE_PATH = "validationProjectId";

    private static Logger logger = LoggerFactory.getLogger(ValidationProjectScopeFilter.class);

    public ValidationProjectScopeFilter() {
        logger.debug("Validation Project Scope Filter initialized");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // get the validation project id from the header
        logger.debug("Step in Validation Project Scope Filter");
        List<String> validationProjectScopeIds = exchange.getRequest().getHeaders().get(VALIDATION_PROJECT_SCOPE_HEADER);
        if (validationProjectScopeIds != null && !validationProjectScopeIds.isEmpty()) {
            String scope = validationProjectScopeIds.get(0);
            logger.debug("Setting validation scope to {}", scope);
            ValidationProjectScope validationProjectScope = ValidationProjectScope.builder()
                    .validationProjectId(scope)
                    .build();
            // store the validation project scope in the reactor context
            Mono<Void> result = chain.filter(exchange).subscriberContext(ValidationProjectScopeHolder.withValidationProjectScope(Mono.just(validationProjectScope)));
            logger.debug("Step out Validation Project Scope Filter");

            HttpHeaders headers = exchange.getResponse().getHeaders();
            if (headers.containsKey(CONTENT_LENGTH_HEADER)) {
                headers.remove(CONTENT_LENGTH_HEADER);
            }
            return result;
        } else {
            Mono<Void> result = chain.filter(exchange);
            logger.debug("Step out Validation Project Scope Filter");

            HttpHeaders headers = exchange.getResponse().getHeaders();
            if (headers.containsKey(CONTENT_LENGTH_HEADER)) {
                headers.remove(CONTENT_LENGTH_HEADER);
            }
            return result;
        }
    }

}
