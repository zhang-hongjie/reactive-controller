package com.example.config.jpa;

import com.example.config.log.mdc.MdcKey;
import org.slf4j.MDC;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class ReactiveAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(MDC.get(MdcKey.AUDIT_USERNAME.getLabel()));
    }

}
