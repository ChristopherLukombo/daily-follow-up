package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * BulkResult for result of operation.
 *
 * @author christopher
 * @version 16
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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
