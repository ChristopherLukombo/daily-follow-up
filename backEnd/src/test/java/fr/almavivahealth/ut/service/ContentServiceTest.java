package fr.almavivahealth.ut.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;

import fr.almavivahealth.dao.ContentRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.dto.ContentList;
import fr.almavivahealth.service.impl.content.ContentServiceImpl;
import fr.almavivahealth.service.mapper.ContentMapper;
import fr.almavivahealth.service.propeties.ContentProperties;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ContentServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private MessageSource messageSource;

	@Mock
	private ContentRepository contentRepository;

	@Mock
	private ContentMapper contentMapper;

	@Mock
	private ContentProperties contentProperties;

	@InjectMocks
	private ContentServiceImpl contentServiceImpl;

	private static Content createContent() {
		return Content.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	private static ContentDTO createContentDTO() {
		return ContentDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldSaveContentWhenIsOk() {
		// Given
		final Content content = createContent();
		final ContentDTO contentDTO = createContentDTO();

		// When
		when(contentRepository.save((Content) any())).thenReturn(content);
		when(contentMapper.contentToContentDTO(any())).thenReturn(contentDTO);

		// Then
		assertThat(contentServiceImpl.save(contentDTO)).isEqualTo(contentDTO);
	}

	@Test
	public void shouldSaveContentWhenIsKo() {
		// Given
		final Content content = null;
		final ContentDTO contentDTO = null;

		// When
		when(contentRepository.save((Content) any())).thenReturn(content);
		when(contentMapper.contentToContentDTO(any())).thenReturn(contentDTO);

		// Then
		assertThat(contentServiceImpl.save(contentDTO)).isEqualTo(contentDTO);
	}

	@Test
	public void shouldUpdateContentWhenIsOk() {
		// Given
		final Content content = createContent();
		final ContentDTO contentDTO = createContentDTO();

		// When
		when(contentRepository.saveAndFlush((Content) any())).thenReturn(content);
		when(contentMapper.contentToContentDTO(any())).thenReturn(contentDTO);

		// Then
		assertThat(contentServiceImpl.update(contentDTO)).isEqualTo(contentDTO);
	}

	@Test
	public void shouldUpdateContentWhenIsKo() {
		// Given
		final Content content = null;
		final ContentDTO contentDTO = null;

		// When
		when(contentRepository.saveAndFlush((Content) any())).thenReturn(content);
		when(contentMapper.contentToContentDTO(any())).thenReturn(contentDTO);

		// Then
		assertThat(contentServiceImpl.update(contentDTO)).isEqualTo(contentDTO);
	}

	@Test
	public void shouldGetAllContentsWhenIsOk() {
		// Given
		final List<Content> contents = Arrays.asList(createContent());

		// Then
		when(contentRepository.findAllByOrderByIdDesc()).thenReturn(contents);

		// Then
		assertThat(contentServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllContentsWhenIsEmpty() {
		// Given
		final List<Content> contents = Collections.emptyList();

		// Then
		when(contentRepository.findAllByOrderByIdDesc()).thenReturn(contents);

		// Then
		assertThat(contentServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllContentsWhenIsNull() {
		// Given
		final List<Content> contents = null;

		// Then
		when(contentRepository.findAllByOrderByIdDesc()).thenReturn(contents);

		// Then
		assertThatThrownBy(() -> contentServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldSaveAllContents() {
		// Given
		final List<ContentDTO> contentsDTO = Arrays.asList(createContentDTO());
		final List<Content> contents = Arrays.asList(createContent());
		final ContentList contentList = new ContentList();
		contentList.setContents(contentsDTO);

		// Then
		when(contentRepository.saveAll(anyList())).thenReturn(contents);

		// Then
		assertThat(contentServiceImpl.saveAll(contentList)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyWhenContentsAreEmpty() {
		// Given
		final List<ContentDTO> contentsDTO = Collections.emptyList();
		final List<Content> contents = Collections.emptyList();
		final ContentList contentList = new ContentList();
		contentList.setContents(contentsDTO);

		// Then
		when(contentRepository.saveAll(anyList())).thenReturn(contents);

		// Then
		assertThat(contentServiceImpl.saveAll(contentList)).isEmpty();
	}

	@Test
	public void shouldReturnNullWhenContentsAreEmpty() {
		// Given
		final List<ContentDTO> contentsDTO = null;
		final ContentList contentList = new ContentList();
		contentList.setContents(contentsDTO);

		// Then
		assertThatThrownBy(() -> contentServiceImpl.saveAll(contentList))
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetContentWhenIsOk() {
		// Given
		final Content content = createContent();
		final ContentDTO contentDTO = createContentDTO();

		// When
		when(contentRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(content));
		when(contentMapper.contentToContentDTO(any())).thenReturn(contentDTO);

		// Then
		assertThat(contentServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(contentDTO));
	}

	@Test
	public void shouldGetContentWhenIsNull() {
		// Given
		final Content content = null;
		final ContentDTO contentDTO = null;

		// When
		when(contentRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(content));

		// Then
		assertThat(contentServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(contentDTO));
	}

	@Test
	public void shouldDeleteContentWhenIsOk() {
		// When
		doNothing().when(contentRepository).deleteById(ID);

		// Then
		contentServiceImpl.delete(ID);

		verify(contentRepository, times(1)).deleteById(anyLong());
	}

	@Test
	public void shouldUploadPicture() throws DailyFollowUpException {
		// Given
		final Optional<Content> content = Optional.ofNullable(createContent());
		final String imageDirectory = "." + "/images/";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain",
				"some xml".getBytes());
		final Locale locale = Locale.FRENCH;

		// When
		when(contentRepository.findById(anyLong())).thenReturn(content);
		when(contentProperties.getImagesPath()).thenReturn(imageDirectory);

		// Then
		assertThat(contentServiceImpl.uploadPicture(file, 1L, locale)).isNotNull();
	}

	@Test
	public void shouldThrowWhenContentNotExist() throws DailyFollowUpException {
		// Given
		final Optional<Content> content = Optional.empty();
		final MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain",
				"some xml".getBytes());
       final Locale locale = Locale.FRENCH;

		// When
		when(contentRepository.findById(anyLong())).thenReturn(content);

		// Then
		assertThatThrownBy(() -> contentServiceImpl.uploadPicture(file, 0L, locale)).isInstanceOf(DailyFollowUpException.class);
	}

	@Test
	public void shouldFindPicture() throws IOException {
		// Given
    	final String imageDirectory = "." + "/images/";
    	final Content content = createContent();
    	content.setImageUrl("filename.txt");
    	createFoldersAndFile();

		// When
        when(contentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(content));
        when(contentProperties.getImagesPath()).thenReturn(imageDirectory);

		// Then
        assertThat(contentServiceImpl.findPicture(ID)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyByte() throws IOException {
    	// When
    	when(contentRepository.findById(anyLong())).thenReturn(Optional.empty());

    	// Then
		assertThat(contentServiceImpl.findPicture(ID)).isEmpty();
	}

	private void createFoldersAndFile() throws IOException {
		Files.createDirectory(Paths.get("./images"));
		Files.createDirectory(Paths.get("./images/1"));
		final File file = new File("./images/1/filename.txt");
		file.createNewFile();
		final String str = "Hello";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(str);
		}
	}

	@Before
	@After
	public void deleteFolder() throws IOException {
		if (Files.isDirectory(Paths.get("./images"))) {
			deleteFolderAndContents();
		}
	}

	public void deleteFolderAndContents() throws IOException {
		final List<String> fileNames = Arrays.asList("./images");

		for (final String fileName: fileNames) {
			final File file = new File(fileName);
			if (file.exists()) {
				FileUtils.forceDelete(new File(fileName));
			}
		}
	}

	@Test
	public void shouldDeleteByIds() {
		// Given
		final List<Long> ids = Arrays.asList(1L, 2L);

		// When
		doNothing().when(contentRepository).deleteById(anyLong());

		// Then
		contentServiceImpl.deleteByIds(ids);

		verify(contentRepository, times(2)).deleteById(anyLong());
	}

	@Test
	public void shouldNotDeleteWhenThereIsNoIds() {
		// Given
		final List<Long> ids = Collections.emptyList();

		// Then
		contentServiceImpl.deleteByIds(ids);

		verify(contentRepository, times(0)).deleteById(anyLong());
	}
}
