package fr.almavivahealth.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.almavivahealth.service.dto.OrdersPerDay;
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
	 * @return the optional
	 */
	Optional<PatientsByStatusDTO> findNumberOfPatientsByStatus();

	/**
	 * Find all for next days.
	 *
	 * @return the map
	 */
	Map<String, List<OrdersPerDay>> findAllForNextDays();
}
