package fr.almavivahealth.exception;

/**
 *
 * @author christopher
 * @version 16
 *
 */
public class DailyFollowUpException extends Exception {

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;

	public DailyFollowUpException(final String message) {
		super(message);
	}

	public DailyFollowUpException(final Throwable cause) {
		super(cause);
	}

	public DailyFollowUpException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DailyFollowUpException(final int errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public DailyFollowUpException(final int errorCode, final String errorMessage, final Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
