package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the Replacement entity.
 */
@AllArgsConstructor
@Builder
public class ReplacementDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private List<ContentDTO> entries;

	private List<ContentDTO> dishes;

	private List<ContentDTO> desserts;

	private List<ContentDTO> dairyProducts;

	public ReplacementDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public List<ContentDTO> getEntries() {
		return entries;
	}

	public void setEntries(final List<ContentDTO> entries) {
		this.entries = entries;
	}

	public List<ContentDTO> getDishes() {
		return dishes;
	}

	public void setDishes(final List<ContentDTO> dishes) {
		this.dishes = dishes;
	}

	public List<ContentDTO> getDesserts() {
		return desserts;
	}

	public void setDesserts(final List<ContentDTO> desserts) {
		this.desserts = desserts;
	}

	public List<ContentDTO> getDairyProducts() {
		return dairyProducts;
	}

	public void setDairyProducts(final List<ContentDTO> dairyProducts) {
		this.dairyProducts = dairyProducts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dairyProducts, desserts, dishes, entries, id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ReplacementDTO other = (ReplacementDTO) obj;
		return Objects.equals(dairyProducts, other.dairyProducts) && Objects.equals(desserts, other.desserts)
				&& Objects.equals(dishes, other.dishes) && Objects.equals(entries, other.entries)
				&& Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
