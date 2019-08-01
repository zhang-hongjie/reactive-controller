package com.example.config.scope;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Aspect
public class WithValidationProjectScopeAdvice {

    @Around("@annotation(com.renault.digital.pval.shared.technical.scope.WithValidationProjectScope)")
    public Object checkValidationProjectScope(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Class<?> returnType = methodSignature.getReturnType();

        Mono<ValidationProjectScope> toInvoke = ValidationProjectScopeHolder.getContext()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation Project Scope Header is missing")));

        if (Mono.class.isAssignableFrom(returnType)) {
            return toInvoke.flatMap(scope -> proceed(pjp));
        }

        if (Flux.class.isAssignableFrom(returnType)) {
            return toInvoke.flatMapMany(scope -> proceed(pjp));
        }

        return toInvoke.flatMapMany(scope -> Flux.from(proceed(pjp)));
    }

    @SuppressWarnings(value = "unchecked")
    private static <T extends Publisher<?>> T proceed(ProceedingJoinPoint pjp) {
        try {
            return (T) pjp.proceed();
        } catch (Throwable throwable) {
            throw Exceptions.propagate(throwable);
        }
    }

}
