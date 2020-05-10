package fr.almavivahealth.ut.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import fr.almavivahealth.dao.ContentRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.impl.ContentServiceImpl;
import fr.almavivahealth.service.mapper.ContentMapper;

@RunWith(MockitoJUnitRunner.class)
public class ContentServiceTest {

	private static final boolean SALT = true;

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
				.salt(SALT)
				.build();
	}
	
	private static ContentDTO createContentDTO() {
		return ContentDTO.builder()
				.id(ID)
				.name(NAME)
				.salt(SALT)
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
}
