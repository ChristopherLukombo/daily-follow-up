package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private LocalDate deliveryDate;

	private String orderStatus;

	private ContentDTO entry;

	private ContentDTO dish;

	private ContentDTO dessert;

	private ContentDTO dairyProduct;

	private ContentDTO garnish;

	private String moment;

	private Long patientId;

	private String createdBy;

	private LocalDateTime createdDate;

	private LocalDateTime lastModifDate;

	private String lastModifBy;

	public OrderDTO() {
		// Empty constructor needed for Jackson.
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
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

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(final LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public ContentDTO getEntry() {
		return entry;
	}

	public void setEntry(final ContentDTO entry) {
		this.entry = entry;
	}

	public ContentDTO getDish() {
		return dish;
	}

	public void setDish(final ContentDTO dish) {
		this.dish = dish;
	}

	public ContentDTO getDessert() {
		return dessert;
	}

	public void setDessert(final ContentDTO dessert) {
		this.dessert = dessert;
	}

	public ContentDTO getDairyProduct() {
		return dairyProduct;
	}

	public void setDairyProduct(final ContentDTO dairyProduct) {
		this.dairyProduct = dairyProduct;
	}

	public ContentDTO getGarnish() {
		return garnish;
	}

	public void setGarnish(final ContentDTO garnish) {
		this.garnish = garnish;
	}

	public String getMoment() {
		return moment;
	}

	public void setMoment(final String moment) {
		this.moment = moment;
	}

	public LocalDateTime getLastModifDate() {
		return lastModifDate;
	}

	public void setLastModifDate(final LocalDateTime lastModifDate) {
		this.lastModifDate = lastModifDate;
	}

	public String getLastModifBy() {
		return lastModifBy;
	}

	public void setLastModifBy(final String lastModifBy) {
		this.lastModifBy = lastModifBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(final LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdBy, createdDate, dairyProduct, deliveryDate, dessert, dish, entry, garnish, id,
				lastModifBy, lastModifDate, moment, orderStatus, patientId);
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
		return Objects.equals(createdBy, other.createdBy) && Objects.equals(createdDate, other.createdDate)
				&& Objects.equals(dairyProduct, other.dairyProduct) && Objects.equals(deliveryDate, other.deliveryDate)
				&& Objects.equals(dessert, other.dessert) && Objects.equals(dish, other.dish)
				&& Objects.equals(entry, other.entry) && Objects.equals(garnish, other.garnish)
				&& Objects.equals(id, other.id) && Objects.equals(lastModifBy, other.lastModifBy)
				&& Objects.equals(lastModifDate, other.lastModifDate) && Objects.equals(moment, other.moment)
				&& Objects.equals(orderStatus, other.orderStatus) && Objects.equals(patientId, other.patientId);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
