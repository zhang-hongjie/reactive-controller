//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.config;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Eval {
    private static final Logger log = LoggerFactory.getLogger(Eval.class);
    private static final String THE_SUPPLIER_CAN_T_BE_NULL = "the supplier can't be null.";

    public Eval() {
    }

    public static <T> T eval(Eval.SupplierWithThrowable<T> supplier) {
        Objects.requireNonNull(supplier, "the supplier can't be null.");

        try {
            return supplier.get();
        } catch (Throwable var2) {
            throw new TechnicalException(var2);
        }
    }

    public static void eval(Eval.RunnableWithThrowable runnable) {
        Objects.requireNonNull(runnable, "the runnable can't be null.");

        try {
            runnable.run();
        } catch (Throwable var2) {
            throw new TechnicalException(var2);
        }
    }

    public static <T> T nullIfEx(Eval.SupplierWithThrowable<T> supplier) {
        Objects.requireNonNull(supplier, "the supplier can't be null.");

        try {
            return Optional.ofNullable(supplier.get()).orElseGet(() -> {
                return null;
            });
        } catch (Throwable var2) {
            log.info("exception occurred (use log level DEBUG for the detail of exception), return null");
            log.debug("", var2);
            return null;
        }
    }

    public static <T> T defaultIfEx(Eval.SupplierWithThrowable<T> supplier, T defaultValue) {
        Objects.requireNonNull(supplier, "the supplier can't be null.");

        try {
            return Optional.ofNullable(supplier.get()).orElseGet(() -> {
                return null;
            });
        } catch (Throwable var3) {
            log.info("exception occured (use log level DEBUG for the detail of exception), return defaultValue " + defaultValue);
            log.debug("", var3);
            return defaultValue;
        }
    }

    public static <T> T defaultIfExOrNull(Eval.SupplierWithThrowable<T> supplier, T defaultValue) {
        Objects.requireNonNull(supplier, "the supplier can't be null.");

        try {
            return Optional.of(supplier.get()).get();
        } catch (Throwable var3) {
            log.info("exception occured (use log level DEBUG for the detail of exception), return defaultValue " + defaultValue);
            log.debug("", var3);
            return defaultValue;
        }
    }

    public static String defaultIfExOrNullOrBlank(Eval.SupplierWithThrowable<String> supplier, String defaultValue) {
        Objects.requireNonNull(supplier, "the supplier can't be null.");

        try {
            return (String)Optional.of(Strings.emptyToNull(((String)supplier.get()).trim())).get();
        } catch (Throwable var3) {
            log.info("exception occured (use log level DEBUG for the detail of exception), return defaultValue " + defaultValue);
            log.debug("", var3);
            return defaultValue;
        }
    }

    public static <T> T fallbackIfExOrNull(Eval.SupplierWithThrowable<T> supplier, Function<Throwable, T> fallback) {
        Objects.requireNonNull(supplier, "the supplier can't be null.");
        Objects.requireNonNull(fallback, "the fallback function can't be null");

        try {
            return Optional.of(supplier.get()).get();
        } catch (Throwable var3) {
            return fallback.apply(var3);
        }
    }

    public static void fallbackIfEx(Eval.RunnableWithThrowable runnable, Consumer<Throwable> fallback) {
        Objects.requireNonNull(runnable, "the runnable can't be null.");
        Objects.requireNonNull(fallback, "the fallback consumer can't be null");

        try {
            runnable.run();
        } catch (Throwable var3) {
            fallback.accept(var3);
        }

    }

    public static <T> T coalesce(Eval.SupplierWithThrowable<T>... suppliers) {
        if (suppliers != null && suppliers.length != 0) {
            try {
                return Optional.of(suppliers[0].get()).get();
            } catch (Throwable var2) {
                return (T) coalesce((SupplierWithThrowable[])Arrays.copyOfRange(suppliers, 1, suppliers.length));
            }
        } else {
            return null;
        }
    }

    @FunctionalInterface
    public interface RunnableWithThrowable {
        void run() throws Throwable;
    }

    @FunctionalInterface
    public interface SupplierWithThrowable<T> {
        T get() throws Throwable;
    }
}
