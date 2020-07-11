package fr.almavivahealth.service.impl.order;

import static fr.almavivahealth.util.functional.LoopWithIndexAndSizeConsumer.forEach;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
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

import fr.almavivahealth.dao.OrderRepository;
import fr.almavivahealth.domain.entity.Comment;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.entity.Room;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.OrderService;
import fr.almavivahealth.service.dto.OrderDTO;
import fr.almavivahealth.service.impl.order.document.cell.DataCellBroker;
import fr.almavivahealth.service.impl.order.document.cell.DataCellFooter;
import fr.almavivahealth.service.impl.order.document.cell.DataCellLeft;
import fr.almavivahealth.service.impl.order.document.cell.DataCellMiddleLeft;
import fr.almavivahealth.service.impl.order.document.cell.DataCellMiddleRight;
import fr.almavivahealth.service.impl.order.document.cell.DataCellRight;
import fr.almavivahealth.service.mapper.OrderMapper;
import fr.almavivahealth.service.propeties.OrderProperties;

/**
 * Service Implementation for managing Order.
 *
 * @author christopher
 * @version 17
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	private final OrderRepository orderRepository;

	private final OrderMapper orderMapper;

	private final OrderProperties menuProperties;

    @Autowired
	public OrderServiceImpl(
			final OrderRepository orderRepository,
			final OrderMapper orderMapper,
			final OrderProperties menuProperties) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
		this.menuProperties = menuProperties;
	}

	/**
	 * Save a order.
	 *
	 * @param orderDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public OrderDTO save(final OrderDTO orderDTO) {
		LOGGER.debug("Request to save Order : {}", orderDTO);
		Order order = orderMapper.orderDTOToOrder(orderDTO);
		order = orderRepository.save(order);
		return orderMapper.orderToOrderDTO(order);
	}

	/**
	 * Update a order.
	 *
	 * @param orderDTO the order DTO
	 * @return the persisted entity
	 */
	@Override
	public OrderDTO update(final OrderDTO orderDTO) {
		LOGGER.debug("Request to update Order : {}", orderDTO);
		Order order = orderMapper.orderDTOToOrder(orderDTO);
		order = orderRepository.saveAndFlush(order);
		return orderMapper.orderToOrderDTO(order);
	}

	/**
	 * Get all the orders.
	 *
	 * @param selectedDate the selected date
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<OrderDTO> findAllForWeek(final LocalDate selectedDate) {
		LOGGER.debug("Request to get all Orders");
		final LocalDate startDate = findDateFromDay(selectedDate, 2);
		final LocalDate endDate = findDateFromDay(selectedDate, 7).plusDays(1L);
		return orderRepository.findAllForWeekBetween(startDate, endDate).stream()
				.map(orderMapper::orderToOrderDTO).
				collect(Collectors.toList());
	}

	private LocalDate findDateFromDay(final LocalDate date, final int day) {
		final TemporalField temporalField = WeekFields.of(Locale.FRANCE).dayOfWeek();
		return date.with(temporalField, day);
	}


	/**
	 * Get the "id" order.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<OrderDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Order : {}", id);
		return orderRepository.findById(id)
				.map(orderMapper::orderToOrderDTO);
	}

	/**
	 * Delete the "id" order.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Order : {}", id);
		orderRepository.deleteById(id);
	}

	/**
	 * Find orders by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<OrderDTO> findOrdersByPatientId(final Long patientId) {
		LOGGER.debug("Request to find Orders by patient id : {}", patientId);
		return orderRepository.findAllByPatientId(patientId).stream()
				.map(orderMapper::orderToOrderDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find all orders between startDate and endDate.
	 *
	 * @param startDate the start date
	 * @param endDate   the end date
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<OrderDTO> findAllOrdersBetween(final LocalDate date) {
		LOGGER.debug("Request to get all Orders between: {}", date);
		return orderRepository.findAllOrdersBetween(date).stream()
				.map(orderMapper::orderToOrderDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find all orders by date.
	 *
	 * @param date the date
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<OrderDTO> findAllOrdersByDate(final LocalDate date) {
		LOGGER.debug("Request to get all Orders for date: {}", date);
		return orderRepository.findAllOrdersForDate(date).stream()
				.map(orderMapper::orderToOrderDTO)
				.collect(Collectors.toList());
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
		final float[] colWidths = { 2.5f, 2.5f, 1.5f, 1.5f };
		final Table table = new Table(UnitValue.createPercentArray(colWidths), true);
		document.add(table);

		// Get orders
		 final List<Order> orders = orderRepository.findAllOrdersForDate(selectedDate);

		final DataCellBroker dataCellBroker = new DataCellBroker();

		forEach(orders, (order, index, size) ->
		this.buildCoupon(table, new Coupon(momentName, selectedDate, dataCellBroker, order, index, size)));

		document.add(table);

		// Flushes the rest of the content and indicates that no more content will be
		// added to the table
		table.complete();
	}

	private void buildCoupon(final Table table, final Coupon coupon) throws DailyFollowUpException {
		final Order order = coupon.getOrder();

		final String roomNumber = findRoomNumber(order.getPatient());
		table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellLeft(
				order.getPatient().getLastName(), roomNumber, menuProperties.getImagesPath())));

		final List<String> dietsName = findDietsName(order.getPatient());
		final String textureName = findTextureName(order.getPatient());
		final List<String> contentNames = findContentNames(order, coupon.getMomentName());

		table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellMiddleLeft(contentNames)));

		final String comment = findComment(order.getPatient());
		table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellMiddleRight(comment)));
		table.addCell(coupon.getDataCellBroker().createDataCell(new DataCellRight(String.join(", ", dietsName), textureName,
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

	private String findComment(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getComment)
				.map(Comment::getContent)
				.orElseGet(() -> StringUtils.EMPTY);
	}

	private String findRoomNumber(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getRoom)
				.map(Room::getNumber)
				.orElseGet(() -> StringUtils.EMPTY);
	}

	private List<String> findContentNames(final Order order, final String momentDay) {
		if (!removeSpecialChar(momentDay != null ? momentDay : StringUtils.EMPTY)
				.equalsIgnoreCase(removeSpecialChar(order.getMoment() != null ? order.getMoment() : StringUtils.EMPTY))) {
			return Collections.emptyList();
		}

		return Arrays.asList(
				toName(order.getEntry()),
				toName(order.getDish()),
				toName(order.getGarnish()),
				toName(order.getDairyProduct()),
				toName(order.getDessert()));
	}

	private String toName(final Content content) {
		return Optional.ofNullable(content)
				.map(Content::getName)
				.orElse(StringUtils.EMPTY);
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
	 * Checks if is created.
	 *
	 * @param orderDTO the order DTO
	 * @return true, if is created
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean isCreated(final OrderDTO orderDTO) {
		final List<Order> orders = orderRepository.findAllByPatientIdAndMoment(orderDTO.getPatientId(),
				orderDTO.getDeliveryDate());

		return orders.stream().anyMatch(o -> o.getMoment().equalsIgnoreCase(orderDTO.getMoment()));
	}
}
