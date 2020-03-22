package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class PatientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;

	private Boolean state;

	private Long textureId;

	public PatientDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(final Boolean state) {
		this.state = state;
	}

	public Long getTextureId() {
		return textureId;
	}

	public void setTextureId(final Long textureId) {
		this.textureId = textureId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, state, textureId, userId);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PatientDTO other = (PatientDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(state, other.state)
				&& Objects.equals(textureId, other.textureId) && Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("PatientDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (userId != null) {
			builder.append("userId=");
			builder.append(userId);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
			builder.append(", ");
		}
		if (textureId != null) {
			builder.append("textureId=");
			builder.append(textureId);
		}
		builder.append("]");
		return builder.toString();
	}
}
