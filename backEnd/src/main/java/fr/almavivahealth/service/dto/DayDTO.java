package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Day entity.
 *
 * @author christopher
 * @version 16
 */
@AllArgsConstructor
@Builder
public class DayDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

	private List<MomentDayDTO> momentDays;

    public DayDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<MomentDayDTO> getMomentDays() {
		return momentDays;
	}

	public void setMomentDays(final List<MomentDayDTO> momentDays) {
		this.momentDays = momentDays;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, momentDays, name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DayDTO other = (DayDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(momentDays, other.momentDays)
				&& Objects.equals(name, other.name);
	}
}
