package fr.almavivahealth;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import fr.almavivahealth.config.DefaultProfileUtil;
import fr.almavivahealth.config.PropertiesConfig;

/**
 *
 * @author christopher
 * @version 16
 */
@EnableConfigurationProperties({PropertiesConfig.class})
@EnableScheduling
@SpringBootApplication
public class DailyFollowUpApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(DailyFollowUpApplication.class);

	public static void main(final String[] args) throws UnknownHostException {
		final SpringApplication app = new SpringApplication(DailyFollowUpApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
		final Environment env = app.run(args).getEnvironment();
		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		LOGGER.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
						+ "External: \t{}://{}:{}\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getActiveProfiles());
	}
}
