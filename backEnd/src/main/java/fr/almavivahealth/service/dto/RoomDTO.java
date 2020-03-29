package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Room entity.
 */
@AllArgsConstructor
@Builder
public class RoomDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String number;

	private boolean state;

	public RoomDTO() {
		// Empty constructor needed for Jackson.
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

	@Override
	public int hashCode() {
		return Objects.hash(id, number, state);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RoomDTO other = (RoomDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(number, other.number) && state == other.state;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RoomDTO [");
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
		builder.append("]");
		return builder.toString();
	}
}
