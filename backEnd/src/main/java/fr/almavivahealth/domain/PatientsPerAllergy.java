package fr.almavivahealth.domain;

import java.math.BigDecimal;

/**
 * @author christopher
 * A projection PatientsPerAllergy.
 */
public interface PatientsPerAllergy {

	String getAllergyName();

	Long getNumberPatients();

	BigDecimal getPercentage();

}
