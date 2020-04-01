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

import fr.almavivahealth.service.TypeMealService;
import fr.almavivahealth.service.dto.TypeMealDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.TypeMealResource;

@RunWith(MockitoJUnitRunner.class)
public class TypeMealResourceTest {
	
	private static final String NAME = "TEST";

	private static final long ID = 1L;
	
	private MockMvc mockMvc;
	
	@Mock
	private TypeMealService typeMealService;
	
	@InjectMocks
	private TypeMealResource typeMealResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(typeMealResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	private static TypeMealDTO createTypeMealDTO() {
		return TypeMealDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldCreateTypeMealWhenIsOk() throws IOException, Exception {
		// Given
		final TypeMealDTO typeMealDTO = createTypeMealDTO();
		typeMealDTO.setId(null);

		// When
		when(typeMealService.save((TypeMealDTO) any())).thenReturn(typeMealDTO);

		// Then
		mockMvc.perform(post("/api/typeMeals")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(typeMealDTO)))
		.andExpect(status().isCreated());
		verify(typeMealService, times(1)).save(typeMealDTO);
	}

	@Test
	public void shouldCreateTypeMealWhenIsKo() throws IOException, Exception {
		// Given
		final TypeMealDTO typeMealDTO = createTypeMealDTO();

		// Then
		mockMvc.perform(post("/api/typeMeals")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(typeMealDTO)))
		.andExpect(status().isBadRequest());
		verify(typeMealService, times(0)).save(typeMealDTO);
	}

	@Test
	public void shouldUpdateTypeMealWhenIsOk() throws IOException, Exception {
		// Given
		final TypeMealDTO typeMealDTO = createTypeMealDTO();

		// When
		when(typeMealService.update((TypeMealDTO) any())).thenReturn(typeMealDTO);

		// Then
		mockMvc.perform(put("/api/typeMeals")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(typeMealDTO)))
		.andExpect(status().isOk());
		verify(typeMealService, times(1)).update(typeMealDTO);
	}

	@Test
	public void shouldUpdateTypeMealWhenIsKo() throws IOException, Exception {
		// Given
		final TypeMealDTO typeMealDTO = createTypeMealDTO();
		typeMealDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/typeMeals")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(typeMealDTO)))
		.andExpect(status().isBadRequest());
		verify(typeMealService, times(0)).update(typeMealDTO);
	}

	@Test
	public void shouldGetAllTypeMealsWhenIsOk() throws IOException, Exception {
		// Given
		final List<TypeMealDTO> typeMealsDTO = Arrays.asList(createTypeMealDTO());

		// When
		when(typeMealService.findAll()).thenReturn(typeMealsDTO);

		// Then
		mockMvc.perform(get("/api/typeMeals")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(typeMealService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllTypeMealsWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<TypeMealDTO> typeMealsDTO =  Collections.emptyList();

		// When
		when(typeMealService.findAll()).thenReturn(typeMealsDTO);

		// Then
		mockMvc.perform(get("/api/typeMeals")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(typeMealService, times(1)).findAll();
	}

	@Test
	public void shouldGetTypeMealWhenIsOk() throws IOException, Exception {
		// Given
		final TypeMealDTO typeMealDTO = createTypeMealDTO();

		// When
		when(typeMealService.findOne(anyLong())).thenReturn(Optional.ofNullable(typeMealDTO));

		// Then
		mockMvc.perform(get("/api/typeMeals/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(typeMealDTO)))
		.andExpect(status().isOk());
		verify(typeMealService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetTypeMealWhenIsNotFOund() throws IOException, Exception {
		// Given
		final TypeMealDTO typeMealDTO = null;

		// When
		when(typeMealService.findOne(anyLong())).thenReturn(Optional.ofNullable(typeMealDTO));

		// Then
		mockMvc.perform(get("/api/typeMeals/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(typeMealDTO)))
		.andExpect(status().isNoContent());
		verify(typeMealService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteTypeMealWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(typeMealService).delete(id);

		// Then
		mockMvc.perform(delete("/api/typeMeals/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(typeMealService, times(1)).delete(id);
	}
}
