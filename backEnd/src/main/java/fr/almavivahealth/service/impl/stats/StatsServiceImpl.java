package fr.almavivahealth.service.impl.stats;

import static fr.almavivahealth.util.LocalDateUtil.findDateFromDay;
import static fr.almavivahealth.util.LocalDateUtil.getDateRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.OrderRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.domain.enums.OrderStatus;
import fr.almavivahealth.service.StatsService;
import fr.almavivahealth.service.dto.OrdersPerDay;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.service.mapper.StatsMapper;

/**
 * Service Implementation for managing Stats.
 */
@Service
@Transactional
public class StatsServiceImpl implements StatsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatsServiceImpl.class);

	private final PatientRepository patientRepository;

	private final StatsMapper statsMapper;

	private final OrderRepository orderRepository;

	@Autowired
	public StatsServiceImpl(final PatientRepository patientRepository, final StatsMapper statsMapper,
			final OrderRepository orderRepository) {
		this.patientRepository = patientRepository;
		this.statsMapper = statsMapper;
		this.orderRepository = orderRepository;
	}

	/**
	 * Find number of patients per allergy.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientsPerAllergyDTO> findNumberOfPatientsPerAllergy() {
		LOGGER.debug("Request to get number of patients per Allergy");
		return patientRepository.findNumberOfPatientsPerAllergy().stream()
				.map(statsMapper::patientsPerAllergyToPatientsPerAllergyDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find number of patients per diet.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientsPerDietDTO> findNumberOfPatientsPerDiet() {
		LOGGER.debug("Request to get number of patients per Diet");
		return patientRepository.findNumberOfPatientsPerDiet().stream()
				.map(statsMapper::patientsPerDietToPatientsPerDietDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find number of patients by status.
	 *
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PatientsByStatusDTO> findNumberOfPatientsByStatus() {
		LOGGER.debug("Request to get number of patients by status");
		return patientRepository.findNumberOfPatientsByStatus()
				.map(statsMapper::patientsByStatusToPatientsByStatusDTO);
	}

	/**
	 * Find all for next days.
	 *
	 * @return the map
	 */
	@Override
	@Transactional(readOnly = true)
	public Map<String, List<OrdersPerDay>> findAllForNextDays() {
		final LocalDate startDate = findDateFromDay(LocalDate.now(), 2);
		final LocalDate endDate = findDateFromDay(LocalDate.now(), 7).plusDays(1L);
		final List<Order> orders = orderRepository.findAllForWeekBetween(startDate, endDate);

		final List<LocalDate> dates = getDateRange(startDate, endDate);

		final Map<String, List<OrdersPerDay>> orderPerStatus = new HashedMap<>();

		for (final OrderStatus status : OrderStatus.values()) {
			buildMapOrderPerDay(orders, dates, orderPerStatus, status);
		}

		return orderPerStatus;
	}

	private void buildMapOrderPerDay(final List<Order> orders, final List<LocalDate> dates,
			final Map<String, List<OrdersPerDay>> orderPerStatus, final OrderStatus status) {
		final Map<LocalDate, Long> orderscollect = orders
				.stream()
				.filter(o -> status.equals(o.getOrderStatus()))
				.collect(Collectors.groupingBy(Order::getDeliveryDate, Collectors.counting()));

		for (final LocalDate date : dates) {
			final Long count = Optional.ofNullable(orderscollect.get(date)).orElse(0L);
			orderscollect.put(date, count);
		}

		final List<Entry<LocalDate, Long>> listOfEntries = new ArrayList<>(orderscollect.entrySet());

		Collections.sort(listOfEntries, (e1, e2) -> {
			final LocalDate v1 = e1.getKey();
			final LocalDate v2 = e2.getKey();
			return v1.compareTo(v2);
		});

		final List<OrdersPerDay> ordersPerDaysBuilt = new ArrayList<>(listOfEntries.size());

		// copying entries from List Entry to List of OrderPerDay
		for(final Entry<LocalDate, Long> entry : listOfEntries){
			final OrdersPerDay ordersPerDay = new OrdersPerDay(entry.getKey(), entry.getValue());
			ordersPerDaysBuilt.add(ordersPerDay);
		}

		orderPerStatus.put(status.toString(), ordersPerDaysBuilt);
	}
}
