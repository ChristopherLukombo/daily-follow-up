package fr.almavivahealth.service.propeties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ConfigurationService for managing user properties.
 * @author christopher
 */
@Configuration
@ConfigurationProperties(prefix = "user")
public class UserProperties {

	private String pathProfiles;

	public String getPathProfiles() {
		return pathProfiles;
	}

	public void setPathProfiles(final String pathProfiles) {
		this.pathProfiles = pathProfiles;
	}
	
}
