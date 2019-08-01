package com.example.config.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.logging.Level;

public class ReactorLogFilter implements WebFilter {

    private Logger logger = LoggerFactory.getLogger(ReactorLogFilter.class);

    @Value("${logging.reactor.enabled}")
    private boolean reactorLogEnabled;

    public ReactorLogFilter() {
        logger.debug("Reactor Log Web Filter initialized");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if(reactorLogEnabled) {
            logger.debug("Step in Reactor Log Web Filter");
            Mono<Void> result = chain.filter(exchange).log(this.getClass().getCanonicalName(),
                    Level.INFO,
                    SignalType.SUBSCRIBE,
                    SignalType.REQUEST,
                    SignalType.CANCEL,
                    SignalType.ON_SUBSCRIBE,
                    SignalType.ON_NEXT,
                    SignalType.ON_ERROR,
                    SignalType.ON_COMPLETE,
                    SignalType.AFTER_TERMINATE);
            logger.debug("Step out Reactor Log Web Filter");
            return result;
        } else {
            return chain.filter(exchange);
        }
    }

}
