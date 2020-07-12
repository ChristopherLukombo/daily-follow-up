package fr.almavivahealth.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

/**
 *
 * @author christopher
 * @version 17
 *
 */
public class ErrorApi {
	private final String message;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timestamp;

	public ErrorApi(final String message, final HttpStatus httpStatus, final ZonedDateTime timestamp) {
		this.message = message;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

}
