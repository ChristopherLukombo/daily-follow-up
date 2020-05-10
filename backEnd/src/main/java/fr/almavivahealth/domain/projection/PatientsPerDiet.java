package fr.almavivahealth.domain.projection;

import java.math.BigDecimal;

/**
 * @author christopher
 * A projection PatientsPerDiet.
 */
public interface PatientsPerDiet {

	String getDietName();

	Long getNumberPatients();

	BigDecimal getPercentage();

}
