package fr.almavivahealth.service.dto;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the PatientsPerAllergy projection.
 */
@AllArgsConstructor
@Builder
public class PatientsPerAllergyDTO {

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
		final StringBuilder builder = new StringBuilder();
		builder.append("PatientsPerAllergyDTO [");
		if (allergyName != null) {
			builder.append("allergyName=");
			builder.append(allergyName);
			builder.append(", ");
		}
		if (numberPatients != null) {
			builder.append("numberPatients=");
			builder.append(numberPatients);
			builder.append(", ");
		}
		if (percentage != null) {
			builder.append("percentage=");
			builder.append(percentage);
		}
		builder.append("]");
		return builder.toString();
	}
	
}
