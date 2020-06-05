package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.exception.DailyFollowUpException;
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
	 * @throws DailyFollowUpException
	 */
	CaregiverDTO save(CaregiverDTO caregiverDTO) throws DailyFollowUpException;

	/**
	 * Update a caregiver.
	 *
	 * @param caregiverDTO the caregiver DTO
	 * @return the persisted entity
	 * @throws DailyFollowUpException
	 */
	CaregiverDTO update(CaregiverDTO caregiverDTO) throws DailyFollowUpException;

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

	/**
	 * Find all by floor number.
	 *
	 * @param number the number
	 * @return the list of entities
	 */
	List<CaregiverDTO> findAllByFloorNumber(Integer number);
}
