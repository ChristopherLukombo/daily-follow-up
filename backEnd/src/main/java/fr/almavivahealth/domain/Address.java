package fr.almavivahealth.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
*
* @author christopher A address.
*/
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String streetName;
	
	@NotNull
	private String city;
	
	@NotNull
	@Pattern(regexp = "^(([0-8][0-9])|(9[0-5]))[0-9]{3}$")
	@Column(name = "postal_code", nullable = false)
	private String postalCode;
	
	@OneToOne(mappedBy = "address")
	private Patient patient;
	 
}
