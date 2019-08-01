package com.example.config.context;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class ThreadLocalContextLifter<T> implements BiFunction<Scannable, CoreSubscriber<? super T>, CoreSubscriber<? super T>> {

    private static Logger logger = LoggerFactory.getLogger(ThreadLocalContextLifter.class);

    @Autowired
    private List<ThreadLocalContextListener> listeners;

    @PostConstruct
    @SuppressWarnings(value = "unchecked")
    public void register() {
        Function<? super Publisher<T>, ? extends Publisher<T>> lift = Operators.lift(this);
        Hooks.onEachOperator((Function<? super Publisher<Object>, ? extends Publisher<Object>>) lift);
    }

    @Override
    public CoreSubscriber<? super T> apply(Scannable scannable, CoreSubscriber<? super T> coreSubscriber) {
        return new ThreadLocalContextCoreSubscriber<>(coreSubscriber);
    }

    final class ThreadLocalContextCoreSubscriber<U> implements CoreSubscriber<U> {

        private final CoreSubscriber<? super U> delegate;
        private Context context;

        ThreadLocalContextCoreSubscriber(CoreSubscriber<? super U> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Context currentContext() {
            return delegate.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            // capture context (does not change after subscription)
            this.context = delegate.currentContext();
            logger.trace("onSubscribe {} {}", delegate, this.context);
            // push and clear
            listeners.forEach(listener -> listener.push(this.context));
            delegate.onSubscribe(s);
            listeners.forEach(ThreadLocalContextListener::clear);
        }

        @Override
        public void onNext(U u) {
            logger.trace("onNext {} {}", delegate, u);
            listeners.forEach(listener -> listener.push(this.context));
            delegate.onNext(u);
            listeners.forEach(ThreadLocalContextListener::clear);
        }

        @Override
        public void onError(Throwable t) {
            delegate.onError(t);
        }

        @Override
        public void onComplete() {
            delegate.onComplete();
        }
    }

}
