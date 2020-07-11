package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the PatientsPerDiet projection.
 *
 * @author christopher
 * @version 16
 */
@AllArgsConstructor
@Builder
public class PatientsPerDietDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String dietName;

	private Long numberPatients;

	private BigDecimal percentage;

	public PatientsPerDietDTO() {
		// Empty constructor needed for Jackson.
	}

	public String getDietName() {
		return dietName;
	}

	public void setDietName(final String dietName) {
		this.dietName = dietName;
	}

	public Long getNumberPatients() {
		return numberPatients;
	}

	public void setNumberPatient(final Long numberPatients) {
		this.numberPatients = numberPatients;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(final BigDecimal percentage) {
		this.percentage = percentage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dietName, numberPatients, percentage);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PatientsPerDietDTO other = (PatientsPerDietDTO) obj;
		return Objects.equals(dietName, other.dietName) && Objects.equals(numberPatients, other.numberPatients)
				&& Objects.equals(percentage, other.percentage);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
