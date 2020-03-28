package fr.almavivahealth.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;

public final class DefaultProfileUtil {
	private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

	private DefaultProfileUtil() {
		// private constructor needed for DefaultProfileUtil class.
	}

	/**
	 * Set a default to use when no profile is configured.
	 *
	 * @param app the Spring application
	 */
	public static void addDefaultProfile(final SpringApplication app) {
		final Map<String, Object> defProperties = new HashMap<>();
		defProperties.put(SPRING_PROFILE_DEFAULT, "dev");
		app.setDefaultProperties(defProperties);
	}
}
