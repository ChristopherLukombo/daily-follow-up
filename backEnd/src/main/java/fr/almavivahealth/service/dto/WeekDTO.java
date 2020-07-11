package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Week entity.
 *
 * @author christopher
 * @version 16
 */
@AllArgsConstructor
@Builder
public class WeekDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer number;

    private List<DayDTO> days;

    public WeekDTO() {
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

	public List<DayDTO> getDays() {
		return days;
	}

	public void setDays(final List<DayDTO> days) {
		this.days = days;
	}

	@Override
	public int hashCode() {
		return Objects.hash(days, id, number);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final WeekDTO other = (WeekDTO) obj;
		return Objects.equals(days, other.days) && Objects.equals(id, other.id) && Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
