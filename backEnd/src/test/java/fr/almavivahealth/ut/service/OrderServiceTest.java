package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import fr.almavivahealth.domain.Order;
import fr.almavivahealth.service.dto.OrderDTO;
import fr.almavivahealth.service.impl.OrderServiceImpl;
import fr.almavivahealth.service.mapper.OrderMapper;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
	
	private static final long ID = 1L;

	@Mock
	private OrderRepository orderRepository;
	
	@Mock
	private OrderMapper orderMapper;

	@InjectMocks
	private OrderServiceImpl orderServiceImpl;
	
	private static Order createOrder() {
		return Order.builder()
				.id(ID)
				.contents(null)
				.momentDays(null)
				.patient(null)
				.build();
	}
	
	private static OrderDTO createOrderDTO() {
		return OrderDTO.builder()
				.id(ID)
				.date(LocalDate.now())
				.patientId(null)
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
		
		// Then
		when(orderRepository.findAll()).thenReturn(orders);
		
		// Then
		assertThat(orderServiceImpl.findAll()).isNotEmpty();
	}
	
	@Test
	public void shouldGetAllOrdersWhenIsEmpty() {
		// Given
		final List<Order> orders = Collections.emptyList();
		
		// Then
		when(orderRepository.findAll()).thenReturn(orders);
		
		// Then
		assertThat(orderServiceImpl.findAll()).isEmpty();
	}
	
	@Test
	public void shouldGetAllOrdersWhenIsNull() {
		// Given
		final List<Order> orders = null;
		
		// Then
		when(orderRepository.findAll()).thenReturn(orders);
		
		// Then
		assertThatThrownBy(() -> orderServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetOrderWhenIsOk() {
		// Given
		final Order order = createOrder();
		final OrderDTO orderDTO = createOrderDTO();

		// When
		when(orderRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(order));
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
		when(orderRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(order));
		
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
	
	
}
