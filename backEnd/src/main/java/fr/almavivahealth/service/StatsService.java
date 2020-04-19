package fr.almavivahealth.service;

import java.util.List;

import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;

/**
 * The Interface StatsService.
 */
public interface StatsService {

	/**
	 * Find number of patients per allergy.
	 *
	 * @return the list
	 */
	List<PatientsPerAllergyDTO> findNumberOfPatientsPerAllergy();
	
	/**
	 * Find number of patients per diet.
	 *
	 * @return the list
	 */
	List<PatientsPerDietDTO> findNumberOfPatientsPerDiet();
	
	/**
	 * Find number of patients by status.
	 *
	 * @return the list
	 */
	List<PatientsByStatusDTO> findNumberOfPatientsByStatus();

}
