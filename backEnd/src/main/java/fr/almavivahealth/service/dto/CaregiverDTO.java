package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@NotNull
	private UserDTO user;

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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(final UserDTO userDTO) {
		this.user = userDTO;
	}

	public Long getFloorId() {
		return floorId;
	}

	public void setFloorId(final Long floorId) {
		this.floorId = floorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorId, id, user);
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
				&& Objects.equals(user, other.user);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
