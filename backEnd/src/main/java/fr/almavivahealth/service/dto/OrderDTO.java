package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * A DTO for the MomentDay entity.
 */
@AllArgsConstructor
@Builder
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private LocalDate date;

	private String orderStatus;

	private List<ContentDTO> entries;

	private List<ContentDTO> dishes;

	private List<ContentDTO> desserts;

	private List<ContentDTO> starchyFoods;

	private List<ContentDTO> vegetables;

	private List<ContentDTO> dairyProducts;

	private Long patientId;

	public OrderDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(final String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(final Long patientId) {
		this.patientId = patientId;
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

	public List<ContentDTO> getStarchyFoods() {
		return starchyFoods;
	}

	public void setStarchyFoods(final List<ContentDTO> starchyFoods) {
		this.starchyFoods = starchyFoods;
	}

	public List<ContentDTO> getVegetables() {
		return vegetables;
	}

	public void setVegetables(final List<ContentDTO> vegetables) {
		this.vegetables = vegetables;
	}

	public List<ContentDTO> getDairyProducts() {
		return dairyProducts;
	}

	public void setDairyProducts(final List<ContentDTO> dairyProducts) {
		this.dairyProducts = dairyProducts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dairyProducts, date, desserts, dishes, entries, id, orderStatus, patientId, starchyFoods,
				vegetables);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OrderDTO other = (OrderDTO) obj;
		return Objects.equals(dairyProducts, other.dairyProducts) && Objects.equals(date, other.date)
				&& Objects.equals(desserts, other.desserts) && Objects.equals(dishes, other.dishes)
				&& Objects.equals(entries, other.entries) && Objects.equals(id, other.id)
				&& orderStatus == other.orderStatus && Objects.equals(patientId, other.patientId)
				&& Objects.equals(starchyFoods, other.starchyFoods) && Objects.equals(vegetables, other.vegetables);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
