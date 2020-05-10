package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.almavivahealth.domain.entity.Role;
import fr.almavivahealth.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO representing a user, with his role.
 */
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank
	@Pattern(regexp = "^[_'.@A-Za-z0-9-]*$")
	@Size(min = 1, max = 50)
	private String pseudo;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	private String password;

	@Email
	@Size(min = 5, max = 100)
	private String email;

	@Size(max = 256)
	private String imageUrl;

	private LocalDate createDate;

	@NotNull
	private Boolean status;

	private LocalDate birthDay;

	private String roleName;

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
		final Role role = user.getRole();
		if (null != role) {
			this.roleName = role.getName();
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

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(final String password) {
		this.password = password;
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

	public Boolean isStatus() {
		return status;
	}

	public void setStatus(final Boolean status) {
		this.status = status;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(final LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(final String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
