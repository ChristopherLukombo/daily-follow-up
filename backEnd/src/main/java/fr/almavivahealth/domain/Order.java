package fr.almavivahealth.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "patientOrder")
@AllArgsConstructor
@Builder
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate date;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Content> contents;

	@OneToMany(fetch = FetchType.LAZY)
	private List<MomentDay> momentDays;

	@ManyToOne
	private Patient patient;

	public Order() {
		// Empty constructor needed for Hibernate.
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

	public List<Content> getContents() {
		return contents;
	}

	public void setContents(final List<Content> contents) {
		this.contents = contents;
	}

	public List<MomentDay> getMomentDays() {
		return momentDays;
	}

	public void setMomentDays(final List<MomentDay> momentDays) {
		this.momentDays = momentDays;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(final Patient patient) {
		this.patient = patient;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contents, date, id, momentDays, patient);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Order other = (Order) obj;
		return Objects.equals(contents, other.contents) && Objects.equals(date, other.date)
				&& Objects.equals(id, other.id) && Objects.equals(momentDays, other.momentDays)
				&& Objects.equals(patient, other.patient);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Order [");
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
		if (contents != null) {
			builder.append("contents=");
			builder.append(contents);
			builder.append(", ");
		}
		if (momentDays != null) {
			builder.append("momentDays=");
			builder.append(momentDays);
			builder.append(", ");
		}
		if (patient != null) {
			builder.append("patient=");
			builder.append(patient);
		}
		builder.append("]");
		return builder.toString();
	}
}
