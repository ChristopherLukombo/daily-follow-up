package fr.almavivahealth.service.impl.menu.document.cell;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCellRight.
 */
public class DataCellRight extends DataCell {

	private final String dietName;

	private final String textureName;

	private final String date;

	public DataCellRight(final String dietName, final String textureName, final String date) {
		this.dietName = dietName;
		this.textureName = textureName;
		this.date = date;
	}

	/**
	 * Creates the data cell.
	 *
	 * @return the cell
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public Cell createDataCell() throws DailyFollowUpException {
		final Cell cellDate = new Cell();
		cellDate.setVerticalAlignment(VerticalAlignment.BOTTOM);
		cellDate.add(getCell(date, TextAlignment.RIGHT, false, -1));
		cellDate.setBorderLeft(Border.NO_BORDER);
		cellDate.setBorderRight(Border.NO_BORDER);
		cellDate.setHeight(140);
		final Cell cell = new Cell();
		cell.setKeepTogether(true);
		cell.add(getCell(dietName, TextAlignment.RIGHT, false, -1));
		cell.add(getCell(textureName, TextAlignment.RIGHT, false, -1));
		cell.setBorderRight(Border.NO_BORDER);
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setHeight(180);
		cell.add(cellDate);
		return cell;
	}

}
