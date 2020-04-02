package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Floor entity.
 */
@AllArgsConstructor
@Builder
public class FloorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Integer number;
	
	private boolean state;

	public FloorDTO() {
		// Empty constructor needed for Jackson.
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
		final FloorDTO other = (FloorDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(number, other.number) && state == other.state;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("FloorDTO [");
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
