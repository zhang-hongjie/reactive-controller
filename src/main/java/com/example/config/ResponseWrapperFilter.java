package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class ResponseWrapperFilter implements WebFilter {

    private static final String CONTENT_LENGTH_HEADER = "Content-Length";

    private static Logger logger = LoggerFactory.getLogger(ResponseWrapperFilter.class);

    public ResponseWrapperFilter() {
        logger.debug("-------Response Wrapper Filter initialized");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // get the validation project id from the header
        logger.debug("Step in Response Wrapper Filter");

//        chain.doFilter(request, new HttpServletResponseWrapper((HttpServletResponse) exchange.getResponse()) {
//            public void setHeader(String name, String value) {
//                if (!name.equalsIgnoreCase("Content-Disposition")) {
//                    super.setHeader(name, value);
//                }
//            }
//        });
//        exchange.getResponse();
//        new ServerRequestWrapper().at;



        return chain.filter(exchange).then(Mono.defer(()->{

            HttpHeaders headers = exchange.getResponse().getHeaders();

            if (headers.containsKey(CONTENT_LENGTH_HEADER)) {
//                headers.remove(CONTENT_LENGTH_HEADER);
            }

            return Mono.empty();
        }));


//        List<String> validationProjectScopeIds = exchange.getRequest().getHeaders().get(CONTENT_LENGTH_HEADER);
//        if (validationProjectScopeIds != null && !validationProjectScopeIds.isEmpty()) {
//            String scope = validationProjectScopeIds.get(0);
//            logger.debug("Setting validation scope to {}", scope);
//            ValidationProjectScope validationProjectScope = ValidationProjectScope.builder()
//                    .validationProjectId(scope)
//                    .build();
//            // store the validation project scope in the reactor context
//            Mono<Void> result = chain.filter(exchange).subscriberContext(ValidationProjectScopeHolder.withValidationProjectScope(Mono.just(validationProjectScope)));
//            logger.debug("Step out Validation Project Scope Filter");
//
//            HttpHeaders headers = exchange.getResponse().getHeaders();
//            return result;
//        } else {
//            Mono<Void> result = chain.filter(exchange);
//            logger.debug("Step out Validation Project Scope Filter");
//
//            HttpHeaders headers = exchange.getResponse().getHeaders();
//            return result;
//        }
    }

}
