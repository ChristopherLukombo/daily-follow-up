package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.PatientDTO;

/**
 * The Interface PatientService.
 */
public interface PatientService {

	/**
	 * Save a patient.
	 *
	 * @param patientDTO the entity to save
	 * @return the persisted entity
	 */
	PatientDTO save(PatientDTO patientDTO);

	/**
	 * Update a patient.
	 *
	 * @param patientDTO the patient DTO
	 * @return the persisted entity
	 */
	PatientDTO update(PatientDTO patientDTO);

	/**
	 * Get all the patients.
	 *
	 * @return the list of entities
	 */
	List<PatientDTO> findAll();

	/**
	 * Get the "id" patient.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<PatientDTO> findOne(Long id);

	/**
	 * Delete the "id" patient.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
