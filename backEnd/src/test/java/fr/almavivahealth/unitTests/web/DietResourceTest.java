package fr.almavivahealth.unitTests.web;

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

import fr.almavivahealth.service.DietService;
import fr.almavivahealth.service.dto.DietDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.DietResource;

@RunWith(MockitoJUnitRunner.class)
public class DietResourceTest {
	
	private static final String NAME = "TEST";

	private static final long ID = 1L;
	
    private MockMvc mockMvc;
	
	@Mock
	private DietService dietService;
	
	@InjectMocks
	private DietResource dietResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(dietResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	private static DietDTO createDietDTO() {
		return DietDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
    public void shouldCreateDietWhenIsOk() throws IOException, Exception {
    	// Given
    	final DietDTO dietDTO = createDietDTO();
    	dietDTO.setId(null);
    	
    	// When
		when(dietService.save((DietDTO) any())).thenReturn(dietDTO);
    	
    	// Then
    	mockMvc.perform(post("/api/diets")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dietDTO)))
		        .andExpect(status().isCreated());
		verify(dietService, times(1)).save(dietDTO);
    }
	
	@Test
    public void shouldCreateDietWhenIsKo() throws IOException, Exception {
    	// Given
    	final DietDTO dietDTO = createDietDTO();
    	
    	// Then
    	mockMvc.perform(post("/api/diets")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dietDTO)))
		        .andExpect(status().isBadRequest());
		verify(dietService, times(0)).save(dietDTO);
    }
	
	@Test
    public void shouldUpdateDietWhenIsOk() throws IOException, Exception {
    	// Given
    	final DietDTO dietDTO = createDietDTO();
    	
    	// When
		when(dietService.update((DietDTO) any())).thenReturn(dietDTO);
    	
    	// Then
    	mockMvc.perform(put("/api/diets")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dietDTO)))
		        .andExpect(status().isOk());
		verify(dietService, times(1)).update(dietDTO);
    }
	
	@Test
    public void shouldUpdateDietWhenIsKo() throws IOException, Exception {
    	// Given
    	final DietDTO dietDTO = createDietDTO();
    	dietDTO.setId(null);
    	
    	// Then
    	mockMvc.perform(put("/api/diets")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dietDTO)))
		        .andExpect(status().isBadRequest());
		verify(dietService, times(0)).update(dietDTO);
    }

	@Test
	public void shouldGetAllDietsWhenIsOk() throws IOException, Exception {
		// Given
		final List<DietDTO> caregiversDTO = Arrays.asList(createDietDTO());

		// When
		when(dietService.findAll()).thenReturn(caregiversDTO);

		// Then
		mockMvc.perform(get("/api/diets")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(dietService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllDietsWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<DietDTO> dietsDTO =  Collections.emptyList();

		// When
		when(dietService.findAll()).thenReturn(dietsDTO);

		// Then
		mockMvc.perform(get("/api/diets")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(dietService, times(1)).findAll();
	}

	@Test
	public void shouldGetDietWhenIsOk() throws IOException, Exception {
		// Given
		final DietDTO caregiverDTO = createDietDTO();

		// When
		when(dietService.findOne(anyLong())).thenReturn(Optional.ofNullable(caregiverDTO));

		// Then
		mockMvc.perform(get("/api/diets/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		        .andExpect(status().isOk());
		verify(dietService, times(1)).findOne(1L);
	}
	
	@Test
	public void shouldGetDietWhenIsNotFOund() throws IOException, Exception {
		// Given
		final DietDTO dietDTO = null;

		// When
		when(dietService.findOne(anyLong())).thenReturn(Optional.ofNullable(dietDTO));

		// Then
		mockMvc.perform(get("/api/diets/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dietDTO)))
		.andExpect(status().isNoContent());
		verify(dietService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteDietWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(dietService).delete(id);

		// Then
		mockMvc.perform(delete("/api/diets/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(dietService, times(1)).delete(id);
	}
}
