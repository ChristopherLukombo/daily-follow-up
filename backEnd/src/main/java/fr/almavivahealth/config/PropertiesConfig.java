package fr.almavivahealth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * PropertiesConfig for import configuration file.
 * 
 * @author christopher
 */
@Configuration
@ConfigurationProperties
@PropertySources(value = {
		@PropertySource(value = "file:${CONF_DIR}/dailyFollowUp.properties", ignoreResourceNotFound = true) })
public class PropertiesConfig {

}
