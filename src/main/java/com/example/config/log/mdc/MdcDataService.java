package com.example.config.log.mdc;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
@Slf4j
public class MdcDataService {

    public static <T> T relayMdc(Supplier<T> supplier) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        T result = supplier.get();
        if (contextMap != null) MDC.setContextMap(contextMap);
        return result;
    }

    public static <T> T block(Mono<T> mono) {
        return relayMdc(() -> mono.block());
    }

    public static <T> List<T> block(Flux<T> flux) {
        List<T> block = flux.collectList().block();
        return relayMdc(() -> block);
    }
}

