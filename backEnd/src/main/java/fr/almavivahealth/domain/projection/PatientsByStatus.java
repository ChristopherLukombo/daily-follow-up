package fr.almavivahealth.domain.projection;

/**
 * @author christopher
 * A projection PatientsByStatus.
 */
public interface PatientsByStatus {

	Long getActivePatients();

	Long getInactivePatients();

	Long getTotalPatients();

}
