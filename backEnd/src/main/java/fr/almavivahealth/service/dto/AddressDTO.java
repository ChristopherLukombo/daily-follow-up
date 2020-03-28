package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Address entity.
 */
@AllArgsConstructor
@Builder
public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	@NotNull
	private String streetName;
	
	@NotNull
	private String city;
	
	@NotNull
	@Pattern(regexp = "^(([0-8][0-9])|(9[0-5]))[0-9]{3}$")
	private String postalCode;

	public AddressDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(final String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(city, id, postalCode, streetName);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AddressDTO other = (AddressDTO) obj;
		return Objects.equals(city, other.city) && Objects.equals(id, other.id)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(streetName, other.streetName);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AddressDTO [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (streetName != null) {
			builder.append("streetName=");
			builder.append(streetName);
			builder.append(", ");
		}
		if (city != null) {
			builder.append("city=");
			builder.append(city);
			builder.append(", ");
		}
		if (postalCode != null) {
			builder.append("postalCode=");
			builder.append(postalCode);
		}
		builder.append("]");
		return builder.toString();
	}
}