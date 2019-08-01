package com.example.config;

import com.example.config.context.ProfilesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

//@Configuration
public class FixtureDataConfig {

	private static final Logger log = LoggerFactory.getLogger(FixtureDataConfig.class);

	@Value("classpath:reset.sql")
	private Resource resetScript;
	@Value("classpath:data.sql")
	private Resource dataScript;

	// take the value from vm options: -Dreset.to.fixture
	@Value("#{systemProperties['reset.to.fixture']}")
	private Boolean resetToFixture;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ProfilesUtils profilesUtils;

	private static final Set<String> enabledProfiles = newHashSet("IT", "dev");

	@PostConstruct
	public void populate() {
		log.info("Run SQL data files ____________________");
		log.info("resetToFixture: "+resetToFixture);
		log.info("enabledProfiles: "+enabledProfiles);

		// if -Dreset.to.fixture presents, use its value(true/false), if not present use the value of enabledProfiles
		if (resetToFixture!=null ?  resetToFixture : profilesUtils.isEnabledProfiles(enabledProfiles)) {
			dataSourceInitializer().afterPropertiesSet();
		}
	}

	public void reset() {
		log.info("Run SQL data files ____________________");
		if (profilesUtils.isEnabledProfiles(newHashSet("dev", "int", "qua", "sta", "IT"))) {
			dataSourceInitializer().afterPropertiesSet();
		}
	}

	private DataSourceInitializer dataSourceInitializer() {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator());
		return initializer;
	}

	private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(resetScript);
		populator.addScript(dataScript);
		return populator;
	}

}
