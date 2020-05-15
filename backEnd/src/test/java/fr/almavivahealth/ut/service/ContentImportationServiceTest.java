package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.almavivahealth.dao.ContentRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.service.impl.content.ContentImportationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ContentImportationServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private ContentRepository contentRepository;

	@Captor
	private ArgumentCaptor<List<Content>> captor;

	@InjectMocks
	private ContentImportationServiceImpl contentImportationServiceImpl;

	private static Content createContent() {
		return Content.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldImportContents() {
		// Given
		final List<Content> contents = Arrays.asList(createContent());

		// When
		when(contentRepository.saveAll(anySet())).thenReturn(contents);
		contentImportationServiceImpl.importContents();

		// Then
		verify(contentRepository, times(1)).saveAll(captor.capture());

		assertThat(captor.getAllValues()).isNotEmpty();
	}

	@Test
	public void shouldReturnTrueWhenHasElements() {
		final Long numberOfItems = 1L;

		// When
		when(contentRepository.count()).thenReturn(numberOfItems);

		// Then
		assertThat(contentImportationServiceImpl.hasElements()).isTrue();
	}

	@Test
	public void shouldReturnFalseWhenHasNotElements() {
		final Long numberOfItems = 0L;

		// When
		when(contentRepository.count()).thenReturn(numberOfItems);

		// Then
		assertThat(contentImportationServiceImpl.hasElements()).isFalse();
	}
}
