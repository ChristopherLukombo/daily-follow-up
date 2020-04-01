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

import fr.almavivahealth.service.MenuService;
import fr.almavivahealth.service.dto.MenuDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.MenuResource;

@RunWith(MockitoJUnitRunner.class)
public class MenuResourceTest {
	
	private static final long ID = 1L;
	
	private MockMvc mockMvc;
	
	@Mock
	private MenuService menuService;
	
	@InjectMocks
	private MenuResource menuResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(menuResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	private static MenuDTO createMenuDTO() {
		return MenuDTO.builder()
				.id(ID)
				.startDate(LocalDate.now())
				.build();
	}

	@Test
	public void shouldCreateMenuWhenIsOk() throws IOException, Exception {
		// Given
		final MenuDTO menuDTO = createMenuDTO();
		menuDTO.setId(null);

		// When
		when(menuService.save((MenuDTO) any())).thenReturn(menuDTO);

		// Then
		mockMvc.perform(post("/api/menus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(menuDTO)))
		.andExpect(status().isCreated());
		verify(menuService, times(1)).save(menuDTO);
	}

	@Test
	public void shouldCreateMenuWhenIsKo() throws IOException, Exception {
		// Given
		final MenuDTO menuDTO = createMenuDTO();

		// Then
		mockMvc.perform(post("/api/menus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(menuDTO)))
		.andExpect(status().isBadRequest());
		verify(menuService, times(0)).save(menuDTO);
	}
	
	@Test
	public void shouldUpdateMenuWhenIsOk() throws IOException, Exception {
		// Given
		final MenuDTO menuDTO = createMenuDTO();

		// When
		when(menuService.update((MenuDTO) any())).thenReturn(menuDTO);

		// Then
		mockMvc.perform(put("/api/menus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(menuDTO)))
		.andExpect(status().isOk());
		verify(menuService, times(1)).update(menuDTO);
	}

	@Test
	public void shouldUpdateMenuWhenIsKo() throws IOException, Exception {
		// Given
		final MenuDTO menuDTO = createMenuDTO();
		menuDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/menus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(menuDTO)))
		.andExpect(status().isBadRequest());
		verify(menuService, times(0)).update(menuDTO);
	}
	
	@Test
	public void shouldGetAllMenusWhenIsOk() throws IOException, Exception {
		// Given
		final List<MenuDTO> menusDTO = Arrays.asList(createMenuDTO());

		// When
		when(menuService.findAll()).thenReturn(menusDTO);

		// Then
		mockMvc.perform(get("/api/menus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(menuService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllMenusWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<MenuDTO> menusDTO =  Collections.emptyList();

		// When
		when(menuService.findAll()).thenReturn(menusDTO);

		// Then
		mockMvc.perform(get("/api/menus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(menuService, times(1)).findAll();
	}
	
	@Test
	public void shouldGetMenuWhenIsOk() throws IOException, Exception {
		// Given
		final MenuDTO menuDTO = createMenuDTO();

		// When
		when(menuService.findOne(anyLong())).thenReturn(Optional.ofNullable(menuDTO));

		// Then
		mockMvc.perform(get("/api/menus/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(menuDTO)))
		.andExpect(status().isOk());
		verify(menuService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetMenuWhenIsNotFOund() throws IOException, Exception {
		// Given
		final MenuDTO menuDTO = null;

		// When
		when(menuService.findOne(anyLong())).thenReturn(Optional.ofNullable(menuDTO));

		// Then
		mockMvc.perform(get("/api/menus/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(menuDTO)))
		.andExpect(status().isNoContent());
		verify(menuService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteMenuWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(menuService).delete(id);

		// Then
		mockMvc.perform(delete("/api/menus/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(menuService, times(1)).delete(id);
	}
}
