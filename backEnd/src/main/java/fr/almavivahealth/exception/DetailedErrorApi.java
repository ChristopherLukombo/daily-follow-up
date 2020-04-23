package fr.almavivahealth.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class DetailedErrorApi extends ErrorApi {
	private final String detailedMessage;
	
	public DetailedErrorApi(final String message, final String detailedMessage, final HttpStatus httpStatus,
			final ZonedDateTime timestamp) {
		super(message, httpStatus, timestamp);
		this.detailedMessage = detailedMessage;
	}

	public String getDetailedMessage() {
		return detailedMessage;
	}

}