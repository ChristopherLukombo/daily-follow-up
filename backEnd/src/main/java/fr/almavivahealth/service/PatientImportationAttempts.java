package fr.almavivahealth.service;

/**
 * The Interface PatientImportationAttempts.
 * @author christopher
 * @version 17
 */
public interface PatientImportationAttempts {

	/**
	 * Validate attempt.
	 *
	 * @param ip the ip
	 * @param pseudo the pseudo
	 */
	void validateAttempt(final String ip, final String pseudo);

	/**
	 * Store failed attempts.
	 *
	 * @param ip the ip
	 * @param pseudo the pseudo
	 */
	void storeFailedAttempts(final String ip, final String pseudo);

	/**
	 * Checks for made an attempt.
	 *
	 * @param ip the ip
	 * @param pseudo the pseudo
	 * @return true, if successful
	 */
	boolean hasMadeAnAttempt(final String ip, final String pseudo);

}
