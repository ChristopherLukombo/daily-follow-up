package fr.almavivahealth.service.impl.menu;

import java.time.LocalDate;

import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.service.impl.menu.document.cell.DataCellBroker;

class Coupon {

	private final String momentName;
	private final LocalDate selectedDate;

	private final DataCellBroker dataCellBroker;

	private final Patient patient;
	private final int index;
	private final int size;

	public Coupon(
			final String momentName,
			final LocalDate selectedDate,
			final DataCellBroker dataCellBroker,
			final Patient patient,
			final int index,
			final int size) {
		this.momentName = momentName;
		this.selectedDate = selectedDate;
		this.dataCellBroker = dataCellBroker;
		this.patient = patient;
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

	public Patient getPatient() {
		return patient;
	}

	public int getIndex() {
		return index;
	}

	public int getSize() {
		return size;
	}
}
