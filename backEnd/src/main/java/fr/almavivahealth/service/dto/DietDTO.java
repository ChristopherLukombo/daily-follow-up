package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Diet entity.
 */
@AllArgsConstructor
@Builder
public class DietDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Map<String, Integer> elementsToCheck;

	public DietDTO() {
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

	public Map<String, Integer> getElementsToCheck() {
		return elementsToCheck;
	}

	public void setElementsToCheck(final Map<String, Integer> elementsToCheck) {
		this.elementsToCheck = elementsToCheck;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DietDTO other = (DietDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
