package com.example.config.reactive;

import com.example.config.scope.ValidationProjectScope;
import com.example.config.scope.ValidationProjectScopeHolder;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.function.Supplier;

public class Monos {

    public static <T> Mono<T> defer(Supplier<? extends Mono<? extends T>> supplier) {
        // Force the defer to be executed as an operator
        // This allow the ThreadLocalContextLifter to work correctly
        // Do not replace with the default method Mono.defer
        return Mono.just(1).flatMap(t -> supplier.get());
    }

    public static Mono<Void> defer(Runnable runnable) {
        return Monos.defer(() -> {
            runnable.run();
            return Mono.empty();
        });
    }

    public static Mono<JwtUserWithToken> currentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (JwtUserWithToken) securityContext.getAuthentication().getPrincipal());
    }

    public static Mono<String> currentScopeValidationProjectId() {
        return ValidationProjectScopeHolder.getContext().map(ValidationProjectScope::getValidationProjectId);
    }
    
    public static Mono<Tuple2<String, JwtUserWithToken>> mergeCurrentProjectWithCurrentUser(){
        return Mono.zip(currentScopeValidationProjectId(), currentUser());
    }

}
