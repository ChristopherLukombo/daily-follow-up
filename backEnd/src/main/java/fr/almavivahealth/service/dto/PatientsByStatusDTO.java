package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the PatientsByStatus projection.
 */
@AllArgsConstructor
@Builder
public class PatientsByStatusDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long activePatients;

	private Long inactivePatients;

	private Long totalPatients;

	public PatientsByStatusDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getActivePatients() {
		return activePatients;
	}

	public void setActivePatients(final Long activePatients) {
		this.activePatients = activePatients;
	}

	public Long getInactivePatients() {
		return inactivePatients;
	}

	public void setInactivePatients(final Long inactivePatients) {
		this.inactivePatients = inactivePatients;
	}

	public Long getTotalPatients() {
		return totalPatients;
	}

	public void setTotalPatients(final Long totalPatients) {
		this.totalPatients = totalPatients;
	}

	@Override
	public int hashCode() {
		return Objects.hash(activePatients, inactivePatients, totalPatients);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PatientsByStatusDTO other = (PatientsByStatusDTO) obj;
		return Objects.equals(activePatients, other.activePatients)
				&& Objects.equals(inactivePatients, other.inactivePatients)
				&& Objects.equals(totalPatients, other.totalPatients);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
