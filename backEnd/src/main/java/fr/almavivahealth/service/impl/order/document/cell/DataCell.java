package fr.almavivahealth.service.impl.order.document.cell;

import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.canvas.PdfCanvasConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * The Class DataCell.
 */
public abstract class DataCell {

	/**
	 * Gets the cell.
	 *
	 * @param text the text
	 * @param alignment the alignment
	 * @param boldEnabled the bold enabled
	 * @param size the size
	 * @return the cell
	 */
	protected Cell getCell(final String text, final TextAlignment alignment, final boolean boldEnabled, final int size) {
	    final Cell cell = new Cell();

	    final Paragraph paragraph = new Paragraph(StringUtils.isNotBlank(text) ? text : StringUtils.LF);

	    if (boldEnabled) {
	    	paragraph.setTextRenderingMode(PdfCanvasConstants.TextRenderingMode.FILL_STROKE);
	  	    paragraph.setStrokeWidth(0.5f);
	  	    paragraph.setStrokeColor(DeviceGray.BLACK);
	    }

	    if (size != -1) {
	  	    paragraph.setFontSize(8);
	    }

		cell.add(paragraph);
	    cell.setPadding(0);
	    cell.setTextAlignment(alignment);
	    return cell;
	}

	/**
	 * Creates the image.
	 *
	 * @param filename the name
	 * @return the image
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	protected Image createImage(final String filename) throws DailyFollowUpException {
		try {
			final ImageData imageData = ImageDataFactory.create(filename);
			return new Image(imageData);
		} catch (final MalformedURLException e) {
			throw new DailyFollowUpException(
					"An error occurred while trying to create the image", e);
		}
	}

	/**
	 * Creates the data cell.
	 *
	 * @return the cell
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	public abstract Cell createDataCell() throws DailyFollowUpException;

}
