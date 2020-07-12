package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.ContentService;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.dto.ContentList;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.ContentResource;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ContentResourceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private MessageSource messageSource;

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
				.build();
	}

	@Test
    public void shouldCreateContentWhenIsOk() throws Exception {
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
    public void shouldCreateContentWhenIsKo() throws Exception {
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
    public void shouldUpdateContentWhenIsOk() throws Exception {
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
    public void shouldUpdateContentWhenIsKo() throws Exception {
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
	public void shouldGetAllContentsWhenIsOk() throws Exception {
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
	public void shouldGetAllContentsWhenIsEmpty() throws Exception {
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
	public void shouldGetContentWhenIsOk() throws Exception {
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
	public void shouldGetContentWhenIsNotFOund() throws Exception {
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

	@Test
	public void shouldSaveAllContents() throws Exception {
		// Given
		final List<ContentDTO> contentsDTO = Arrays.asList(createContentDTO());
		final ContentList contentList = new ContentList();
		contentList.setContents(contentsDTO);

		// When
        when(contentService.saveAll((ContentList) any())).thenReturn(contentsDTO);

		// Then
		mockMvc.perform(post("/api/contents/contentList")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentList)))
		        .andExpect(status().isOk());
		verify(contentService, times(1)).saveAll((ContentList) any());
	}

	@Test
	public void shouldThrowInternalServerErrorWhenListOfContentsIsEmpty() throws Exception {
		// Given
		final List<ContentDTO> contentsDTO = Collections.emptyList();
		final ContentList contentList = new ContentList();
		contentList.setContents(contentsDTO);

		// Then
		mockMvc.perform(post("/api/contents/contentList")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(contentList)))
		        .andExpect(status().isUnprocessableEntity());
		verify(contentService, times(0)).saveAll((ContentList) any());
	}

	@Test
	public void shouldUploadPicture() throws Exception {
		// Given
		final MockMultipartFile file = new MockMultipartFile("file", "filename.png", "text/plain",
				"some xml".getBytes());
		final MockPart part = new MockPart("file", "filename.png", file.getBytes());

		// When
		when(contentService.uploadPicture((MultipartFile) any(), anyLong(), (Locale) any())).thenReturn("test");

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/contents/picture/1")
				.part(part))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
		verify(contentService, times(1)).uploadPicture((MultipartFile) any(), anyLong(), (Locale) any());
	}

	@Test
	public void shouldReturn500WhenTryingToUploadPicture() throws Exception {
		// Given
		final MockMultipartFile file = new MockMultipartFile("file", "filename.png", "text/plain",
				"some xml".getBytes());
		final MockPart part = new MockPart("file", "filename.png", file.getBytes());

		// When
        doThrow(DailyFollowUpException.class).when(contentService).uploadPicture((MultipartFile) any(), anyLong(), (Locale) any());

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/contents/picture/1")
				.part(part))
		        .andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$").isNotEmpty());
		verify(contentService, times(1)).uploadPicture((MultipartFile) any(), anyLong(), (Locale) any());
	}

	@Test
	public void shouldGetPictureWhen() throws Exception {
		// Given
		final byte[] profilePicture = new byte[] {0};

		// When
		when(contentService.findPicture(anyLong())).thenReturn(profilePicture);

		// Then
		mockMvc.perform(get("/api/contents/picture/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(contentService, times(1)).findPicture(anyLong());
	}

	@Test
	public void shoulGetdBadRequestWhenPictureRequestIsNotValid() throws Exception {
		// Then
		mockMvc.perform(get("/api/contents/picture/test")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
		verify(contentService, times(0)).findPicture(anyLong());
	}

	@Test
	public void shouldDeleteByIds() throws Exception {
		// Given
		final List<Long> ids = Arrays.asList(ID);

		// When
		doNothing().when(contentService).deleteByIds(ids);

		// Then
		mockMvc.perform(delete("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(ids)))
		        .andExpect(status().isNoContent());
		verify(contentService, times(1)).deleteByIds(ids);
	}


	@Test
	public void shouldThrowBadRequestWhenThereIsNoId() throws Exception {
		// Given
		final List<Long> ids = null;

		// Then
		mockMvc.perform(delete("/api/contents")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(ids)))
		        .andExpect(status().isBadRequest());
		verify(contentService, times(0)).deleteByIds(ids);
	}
}
