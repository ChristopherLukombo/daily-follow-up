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

import fr.almavivahealth.dao.DishRepository;
import fr.almavivahealth.domain.entity.Dish;
import fr.almavivahealth.service.impl.dish.DishImportationServiceImpl;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DishImportationServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private DishRepository dishRepository;

	@Captor
	private ArgumentCaptor<List<Dish>> captor;

	@InjectMocks
	private DishImportationServiceImpl dishImportationServiceImpl;

	private static Dish createDish() {
		return Dish.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldImportDishs() {
		// Given
		final List<Dish> dishes = Arrays.asList(createDish());

		// When
		when(dishRepository.saveAll(anySet())).thenReturn(dishes);
		dishImportationServiceImpl.importDishes();

		// Then
		verify(dishRepository, times(1)).saveAll(captor.capture());

		assertThat(captor.getAllValues()).isNotEmpty();
	}

	@Test
	public void shouldReturnTrueWhenHasElements() {
		final Long numberOfItems = 1L;

		// When
		when(dishRepository.count()).thenReturn(numberOfItems);

		// Then
		assertThat(dishImportationServiceImpl.hasElements()).isTrue();
	}

	@Test
	public void shouldReturnFalseWhenHasNotElements() {
		final Long numberOfItems = 0L;

		// When
		when(dishRepository.count()).thenReturn(numberOfItems);

		// Then
		assertThat(dishImportationServiceImpl.hasElements()).isFalse();
	}
}
