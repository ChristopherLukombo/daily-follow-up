package fr.almavivahealth.service.impl;

import static fr.almavivahealth.config.Constants.CLINIQUE_BERGER;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import fr.almavivahealth.dao.MenuRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.domain.Comment;
import fr.almavivahealth.domain.Content;
import fr.almavivahealth.domain.Day;
import fr.almavivahealth.domain.Diet;
import fr.almavivahealth.domain.Menu;
import fr.almavivahealth.domain.MomentDay;
import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.domain.Room;
import fr.almavivahealth.domain.Texture;
import fr.almavivahealth.domain.TypeMeal;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.MenuService;
import fr.almavivahealth.service.dto.MenuDTO;
import fr.almavivahealth.service.impl.menu.document.cell.DataCellBroker;
import fr.almavivahealth.service.impl.menu.document.cell.DataCellFooter;
import fr.almavivahealth.service.impl.menu.document.cell.DataCellLeft;
import fr.almavivahealth.service.impl.menu.document.cell.DataCellMiddleLeft;
import fr.almavivahealth.service.impl.menu.document.cell.DataCellMiddleRight;
import fr.almavivahealth.service.impl.menu.document.cell.DataCellRight;
import fr.almavivahealth.service.mapper.MenuMapper;
import fr.almavivahealth.service.propeties.MenuProperties;

/**
 * Service Implementation for managing Menu.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

	private final MenuRepository menuRepository;

	private final MenuMapper menuMapper;

	private final PatientRepository patientRepository;

	private final MenuProperties menuProperties;

	@Autowired
	public MenuServiceImpl(
			final MenuRepository menuRepository,
			final MenuMapper menuMapper,
			final PatientRepository patientRepository,
			final MenuProperties menuProperties) {
		this.menuRepository = menuRepository;
		this.menuMapper = menuMapper;
		this.patientRepository = patientRepository;
		this.menuProperties = menuProperties;
	}

	/**
	 * Save a menu.
	 *
	 * @param menuDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public MenuDTO save(final MenuDTO menuDTO) {
		LOGGER.debug("Request to save Menu : {}", menuDTO);
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
		menu = menuRepository.save(menu);
		return menuMapper.menuToMenuDTO(menu);
	}

	/**
	 * Update a menu.
	 *
	 * @param menuDTO the menu DTO
	 * @return the persisted entity
	 */
	@Override
	public MenuDTO update(final MenuDTO menuDTO) {
		LOGGER.debug("Request to update Menu : {}", menuDTO);
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
		menu = menuRepository.saveAndFlush(menu);
		return menuMapper.menuToMenuDTO(menu);
	}

	/**
	 * Get all the menus.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findAll() {
		LOGGER.debug("Request to get all Menus");
		return menuRepository.findAllByOrderByIdDesc().stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" menu.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<MenuDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Menu : {}", id);
		return menuRepository.findById(id)
				.map(menuMapper::menuToMenuDTO);
	}

	/**
	 * Delete the "id" menu.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Menu : {}", id);
		menuRepository.deleteById(id);
	}

	/**
	 * Generate coupons.
	 *
	 * @param momentName the moment name
	 * @param selectedDate the selected date
	 * @return the byte[]
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	@Transactional(readOnly = true)
	public byte[] generateCoupons(final String momentName, final LocalDate selectedDate) throws DailyFollowUpException {
		LOGGER.debug("Request to generate coupon");
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (final PdfWriter writer = new PdfWriter(baos)) {
			final Document document = createDocument(writer);
			addContent(document, momentName, selectedDate);
			document.close();
			return baos.toByteArray();
		} catch (final Exception e) {
			throw new DailyFollowUpException("the pdf could not be generated", e);
		}
	}

	private Document createDocument(final PdfWriter writer) {
		// Initialize PDF document
		final PdfDocument pdf = new PdfDocument(writer);
		// Initialize document
		return new Document(pdf, PageSize.A4);
	}

	private void addContent(final Document document, final String momentName, final LocalDate selectedDate)
			throws DailyFollowUpException {
		final float[] colWidths = { 2.5f, 2.5f, 2f, 1f };
		final Table table = new Table(UnitValue.createPercentArray(colWidths), true);
		document.add(table);

		// Get active patients
		final List<Patient> patients = patientRepository.findAllByStateTrueOrderByIdDesc();

		final DataCellBroker dataCellBroker = new DataCellBroker();
		final TemporalField temporalField = WeekFields.of(Locale.FRANCE).dayOfWeek();

		for (int i = 0; i < patients.size(); i++) {
			addMenus(momentName, selectedDate, table, patients, dataCellBroker, temporalField, i);
		}

		document.add(table);

		// Flushes the rest of the content and indicates that no more content will be
		// added to the table
		table.complete();
	}

	private void addMenus(final String momentName, final LocalDate selectedDate, final Table table,
			final List<Patient> patients, final DataCellBroker dataCellBroker, final TemporalField temporalField,
			final int i) throws DailyFollowUpException {

		final String textureName = findTextureName(patients.get(i));
		final Set<String> dietsName = findDietsName(patients.get(i));
		final String roomNumber = findRoomNumber(patients.get(i));
		final LocalDate startDateWeek = selectedDate.with(temporalField, 1);
		final LocalDate endDateWeek = selectedDate.with(temporalField, 7);
		final List<Menu> menus = menuRepository.findAllByWeek(textureName, dietsName, startDateWeek, endDateWeek);
		final Menu menu = !menus.isEmpty() ? menus.get(0) : null;

		table.addCell(dataCellBroker.createDataCell(
				new DataCellLeft(
						CLINIQUE_BERGER,
						patients.get(i).getLastName(),
				        roomNumber,
				        menuProperties.getImagesPath())));

		if (menu == null) {
			addDefaultCellMiddle(table, dataCellBroker);
		} else {
			final String dayToSearch = toDayOfWeek(selectedDate);
			final List<String> contentNames = findContentNames(menu, momentName, dayToSearch);
			if (!contentNames.isEmpty()) {
				table.addCell(dataCellBroker.createDataCell(
						new DataCellMiddleLeft(getNameByIndex(contentNames, 0),
								getNameByIndex(contentNames, 1),
								getNameByIndex(contentNames, 2),
								getNameByIndex(contentNames, 3))));
			} else {
				addDefaultCellMiddle(table, dataCellBroker);
			}
		}

		final String comment = findComment(patients.get(i));
		final String dietName = findDietName(menu);

		table.addCell(dataCellBroker.createDataCell(new DataCellMiddleRight(comment)));

		table.addCell(dataCellBroker.createDataCell(new DataCellRight(dietName, textureName,
				selectedDate.format(DateTimeFormatter.ofPattern("dd/MM")))));

		// Add footer
		if (((i + 1) % 4 == 0) || (patients.size() - 1 == i)) {
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
		}
	}

	private String findComment(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getComment)
				.map(Comment::getContent)
				.orElseGet(() -> StringUtils.EMPTY);
	}

	private String getNameByIndex(final List<String> contentNames, final int index) {
		return index >= 0 && index < contentNames.size() ? contentNames.get(index)
				: StringUtils.EMPTY;
	}

	private String toDayOfWeek(final LocalDate date) {
		return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);
	}

	private void addDefaultCellMiddle(final Table table, final DataCellBroker dataCellBroker)
			throws DailyFollowUpException {
		table.addCell(dataCellBroker.createDataCell(
				new DataCellMiddleLeft(StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY)));
	}

	private String findDietName(final Menu menu) {
		return Optional.ofNullable(menu)
				.map(Menu::getDiet)
				.map(Diet::getName)
				.orElseGet((() -> StringUtils.EMPTY));
	}

	private String findRoomNumber(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getRoom)
				.map(Room::getNumber)
				.orElseGet(() -> StringUtils.EMPTY);
	}

	private List<String> findContentNames(final Menu menu, final String momentDay, final String dayToSearch) {
        // Defined order of  typeMeals
		final List<String> definedOrder = Arrays.asList("ENTREE", "PLAT", "GARNITURE", "PRODUIT LAITIER", "DESSERT");

		return menu.getDays().stream()
				.filter(d -> isDaySelected(d, dayToSearch))
				.map(Day::getMomentDays)
				.flatMap(Collection::stream)
				.filter(m -> isMomentDay(momentDay, m))
				.map(MomentDay::getTypeMeals)
				.flatMap(Collection::stream)
				.sorted((o1, o2) -> Integer.valueOf(definedOrder.indexOf(removeSpecialChar(o1.getName())))
						.compareTo(Integer.valueOf(definedOrder.indexOf(removeSpecialChar(o2.getName())))))
				.map(TypeMeal::getContents)
				.flatMap(Collection::stream)
				.map(Content::getName)
				.collect(Collectors.toList());
	}

	private boolean isDaySelected(final Day day, final String dayToSearch) {
		return day.getName().equalsIgnoreCase(dayToSearch);
	}

	private boolean isMomentDay(final String momentDay, final MomentDay m) {
		return removeSpecialChar(m.getName()).equalsIgnoreCase(momentDay);
	}

	private String removeSpecialChar(final String text) {
		return StringUtils.stripAccents(text.trim());
	}

	private Set<String> findDietsName(final Patient patient) {
		return patient.getDiets().stream()
				.map(Diet::getName)
				.collect(Collectors.toSet());
	}

	private String findTextureName(final Patient patient) {
		return Optional.ofNullable(patient.getTexture())
				.map(Texture::getName)
				.orElseGet(() -> StringUtils.EMPTY);
	}

}
