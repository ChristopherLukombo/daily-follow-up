package fr.almavivahealth.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

/**
 *
 * @author christopher
 * @version 17
 *
 */
public class DetailedErrorApi extends ErrorApi {
	private final String detailedMessage;

	public DetailedErrorApi(
			final String message,
			final String detailedMessage,
			final HttpStatus httpStatus,
			final ZonedDateTime timestamp) {
		super(message, httpStatus, timestamp);
		this.detailedMessage = detailedMessage;
	}

	/**
	 * Gets the detailed message.
	 *
	 * @return the detailed message
	 */
	public String getDetailedMessage() {
		return detailedMessage;
	}

}
