package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

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
		final StringBuilder builder = new StringBuilder();
		builder.append("MenuDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (startDate != null) {
			builder.append("startDate=");
			builder.append(startDate);
			builder.append(", ");
		}
		if (endDate != null) {
			builder.append("endDate=");
			builder.append(endDate);
			builder.append(", ");
		}
		if (weekNumber != null) {
			builder.append("weekNumber=");
			builder.append(weekNumber);
			builder.append(", ");
		}
		if (textureId != null) {
			builder.append("textureId=");
			builder.append(textureId);
			builder.append(", ");
		}
		if (dietId != null) {
			builder.append("dietId=");
			builder.append(dietId);
		}
		builder.append("]");
		return builder.toString();
	}
}
