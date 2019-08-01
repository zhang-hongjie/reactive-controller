package com.example.config.context;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.stream.Stream;

import static com.example.config.Eval.defaultIfExOrNull;


public class DisabledProfilesCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment() == null ? true
                : defaultIfExOrNull(() -> metadata.getAllAnnotationAttributes(DisabledProfiles.class.getName()).get("value").stream(), Stream.empty())
                    .flatMap(o -> Stream.of((String[]) o))
                    .noneMatch(profile-> context.getEnvironment().acceptsProfiles(profile));
    }
}