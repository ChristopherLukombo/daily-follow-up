package fr.almavivahealth.service.propeties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ContentProperties for managing menu properties.
 * @author christopher
 * @version 17
 */
@Configuration
@ConfigurationProperties(prefix = "content")
public class ContentProperties {

	private String imagesPath;

	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(final String imagesPath) {
		this.imagesPath = imagesPath;
	}
}
