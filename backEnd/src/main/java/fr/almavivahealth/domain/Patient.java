package fr.almavivahealth.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.SerializationUtils;

import fr.almavivahealth.listener.PatientEntityListener;
import fr.almavivahealth.service.validator.patient.ValidBloodGroup;
import fr.almavivahealth.service.validator.patient.ValidGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author christopher A patient.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
@EntityListeners(PatientEntityListener.class)
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 2, max = 20)
	@Column(name = "first_name", length = 20)
	private String firstName;

	@Size(min = 2, max = 20)
	@Column(name = "last_name", length = 20)
	private String lastName;

	@Email
	@Size(min = 5, max = 100)
	@Column(length = 100, unique = true)
	private String email;

	private String situation;

	private LocalDate dateOfBirth;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@Pattern(regexp = "^((\\+|00)33\\s?|0)[1-59](\\s?\\d{2}){4}$", message = "{error.patient.phoneNumber_not_valid}")
	private String phoneNumber;

	@Pattern(regexp = "^((\\+|00)33\\s?|0)[67](\\s?\\d{2}){4}$", message = "{error.patient.phoneMobilePhone_not_valid}")
	private String mobilePhone;

	@Size(max = 50)
	private String job;

	@Size(max = 3)
	@Column(name = "blood_group", columnDefinition = "VARCHAR(3) CHECK (blood_group IN ('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-'))")
	@ValidBloodGroup
	private String bloodGroup;

	@Max(251)
	private Double height;

	@Max(597)
	private Double weight;

	@Column(name = "sex", columnDefinition = "VARCHAR(10) CHECK (sex IN ('Homme', 'Femme'))")
	@ValidGender
	private String sex;

	@NotNull
	private Boolean state;

	@OneToOne
	private Texture texture;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Diet> diets;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Allergy> allergies;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
	private List<Order> orders;

	@OneToOne(cascade = CascadeType.ALL)
	private Comment comment;

	@ManyToOne
	private Room room;
	
	@Transient
	private transient Patient previousPatient;

	@PostLoad
	private void savePreviousPatient() {
		this.previousPatient = SerializationUtils.clone(this);
	}

	public Patient getPreviousPatient() {
		return previousPatient;
	}
	
}
