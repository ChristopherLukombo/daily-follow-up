package fr.almavivahealth.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
public class MomentDay implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<TypeMeal> typeMeals;

	public MomentDay() {
		// Empty constructor needed for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<TypeMeal> getTypeMeals() {
		return typeMeals;
	}

	public void setTypeMeals(final List<TypeMeal> typeMeals) {
		this.typeMeals = typeMeals;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, typeMeals);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MomentDay other = (MomentDay) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(typeMeals, other.typeMeals);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("MomentDay [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (typeMeals != null) {
			builder.append("typeMeals=");
			builder.append(typeMeals);
		}
		builder.append("]");
		return builder.toString();
	}
}
