package fr.almavivahealth.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String number;
	
    @NotNull
    @Column(nullable = false)
	private boolean state;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Patient> patients;

	public Room() {
		// Empty constructor needed for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public boolean isState() {
		return state;
	}

	public void setState(final boolean state) {
		this.state = state;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(final List<Patient> patients) {
		this.patients = patients;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, number, patients, state);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Room other = (Room) obj;
		return Objects.equals(id, other.id) && Objects.equals(number, other.number)
				&& Objects.equals(patients, other.patients) && state == other.state;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Room [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (number != null) {
			builder.append("number=");
			builder.append(number);
			builder.append(", ");
		}
		builder.append("state=");
		builder.append(state);
		builder.append(", ");
		if (patients != null) {
			builder.append("patients=");
			builder.append(patients);
		}
		builder.append("]");
		return builder.toString();
	}
}
