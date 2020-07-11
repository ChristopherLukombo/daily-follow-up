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

import fr.almavivahealth.service.TextureService;
import fr.almavivahealth.service.dto.TextureDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.TextureResource;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class TextureResourceTest {

	private static final long ID = 1L;

	private static final String NAME = "TEST";

	private MockMvc mockMvc;

	@Mock
	private TextureService textureService;

	@InjectMocks
	private TextureResource textureResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(textureResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static TextureDTO createTextureDTO() {
		return TextureDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldCreateTextureWhenIsOk() throws IOException, Exception {
		// Given
		final TextureDTO textureDTO = createTextureDTO();
		textureDTO.setId(null);

		// When
		when(textureService.save((TextureDTO) any())).thenReturn(textureDTO);

		// Then
		mockMvc.perform(post("/api/textures")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(textureDTO)))
		.andExpect(status().isCreated());
		verify(textureService, times(1)).save(textureDTO);
	}

	@Test
	public void shouldCreateTextureWhenIsKo() throws IOException, Exception {
		// Given
		final TextureDTO textureDTO = createTextureDTO();

		// Then
		mockMvc.perform(post("/api/textures")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(textureDTO)))
		.andExpect(status().isBadRequest());
		verify(textureService, times(0)).save(textureDTO);
	}

	@Test
	public void shouldUpdateTextureWhenIsOk() throws IOException, Exception {
		// Given
		final TextureDTO textureDTO = createTextureDTO();

		// When
		when(textureService.update((TextureDTO) any())).thenReturn(textureDTO);

		// Then
		mockMvc.perform(put("/api/textures")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(textureDTO)))
		.andExpect(status().isOk());
		verify(textureService, times(1)).update(textureDTO);
	}

	@Test
	public void shouldUpdateTextureWhenIsKo() throws IOException, Exception {
		// Given
		final TextureDTO textureDTO = createTextureDTO();
		textureDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/textures")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(textureDTO)))
		.andExpect(status().isBadRequest());
		verify(textureService, times(0)).update(textureDTO);
	}

	@Test
	public void shouldGetAllTexturesWhenIsOk() throws IOException, Exception {
		// Given
		final List<TextureDTO> texturesDTO = Arrays.asList(createTextureDTO());

		// When
		when(textureService.findAll()).thenReturn(texturesDTO);

		// Then
		mockMvc.perform(get("/api/textures")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(textureService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllTexturesWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<TextureDTO> texturesDTO =  Collections.emptyList();

		// When
		when(textureService.findAll()).thenReturn(texturesDTO);

		// Then
		mockMvc.perform(get("/api/textures")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(textureService, times(1)).findAll();
	}

	@Test
	public void shouldGetTextureWhenIsOk() throws IOException, Exception {
		// Given
		final TextureDTO textureDTO = createTextureDTO();

		// When
		when(textureService.findOne(anyLong())).thenReturn(Optional.ofNullable(textureDTO));

		// Then
		mockMvc.perform(get("/api/textures/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(textureDTO)))
		.andExpect(status().isOk());
		verify(textureService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetTextureWhenIsNotFOund() throws IOException, Exception {
		// Given
		final TextureDTO textureDTO = null;

		// When
		when(textureService.findOne(anyLong())).thenReturn(Optional.ofNullable(textureDTO));

		// Then
		mockMvc.perform(get("/api/textures/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(textureDTO)))
		.andExpect(status().isNoContent());
		verify(textureService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteTextureWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(textureService).delete(id);

		// Then
		mockMvc.perform(delete("/api/textures/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(textureService, times(1)).delete(id);
	}
}
