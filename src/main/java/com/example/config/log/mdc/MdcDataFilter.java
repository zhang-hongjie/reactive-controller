package com.example.config.log.mdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class MdcDataFilter implements WebFilter {

    private static Logger logger = LoggerFactory.getLogger(MdcDataFilter.class);

    private Environment environment;

    public MdcDataFilter(Environment environment) {
        this.environment = environment;
        logger.debug("Mdc Data Filter Filter initialized");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.debug("Step in Mdc Data Filter");
        MdcData data = new MdcData();
        logger.debug("Setting mdc data {} ", data);
        // setting env
        data.putEnvironment(environment.getActiveProfiles());
        // TODO Add sollicitation id
        // setting request url
        ServerHttpRequest request = exchange.getRequest();
        data.putRequestUrl(request.getURI());
        // setting request method
        data.putRequestMethod(request.getMethod());
        // setting remote address
        data.putRemoteAddress(request.getRemoteAddress());
        // execute the chain
        Mono<Void> result = chain.filter(exchange).subscriberContext(context -> {
            // setting username
            Optional<Mono<SecurityContext>> securityContext = context.getOrEmpty(SecurityContext.class);
            if (securityContext.isPresent()) {
                // this looks bad, because we block, but the security context is extracted from the Jwt Token
                // there is no IO involved while reading the token, so this call does not block the current thread
                // @see com.renault.digital.pval.shared.technical.security.JwtWebFilter
                data.putUsername(securityContext.get().block());
            } else {
                logger.debug("Context does not contain security context");
            }
            logger.debug("Add mdc data to context {}", data);
            return context.putAll(MdcDataHolder.withMdcData(data));
        });
        logger.debug("Step out in Mdc Data Filter");
        return result;
    }

}
