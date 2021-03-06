package fr.almavivahealth.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * ConfigurationService for managing configuration properties.
 *
 * @author christopher
 * @version 17
 */
@Configuration
public class ConfigurationProperties {

	@Value("#{'${app.authorizedURLs}'.split(',')}")
	private Set<String> corsAllowedOrigins;

	public Set<String> getCorsAllowedOrigins() {
		return corsAllowedOrigins;
	}
}
