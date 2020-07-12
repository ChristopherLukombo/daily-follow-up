package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the PatientsPerAllergy projection.
 *
 * @author christopher
 * @version 17
 */
@AllArgsConstructor
@Builder
public class PatientsPerAllergyDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String allergyName;

	private Long numberPatients;

	private BigDecimal percentage;

	public PatientsPerAllergyDTO() {
		// Empty constructor needed for Jackson.
	}

	public String getAllergyName() {
		return allergyName;
	}

	public void setAllergyName(final String allergyName) {
		this.allergyName = allergyName;
	}

	public Long getNumberPatients() {
		return numberPatients;
	}

	public void setNumberPatients(final Long numberPatients) {
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
		return Objects.hash(allergyName, numberPatients, percentage);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PatientsPerAllergyDTO other = (PatientsPerAllergyDTO) obj;
		return Objects.equals(allergyName, other.allergyName) && Objects.equals(numberPatients, other.numberPatients)
				&& Objects.equals(percentage, other.percentage);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
