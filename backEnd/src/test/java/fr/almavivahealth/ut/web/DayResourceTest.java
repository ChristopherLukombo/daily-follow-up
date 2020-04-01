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

import fr.almavivahealth.service.DayService;
import fr.almavivahealth.service.dto.DayDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.DayResource;

@RunWith(MockitoJUnitRunner.class)
public class DayResourceTest {
	
	private static final String NAME = "TEST";

	private static final long ID = 1L;
	
	private MockMvc mockMvc;

	@Mock
	private DayService dayService;

	@InjectMocks
	private DayResource dayResource;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(dayResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	private static DayDTO createDayDTO() {
		return DayDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
    public void shouldCreateDayWhenIsOk() throws IOException, Exception {
    	// Given
    	final DayDTO dayDTO = createDayDTO();
    	dayDTO.setId(null);
    	
    	// When
		when(dayService.save((DayDTO) any())).thenReturn(dayDTO);
    	
    	// Then
    	mockMvc.perform(post("/api/days")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dayDTO)))
		        .andExpect(status().isCreated());
		verify(dayService, times(1)).save(dayDTO);
    }
	
	@Test
    public void shouldCreateDayWhenIsKo() throws IOException, Exception {
    	// Given
    	final DayDTO dayDTO = createDayDTO();
    	
    	// Then
    	mockMvc.perform(post("/api/days")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dayDTO)))
		        .andExpect(status().isBadRequest());
		verify(dayService, times(0)).save(dayDTO);
    }
	
	@Test
    public void shouldUpdateDayWhenIsOk() throws IOException, Exception {
    	// Given
    	final DayDTO dayDTO = createDayDTO();
    	
    	// When
		when(dayService.update((DayDTO) any())).thenReturn(dayDTO);
    	
    	// Then
    	mockMvc.perform(put("/api/days")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dayDTO)))
		        .andExpect(status().isOk());
		verify(dayService, times(1)).update(dayDTO);
    }
	
	@Test
    public void shouldUpdateDayWhenIsKo() throws IOException, Exception {
    	// Given
    	final DayDTO dayDTO = createDayDTO();
    	dayDTO.setId(null);
    	
    	// Then
    	mockMvc.perform(put("/api/days")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dayDTO)))
		        .andExpect(status().isBadRequest());
		verify(dayService, times(0)).update(dayDTO);
    }
	
	@Test
	public void shouldGetAllDaysWhenIsOk() throws IOException, Exception {
		// Given
		final List<DayDTO> daysDTO = Arrays.asList(createDayDTO());

		// When
		when(dayService.findAll()).thenReturn(daysDTO);

		// Then
		mockMvc.perform(get("/api/days")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(dayService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllDaysWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<DayDTO> daysDTO = Collections.emptyList();

		// When
		when(dayService.findAll()).thenReturn(daysDTO);

		// Then
		mockMvc.perform(get("/api/days")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());
		verify(dayService, times(1)).findAll();
	}
	
	@Test
	public void shouldGetDayWhenIsOk() throws IOException, Exception {
		// Given
		final DayDTO dayDTO = createDayDTO();

		// When
		when(dayService.findOne(anyLong())).thenReturn(Optional.ofNullable(dayDTO));

		// Then
		mockMvc.perform(get("/api/days/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dayDTO)))
		        .andExpect(status().isOk());
		verify(dayService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetDayWhenIsNotFOund() throws IOException, Exception {
		// Given
		final DayDTO dayDTO = null;

		// When
		when(dayService.findOne(anyLong())).thenReturn(Optional.ofNullable(dayDTO));

		// Then
		mockMvc.perform(get("/api/days/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dayDTO)))
		        .andExpect(status().isNoContent());
		verify(dayService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteDayWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(dayService).delete(id);

		// Then
		mockMvc.perform(delete("/api/days/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());
		verify(dayService, times(1)).delete(id);
	}
}
