package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

	private String texture;

	private ReplacementDTO replacement;

	private String diet;

	private List<WeekDTO> weeks;

	private String lastModifiedBy;

	private LocalDateTime lastModificationDateBy;

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

	public String getTexture() {
		return texture;
	}

	public void setTexture(final String texture) {
		this.texture = texture;
	}

	public ReplacementDTO getReplacement() {
		return replacement;
	}

	public void setReplacement(final ReplacementDTO replacement) {
		this.replacement = replacement;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(final String diet) {
		this.diet = diet;
	}

	public List<WeekDTO> getWeeks() {
		return weeks;
	}

	public void setWeeks(final List<WeekDTO> weeks) {
		this.weeks = weeks;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(final String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public LocalDateTime getLastModificationDateBy() {
		return lastModificationDateBy;
	}

	public void setLastModificationDateBy(final LocalDateTime lastModificationDateBy) {
		this.lastModificationDateBy = lastModificationDateBy;
	}

	@Override
	public int hashCode() {
		return Objects.hash(diet, endDate, id, lastModificationDateBy, lastModifiedBy, replacement, startDate, texture,
				weeks);
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
		return Objects.equals(diet, other.diet) && Objects.equals(endDate, other.endDate)
				&& Objects.equals(id, other.id) && Objects.equals(lastModificationDateBy, other.lastModificationDateBy)
				&& Objects.equals(lastModifiedBy, other.lastModifiedBy)
				&& Objects.equals(replacement, other.replacement) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(texture, other.texture) && Objects.equals(weeks, other.weeks);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
