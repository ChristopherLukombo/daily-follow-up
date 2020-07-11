package fr.almavivahealth.domain.projection;

import java.math.BigDecimal;

/**
 * A projection PatientsPerAllergy.
 *
 * @author christopher
 * @version 16
 */
public interface PatientsPerAllergy {

	String getAllergyName();

	Long getNumberPatients();

	BigDecimal getPercentage();

}
