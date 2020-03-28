package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.FloorDTO;

/**
 * The Interface FloorService.
 */
public interface FloorService {

	/**
	 * Save a floor.
	 *
	 * @param floorDTO the entity to save
	 * @return the persisted entity
	 */
	FloorDTO save(FloorDTO floorDTO);

	/**
	 * Update a floor.
	 *
	 * @param floorDTO the floor DTO
	 * @return the persisted entity
	 */
	FloorDTO update(FloorDTO floorDTO);

	/**
	 * Get all the floors.
	 *
	 * @return the list of entities
	 */
	List<FloorDTO> findAll();

	/**
	 * Get the "id" floor.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<FloorDTO> findOne(Long id);

	/**
	 * Delete the "id" floor.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
