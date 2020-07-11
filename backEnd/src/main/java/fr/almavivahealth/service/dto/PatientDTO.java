package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import fr.almavivahealth.domain.validator.patient.ValidBloodGroup;
import fr.almavivahealth.domain.validator.patient.ValidGender;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Patient entity.
 *
 * @author christopher
 * @version 16
 */
@AllArgsConstructor
@Builder
public class PatientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    @Size(min = 2, max = 20)
	@NotNull(message = "{error.patient.firstName_not_null}")
    private String firstName;

    @Size(min = 2, max = 20)
	@NotNull(message = "{error.patient.lastName_not_null}")
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

	private String situation;

	private LocalDate dateOfBirth;

	private AddressDTO address;

	@Pattern(regexp = "^((\\+|00)33\\s?|0)[1-59](\\s?\\d{2}){4}$", message = "{error.patient.phoneNumber_not_valid}")
	private String phoneNumber;

	@Pattern(regexp = "^((\\+|00)33\\s?|0)[67](\\s?\\d{2}){4}$", message = "{error.patient.phoneMobilePhone_not_valid}")
	private String mobilePhone;

	@Size(max = 50)
	private String job;

	@Size(max = 3)
	@ValidBloodGroup
	private String bloodGroup;

	@Max(251)
    private Double height;

	@Max(597)
	private Double weight;

	@ValidGender
    private String sex;

	private Boolean state;

	@NotNull(message = "{error.patient.texture_not_null}")
	private TextureDTO texture;

	@NotEmpty(message = "{error.patient.diet_not_empty}")
	private List<DietDTO> diets;

	private List<AllergyDTO> allergies;

	private List<OrderDTO> orders;

	private CommentDTO comment;

	private Long roomId;

	public PatientDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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

	public String getSituation() {
		return situation;
	}

	public void setSituation(final String situation) {
		this.situation = situation;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(final AddressDTO address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(final String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getJob() {
		return job;
	}

	public void setJob(final String job) {
		this.job = job;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(final String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(final Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(final Double weight) {
		this.weight = weight;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(final String sex) {
		this.sex = sex;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(final Boolean state) {
		this.state = state;
	}

	public TextureDTO getTexture() {
		return texture;
	}

	public void setTexture(final TextureDTO texture) {
		this.texture = texture;
	}

	public List<DietDTO> getDiets() {
		return diets;
	}

	public void setDiets(final List<DietDTO> diets) {
		this.diets = diets;
	}

	public List<AllergyDTO> getAllergies() {
		return allergies;
	}

	public void setAllergies(final List<AllergyDTO> allergies) {
		this.allergies = allergies;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(final List<OrderDTO> orders) {
		this.orders = orders;
	}

	public CommentDTO getComment() {
		return comment;
	}

	public void setComment(final CommentDTO comment) {
		this.comment = comment;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(final Long roomId) {
		this.roomId = roomId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, allergies, bloodGroup, comment, dateOfBirth, diets, email, firstName, height, id,
				job, lastName, mobilePhone, orders, phoneNumber, roomId, sex, situation, state, texture, weight);
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
		return Objects.equals(address, other.address) && Objects.equals(allergies, other.allergies)
				&& Objects.equals(bloodGroup, other.bloodGroup) && Objects.equals(comment, other.comment)
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(diets, other.diets)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(height, other.height) && Objects.equals(id, other.id)
				&& Objects.equals(job, other.job) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(mobilePhone, other.mobilePhone) && Objects.equals(orders, other.orders)
				&& Objects.equals(phoneNumber, other.phoneNumber) && Objects.equals(roomId, other.roomId)
				&& Objects.equals(sex, other.sex) && Objects.equals(situation, other.situation)
				&& Objects.equals(state, other.state) && Objects.equals(texture, other.texture)
				&& Objects.equals(weight, other.weight);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
