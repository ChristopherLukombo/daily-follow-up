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

import fr.almavivahealth.service.AllergyService;
import fr.almavivahealth.service.dto.AllergyDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.AllergyResource;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AllergyResourceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private AllergyService allergyService;

	@InjectMocks
	private AllergyResource allergyResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(allergyResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private AllergyDTO createAllergyDTO() {
		return AllergyDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
    public void shouldCreateAllergyWhenIsOk() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = createAllergyDTO();
    	allergyDTO.setId(null);

    	// When
		when(allergyService.save((AllergyDTO) any())).thenReturn(allergyDTO);

    	// Then
    	mockMvc.perform(post("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isCreated());
    	verify(allergyService, times(1)).save(allergyDTO);
    }

	@Test
    public void shouldCreateAllergyWhenIdIsNotEmpty() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = createAllergyDTO();

    	// Then
    	mockMvc.perform(post("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isBadRequest());
    	verify(allergyService, times(0)).save(allergyDTO);
    }

	@Test
    public void shouldCreateAllergyWhenIdIsNotValid() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = createAllergyDTO();
    	allergyDTO.setName(null);

    	// Then
    	mockMvc.perform(post("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isUnprocessableEntity());
    	verify(allergyService, times(0)).save(allergyDTO);
    }

    @Test
    public void shouldCreateAllergyWhenIsKo() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = null;

    	// Then
    	mockMvc.perform(post("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isBadRequest());
    	verify(allergyService, times(0)).save(allergyDTO);
    }

    @Test
    public void shouldUpdateAllergyWhenIsOk() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = createAllergyDTO();

    	// When
		when(allergyService.update((AllergyDTO) any())).thenReturn(allergyDTO);

    	// Then
    	mockMvc.perform(put("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isOk());
    	verify(allergyService, times(1)).update(allergyDTO);
    }

    @Test
    public void shouldUpdateAllergyWhenIsKo() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = null;

    	// Then
    	mockMvc.perform(put("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isBadRequest());
       	verify(allergyService, times(0)).update(allergyDTO);
    }

	@Test
    public void shouldCreateAllergyWhenIdIsEmpty() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = createAllergyDTO();
    	allergyDTO.setId(null);

    	// Then
    	mockMvc.perform(put("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isBadRequest());
    	verify(allergyService, times(0)).update(allergyDTO);
    }

	@Test
    public void shouldUpdateAllergyWhenIdIsNotValid() throws IOException, Exception {
    	// Given
    	final AllergyDTO allergyDTO = createAllergyDTO();
    	allergyDTO.setId(null);
    	allergyDTO.setName(null);

    	// Then
    	mockMvc.perform(put("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(allergyDTO)))
		        .andExpect(status().isUnprocessableEntity());
    	verify(allergyService, times(0)).update(allergyDTO);
    }

	@Test
	public void shouldGetAllAllergysWhenIsOk() throws Exception {
		// Given
		final List<AllergyDTO> allergys = Arrays.asList(createAllergyDTO());

		// When
		when(allergyService.findAll()).thenReturn(allergys);

		// Then
		mockMvc.perform(get("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(allergyService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllAllergysWhenIsEmpty() throws Exception {
		// Given
		final List<AllergyDTO> allergys = Collections.emptyList();

		// When
		when(allergyService.findAll()).thenReturn(allergys);

		// Then
		mockMvc.perform(get("/api/allergys")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(allergyService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllergyWhenIsOk() throws Exception {
		// Given
		final AllergyDTO allergy = createAllergyDTO();

		// When
		when(allergyService.findOne(anyLong())).thenReturn(Optional.ofNullable(allergy));

		// Then
		mockMvc.perform(get("/api/allergys/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(allergyService, times(1)).findOne(ID);
	}

	@Test
	public void shouldGetAllergyWhenIsNotFound() throws Exception {
		// Given
		final AllergyDTO allergy = null;

		// When
		when(allergyService.findOne(anyLong())).thenReturn(Optional.ofNullable(allergy));

		// Then
		mockMvc.perform(get("/api/allergys/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(allergyService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteAllergyWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(allergyService).delete(id);

		// Then
		mockMvc.perform(delete("/api/allergys/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(allergyService, times(1)).delete(id);
	}
}
