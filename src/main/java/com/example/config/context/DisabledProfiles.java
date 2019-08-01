package com.example.config.context;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(DisabledProfilesCondition.class)
public @interface DisabledProfiles {
    String[] value();
}
