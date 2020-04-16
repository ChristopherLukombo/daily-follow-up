package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * BulkResult for result of operation.
 */
@AllArgsConstructor
@Builder
public class BulkResult implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<PatientDTO> updatedPatients;
	private List<PatientDTO> savedPatients;

	public BulkResult() {
		// Empty constructor needed for Jackson.
	}

	public List<PatientDTO> getUpdatedPatients() {
		return updatedPatients;
	}

	public void setUpdatedPatients(final List<PatientDTO> updatedPatients) {
		this.updatedPatients = updatedPatients;
	}

	public List<PatientDTO> getSavedPatients() {
		return savedPatients;
	}

	public void setSavedPatients(final List<PatientDTO> savedPatients) {
		this.savedPatients = savedPatients;
	}

	@Override
	public int hashCode() {
		return Objects.hash(savedPatients, updatedPatients);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BulkResult other = (BulkResult) obj;
		return Objects.equals(savedPatients, other.savedPatients)
				&& Objects.equals(updatedPatients, other.updatedPatients);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BulkResult [");
		if (updatedPatients != null) {
			builder.append("updatedPatients=");
			builder.append(updatedPatients);
			builder.append(", ");
		}
		if (savedPatients != null) {
			builder.append("savedPatients=");
			builder.append(savedPatients);
		}
		builder.append("]");
		return builder.toString();
	}

}
