package com.example.config.scope;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class ValidationProjectScopeHolder {

    private static final Class<ValidationProjectScope> VALIDATION_PROJECT_SCOPE_KEY = ValidationProjectScope.class;

    public static Mono<ValidationProjectScope> getContext() {
        return Mono.subscriberContext()
                .filter(c -> c.hasKey(VALIDATION_PROJECT_SCOPE_KEY))
                .flatMap(c -> c.<Mono<ValidationProjectScope>>get(VALIDATION_PROJECT_SCOPE_KEY));
    }


    public static Context withValidationProjectScope(Mono<? extends ValidationProjectScope> validationProjectScope) {
        return Context.of(VALIDATION_PROJECT_SCOPE_KEY, validationProjectScope);
    }

}

