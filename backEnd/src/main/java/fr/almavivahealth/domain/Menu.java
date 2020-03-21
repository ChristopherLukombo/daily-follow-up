package fr.almavivahealth.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Menu implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MenuCodeGenerator")
	private Long id;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Integer weekNumber;
	
	private Texture texture;

	public Menu() {
		// Empty constructor needed for Hibernate.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(final LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(final LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(final Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(final Texture texture) {
		this.texture = texture;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endDate, id, startDate, texture, weekNumber);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Menu other = (Menu) obj;
		return Objects.equals(endDate, other.endDate) && Objects.equals(id, other.id)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(texture, other.texture)
				&& Objects.equals(weekNumber, other.weekNumber);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Menu [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (startDate != null) {
			builder.append("startDate=");
			builder.append(startDate);
			builder.append(", ");
		}
		if (endDate != null) {
			builder.append("endDate=");
			builder.append(endDate);
			builder.append(", ");
		}
		if (weekNumber != null) {
			builder.append("weekNumber=");
			builder.append(weekNumber);
			builder.append(", ");
		}
		if (texture != null) {
			builder.append("texture=");
			builder.append(texture);
		}
		builder.append("]");
		return builder.toString();
	}
}
