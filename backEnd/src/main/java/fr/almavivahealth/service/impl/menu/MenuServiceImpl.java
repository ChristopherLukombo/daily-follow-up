package fr.almavivahealth.service.impl.menu;

import static fr.almavivahealth.constants.Constants.CLINIQUE_BERGER;
import static fr.almavivahealth.util.functional.LoopWithIndexAndSizeConsumer.forEach;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import fr.almavivahealth.domain.entity.Comment;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.entity.Day;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.entity.Room;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.domain.entity.Week;
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
		return menuRepository.findAllByOrderByIdAsc().stream()
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
		try (PdfWriter writer = new PdfWriter(baos)) {
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

	private void addContent(final Document document, final String momentName, final LocalDate selectedDate) {
		final float[] colWidths = { 2.5f, 2.5f, 2f, 1f };
		final Table table = new Table(UnitValue.createPercentArray(colWidths), true);
		document.add(table);

		// Get active patients
		final List<Patient> patients = patientRepository.findAllByStateTrueOrderByIdDesc();

		final DataCellBroker dataCellBroker = new DataCellBroker();

		forEach(patients, (patient, index, size) ->
		this.buildCoupon(table, new Coupon(momentName, selectedDate, dataCellBroker, patient, index, size)));

		document.add(table);

		// Flushes the rest of the content and indicates that no more content will be
		// added to the table
		table.complete();
	}

	private void buildCoupon(final Table table, final Coupon coupon) throws DailyFollowUpException {
		final String roomNumber = findRoomNumber(coupon.getPatient());
		table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellLeft(CLINIQUE_BERGER,
				coupon.getPatient().getLastName(), roomNumber, menuProperties.getImagesPath())));

		final List<String> dietsName = findDietsName(coupon.getPatient());
		final String textureName = findTextureName(coupon.getPatient());
		final List<Menu> menus = findAllByWeek(coupon.getSelectedDate(), dietsName, textureName);
		final Menu menu = !menus.isEmpty() ? menus.get(0) : null;
		if (menu == null) {
			addDefaultCellMiddle(table, coupon.getDataCellBroker());
		} else {
			final String dayToSearch = toDayOfWeek(coupon.getSelectedDate());
			final List<String> contentNames = findContentNames(menu, coupon.getMomentName(), dayToSearch);
			table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellMiddleLeft(contentNames)));
		}

		final String comment = findComment(coupon.getPatient());
		table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellMiddleRight(comment)));

		final String dietName = findDietName(menu);
		table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellRight(dietName, textureName,
				coupon.getSelectedDate().format(DateTimeFormatter.ofPattern("dd/MM")))));

		addFooter(table, coupon.getDataCellBroker(), coupon.getIndex(), coupon.getSize());
	}

	private void addFooter(final Table table, final DataCellBroker dataCellBroker, final int index, final int size)
			throws DailyFollowUpException {
		if (((index + 1) % 4 == 0) || (size - 1 == index)) {
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
			table.addCell(dataCellBroker.createDataCell(new DataCellFooter()));
		}
	}

	private List<Menu> findAllByWeek(
			final LocalDate selectedDate,
			final List<String> dietsName,
			final String textureName) {
		final LocalDate startDateWeek = findDateFromDay(selectedDate, 1);
		final LocalDate endDateWeek = findDateFromDay(selectedDate, 7);
		return menuRepository.findAllByWeek(textureName, dietsName, startDateWeek, endDateWeek);
	}

	private void addDefaultCellMiddle(final Table table, final DataCellBroker dataCellBroker)
			throws DailyFollowUpException {
		table.addCell(dataCellBroker.createDataCell(new DataCellMiddleLeft(Collections.emptyList())));
	}

	private LocalDate findDateFromDay(final LocalDate date, final int day) {
		final TemporalField temporalField = WeekFields.of(Locale.FRANCE).dayOfWeek();
		return date.with(temporalField, day);
	}

	private String findComment(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getComment)
				.map(Comment::getContent)
				.orElseGet(() -> StringUtils.EMPTY);
	}

	private String toDayOfWeek(final LocalDate date) {
		return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRANCE);
	}

	private String findDietName(final Menu menu) {
		return Optional.ofNullable(menu)
				.map(Menu::getDiets)
				.map(Collection::toString)
				.orElseGet(() -> StringUtils.EMPTY);
	}

	private String findRoomNumber(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getRoom)
				.map(Room::getNumber)
				.orElseGet(() -> StringUtils.EMPTY);
	}

	private List<String> findContentNames(final Menu menu, final String momentDay, final String dayToSearch) {
		final MomentDay md = menu.getWeeks().stream()
				.map(Week::getDays)
				.flatMap(Collection::stream)
				.filter(d -> isDaySelected(d, dayToSearch))
				.map(Day::getMomentDays)
				.flatMap(Collection::stream)
				.filter(m -> isMomentDay(momentDay, m))
				.findFirst()
				.orElse(null);

		if (null == md) {
			return Collections.emptyList();
		}

		return Arrays.asList(
				toName(md.getEntry()),
				toName(md.getDish()),
				toName(md.getGarnish()),
				toName(md.getDairyProduct()),
				toName(md.getDessert()));
	}

	private String toName(final Content content) {
		return Optional.ofNullable(content)
				.map(Content::getName)
				.orElse(StringUtils.EMPTY);
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

	private List<String> findDietsName(final Patient patient) {
		return patient.getDiets().stream()
				.map(Diet::getName)
				.collect(Collectors.toList());
	}

	private String findTextureName(final Patient patient) {
		return Optional.ofNullable(patient.getTexture())
				.map(Texture::getName)
				.orElseGet(() -> StringUtils.EMPTY);
	}


	/**
	 * Find current menus.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findCurrentMenus() {
		return menuRepository.findCurrentMenus(LocalDate.now()).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Check specifications.
	 *
	 * @param menuDTO the menu DTO
	 * @return true, if successful
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean checkSpecifications(final MenuDTO menuDTO) {
		return menuRepository.findAll().stream()
				.filter(menu -> isOverlapped(menuDTO, menu))
				.anyMatch(menu -> isTheSameCharacteristics(menuDTO, menu));
	}

	private boolean isOverlapped(final MenuDTO menuDTO, final Menu menu) {
		if (null == menu.getStartDate() || null == menu.getEndDate()) {
			return false;
		}

		boolean overlapped = false;
		if ((menuDTO.getStartDate().compareTo(menu.getStartDate()) < 0)
				&& (menuDTO.getEndDate().compareTo(menu.getStartDate()) < 0)) {
			overlapped = false;
		} else if ((menu.getStartDate().compareTo(menuDTO.getStartDate()) < 0)
				&& (menu.getEndDate().compareTo(menuDTO.getStartDate()) < 0)) {
			overlapped = false;
		} else {
			overlapped = true;
		}
		return overlapped;
	}

	private boolean isTheSameCharacteristics(final MenuDTO menuDTO, final Menu menu) {
		if (menuDTO.getId() != null && menuDTO.getId().equals(menu.getId())) {
			return false;
		}

		return menu.getDiets().containsAll(menuDTO.getDiets())
				&& findString(menu.getTexture()).equalsIgnoreCase(menuDTO.getTexture());
	}

	private String findString(final String value) {
		return Optional.ofNullable(value)
				.map(String::trim)
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Find Future menus.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findFutureMenus() {
		return menuRepository.findFutureMenus(LocalDate.now()).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find Past menus.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findPastMenus() {
		return menuRepository.findPastMenus(LocalDate.now()).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}
}
