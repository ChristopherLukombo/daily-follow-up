package fr.almavivahealth.service;

/**
 * The Interface DishImportationService.
 * @author christopher
 * @version 16
 */
public interface DishImportationService {

	/**
	 * Checks for elements.
	 *
	 * @return true, if successful
	 */
	boolean hasElements();

	/**
	 * Import dishes.
	 */
	void importDishes();
}
