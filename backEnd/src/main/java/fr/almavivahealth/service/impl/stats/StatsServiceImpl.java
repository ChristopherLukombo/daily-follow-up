package fr.almavivahealth.service.impl.stats;

import static fr.almavivahealth.util.LocalDateUtil.findDateFromDay;
import static fr.almavivahealth.util.LocalDateUtil.getDateRange;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.MenuRepository;
import fr.almavivahealth.dao.OrderRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.entity.Day;
import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.domain.entity.Week;
import fr.almavivahealth.domain.enums.OrderStatus;
import fr.almavivahealth.service.StatsService;
import fr.almavivahealth.service.dto.OrdersPerDay;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.service.dto.TopTrendyMenuDTO;
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

	private final MenuRepository menuRepository;

    @Autowired
	public StatsServiceImpl(
			final PatientRepository patientRepository,
			final StatsMapper statsMapper,
			final OrderRepository orderRepository,
			final MenuRepository menuRepository) {
		this.patientRepository = patientRepository;
		this.statsMapper = statsMapper;
		this.orderRepository = orderRepository;
		this.menuRepository = menuRepository;
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
		LOGGER.debug("Request to get number of order per day");
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

	/**
	 * Find trendy diets.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TopTrendyMenuDTO> findTrendyDiets() {
		LOGGER.debug("Request to get trendy diets");
		return menuRepository.findTrendyDishes().stream()
				.map(statsMapper::topTrendyMenuToTopTrendyMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find all trendy contents.
	 *
	 * @return the linked hash map
	 */
	@Override
	@Transactional(readOnly = true)
	public LinkedHashMap<String, Long> findAllTrendyContents() {
		LOGGER.debug("Request to get trendy contents");
		final List<Menu> menus = menuRepository.findAll();
		final List<MomentDay> momentDays = findMomentDays(menus);

		final LinkedHashMap<String, Long> contentsPerMomentsDay = new LinkedHashMap<>();

		for (final MomentDay momentDay : momentDays) {
			final String entry = mapToName(momentDay.getEntry());
			final String dish = mapToName(momentDay.getDish());
			final String garnish = mapToName(momentDay.getGarnish());
			final String dairyProduct = mapToName(momentDay.getDairyProduct());
			final String dessert = mapToName(momentDay.getDessert());

			updateMap(contentsPerMomentsDay, entry);
			updateMap(contentsPerMomentsDay, dish);
			updateMap(contentsPerMomentsDay, garnish);
			updateMap(contentsPerMomentsDay, dairyProduct);
			updateMap(contentsPerMomentsDay, dessert);
		}

		final List<Entry<String, Long>> arrayList = new ArrayList<>(contentsPerMomentsDay.entrySet());

		Collections.sort(arrayList, (e1, e2) -> {
			final Long v1 = e1.getValue();
			final Long v2 = e2.getValue();
			return v1.compareTo(v2);
		});

		final LinkedHashMap<String, Long> contentsPerMomentsDaySort = new LinkedHashMap<>(contentsPerMomentsDay.size());

		for (int i = arrayList.size() - 1, cp = 0; i > 0 && cp != 5; i--, cp++) {
			contentsPerMomentsDaySort.put(arrayList.get(i).getKey(), arrayList.get(i).getValue());
		}

		return contentsPerMomentsDaySort;
	}

	private List<MomentDay> findMomentDays(final List<Menu> menus) {
		return menus.stream()
				.map(Menu::getWeeks)
				.flatMap(Collection::stream)
				.map(Week::getDays)
				.flatMap(Collection::stream)
				.map(Day::getMomentDays)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private void updateMap(final LinkedHashMap<String, Long> contentsPerMomentsDay, final String contentName) {
		if (!contentName.isEmpty() && contentsPerMomentsDay.containsKey(contentName)) {
			final Long count = contentsPerMomentsDay.get(contentName);
			contentsPerMomentsDay.put(contentName, count + 1);
		} else if (!contentName.isEmpty() && !contentsPerMomentsDay.containsKey(contentName)){
			contentsPerMomentsDay.put(contentName, 1L);
		}
	}

	private String mapToName(final Content content) {
		return Optional.ofNullable(content)
				.map(Content::getName)
				.orElse(StringUtils.EMPTY);
	}
}
