package com.example.config;

import com.example.config.scope.ValidationProjectScopeFilter;
import com.example.config.scope.WithValidationProjectScopeAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

//@Configuration
//@EnableAspectJAutoProxy
public class ValidationProjectScopeConfig {

    @Bean
    public WithValidationProjectScopeAdvice withValidationProjectScopeAdvice() {
        return new WithValidationProjectScopeAdvice();
    }

    @Bean
    @Order(FilterOrder.VALIDATION_PROJECT_SCOPE_FILTER)
    public ValidationProjectScopeFilter validationProjectScopeFilter() {
        return new ValidationProjectScopeFilter();
    }

}
