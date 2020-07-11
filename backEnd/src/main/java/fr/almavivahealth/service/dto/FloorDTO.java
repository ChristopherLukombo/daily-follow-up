package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Floor entity.
 *
 * @author christopher
 * @version 16
 */
@AllArgsConstructor
@Builder
public class FloorDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer number;

	private boolean state;

	private List<RoomDTO> rooms;

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

	public List<RoomDTO> getRooms() {
		return rooms;
	}

	public void setRooms(final List<RoomDTO> rooms) {
		this.rooms = rooms;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, number, rooms, state);
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
		return Objects.equals(id, other.id) && Objects.equals(number, other.number)
				&& Objects.equals(rooms, other.rooms) && state == other.state;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
