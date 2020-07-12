package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import fr.almavivahealth.domain.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the MomentDay entity.
 *
 * @author christopher
 * @version 17
 */
@AllArgsConstructor
@Builder
public class MomentDayDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private Content entry;

	private Content dish;

	private Content garnish;

	private Content dairyProduct;

	private Content dessert;

	public MomentDayDTO() {
		// Empty constructor needed for Jackson.
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

	public Content getEntry() {
		return entry;
	}

	public void setEntry(final Content entry) {
		this.entry = entry;
	}

	public Content getDish() {
		return dish;
	}

	public void setDish(final Content dish) {
		this.dish = dish;
	}

	public Content getGarnish() {
		return garnish;
	}

	public void setGarnish(final Content garnish) {
		this.garnish = garnish;
	}

	public Content getDairyProduct() {
		return dairyProduct;
	}

	public void setDairyProduct(final Content dairyProduct) {
		this.dairyProduct = dairyProduct;
	}

	public Content getDessert() {
		return dessert;
	}

	public void setDessert(final Content dessert) {
		this.dessert = dessert;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dairyProduct, dessert, dish, entry, garnish, id, name);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MomentDayDTO other = (MomentDayDTO) obj;
		return Objects.equals(dairyProduct, other.dairyProduct) && Objects.equals(dessert, other.dessert)
				&& Objects.equals(dish, other.dish) && Objects.equals(entry, other.entry)
				&& Objects.equals(garnish, other.garnish) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
