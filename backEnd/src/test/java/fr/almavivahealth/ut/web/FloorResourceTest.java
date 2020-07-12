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

import fr.almavivahealth.service.FloorService;
import fr.almavivahealth.service.dto.FloorDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.FloorResource;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class FloorResourceTest {

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private FloorService floorService;

	@InjectMocks
	private FloorResource floorResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(floorResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static FloorDTO createFloorDTO() {
		return FloorDTO.builder()
				.id(ID)
				.build();
	}

	@Test
	public void shouldCreateFloorWhenIsOk() throws IOException, Exception {
		// Given
		final FloorDTO floorDTO = createFloorDTO();
		floorDTO.setId(null);

		// When
		when(floorService.save((FloorDTO) any())).thenReturn(floorDTO);

		// Then
		mockMvc.perform(post("/api/floors")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(floorDTO)))
		.andExpect(status().isCreated());
		verify(floorService, times(1)).save(floorDTO);
	}

	@Test
	public void shouldCreateFloorWhenIsKo() throws IOException, Exception {
		// Given
		final FloorDTO floorDTO = createFloorDTO();

		// Then
		mockMvc.perform(post("/api/floors")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(floorDTO)))
		.andExpect(status().isBadRequest());
		verify(floorService, times(0)).save(floorDTO);
	}

	@Test
	public void shouldUpdateFloorWhenIsOk() throws IOException, Exception {
		// Given
		final FloorDTO floorDTO = createFloorDTO();

		// When
		when(floorService.update((FloorDTO) any())).thenReturn(floorDTO);

		// Then
		mockMvc.perform(put("/api/floors")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(floorDTO)))
		.andExpect(status().isOk());
		verify(floorService, times(1)).update(floorDTO);
	}

	@Test
	public void shouldUpdateFloorWhenIsKo() throws IOException, Exception {
		// Given
		final FloorDTO floorDTO = createFloorDTO();
		floorDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/floors")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(floorDTO)))
		.andExpect(status().isBadRequest());
		verify(floorService, times(0)).update(floorDTO);
	}

	@Test
	public void shouldGetAllFloorsWhenIsOk() throws IOException, Exception {
		// Given
		final List<FloorDTO> floorsDTO = Arrays.asList(createFloorDTO());

		// When
		when(floorService.findAll()).thenReturn(floorsDTO);

		// Then
		mockMvc.perform(get("/api/floors")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(floorService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllFloorssWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<FloorDTO> floorsDTO =  Collections.emptyList();

		// When
		when(floorService.findAll()).thenReturn(floorsDTO);

		// Then
		mockMvc.perform(get("/api/floors")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(floorService, times(1)).findAll();
	}

	@Test
	public void shouldGetFloorWhenIsOk() throws IOException, Exception {
		// Given
		final FloorDTO floorDTO = createFloorDTO();

		// When
		when(floorService.findOne(anyLong())).thenReturn(Optional.ofNullable(floorDTO));

		// Then
		mockMvc.perform(get("/api/floors/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(floorDTO)))
		.andExpect(status().isOk());
		verify(floorService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetFloorWhenIsNotFOund() throws IOException, Exception {
		// Given
		final FloorDTO floorDTO = null;

		// When
		when(floorService.findOne(anyLong())).thenReturn(Optional.ofNullable(floorDTO));

		// Then
		mockMvc.perform(get("/api/floors/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(floorDTO)))
		.andExpect(status().isNoContent());
		verify(floorService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteFloorWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(floorService).delete(id);

		// Then
		mockMvc.perform(delete("/api/floors/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(floorService, times(1)).delete(id);
	}
}
