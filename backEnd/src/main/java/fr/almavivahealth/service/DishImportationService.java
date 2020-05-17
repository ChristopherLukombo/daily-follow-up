package fr.almavivahealth.service;

/**
 * The Interface DishImportationService.
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
