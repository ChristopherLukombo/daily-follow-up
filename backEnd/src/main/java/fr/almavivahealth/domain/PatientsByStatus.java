package fr.almavivahealth.domain;

/**
 * @author christopher
 * A projection PatientsByStatus.
 */
public interface PatientsByStatus {

	Long getActivePatients();

	Long getInactivePatients();

	Long getTotalPatients();

}
