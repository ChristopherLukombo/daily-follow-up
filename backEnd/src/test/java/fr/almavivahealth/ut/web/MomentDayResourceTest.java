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

import fr.almavivahealth.service.MomentDayService;
import fr.almavivahealth.service.dto.MomentDayDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.MomentDayResource;

@RunWith(MockitoJUnitRunner.class)
public class MomentDayResourceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private MomentDayService momentDayService;

	@InjectMocks
	private MomentDayResource momentDayResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(momentDayResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static MomentDayDTO createMomentDayDTO() {
		return MomentDayDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldCreateMomentDayWhenIsOk() throws IOException, Exception {
		// Given
		final MomentDayDTO momentDayDTO = createMomentDayDTO();
		momentDayDTO.setId(null);

		// When
		when(momentDayService.save((MomentDayDTO) any())).thenReturn(momentDayDTO);

		// Then
		mockMvc.perform(post("/api/momentDays")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(momentDayDTO)))
		.andExpect(status().isCreated());
		verify(momentDayService, times(1)).save(momentDayDTO);
	}

	@Test
	public void shouldCreateMomentDayWhenIsKo() throws IOException, Exception {
		// Given
		final MomentDayDTO momentDayDTO = createMomentDayDTO();

		// Then
		mockMvc.perform(post("/api/momentDays")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(momentDayDTO)))
		.andExpect(status().isBadRequest());
		verify(momentDayService, times(0)).save(momentDayDTO);
	}

	@Test
	public void shouldUpdateMomentDayWhenIsOk() throws IOException, Exception {
		// Given
		final MomentDayDTO momentDayDTO = createMomentDayDTO();

		// When
		when(momentDayService.update((MomentDayDTO) any())).thenReturn(momentDayDTO);

		// Then
		mockMvc.perform(put("/api/momentDays")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(momentDayDTO)))
		.andExpect(status().isOk());
		verify(momentDayService, times(1)).update(momentDayDTO);
	}

	@Test
	public void shouldUpdateMomentDayWhenIsKo() throws IOException, Exception {
		// Given
		final MomentDayDTO momentDayDTO = createMomentDayDTO();
		momentDayDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/momentDays")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(momentDayDTO)))
		.andExpect(status().isBadRequest());
		verify(momentDayService, times(0)).update(momentDayDTO);
	}

	@Test
	public void shouldGetAllMomentDaysWhenIsOk() throws IOException, Exception {
		// Given
		final List<MomentDayDTO> menusDTO = Arrays.asList(createMomentDayDTO());

		// When
		when(momentDayService.findAll()).thenReturn(menusDTO);

		// Then
		mockMvc.perform(get("/api/momentDays")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(momentDayService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllMomentDaysWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<MomentDayDTO> menusDTO =  Collections.emptyList();

		// When
		when(momentDayService.findAll()).thenReturn(menusDTO);

		// Then
		mockMvc.perform(get("/api/momentDays")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(momentDayService, times(1)).findAll();
	}

	@Test
	public void shouldGetMomentDayWhenIsOk() throws IOException, Exception {
		// Given
		final MomentDayDTO caregiverDTO = createMomentDayDTO();

		// When
		when(momentDayService.findOne(anyLong())).thenReturn(Optional.ofNullable(caregiverDTO));

		// Then
		mockMvc.perform(get("/api/momentDays/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isOk());
		verify(momentDayService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetMomentDayWhenIsNotFOund() throws IOException, Exception {
		// Given
		final MomentDayDTO caregiverDTO = null;

		// When
		when(momentDayService.findOne(anyLong())).thenReturn(Optional.ofNullable(caregiverDTO));

		// Then
		mockMvc.perform(get("/api/momentDays/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isNoContent());
		verify(momentDayService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteMomentDayWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(momentDayService).delete(id);

		// Then
		mockMvc.perform(delete("/api/momentDays/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(momentDayService, times(1)).delete(id);
	}
}
