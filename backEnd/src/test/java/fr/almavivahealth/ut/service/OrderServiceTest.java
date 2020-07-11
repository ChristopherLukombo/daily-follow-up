package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.almavivahealth.dao.OrderRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.OrderDTO;
import fr.almavivahealth.service.impl.order.OrderServiceImpl;
import fr.almavivahealth.service.mapper.OrderMapper;
import fr.almavivahealth.service.propeties.OrderProperties;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	private static final String EMAIL = "ben.zotito@gmail.com";

	private static final String LASTNAME = "Zotito";

	private static final String TEXTURE_NAME = "Sel";

	@Mock
	private OrderProperties orderProperties;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderMapper orderMapper;

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;

	private static Order createOrder() {
		return Order.builder()
				.id(ID)
				.patient(getPatient())
				.garnish(createContent())
				.build();
	}

	private static OrderDTO createOrderDTO() {
		return OrderDTO.builder()
				.id(ID)
				.deliveryDate(LocalDate.of(2020, Month.JANUARY, 1))
				.patientId(null)
				.build();
	}

	private static Patient getPatient() {
		return Patient.builder()
				.id(ID)
				.firstName("Ben")
				.lastName(LASTNAME)
				.email(EMAIL)
				.state(true)
				.texture(getTexture())
				.diets(Arrays.asList(createDiet()))
				.build();
	}

	private static Texture getTexture() {
		return Texture.builder()
				.id(ID)
				.name(TEXTURE_NAME)
				.build();
	}

	private static Content createContent() {
		return Content.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	private static Diet createDiet() {
		return Diet.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldSaveOrderWhenIsOk() {
		// Given
		final Order order = createOrder();
		final OrderDTO orderDTO = createOrderDTO();

		// When
		when(orderRepository.save((Order) any())).thenReturn(order);
		when(orderMapper.orderToOrderDTO((Order) any())).thenReturn(orderDTO);

		// Then
		assertThat(orderServiceImpl.save(orderDTO)).isEqualTo(orderDTO);
	}

	@Test
	public void shouldSaveOrderWhenIsKo() {
		// Given
		final Order order = null;
		final OrderDTO orderDTO = null;

		// When
		when(orderRepository.save((Order) any())).thenReturn(order);
		when(orderMapper.orderToOrderDTO((Order) any())).thenReturn(orderDTO);

		// Then
		assertThat(orderServiceImpl.save(orderDTO)).isNull();
	}

	@Test
	public void shouldUpdateOrderWhenIsOk() {
		// Given
		final Order order = createOrder();
		final OrderDTO orderDTO = createOrderDTO();

		// When
		when(orderRepository.saveAndFlush((Order) any())).thenReturn(order);
		when(orderMapper.orderToOrderDTO((Order) any())).thenReturn(orderDTO);

		// Then
		assertThat(orderServiceImpl.update(orderDTO)).isEqualTo(orderDTO);
	}

	@Test
	public void shouldUpdateOrderWhenIsKo() {
		// Given
		final Order order = null;
		final OrderDTO orderDTO = null;

		// When
		when(orderRepository.saveAndFlush((Order) any())).thenReturn(order);
		when(orderMapper.orderToOrderDTO((Order) any())).thenReturn(orderDTO);

		// Then
		assertThat(orderServiceImpl.update(orderDTO)).isNull();
	}

	@Test
	public void shouldGetAllOrdersWhenIsOk() {
		// Given
		final List<Order> orders = Arrays.asList(createOrder());
		final LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);

		// Then
		when(orderRepository.findAllForWeekBetween((LocalDate) any(), (LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findAllForWeek(date)).isNotEmpty();
	}

	@Test
	public void shouldGetAllOrdersWhenIsEmpty() {
		// Given
		final List<Order> orders = Collections.emptyList();

		// Then
		when(orderRepository.findAllForWeekBetween((LocalDate) any(), (LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findAllForWeek(LocalDate.now())).isEmpty();
	}

	@Test
	public void shouldGetAllOrdersWhenIsNull() {
		// Given
		final List<Order> orders = null;

		// Then
		when(orderRepository.findAllForWeekBetween((LocalDate) any(), (LocalDate) any())).thenReturn(orders);

		// Then
		assertThatThrownBy(() -> orderServiceImpl.findAllForWeek(LocalDate.now()))
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetOrderWhenIsOk() {
		// Given
		final Order order = createOrder();
		final OrderDTO orderDTO = createOrderDTO();

		// When
		when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
		when(orderMapper.orderToOrderDTO((Order) any())).thenReturn(orderDTO);

		// Then
		assertThat(orderServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(orderDTO));
	}

	@Test
	public void shouldGetOrderWhenIsNull() {
		// Given
		final Order order = null;
		final OrderDTO orderDTO = null;

		// When
		when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));

		// Then
		assertThat(orderServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(orderDTO));
	}

	@Test
	public void shouldDeleteOrderWhenIsOk() {
		// When
		doNothing().when(orderRepository).deleteById(ID);

		// Then
		orderServiceImpl.delete(ID);

		verify(orderRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void shouldFindOrdersByPatientId() {
		// Given
		final List<Order> orders = Arrays.asList(createOrder());

		// When
		when(orderRepository.findAllByPatientId(anyLong())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findOrdersByPatientId(ID)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyListWhenTryingToFindOrdersByPatientId() {
		// Given
		final List<Order> orders = Collections.emptyList();

		// When
		when(orderRepository.findAllByPatientId(anyLong())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findOrdersByPatientId(ID)).isEmpty();
	}

	@Test
	public void shouldThrowWhenOrderRepositoryIsNullInTryingToFindOrdersByPatientId() {
		// Given
		final List<Order> orders = null;

		// When
		when(orderRepository.findAllByPatientId(anyLong())).thenReturn(orders);

		// Then
		assertThatThrownBy(() -> orderServiceImpl.findOrdersByPatientId(ID))
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetAllOrdersBetweenDate() {
		// Given
		final List<Order> orders = Arrays.asList(createOrder());
		final LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);

		// Then
		when(orderRepository.findAllOrdersBetween((LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findAllOrdersBetween(date)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyListWhenTryingToGetAllOrdersBetweenDate() {
		// Given
		final List<Order> orders = Collections.emptyList();
		final LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);

		// Then
		when(orderRepository.findAllOrdersBetween((LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findAllOrdersBetween(date)).isEmpty();
	}

	@Test
	public void shouldGetAllOrdersForDate() {
		// Given
		final List<Order> orders = Arrays.asList(createOrder());
		final LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);

		// Then
		when(orderRepository.findAllOrdersForDate((LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findAllOrdersByDate(date)).isNotEmpty();
	}

	@Test
	public void shoulReturnEmptyListAllOrdersForDate() {
		// Given
		final List<Order> orders = Collections.emptyList();
		final LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);

		// Then
		when(orderRepository.findAllOrdersForDate((LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.findAllOrdersByDate(date)).isEmpty();
	}

	@Test
	public void shoulReturnNullListAllOrdersForDate() {
		// Given
		final List<Order> orders = null;
		final LocalDate date = LocalDate.of(2020, Month.JANUARY, 1);

		// Then
		when(orderRepository.findAllOrdersForDate((LocalDate) any())).thenReturn(orders);

		// Then
		assertThatThrownBy(() -> orderServiceImpl.findAllOrdersByDate(date))
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGenerateCouponsWhenIsOk() throws DailyFollowUpException {
		// Given
		final List<Order> orders = Arrays.asList(createOrder());
		final Path path = Paths.get("src", "main", "resources", "images", "logo-almaviva-sante.png");

		// When
		when(orderProperties.getImagesPath()).thenReturn(path.toString());
		when(orderRepository.findAllOrdersForDate((LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.generateCoupons("DEJEUNER", LocalDate.of(2020, Month.APRIL, 11))).isNotEmpty();
	}

	@Test
	public void shouldEmptyGenerateCoupons() throws DailyFollowUpException {
		// Given
		final List<Order> orders = Collections.emptyList();

		// When
		when(orderRepository.findAllOrdersForDate((LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(orderServiceImpl.generateCoupons("DINER", LocalDate.of(2020, Month.APRIL, 12))).isNotEmpty();
	}

	@Test
	public void shouldThrowWhenTryingToGenerateCouponsIsNotFound() throws DailyFollowUpException {
		// Given
		final List<Order> orders = null;

		// When
		when(orderRepository.findAllOrdersForDate((LocalDate) any())).thenReturn(orders);

		// Then
		assertThatThrownBy(() -> orderServiceImpl.generateCoupons("DINER", LocalDate.of(2020, Month.APRIL, 12)))
		.isInstanceOf(DailyFollowUpException.class);
	}

	@Test
	public void shouldThrowWhenCouponsAreNotGenerated() throws DailyFollowUpException {
		// Given
		final List<Order> orders = Arrays.asList(createOrder());
		final String fileName = null;

		// When
		when(orderRepository.findAllOrdersForDate((LocalDate) any())).thenReturn(orders);
		when(orderProperties.getImagesPath()).thenReturn(fileName);

		// Then
		assertThatThrownBy(() -> orderServiceImpl.generateCoupons("DEJEUNER", LocalDate.of(2020, Month.APRIL, 11)))
				.isInstanceOf(DailyFollowUpException.class);
	}
}
