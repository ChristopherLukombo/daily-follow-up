package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.almavivahealth.service.OrderService;
import fr.almavivahealth.service.dto.OrderDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.OrderResource;

@RunWith(MockitoJUnitRunner.class)
public class OrderResourceTest {

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderResource orderResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(orderResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static OrderDTO createOrderDTO() {
		return OrderDTO.builder()
				.id(ID)
				.deliveryDate(LocalDate.now())
				.patientId(null)
				.build();
	}

	@Test
	public void shouldCreateOrderWhenIsOk() throws IOException, Exception {
		// Given
		final OrderDTO orderDTO = createOrderDTO();
		orderDTO.setId(null);

		// When
		when(orderService.save((OrderDTO) any())).thenReturn(orderDTO);

		// Then
		mockMvc.perform(post("/api/orders")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(orderDTO)))
		.andExpect(status().isCreated());
		verify(orderService, times(1)).save(orderDTO);
	}

	@Test
	public void shouldCreateOrderWhenIsKo() throws IOException, Exception {
		// Given
		final OrderDTO orderDTO = createOrderDTO();

		// Then
		mockMvc.perform(post("/api/orders")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(orderDTO)))
		.andExpect(status().isBadRequest());
		verify(orderService, times(0)).save(orderDTO);
	}

	@Test
	public void shouldUpdateOrderWhenIsOk() throws IOException, Exception {
		// Given
		final OrderDTO orderDTO = createOrderDTO();

		// When
		when(orderService.update((OrderDTO) any())).thenReturn(orderDTO);

		// Then
		mockMvc.perform(put("/api/orders")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(orderDTO)))
		.andExpect(status().isOk());
		verify(orderService, times(1)).update(orderDTO);
	}

	@Test
	public void shouldUpdateOrderWhenIsKo() throws IOException, Exception {
		// Given
		final OrderDTO orderDTO = createOrderDTO();
		orderDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/orders")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(orderDTO)))
		.andExpect(status().isBadRequest());
		verify(orderService, times(0)).update(orderDTO);
	}

	@Test
	public void shouldGetAllOrdersWhenIsOk() throws IOException, Exception {
		// Given
		final List<OrderDTO> ordersDTO = Arrays.asList(createOrderDTO());

		// When
		when(orderService.findAllForWeek((LocalDate) any())).thenReturn(ordersDTO);

		// Then
		mockMvc.perform(get("/api/orders?selectedDate=2016-12-12")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(orderService, times(1)).findAllForWeek((LocalDate) any());
	}

	@Test
	public void shouldGetAllOrdersWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<OrderDTO> ordersDTO =  Collections.emptyList();

		// When
		when(orderService.findAllForWeek((LocalDate) any())).thenReturn(ordersDTO);

		// Then
		mockMvc.perform(get("/api/orders?selectedDate=2016-12-12")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(orderService, times(1)).findAllForWeek((LocalDate) any());
	}

	@Test
	public void shouldGetOrderWhenIsOk() throws IOException, Exception {
		// Given
		final OrderDTO orderDTO = createOrderDTO();

		// When
		when(orderService.findOne(anyLong())).thenReturn(Optional.ofNullable(orderDTO));

		// Then
		mockMvc.perform(get("/api/orders/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(orderDTO)))
		.andExpect(status().isOk());
		verify(orderService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetOrderWhenIsNotFOund() throws IOException, Exception {
		// Given
		final OrderDTO orderDTO = null;

		// When
		when(orderService.findOne(anyLong())).thenReturn(Optional.ofNullable(orderDTO));

		// Then
		mockMvc.perform(get("/api/orders/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(orderDTO)))
		.andExpect(status().isNoContent());
		verify(orderService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteOrderWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(orderService).delete(id);

		// Then
		mockMvc.perform(delete("/api/orders/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(orderService, times(1)).delete(id);
	}

	@Test
	public void shouldGetAllOrdersByPatientId() throws IOException, Exception {
		// Given
		final List<OrderDTO> ordersDTO = Arrays.asList(createOrderDTO());

		// When
		when(orderService.findOrdersByPatientId(anyLong())).thenReturn(ordersDTO);

		// Then
		mockMvc.perform(get("/api/orders/patient/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(orderService, times(1)).findOrdersByPatientId(anyLong());
	}

	@Test
	public void shouldReturn204WhenTryingToGetAllOrdersByPatientId() throws IOException, Exception {
		// Given
		final List<OrderDTO> ordersDTO = Collections.emptyList();

		// When
		when(orderService.findOrdersByPatientId(anyLong())).thenReturn(ordersDTO);

		// Then
		mockMvc.perform(get("/api/orders/patient/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(orderService, times(1)).findOrdersByPatientId(anyLong());
	}
}
