package fr.almavivahealth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenPropeties {

    private String secretKey;

    private long tokenValidityInMilliseconds;

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(final String secretKey) {
		this.secretKey = secretKey;
	}

	public long getTokenValidityInMilliseconds() {
		return tokenValidityInMilliseconds;
	}

	public void setTokenValidityInMilliseconds(final long tokenValidityInMilliseconds) {
		this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
	}
}
