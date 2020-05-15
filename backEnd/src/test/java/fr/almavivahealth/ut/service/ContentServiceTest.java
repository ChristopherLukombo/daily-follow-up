package fr.almavivahealth.ut.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import fr.almavivahealth.dao.ContentRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.dto.ContentList;
import fr.almavivahealth.service.impl.content.ContentServiceImpl;
import fr.almavivahealth.service.mapper.ContentMapper;

@RunWith(MockitoJUnitRunner.class)
public class ContentServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private ContentRepository contentRepository;

	@Mock
	private ContentMapper contentMapper;

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
	public void shouldFindFirst5ByNameByName() {
		// Given
		final List<Content> contents = Arrays.asList(createContent());
		final Page<Content> page = new PageImpl<>(contents);
		final ContentDTO contentDTO = createContentDTO();

		// When
		when(contentRepository.findByNameIgnoreCaseContaining(anyString(), any(Pageable.class))).thenReturn(page);
		when(contentMapper.contentToContentDTO((Content) any())).thenReturn(contentDTO);

		// Then
		assertThat(contentServiceImpl.findFirst5ByName("Salade")).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyWhenNameIsNotPresent() {
		// Given
		final List<Content> contents = Collections.emptyList();
		final Page<Content> page = new PageImpl<>(contents);

		// When
		when(contentRepository.findByNameIgnoreCaseContaining(anyString(), any(Pageable.class))).thenReturn(page);

		// Then
		assertThat(contentServiceImpl.findFirst5ByName("Salade")).isEmpty();
	}
}
