package fr.almavivahealth.service.impl.order;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.OrderRepository;
import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.service.OrderService;
import fr.almavivahealth.service.dto.OrderDTO;
import fr.almavivahealth.service.mapper.OrderMapper;

/**
 * Service Implementation for managing Order.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

	private final OrderRepository orderRepository;

	private final OrderMapper orderMapper;

	@Autowired
	public OrderServiceImpl(final OrderRepository orderRepository, final OrderMapper orderMapper) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
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
}
