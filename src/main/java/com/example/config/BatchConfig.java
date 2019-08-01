package com.example.config;

import com.example.config.context.DisabledProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Separate configuration to only enable scheduling/starting of batches when
 * needed
 */
@Configuration
@EnableScheduling
@DisabledProfiles({ "test", "IT" })
public class BatchConfig {

	@Autowired
	private DateOutTspr dateOutTspr;

	@Bean(destroyMethod = "shutdownNow")
	public ScheduledExecutorService executor() { // the execution of batches is multi-thread now.
		return Executors.newScheduledThreadPool(3);
	}

	@Scheduled(cron = "0 0 12 * * ?")
	public void importRmJob() {
		dateOutTspr.processValidationSupportNeeds();
	}
}
