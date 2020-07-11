package fr.almavivahealth.domain.projection;

import java.math.BigDecimal;

/**
 *
 * @author christopher
 * @version 16
 *
 */
public interface MenuByPatients {

	Long getNumberMenus();

	Long getNumberPatients();

	BigDecimal getPercentage();
}
