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

import fr.almavivahealth.service.ContentService;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.ContentResource;

@RunWith(MockitoJUnitRunner.class)
public class ContentResourceTest {

	private static final boolean SALT = true;

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private ContentService contentService;

	@InjectMocks
	private ContentResource contentResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(contentResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static ContentDTO createContentDTO() {
		return ContentDTO.builder()
				.id(ID)
				.name(NAME)
				.salt(SALT)
				.typeMeal("ENTRY")
				.build();
	}

	@Test
    public void shouldCreateContentWhenIsOk() throws IOException, Exception {
    	// Given
    	final ContentDTO contentDTO = createContentDTO();
    	contentDTO.setId(null);

    	// When
		when(contentService.save((ContentDTO) any())).thenReturn(contentDTO);

    	// Then
    	mockMvc.perform(post("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentDTO)))
		        .andExpect(status().isCreated());
		verify(contentService, times(1)).save(any(ContentDTO.class));
    }

	@Test
    public void shouldCreateContentWhenIsKo() throws IOException, Exception {
    	// Given
    	final ContentDTO contentDTO = createContentDTO();

    	// Then
    	mockMvc.perform(post("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentDTO)))
		        .andExpect(status().isBadRequest());
		verify(contentService, times(0)).save(contentDTO);
    }

	@Test
    public void shouldUpdateContentWhenIsOk() throws IOException, Exception {
    	// Given
    	final ContentDTO contentDTO = createContentDTO();

    	// When
		when(contentService.update((ContentDTO) any())).thenReturn(contentDTO);

    	// Then
    	mockMvc.perform(put("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentDTO)))
		        .andExpect(status().isOk());
		verify(contentService, times(1)).update(any(ContentDTO.class));
    }

	@Test
    public void shouldUpdateContentWhenIsKo() throws IOException, Exception {
    	// Given
    	final ContentDTO contentDTO = createContentDTO();
    	contentDTO.setId(null);

    	// Then
    	mockMvc.perform(put("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentDTO)))
		        .andExpect(status().isBadRequest());
		verify(contentService, times(0)).update(contentDTO);
    }

	@Test
	public void shouldGetAllContentsWhenIsOk() throws IOException, Exception {
		// Given
		final List<ContentDTO> contentsDTO = Arrays.asList(createContentDTO());

		// When
		when(contentService.findAll()).thenReturn(contentsDTO);

		// Then
		mockMvc.perform(get("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(contentService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllContentsWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<ContentDTO> contentsDTO = Collections.emptyList();

		// When
		when(contentService.findAll()).thenReturn(contentsDTO);

		// Then
		mockMvc.perform(get("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());
		verify(contentService, times(1)).findAll();
	}

	@Test
	public void shouldGetContentWhenIsOk() throws IOException, Exception {
		// Given
		final ContentDTO contentDTO = createContentDTO();

		// When
		when(contentService.findOne(anyLong())).thenReturn(Optional.ofNullable(contentDTO));

		// Then
		mockMvc.perform(get("/api/contents/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentDTO))).andExpect(status().isOk());
		verify(contentService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetContentWhenIsNotFOund() throws IOException, Exception {
		// Given
		final ContentDTO contentDTO = null;

		// When
		when(contentService.findOne(anyLong())).thenReturn(Optional.ofNullable(contentDTO));

		// Then
		mockMvc.perform(get("/api/contents/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentDTO)))
		        .andExpect(status().isNoContent());
		verify(contentService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteContentWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(contentService).delete(id);

		// Then
		mockMvc.perform(delete("/api/contents/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isNoContent());
		verify(contentService, times(1)).delete(id);
	}
}
