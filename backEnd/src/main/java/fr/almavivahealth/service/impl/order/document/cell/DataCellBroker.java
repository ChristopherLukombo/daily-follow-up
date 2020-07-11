package fr.almavivahealth.service.impl.order.document.cell;

import com.itextpdf.layout.element.Cell;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCellBroker.
 */
public class DataCellBroker {

	/**
	 * Creates the data cell.
	 *
	 * @param dataCell the data cell
	 * @return the cell
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	public Cell createDataCell(final DataCell dataCell) throws DailyFollowUpException {
		return dataCell.createDataCell();
	}
}
