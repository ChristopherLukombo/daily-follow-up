package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.BulkResult;
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
	 * Get all active patients.
	 *
	 * @return the list of entities
	 */
	List<PatientDTO> findAllActivePatients();

	/**
	 * Get all former patients.
	 *
	 * @return the list of entities
	 */
	List<PatientDTO> findAllFormerPatients();

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

	/**
	 * Import patient file in database.
	 *
	 * @param fileToImport the file to import
	 * @param request the request
	 * @return the bulk result
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	BulkResult importPatientFile(MultipartFile fileToImport, final HttpServletRequest request) throws DailyFollowUpException;

	/**
	 * Checks if is csv.
	 *
	 * @param fileToImport the file to import
	 * @return true, if is csv
	 */
	boolean isCSV(MultipartFile fileToImport);

	/**
	 * Reactivate patient.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	Optional<Patient> reactivatePatient(Long id);

}
