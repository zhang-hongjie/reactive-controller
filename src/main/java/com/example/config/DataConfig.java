package com.example.config;

import com.example.config.jpa.ReactiveAuditorAware;
import com.zaxxer.hikari.HikariConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
public class DataConfig implements WebFluxConfigurer {

    @Autowired
    private HikariConfig hikariConfig;

    @Bean
    public Scheduler connectionPoolSizedScheduler() {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(hikariConfig.getMaximumPoolSize()));
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new ReactiveAuditorAware();
    }

}
