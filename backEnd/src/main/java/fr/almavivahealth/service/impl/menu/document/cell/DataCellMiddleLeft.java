package fr.almavivahealth.service.impl.menu.document.cell;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCellMiddleLeft.
 */
public class DataCellMiddleLeft extends DataCell {
	
	private final String entry;
	
	private final String dish; 
	
	private final String topping;
	
	private final String dessert;

	public DataCellMiddleLeft(final String entry, final String dish, final String topping, final String dessert) {
		this.entry = entry;
		this.dish = dish;
		this.topping = topping;
		this.dessert = dessert;
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
		cell.add(getCell(entry.toUpperCase(), TextAlignment.JUSTIFIED, true, -1));
		cell.add(getCell(dish.toUpperCase(), TextAlignment.JUSTIFIED, true, -1));
		cell.add(getCell(topping.toUpperCase(), TextAlignment.JUSTIFIED, true, -1));
		cell.add(getCell(dessert.toUpperCase(), TextAlignment.JUSTIFIED, true, -1));
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderRight(Border.NO_BORDER);
		cell.setBorderLeft(Border.NO_BORDER);
		cell.setBorderBottom(Border.NO_BORDER);
		return cell;
	}

}
