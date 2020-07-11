package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.AllergyDTO;

/**
 * The Interface AllergyService.
 * @author christopher
 * @version 16
 */
public interface AllergyService {

	/**
	 * Save a allergy.
	 *
	 * @param allergyDTO the entity to save
	 * @return the persisted entity
	 */
	AllergyDTO save(AllergyDTO allergyDTO);

	/**
	 * Update a allergy.
	 *
	 * @param allergyDTO the allergy DTO
	 * @return the persisted entity
	 */
	AllergyDTO update(AllergyDTO allergyDTO);

	/**
	 * Get all the allergys.
	 *
	 * @return the list of entities
	 */
	List<AllergyDTO> findAll();

	/**
	 * Get the "id" allergy.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<AllergyDTO> findOne(Long id);

	/**
	 * Delete the "id" allergy.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
