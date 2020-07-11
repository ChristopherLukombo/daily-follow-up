package fr.almavivahealth.service.impl.order.document.cell;

import static fr.almavivahealth.constants.Constants.BON_APPETIT;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.property.TextAlignment;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCellLeft.
 *
 * @author christopher
 * @version 17
 */
public class DataCellLeft extends DataCell {

	private final String patientName;

	private final String roomNumber;

	private final String filename;

	public DataCellLeft(
			final String patientName,
			final String roomNumber,
			final String filename) {
		this.patientName = patientName;
		this.roomNumber = roomNumber;
		this.filename = filename;
	}

	/**
	 * Creates the data cell.
	 *
	 * @return the cell
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public Cell createDataCell() throws DailyFollowUpException {
		final Image imageLogo = createImage(filename);
		imageLogo.setHeight(50);
		imageLogo.setWidth(100);
		final Cell cell = new Cell();
		cell.setKeepTogether(true);
		cell.add(imageLogo);
		cell.add(getCell(StringUtils.LF, TextAlignment.LEFT, false, -1));
		cell.add(getCell(patientName, TextAlignment.LEFT, false, -1));
		cell.add(getCell(StringUtils.LF, TextAlignment.LEFT, false, -1));
		cell.add(getCell(roomNumber, TextAlignment.LEFT, false, -1));
		cell.add(getCell(StringUtils.LF, TextAlignment.LEFT, false, -1));
		cell.add(getCell(BON_APPETIT, TextAlignment.LEFT, false, -1));
		cell.setBorderRight(Border.NO_BORDER);
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderBottom(Border.NO_BORDER);
		return cell;
	}

}
