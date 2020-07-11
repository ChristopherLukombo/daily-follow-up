package fr.almavivahealth.service.impl.order;

import java.time.LocalDate;

import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.service.impl.order.document.cell.DataCellBroker;

/**
 *
 * @author christopher
 * @version 17
 *
 */
class Coupon {

	private final String momentName;
	private final LocalDate selectedDate;

	private final DataCellBroker dataCellBroker;

	private final Order order;
	private final int index;
	private final int size;

	public Coupon(
			final String momentName,
			final LocalDate selectedDate,
			final DataCellBroker dataCellBroker,
			final Order order,
			final int index,
			final int size) {
		this.momentName = momentName;
		this.selectedDate = selectedDate;
		this.dataCellBroker = dataCellBroker;
		this.order = order;
		this.index = index;
		this.size = size;
	}

	public String getMomentName() {
		return momentName;
	}

	public LocalDate getSelectedDate() {
		return selectedDate;
	}

	public DataCellBroker getDataCellBroker() {
		return dataCellBroker;
	}

	public Order getOrder() {
		return order;
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}
}
