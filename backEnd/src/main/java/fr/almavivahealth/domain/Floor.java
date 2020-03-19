package fr.almavivahealth.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
public class Floor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer number;
	
	private boolean state;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Room> rooms;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Caregiver> caregivers;

	public Floor() {
		// Empty constructor needed for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(final Integer number) {
		this.number = number;
	}

	public boolean isState() {
		return state;
	}

	public void setState(final boolean state) {
		this.state = state;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(final List<Room> rooms) {
		this.rooms = rooms;
	}
	
	public List<Caregiver> getCaregivers() {
		return caregivers;
	}

	public void setCaregivers(final List<Caregiver> caregivers) {
		this.caregivers = caregivers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(caregivers, id, number, rooms, state);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Floor other = (Floor) obj;
		return Objects.equals(caregivers, other.caregivers) && Objects.equals(id, other.id)
				&& Objects.equals(number, other.number) && Objects.equals(rooms, other.rooms) && state == other.state;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Floor [");
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
		if (rooms != null) {
			builder.append("rooms=");
			builder.append(rooms);
			builder.append(", ");
		}
		if (caregivers != null) {
			builder.append("caregivers=");
			builder.append(caregivers);
		}
		builder.append("]");
		return builder.toString();
	}
}
