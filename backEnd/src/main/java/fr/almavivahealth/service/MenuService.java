package fr.almavivahealth.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.MenuDTO;

/**
 * The Interface MenuService.
 */
public interface MenuService {

	/**
	 * Save a menu.
	 *
	 * @param menuDTO the entity to save
	 * @return the persisted entity
	 */
	MenuDTO save(MenuDTO menuDTO);

	/**
	 * Update a menu.
	 *
	 * @param menuDTO the menu DTO
	 * @return the persisted entity
	 */
	MenuDTO update(MenuDTO menuDTO);

	/**
	 * Get all the menus.
	 *
	 * @return the list of entities
	 */
	List<MenuDTO> findAll();

	/**
	 * Get the "id" menu.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<MenuDTO> findOne(Long id);

	/**
	 * Delete the "id" menu.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);


	/**
	 * Generate coupons.
	 *
	 * @param momentName the moment name
	 * @param selectedDate the selected date
	 * @return the byte[]
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	byte[] generateCoupons(final String momentName, final LocalDate selectedDate) throws DailyFollowUpException;
}
