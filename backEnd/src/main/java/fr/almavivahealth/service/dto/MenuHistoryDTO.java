package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A DTO for the MenuHistory entity.
 */
public class MenuHistoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private MenuDTO menu;

	private String modifiedBy;

	private LocalDateTime modifiedDate;

	private String action;

	public MenuHistoryDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public MenuDTO getMenu() {
		return menu;
	}

	public void setMenu(final MenuDTO menu) {
		this.menu = menu;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(final String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(final LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	@Override
	public int hashCode() {
		return Objects.hash(action, id, menu, modifiedBy, modifiedDate);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MenuHistoryDTO other = (MenuHistoryDTO) obj;
		return Objects.equals(action, other.action) && Objects.equals(id, other.id) && Objects.equals(menu, other.menu)
				&& Objects.equals(modifiedBy, other.modifiedBy) && Objects.equals(modifiedDate, other.modifiedDate);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
