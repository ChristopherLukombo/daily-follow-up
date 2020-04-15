package fr.almavivahealth.service.impl.menu.document.cell;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCellFooter.
 */
public class DataCellFooter extends DataCell {

	/**
	 * Creates the data cell.
	 *
	 * @return the cell
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public Cell createDataCell() throws DailyFollowUpException {
		final Cell cell = new Cell();
		cell.setBorderRight(Border.NO_BORDER);
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderBottom(Border.NO_BORDER);
		return cell;
	}

}
