package fr.almavivahealth.domain;

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
