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
public class Day implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToMany(fetch = FetchType.LAZY)
	private List<MomentDay> momentDays;

	public Day() {
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

	public List<MomentDay> getMomentDays() {
		return momentDays;
	}

	public void setMomentDays(final List<MomentDay> momentDays) {
		this.momentDays = momentDays;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, momentDays, name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Day other = (Day) obj;
		return Objects.equals(id, other.id) && Objects.equals(momentDays, other.momentDays)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Day [");
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
		if (momentDays != null) {
			builder.append("momentDays=");
			builder.append(momentDays);
		}
		builder.append("]");
		return builder.toString();
	}
}
