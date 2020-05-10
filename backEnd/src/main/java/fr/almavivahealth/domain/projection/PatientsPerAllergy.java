package fr.almavivahealth.domain.projection;

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
