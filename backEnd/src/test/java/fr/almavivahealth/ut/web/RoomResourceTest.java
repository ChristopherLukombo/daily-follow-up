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

import fr.almavivahealth.service.RoomService;
import fr.almavivahealth.service.dto.RoomDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.RoomResource;

@RunWith(MockitoJUnitRunner.class)
public class RoomResourceTest {

	private static final long ID = 1L;

	private static final boolean STATE = true;

	private static final String NUMBER = "1-1234";

	private MockMvc mockMvc;

	@Mock
	private RoomService roomService;

	@InjectMocks
	private RoomResource roomResource;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(roomResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static RoomDTO createRoomDTO() {
		return RoomDTO.builder()
				.id(ID)
				.number(NUMBER)
				.state(STATE)
				.build();
	}

	@Test
	public void shouldCreateRoomWhenIsOk() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = createRoomDTO();
		roomDTO.setId(null);

		// When
		when(roomService.save((RoomDTO) any())).thenReturn(roomDTO);

		// Then
		mockMvc.perform(post("/api/rooms")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isCreated());
		verify(roomService, times(1)).save(roomDTO);
	}

	@Test
	public void shouldCreateRoomWhenIsKo() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = createRoomDTO();

		// Then
		mockMvc.perform(post("/api/rooms")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isBadRequest());
		verify(roomService, times(0)).save(roomDTO);
	}

	@Test
	public void shouldUpdateRoomWhenIsOk() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = createRoomDTO();

		// When
		when(roomService.update((RoomDTO) any())).thenReturn(roomDTO);

		// Then
		mockMvc.perform(put("/api/rooms")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isOk());
		verify(roomService, times(1)).update(roomDTO);
	}

	@Test
	public void shouldUpdateRoomWhenIsKo() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = createRoomDTO();
		roomDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/rooms")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isBadRequest());
		verify(roomService, times(0)).update(roomDTO);
	}

	@Test
	public void shouldGetAllRoomsWhenIsOk() throws IOException, Exception {
		// Given
		final List<RoomDTO> roomsDTO = Arrays.asList(createRoomDTO());

		// When
		when(roomService.findAll()).thenReturn(roomsDTO);

		// Then
		mockMvc.perform(get("/api/rooms")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(roomService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllRoomsWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<RoomDTO> roomsDTO =  Collections.emptyList();

		// When
		when(roomService.findAll()).thenReturn(roomsDTO);

		// Then
		mockMvc.perform(get("/api/rooms")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(roomService, times(1)).findAll();
	}

	@Test
	public void shouldGetRoomWhenIsOk() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = createRoomDTO();

		// When
		when(roomService.findOne(anyLong())).thenReturn(Optional.ofNullable(roomDTO));

		// Then
		mockMvc.perform(get("/api/rooms/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isOk());
		verify(roomService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetRoomWhenIsNotFOund() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = null;

		// When
		when(roomService.findOne(anyLong())).thenReturn(Optional.ofNullable(roomDTO));

		// Then
		mockMvc.perform(get("/api/rooms/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isNoContent());
		verify(roomService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteRoomWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(roomService).delete(id);

		// Then
		mockMvc.perform(delete("/api/rooms/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(roomService, times(1)).delete(id);
	}
	
	@Test
	public void shouldGetRoomByPatientIdWhenIsOk() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = createRoomDTO();

		// When
		when(roomService.findByPatientId(anyLong())).thenReturn(Optional.ofNullable(roomDTO));

		// Then
		mockMvc.perform(get("/api/rooms/patient/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isOk());
		verify(roomService, times(1)).findByPatientId(1L);
	}
	
	@Test
	public void shouldGetRoomByPatientIdWhenIsNotFound() throws IOException, Exception {
		// Given
		final RoomDTO roomDTO = null;

		// When
		when(roomService.findByPatientId(anyLong())).thenReturn(Optional.ofNullable(roomDTO));

		// Then
		mockMvc.perform(get("/api/rooms/patient/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(roomDTO)))
		.andExpect(status().isNoContent());
		verify(roomService, times(1)).findByPatientId(2L);
	}
	
}
