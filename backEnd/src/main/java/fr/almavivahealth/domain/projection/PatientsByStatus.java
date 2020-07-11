package fr.almavivahealth.domain.projection;

/**
 * A projection PatientsByStatus.
 *
 * @author christopher
 * @version 17
 */
public interface PatientsByStatus {

	Long getActivePatients();

	Long getInactivePatients();

	Long getTotalPatients();

}
