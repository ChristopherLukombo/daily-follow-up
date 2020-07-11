package fr.almavivahealth.service.propeties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * OrderProperties for managing order properties.
 * @author christopher
 */
@Configuration
@ConfigurationProperties(prefix = "order")
public class OrderProperties {

	private String imagesPath;

	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(final String imagesPath) {
		this.imagesPath = imagesPath;
	}

}
