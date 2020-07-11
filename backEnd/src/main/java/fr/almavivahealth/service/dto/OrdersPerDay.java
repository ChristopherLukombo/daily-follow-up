package fr.almavivahealth.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A DTO for the OrdersPerDay entity.
 *
 * @author christopher
 * @version 17
 */
public class OrdersPerDay implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDate date;

	private Long count;

	public OrdersPerDay(final LocalDate date, final Long count) {
		this.date = date;
		this.count = count;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(final Long count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		return Objects.hash(count, date);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OrdersPerDay other = (OrdersPerDay) obj;
		return Objects.equals(count, other.count) && Objects.equals(date, other.date);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
