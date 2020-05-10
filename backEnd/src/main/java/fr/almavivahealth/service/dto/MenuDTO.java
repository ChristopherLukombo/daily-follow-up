package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Menu entity.
 */
@AllArgsConstructor
@Builder
public class MenuDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private LocalDate startDate;

	private LocalDate endDate;

	private Integer weekNumber;

	private Long textureId;

	private Long dietId;

	public MenuDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(final LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(final LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(final Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Long getTextureId() {
		return textureId;
	}

	public void setTextureId(final Long textureId) {
		this.textureId = textureId;
	}

	public Long getDietId() {
		return dietId;
	}

	public void setDietId(final Long dietId) {
		this.dietId = dietId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dietId, endDate, id, startDate, textureId, weekNumber);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MenuDTO other = (MenuDTO) obj;
		return Objects.equals(dietId, other.dietId) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(textureId, other.textureId) && Objects.equals(weekNumber, other.weekNumber);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
