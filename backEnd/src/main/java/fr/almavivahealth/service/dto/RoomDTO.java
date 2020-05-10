package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@NotNull
	private boolean state;

	@Min(0)
	@Max(2)
	private Integer maxCapacity;

	private Integer numberOfPatients;

	private Boolean isFull;

	public RoomDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
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

	public Integer getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(final Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Integer getNumberOfPatients() {
		return numberOfPatients;
	}

	public void setNumberOfPatients(final Integer numberOfPatients) {
		this.numberOfPatients = numberOfPatients;
	}

	public Boolean getIsFull() {
		return isFull;
	}

	public void setIsFull(final Boolean isFull) {
		this.isFull = isFull;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isFull, maxCapacity, number, numberOfPatients, state);
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
		return Objects.equals(id, other.id) && Objects.equals(isFull, other.isFull)
				&& Objects.equals(maxCapacity, other.maxCapacity) && Objects.equals(number, other.number)
				&& Objects.equals(numberOfPatients, other.numberOfPatients) && state == other.state;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
