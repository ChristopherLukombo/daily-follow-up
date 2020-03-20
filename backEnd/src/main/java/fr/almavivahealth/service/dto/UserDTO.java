package fr.almavivahealth.service.dto;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import fr.almavivahealth.domain.User;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {
	private Long id;

	@NotBlank
	@Pattern(regexp = "^[_'.@A-Za-z0-9-]*$")
	@Size(min = 1, max = 50)
	private String pseudo;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 100)
	private String email;

	@Size(max = 256)
	private String imageUrl;

	private LocalDate createDate;

	private boolean status = false;

	private LocalDate birthDay;

	private Long roleId;

	public UserDTO() {
		// Empty constructor needed for Jackson.
	}

	public UserDTO(final User user) {
		this.id = user.getId();
		this.pseudo = user.getPseudo();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.status = user.isStatus();
		this.createDate = user.getCreateDate();
		this.imageUrl = user.getImageUrl();
		this.birthDay = user.getBirthDay();
		if (null != user.getRole()) {
			this.roleId = user.getRole().getId();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(final String pseudo) {
		this.pseudo = pseudo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(final LocalDate createDate) {
		this.createDate = createDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(final boolean status) {
		this.status = status;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(final LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(final Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDay, createDate, email, firstName, id, imageUrl, lastName, pseudo, roleId, status);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final UserDTO other = (UserDTO) obj;
		return Objects.equals(birthDay, other.birthDay) && Objects.equals(createDate, other.createDate)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(imageUrl, other.imageUrl)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(pseudo, other.pseudo)
				&& Objects.equals(roleId, other.roleId) && status == other.status;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (pseudo != null) {
			builder.append("pseudo=");
			builder.append(pseudo);
			builder.append(", ");
		}
		if (firstName != null) {
			builder.append("firstName=");
			builder.append(firstName);
			builder.append(", ");
		}
		if (lastName != null) {
			builder.append("lastName=");
			builder.append(lastName);
			builder.append(", ");
		}
		if (email != null) {
			builder.append("email=");
			builder.append(email);
			builder.append(", ");
		}
		if (imageUrl != null) {
			builder.append("imageUrl=");
			builder.append(imageUrl);
			builder.append(", ");
		}
		if (createDate != null) {
			builder.append("createDate=");
			builder.append(createDate);
			builder.append(", ");
		}
		builder.append("status=");
		builder.append(status);
		builder.append(", ");
		if (birthDay != null) {
			builder.append("birthDay=");
			builder.append(birthDay);
			builder.append(", ");
		}
		if (roleId != null) {
			builder.append("roleId=");
			builder.append(roleId);
		}
		builder.append("]");
		return builder.toString();
	}
}
