package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private LocalDate date;
	
	private Long patientId;

	public OrderDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(final Long patientId) {
		this.patientId = patientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(patientId, date, id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OrderDTO other = (OrderDTO) obj;
		return Objects.equals(patientId, other.patientId) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OrderDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (date != null) {
			builder.append("date=");
			builder.append(date);
			builder.append(", ");
		}
		if (patientId != null) {
			builder.append("PatientId=");
			builder.append(patientId);
		}
		builder.append("]");
		return builder.toString();
	}
}
