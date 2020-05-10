package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Content entity.
 */
@AllArgsConstructor
@Builder
public class ContentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Long textureId;

	@NotNull
	private String typeMeal;

	@NotNull
	private boolean salt;

	@NotNull
	private boolean sugar;

	public ContentDTO() {
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

	public Long getTextureId() {
		return textureId;
	}

	public void setTextureId(final Long textureId) {
		this.textureId = textureId;
	}

	public boolean isSalt() {
		return salt;
	}

	public void setSalt(final boolean salt) {
		this.salt = salt;
	}

	public boolean isSugar() {
		return sugar;
	}

	public void setSugar(final boolean sugar) {
		this.sugar = sugar;
	}

	public String getTypeMeal() {
		return typeMeal;
	}

	public void setTypeMeal(final String typeMeal) {
		this.typeMeal = typeMeal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, salt, sugar, textureId, typeMeal);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ContentDTO other = (ContentDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && salt == other.salt
				&& sugar == other.sugar && Objects.equals(textureId, other.textureId)
				&& Objects.equals(typeMeal, other.typeMeal);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
