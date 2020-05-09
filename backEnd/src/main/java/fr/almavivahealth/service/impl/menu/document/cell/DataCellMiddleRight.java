package fr.almavivahealth.service.impl.menu.document.cell;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCellMiddleRight.
 */
public class DataCellMiddleRight extends DataCell {

	private final String comment;

	public DataCellMiddleRight(final String comment) {
		this.comment = comment;
	}

	/**
	 * Creates the data cell.
	 *
	 * @return the cell
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public Cell createDataCell() throws DailyFollowUpException {
		final Cell cell = new Cell();
		cell.setPadding(0);
		cell.add(getCell(StringUtils.LF, TextAlignment.CENTER, false, -1));
		cell.add(getCell(StringUtils.LF, TextAlignment.CENTER, false, -1));
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderRight(Border.NO_BORDER);
		cell.add(getCell(comment, TextAlignment.JUSTIFIED, false, -1));
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderBottom(Border.NO_BORDER);
		return cell;
	}

}
