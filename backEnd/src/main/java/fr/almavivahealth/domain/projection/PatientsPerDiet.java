package fr.almavivahealth.domain.projection;

import java.math.BigDecimal;

/**
 * A projection PatientsPerDiet.
 *
 * @author christopher
 * @version 16
 */
public interface PatientsPerDiet {

	String getDietName();

	Long getNumberPatients();

	BigDecimal getPercentage();

}
