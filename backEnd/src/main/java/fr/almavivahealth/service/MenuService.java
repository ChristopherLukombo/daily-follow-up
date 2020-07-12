package fr.almavivahealth.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.MenuDTO;

/**
 * The Interface MenuService.
 * @author christopher
 * @version 17
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
	 * Find current menus.
	 *
	 * @return the list of entities
	 */
	List<MenuDTO> findCurrentMenus();

	/**
	 * Check specifications.
	 *
	 * @param menuDTO the menu DTO
	 * @return true, if successful
	 */
	boolean checkSpecifications(MenuDTO menuDTO);

	/**
	 * Find Future menus.
	 *
	 * @return the list of entities.
	 */
	List<MenuDTO> findFutureMenus();

	/**
	 * Find Past menus.
	 *
	 * @return the list of entities.
	 */
	List<MenuDTO> findPastMenus();

	/**
	 * Delete by ids.
	 *
	 * @param ids the ids
	 */
	void deleteByIds(List<Long> ids);

	/**
	 * Find menus for date.
	 *
	 * @param date the date
	 * @return the list of entities
	 */
	List<MenuDTO> findMenusForDate(LocalDate date);
}
