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

import fr.almavivahealth.dao.TypeMealRepository;
import fr.almavivahealth.domain.TypeMeal;
import fr.almavivahealth.service.dto.TypeMealDTO;
import fr.almavivahealth.service.impl.TypeMealServiceImpl;
import fr.almavivahealth.service.mapper.TypeMealMapper;

@RunWith(MockitoJUnitRunner.class)
public class TypeMealServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private TypeMealRepository typeMealRepository;
	
	@Mock
	private TypeMealMapper typeMealMapper;

	@InjectMocks
	private TypeMealServiceImpl typeMealServiceImpl;
	
	private static TypeMeal createTypeMeal() {
		return TypeMeal.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	private static TypeMealDTO createTypeMealDTO() {
		return TypeMealDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	@Test
	public void shouldSaveTypeMealWhenIsOk() {
		// Given
		final TypeMeal typeMeal = createTypeMeal();
		final TypeMealDTO typeMealDTO = createTypeMealDTO();

		// When
		when(typeMealRepository.save((TypeMeal) any())).thenReturn(typeMeal);
		when(typeMealMapper.typeMealToTypeMealDTO((TypeMeal) any())).thenReturn(typeMealDTO);

		// Then
		assertThat(typeMealServiceImpl.save(typeMealDTO)).isEqualTo(typeMealDTO);
	}
	
	@Test
	public void shouldSaveTypeMealWhenIsKo() {
		// Given
		final TypeMeal typeMeal = null;
		final TypeMealDTO typeMealDTO = null;

		// When
		when(typeMealRepository.save((TypeMeal) any())).thenReturn(typeMeal);
		when(typeMealMapper.typeMealToTypeMealDTO((TypeMeal) any())).thenReturn(typeMealDTO);

		// Then
		assertThat(typeMealServiceImpl.save(typeMealDTO)).isEqualTo(typeMealDTO);
	}
	
	@Test
	public void shouldUpdateTypeMealWhenIsOk() {
		// Given
		final TypeMeal typeMeal = createTypeMeal();
		final TypeMealDTO typeMealDTO = createTypeMealDTO();

		// When
		when(typeMealRepository.saveAndFlush((TypeMeal) any())).thenReturn(typeMeal);
		when(typeMealMapper.typeMealToTypeMealDTO((TypeMeal) any())).thenReturn(typeMealDTO);

		// Then
		assertThat(typeMealServiceImpl.update(typeMealDTO)).isEqualTo(typeMealDTO);
	}
	
	@Test
	public void shouldUpdateTypeMealWhenIsKo() {
		// Given
		final TypeMeal typeMeal = null;
		final TypeMealDTO typeMealDTO = null;

		// When
		when(typeMealRepository.saveAndFlush((TypeMeal) any())).thenReturn(typeMeal);
		when(typeMealMapper.typeMealToTypeMealDTO((TypeMeal) any())).thenReturn(typeMealDTO);

		// Then
		assertThat(typeMealServiceImpl.update(typeMealDTO)).isEqualTo(typeMealDTO);
	}
	
	@Test
	public void shouldGetAllTypeMealsWhenIsOk() {
		// Given
		final List<TypeMeal> typeMeals = Arrays.asList(createTypeMeal());

		// Then
		when(typeMealRepository.findAll()).thenReturn(typeMeals);

		// Then
		assertThat(typeMealServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllTypeMealsWhenIsEmpty() {
		// Given
		final List<TypeMeal> typeMeals = Collections.emptyList();

		// Then
		when(typeMealRepository.findAll()).thenReturn(typeMeals);

		// Then
		assertThat(typeMealServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllTypeMealsWhenIsNull() {
		// Given
		final List<TypeMeal> typeMeals = null;

		// Then
		when(typeMealRepository.findAll()).thenReturn(typeMeals);

		// Then
		assertThatThrownBy(() -> typeMealServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetTypeMealWhenIsOk() {
		// Given
		final TypeMeal typeMeal = createTypeMeal();
		final TypeMealDTO typeMealDTO = createTypeMealDTO();

		// When
		when(typeMealRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(typeMeal));
		when(typeMealMapper.typeMealToTypeMealDTO((TypeMeal) any())).thenReturn(typeMealDTO);
		
		// Then
		assertThat(typeMealServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(typeMealDTO));
	}
	
	@Test
	public void shouldGetTypeMealWhenIsNull() {
		// Given
		final TypeMeal typeMeal = null;
		final TypeMealDTO typeMealDTO = null;

		// When
		when(typeMealRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(typeMeal));
		
		// Then
		assertThat(typeMealServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(typeMealDTO));
	}
	
	@Test
	public void shouldDeleteTypeMealWhenIsOk() {
		// When
		doNothing().when(typeMealRepository).deleteById(ID);

		// Then
		typeMealServiceImpl.delete(ID);
		
		verify(typeMealRepository, times(1)).deleteById(anyLong());
	}
}
