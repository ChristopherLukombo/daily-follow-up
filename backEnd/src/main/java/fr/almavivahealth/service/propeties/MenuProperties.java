package fr.almavivahealth.service.propeties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MenuProperties for managing menu properties.
 * @author christopher
 */
@Configuration
@ConfigurationProperties(prefix = "menu")
public class MenuProperties {

	private String imagesPath;

	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(final String imagesPath) {
		this.imagesPath = imagesPath;
	}

}
