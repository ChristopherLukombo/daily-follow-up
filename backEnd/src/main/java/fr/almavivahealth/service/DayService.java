package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.DayDTO;

/**
 * The Interface DayService.
 * @author christopher
 * @version 17
 */
public interface DayService {

	/**
	 * Save a day.
	 *
	 * @param dayDTO the entity to save
	 * @return the persisted entity
	 */
	DayDTO save(DayDTO dayDTO);

	/**
	 * Update a day.
	 *
	 * @param dayDTO the day DTO
	 * @return the persisted entity
	 */
	DayDTO update(DayDTO dayDTO);

	/**
	 * Get all the days.
	 *
	 * @return the list of entities
	 */
	List<DayDTO> findAll();

	/**
	 * Get the "id" day.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<DayDTO> findOne(Long id);

	/**
	 * Delete the "id" day.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
