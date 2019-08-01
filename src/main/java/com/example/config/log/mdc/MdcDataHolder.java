package com.example.config.log.mdc;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Optional;

public class MdcDataHolder {

    private static final Class<MdcData> MDC_DATA_KEY = MdcData.class;

    public static Mono<MdcData> getContext() {
        return Mono.subscriberContext()
                .filter(c -> c.hasKey(MDC_DATA_KEY))
                .flatMap(c -> c.<Mono<MdcData>>get(MDC_DATA_KEY));
    }

    public static Context withMdcData(MdcData mdcData) {
        return Context.of(MDC_DATA_KEY, mdcData);
    }

    public static Optional<MdcData> fromContext(Context context) {
        return context.getOrEmpty(MDC_DATA_KEY);
    }

}
