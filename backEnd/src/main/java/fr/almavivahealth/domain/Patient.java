package fr.almavivahealth.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User userEntity;
	
    @NotNull
    @Column(nullable = false)
	private boolean state;
	
	@ManyToOne
	private Texture texture;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Diet> diets;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Allergy> allergies;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
	private List<Order> orders;

	public Patient() {
		// Empty constructor needed for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public User getUser() {
		return userEntity;
	}

	public void setUser(final User user) {
		this.userEntity = user;
	}

	public boolean isState() {
		return state;
	}

	public void setState(final boolean state) {
		this.state = state;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(final Texture texture) {
		this.texture = texture;
	}

	public List<Diet> getDiets() {
		return diets;
	}

	public void setDiets(final List<Diet> diets) {
		this.diets = diets;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(final List<Allergy> allergies) {
		this.allergies = allergies;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(final List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		return Objects.hash(allergies, diets, id, orders, state, texture, userEntity);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Patient other = (Patient) obj;
		return Objects.equals(allergies, other.allergies) && Objects.equals(diets, other.diets)
				&& Objects.equals(id, other.id) && Objects.equals(orders, other.orders) && state == other.state
				&& Objects.equals(texture, other.texture) && Objects.equals(userEntity, other.userEntity);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Patient [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (userEntity != null) {
			builder.append("userEntity=");
			builder.append(userEntity);
			builder.append(", ");
		}
		builder.append("state=");
		builder.append(state);
		builder.append(", ");
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
		}
		builder.append("]");
		return builder.toString();
	}
}
