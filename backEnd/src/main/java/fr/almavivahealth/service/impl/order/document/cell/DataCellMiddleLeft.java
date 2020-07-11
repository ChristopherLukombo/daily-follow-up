package fr.almavivahealth.service.impl.order.document.cell;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCellMiddleLeft.
 *
 * @author christopher
 * @version 16
 */
public class DataCellMiddleLeft extends DataCell {

	private final List<String> elements;

	public DataCellMiddleLeft(final List<String> elements) {
		this.elements = elements;
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
		cell.setKeepTogether(true);
		cell.add(getCell(StringUtils.LF, TextAlignment.JUSTIFIED, true, -1));
		for (final String element : elements) {
			cell.add(getCell(element.toUpperCase(), TextAlignment.JUSTIFIED, true, -1));
		}
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderRight(Border.NO_BORDER);
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderBottom(Border.NO_BORDER);
		return cell;
	}

}
