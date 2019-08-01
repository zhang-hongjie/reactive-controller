package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DateOutTspr {


    @Transactional
    public void processValidationSupportNeeds() {
        log.info(" #### running   <^^^^^^^^^^>");


    }


}
