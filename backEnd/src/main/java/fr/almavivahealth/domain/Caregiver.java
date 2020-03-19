package fr.almavivahealth.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
public class Caregiver implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private User user;
	
	private Floor floor;

	public Caregiver() {
		// Empty constructor needed for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(final Floor floor) {
		this.floor = floor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(floor, id, user);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Caregiver other = (Caregiver) obj;
		return Objects.equals(floor, other.floor) && Objects.equals(id, other.id) && Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Caregiver [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (user != null) {
			builder.append("user=");
			builder.append(user);
			builder.append(", ");
		}
		if (floor != null) {
			builder.append("floor=");
			builder.append(floor);
		}
		builder.append("]");
		return builder.toString();
	}
}
