package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.MomentDayDTO;

/**
 * The Interface MomentDayService.
 * @author christopher
 * @version 16
 */
public interface MomentDayService {

	/**
	 * Save a momentDay.
	 *
	 * @param momentDay the entity to save
	 * @return the persisted entity
	 */
	MomentDayDTO save(MomentDayDTO momentDayDTO);

	/**
	 * Update a momentDay.
	 *
	 * @param momentDayDTO the day DTO
	 * @return the persisted entity
	 */
	MomentDayDTO update(MomentDayDTO momentDayDTO);

	/**
	 * Get all the momentDays.
	 *
	 * @return the list of entities
	 */
	List<MomentDayDTO> findAll();

	/**
	 * Get the "id" momentDay.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<MomentDayDTO> findOne(Long id);

	/**
	 * Delete the "id" momentDay.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
