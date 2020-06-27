package fr.almavivahealth.domain.projection;

import java.math.BigDecimal;

public interface MenuByPatients {

	Long getNumberMenus();

	Long getNumberPatients();

	BigDecimal getPercentage();
}
