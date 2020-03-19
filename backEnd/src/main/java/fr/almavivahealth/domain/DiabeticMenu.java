package fr.almavivahealth.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class DiabeticMenu extends Menu {

	private static final long serialVersionUID = 1L;

	@OneToMany(fetch = FetchType.LAZY)
	private List<TypeMeal> replacements;
	
	private Diet diet;

	public DiabeticMenu() {
		// Empty constructor needed for Hibernate.
	}

	public List<TypeMeal> getReplacements() {
		return replacements;
	}

	public void setReplacements(final List<TypeMeal> replacements) {
		this.replacements = replacements;
	}

	public Diet getDiet() {
		return diet;
	}

	public void setDiet(final Diet diet) {
		this.diet = diet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(diet, replacements);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DiabeticMenu other = (DiabeticMenu) obj;
		return Objects.equals(diet, other.diet) && Objects.equals(replacements, other.replacements);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("DiabeticMenu [");
		if (replacements != null) {
			builder.append("replacements=");
			builder.append(replacements);
			builder.append(", ");
		}
		if (diet != null) {
			builder.append("diet=");
			builder.append(diet);
		}
		builder.append("]");
		return builder.toString();
	}
}
