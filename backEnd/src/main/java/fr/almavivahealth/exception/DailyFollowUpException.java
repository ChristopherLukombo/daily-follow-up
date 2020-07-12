package fr.almavivahealth.exception;

/**
 *
 * @author christopher
 * @version 17
 *
 */
public class DailyFollowUpException extends Exception {

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;

	/**
	 * Instantiates a new daily follow up exception.
	 *
	 * @param message the message
	 */
	public DailyFollowUpException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new daily follow up exception.
	 *
	 * @param cause the cause
	 */
	public DailyFollowUpException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new daily follow up exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public DailyFollowUpException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new daily follow up exception.
	 *
	 * @param errorCode    the error code
	 * @param errorMessage the error message
	 */
	public DailyFollowUpException(final int errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * Instantiates a new daily follow up exception.
	 *
	 * @param errorCode    the error code
	 * @param errorMessage the error message
	 * @param cause        the cause
	 */
	public DailyFollowUpException(final int errorCode, final String errorMessage, final Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
}
