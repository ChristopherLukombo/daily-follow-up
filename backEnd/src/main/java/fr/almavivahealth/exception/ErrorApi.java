package fr.almavivahealth.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ErrorApi {
	private final String message;
	private final String detailedMessage;
	private final HttpStatus httpStatus;
	private final ZonedDateTime timestamp;
	
	public ErrorApi(final String message, final String detailedMessage, final HttpStatus httpStatus, final ZonedDateTime timestamp) {
		this.message = message;
		this.detailedMessage = detailedMessage;
		this.httpStatus = httpStatus;
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getDetailedMessage() {
		return detailedMessage;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}
}
