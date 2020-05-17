package fr.almavivahealth.service.impl.dish;

import static fr.almavivahealth.constants.Constants.DASH;
import static fr.almavivahealth.constants.Constants.MENUS_TABLE_CIQUAL2017_EXCEL_FR_2017_11_17_XLS;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import fr.almavivahealth.dao.DishRepository;
import fr.almavivahealth.domain.entity.Dish;
import fr.almavivahealth.service.DishImportationService;

/**
 * Service Implementation for managing importation Dish.
 */
@Service
@Transactional
public class DishImportationServiceImpl implements DishImportationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DishImportationServiceImpl.class);

	private final DishRepository dishRepository;

    @Autowired
	public DishImportationServiceImpl(final DishRepository dishRepository) {
		this.dishRepository = dishRepository;
	}

	/**
	 * Import dishes.
	 */
	@Override
	public void importDishes() {
		LOGGER.debug("Importing dishes");
		try (final InputStream inputStream = DishImportationServiceImpl.class.getClassLoader()
				.getResourceAsStream(MENUS_TABLE_CIQUAL2017_EXCEL_FR_2017_11_17_XLS);
				HSSFWorkbook wb = new HSSFWorkbook(inputStream)) {
			final HSSFSheet sheet = wb.getSheetAt(0);

			final List<Row> rows = IterableUtils.toList(sheet);
			LOGGER.info("Dishes numbers to import: {}", rows.size() - 1);

			final List<Dish> dishes = saveAll(rows);
			LOGGER.info("Imported dishes numbers: {}", dishes.size());
		} catch (final IOException e) {
			LOGGER.error("An error occurred while importing dishes", e);
		}
	}

	private List<Dish> saveAll(final List<Row> rows) {
		final Set<Dish> dishes = rows.stream()
				.skip(1)
				.map(this::buildDish)
				.collect(Collectors.toSet());

		return dishRepository.saveAll(dishes);
	}

	private Dish buildDish(final Row row) {
		final List<Cell> cells = ImmutableList.copyOf(row.cellIterator());

		return Dish.builder()
				.code((int) Double.parseDouble(getFieldAsString(cells, 6)))
				.name(getFieldAsString(cells, 7))
				.groupName(getFieldAsString(cells, 3))
				.subGroupName(getFieldAsString(cells, 4))
				.subSubGroupName(getFieldAsString(cells, 5))
				.calories(toDouble(getFieldAsString(cells, 11)))
				.protein(toDouble(getFieldAsString(cells, 13)))
				.carbohydrate(toDouble(getFieldAsString(cells, 15)))
				.lipids(toDouble(getFieldAsString(cells, 16)))
				.sugars(toDouble(getFieldAsString(cells, 17)))
				.foodFibres(toDouble(getFieldAsString(cells, 19)))
				.agSaturates(toDouble(getFieldAsString(cells, 24)))
				.salt(toDouble(getFieldAsString(cells, 42)))
				.build();
	}

	private String getFieldAsString(final List<Cell> array, final int index) {
		return array.size() > index ? getFieldFromCell(array.get(index)) : null;
	}

	private String getFieldFromCell(final Cell cell) {
		switch (cell.getCellType()) {
		case NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		case STRING:
			return DASH.equals(cell.getStringCellValue()) ? null : cell.getStringCellValue();
		default:
			return null;
		}
	}

	private Double toDouble(final String value) {
		if (null == value) {
			return null;
		}
		try {
			final String newvalue = value.replace(",", ".");
			return Double.parseDouble(newvalue);
		} catch (final NumberFormatException nfe) {
			return null;
		}
	}

	/**
	 * Checks for elements.
	 *
	 * @return true, if has elements
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean hasElements() {
		return dishRepository.count() > 0;
	}
}
