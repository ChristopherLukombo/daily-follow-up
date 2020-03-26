package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import fr.almavivahealth.domain.Texture;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Patient entity.
 */
@AllArgsConstructor
@Builder
public class PatientDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;
	
	private String situation;
	
	private String address;
	
	@NotNull
	@Pattern(regexp = "^(([0-8][0-9])|(9[0-5]))[0-9]{3}$")
	private String postalCode;

	@Pattern(regexp = "^[+](\\d{3})\\)?(\\d{3})(\\d{5,6})$|^(\\d{10,10})$")
	private String phoneNumber;

	private String mobilePhone;
	
	@Size(max = 50)
	private String job;

	private String bloodGroup;
	
    private String sex;
	
	private Boolean state;

	private Texture texture;
	
	private List<DietDTO> diets;
	
	private List<AllergyDTO> allergies;
	
	private List<OrderDTO> orders;

	private CommentDTO comment;
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
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

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(final Texture texture) {
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

	@Override
	public int hashCode() {
		return Objects.hash(address, allergies, bloodGroup, comment, diets, email, firstName, id, job, lastName,
				mobilePhone, orders, phoneNumber, postalCode, sex, situation, state, texture);
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
				&& Objects.equals(diets, other.diets) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(job, other.job) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(mobilePhone, other.mobilePhone) && Objects.equals(orders, other.orders)
				&& Objects.equals(phoneNumber, other.phoneNumber) && Objects.equals(postalCode, other.postalCode)
				&& Objects.equals(sex, other.sex) && Objects.equals(situation, other.situation)
				&& Objects.equals(state, other.state) && Objects.equals(texture, other.texture);
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
		if (situation != null) {
			builder.append("situation=");
			builder.append(situation);
			builder.append(", ");
		}
		if (address != null) {
			builder.append("address=");
			builder.append(address);
			builder.append(", ");
		}
		if (postalCode != null) {
			builder.append("postalCode=");
			builder.append(postalCode);
			builder.append(", ");
		}
		if (phoneNumber != null) {
			builder.append("phoneNumber=");
			builder.append(phoneNumber);
			builder.append(", ");
		}
		if (mobilePhone != null) {
			builder.append("mobilePhone=");
			builder.append(mobilePhone);
			builder.append(", ");
		}
		if (job != null) {
			builder.append("job=");
			builder.append(job);
			builder.append(", ");
		}
		if (bloodGroup != null) {
			builder.append("bloodGroup=");
			builder.append(bloodGroup);
			builder.append(", ");
		}
		if (sex != null) {
			builder.append("sex=");
			builder.append(sex);
			builder.append(", ");
		}
		if (state != null) {
			builder.append("state=");
			builder.append(state);
			builder.append(", ");
		}
		if (texture != null) {
			builder.append("texture=");
			builder.append(texture);
			builder.append(", ");
		}
		if (diets != null) {
			builder.append("diets=");
			builder.append(diets);
			builder.append(", ");
		}
		if (allergies != null) {
			builder.append("allergies=");
			builder.append(allergies);
			builder.append(", ");
		}
		if (orders != null) {
			builder.append("orders=");
			builder.append(orders);
			builder.append(", ");
		}
		if (comment != null) {
			builder.append("comment=");
			builder.append(comment);
		}
		builder.append("]");
		return builder.toString();
	}
}