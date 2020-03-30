package fr.almavivahealth.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    @Column(length = 100, unique = true)
    private String email;
	
	private String situation;
	
	private LocalDate dateOfBirth;
	
	private Address address;

	@Pattern(regexp = "^[+](\\d{3})\\)?(\\d{3})(\\d{5,6})$|^(\\d{10,10})$")
	private String phoneNumber;

	private String mobilePhone;
	
	@Size(max = 50)
	private String job;

	@Size(max = 3)
	private String bloodGroup;
	
	private Double height;
	
	private Double weight;
	
    private String sex;

	@NotNull
	private Boolean state;

	@ManyToOne
	private Texture texture;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Diet> diets;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Allergy> allergies;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
	private List<Order> orders;

	@OneToOne
	private Comment comment;
	
	@ManyToOne
	private Room room;
}
