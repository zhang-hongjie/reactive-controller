package com.example.config.log;

import com.example.config.log.mdc.MdcData;
import com.example.config.reactive.Monos;
import org.apache.catalina.connector.ResponseFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.AbstractListenerServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Instant;

public class TomcatAccessLogFilter implements WebFilter {

    private Logger logger = LoggerFactory.getLogger(TomcatAccessLogFilter.class);

    private static final String TOMCAT_ACCESS_LOG_FILTER_START_DATE = "TomcatAccessLogFilter#startDate";

    @Value("${logging.access.enabled}")
    private boolean accessLogEnabled;

    public TomcatAccessLogFilter() {
        logger.debug("Tomcat Access Log Filter initialized");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        logger.debug("Step in Tomcat Access Log Filter");
        if (accessLogEnabled) {
            exchange.getAttributes().put(TOMCAT_ACCESS_LOG_FILTER_START_DATE, Instant.now());
            exchange.getAttributes().put("org.apache.tomcat.sendfile.filename","a.txt");



            Mono<Void> result = chain.filter(exchange)
                    .thenEmpty(Monos.defer(() -> {
                        HttpHeaders headers = exchange.getResponse().getHeaders();

                        if (exchange.getResponse() instanceof AbstractListenerServerHttpResponse) {
                            AbstractListenerServerHttpResponse response = ((AbstractListenerServerHttpResponse) exchange.getResponse());
                            Object nativeResponse = response.getNativeResponse();
                            if (nativeResponse instanceof ResponseFacade) {
                                ResponseFacade facade = (ResponseFacade) nativeResponse;
                                MdcData data = new MdcData();
                                try {
                                    data.putResponseTime(exchange.getAttribute(TOMCAT_ACCESS_LOG_FILTER_START_DATE), Instant.now());
                                    int status = facade.getStatus();
                                    data.putStatusCode(HttpStatus.resolve(status));
                                    data.putStatusHttpCode(status);
                                    data.pushToMdc();
                                } catch (IllegalStateException ise) {
                                    logger.warn("Can not read property from ResponseFacade", ise);
                                } finally {
                                    logger.info("access log");
                                    data.clear();
                                }
                            } else {
                                logger.warn("NativeResponse from exchange is not an ResponseFacade, Access Log Filter can not work");
                            }
                        } else {
                            logger.warn("ServerHttpResponse from exchange is not an AbstractServerHttpResponse, Access Log Filter can not work");
                        }
                    }));
            logger.debug("Step out Tomcat Access Log Filter");
            return result;
        } else {
            Mono<Void> result = chain.filter(exchange);
            logger.debug("Step out Tomcat Access Log Filter");
            return result;
        }
    }

}
