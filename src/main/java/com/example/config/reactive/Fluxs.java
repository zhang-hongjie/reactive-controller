package com.example.config.reactive;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.function.Supplier;

public class Fluxs {

    private Fluxs() {

    }

    public static <T> Flux<T> defer(Supplier<? extends Flux<? extends T>> supplier) {
        // Force the defer to be executed as an operator
        // This allow the ThreadLocalContextLifter to work correctly
        // Do not replace with the default method Mono.defer
        return Flux.just(1).flatMap(t -> supplier.get());
    }


    // check if we need
    public static Flux<Void> defer(Runnable runnable){
        return Fluxs.defer(()->{
            runnable.run();
            return Flux.empty();
        });
    }

    public static Flux<Tuple2<String, JwtUserWithToken>> mergeCurrentProjectWithCurrentUser(){
        return Flux.zip(Monos.currentScopeValidationProjectId(), Monos.currentUser());
    }
}
