package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.DietDTO;

/**
 * The Interface DietService.
 */
public interface DietService {

	/**
	 * Save a diet.
	 *
	 * @param dietDTO the entity to save
	 * @return the persisted entity
	 */
	DietDTO save(DietDTO dietDTO);

	/**
	 * Update a diet.
	 *
	 * @param dietDTO the diet DTO
	 * @return the persisted entity
	 */
	DietDTO update(DietDTO dietDTO);

	/**
	 * Get all the diets.
	 *
	 * @return the list of entities
	 */
	List<DietDTO> findAll();

	/**
	 * Get the "id" diet.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<DietDTO> findOne(Long id);

	/**
	 * Delete the "id" diet.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
