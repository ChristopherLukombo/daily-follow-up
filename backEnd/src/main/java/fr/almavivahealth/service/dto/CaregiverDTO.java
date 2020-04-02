package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Caregiver entity.
 */
@AllArgsConstructor
@Builder
public class CaregiverDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;
	
	private Long floorId;

	public CaregiverDTO() {
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

	public Long getFloorId() {
		return floorId;
	}

	public void setFloorId(final Long floorId) {
		this.floorId = floorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorId, id, userId);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CaregiverDTO other = (CaregiverDTO) obj;
		return Objects.equals(floorId, other.floorId) && Objects.equals(id, other.id)
				&& Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("CaregiverDTO [");
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
		if (floorId != null) {
			builder.append("floorId=");
			builder.append(floorId);
		}
		builder.append("]");
		return builder.toString();
	}
}
