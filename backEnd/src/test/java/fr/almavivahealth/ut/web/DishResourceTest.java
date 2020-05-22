package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import fr.almavivahealth.service.DishService;
import fr.almavivahealth.service.dto.DishDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.DishResource;

@RunWith(MockitoJUnitRunner.class)
public class DishResourceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private DishService dishService;

	@InjectMocks
	private DishResource dishResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(dishResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static DishDTO createDishDTO() {
		return DishDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldGetFirst5ByNameDishesByName() throws Exception {
		// Given
		final List<DishDTO> dishDTOs = Arrays.asList(createDishDTO());

		// When
		when(dishService.findFirst5ByName(anyString())).thenReturn(dishDTOs);

		// Then
		mockMvc.perform(get("/api/dishes/search?name=Salade")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(dishService, times(1)).findFirst5ByName(anyString());
	}

	@Test
	public void shouldReturn204WhenNoDishExistForName() throws Exception {
		// Given
		final List<DishDTO> dishDTOs = Collections.emptyList();

		// When
		when(dishService.findFirst5ByName(anyString())).thenReturn(dishDTOs);

		// Then
		mockMvc.perform(get("/api/dishes/search?name=Salade")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(dishService, times(1)).findFirst5ByName(anyString());
	}

	@Test
	public void shouldGetDishByName() throws Exception {
		// Given
		final Optional<DishDTO> dishDTO = Optional.ofNullable(createDishDTO());

		// When
		when(dishService.findByName(anyString())).thenReturn(dishDTO);

		// Then
		mockMvc.perform(get("/api/dishes/find?name=test")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(dishService, times(1)).findByName(anyString());
	}

	@Test
	public void shouldReturn204WhenNoDishIsFoundByName() throws Exception {
		// Given
		final Optional<DishDTO> dishDTO = Optional.empty();

		// When
		when(dishService.findByName(anyString())).thenReturn(dishDTO);

		// Then
		mockMvc.perform(get("/api/dishes/find?name=test")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(dishService, times(1)).findByName(anyString());
	}
}
