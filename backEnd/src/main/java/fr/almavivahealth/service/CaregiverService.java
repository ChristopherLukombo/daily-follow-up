package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.CaregiverDTO;

/**
 * The Interface CaregiverService.
 */
public interface CaregiverService {

	/**
	 * Save a caregiver.
	 *
	 * @param caregiverDTO the entity to save
	 * @return the persisted entity
	 */
	CaregiverDTO save(CaregiverDTO caregiverDTO);

	/**
	 * Update a caregiver.
	 *
	 * @param caregiverDTO the caregiver DTO
	 * @return the persisted entity
	 */
	CaregiverDTO update(CaregiverDTO caregiverDTO);

	/**
	 * Get all the caregivers.
	 *
	 * @return the list of entities
	 */
	List<CaregiverDTO> findAll();

	/**
	 * Get the "id" caregiver.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<CaregiverDTO> findOne(Long id);

	/**
	 * Delete the "id" caregiver.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
