package fr.almavivahealth.service;

/**
 * The Interface ContentImportationService.
 */
public interface ContentImportationService {

	/**
	 * Checks for elements.
	 *
	 * @return true, if successful
	 */
	boolean hasElements();

	/**
	 * Import contents.
	 */
	void importContents();
}
