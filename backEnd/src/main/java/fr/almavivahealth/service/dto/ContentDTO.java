package fr.almavivahealth.service.dto;

import java.util.Objects;

public class ContentDTO {
	private Long id;

	private String name;

	private Long textureId;

	private boolean salt;

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

	@Override
	public int hashCode() {
		return Objects.hash(id, name, salt, sugar, textureId);
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
				&& sugar == other.sugar && Objects.equals(textureId, other.textureId);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ContentDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (textureId != null) {
			builder.append("textureId=");
			builder.append(textureId);
			builder.append(", ");
		}
		builder.append("salt=");
		builder.append(salt);
		builder.append(", sugar=");
		builder.append(sugar);
		builder.append("]");
		return builder.toString();
	}
}
