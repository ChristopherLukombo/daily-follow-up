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

import fr.almavivahealth.dao.DietRepository;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.service.dto.DietDTO;
import fr.almavivahealth.service.impl.diet.DietServiceImpl;
import fr.almavivahealth.service.mapper.DietMapper;

@RunWith(MockitoJUnitRunner.class)
public class DietServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private DietRepository dietRepository;

	@Mock
	private DietMapper dietMapper;

	@InjectMocks
	private DietServiceImpl dietServiceImpl;

	private static Diet createDiet() {
		return Diet.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	private static DietDTO createDietDTO() {
		return DietDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldSaveDietWhenIsOk() {
		// Given
		final Diet diet = createDiet();
		final DietDTO dietDTO = createDietDTO();

		// When
		when(dietRepository.save((Diet) any())).thenReturn(diet);
		when(dietMapper.dietToDietDTO((Diet) any())).thenReturn(dietDTO);

		// Then
		assertThat(dietServiceImpl.save(dietDTO)).isEqualTo(dietDTO);
	}

	@Test
	public void shouldSaveDietWhenIsKo() {
		// Given
		final Diet diet = null;
		final DietDTO dietDTO = null;

		// When
		when(dietRepository.save((Diet) any())).thenReturn(diet);
		when(dietMapper.dietToDietDTO((Diet) any())).thenReturn(dietDTO);

		// Then
		assertThat(dietServiceImpl.save(dietDTO)).isEqualTo(dietDTO);
	}

	@Test
	public void shouldUpdateDietWhenIsOk() {
		// Given
		final Diet diet = createDiet();
		final DietDTO dietDTO = createDietDTO();

		// When
		when(dietRepository.saveAndFlush((Diet) any())).thenReturn(diet);
		when(dietMapper.dietToDietDTO((Diet) any())).thenReturn(dietDTO);

		// Then
		assertThat(dietServiceImpl.update(dietDTO)).isEqualTo(dietDTO);
	}

	@Test
	public void shouldUpdateDietWhenIsKo() {
		// Given
		final Diet diet = null;
		final DietDTO dietDTO = null;

		// When
		when(dietRepository.saveAndFlush((Diet) any())).thenReturn(diet);
		when(dietMapper.dietToDietDTO((Diet) any())).thenReturn(dietDTO);

		// Then
		assertThat(dietServiceImpl.update(dietDTO)).isEqualTo(dietDTO);
	}

	@Test
	public void shouldGetAllDietsWhenIsOk() {
		// Given
		final List<Diet> diets = Arrays.asList(createDiet());

		// Then
		when(dietRepository.findAllByOrderByIdDesc()).thenReturn(diets);

		// Then
		assertThat(dietServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllDietsWhenIsEmpty() {
		// Given
		final List<Diet> diets = Collections.emptyList();

		// Then
		when(dietRepository.findAllByOrderByIdDesc()).thenReturn(diets);

		// Then
		assertThat(dietServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllDietsWhenIsNull() {
		// Given
		final List<Diet> diets = null;

		// Then
		when(dietRepository.findAllByOrderByIdDesc()).thenReturn(diets);

		// Then
		assertThatThrownBy(() -> dietServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetDietWhenIsOk() {
		// Given
		final Diet diet = createDiet();
		final DietDTO dietDTO = createDietDTO();

		// When
		when(dietRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(diet));
		when(dietMapper.dietToDietDTO((Diet) any())).thenReturn(dietDTO);

		// Then
		assertThat(dietServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(dietDTO));
	}

	@Test
	public void shouldGetDietWhenIsNull() {
		// Given
		final Diet diet = null;
		final DietDTO dietDTO = null;

		// When
		when(dietRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(diet));

		// Then
		assertThat(dietServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(dietDTO));
	}

	@Test
	public void shouldDeleteDietWhenIsOk() {
		// When
		doNothing().when(dietRepository).deleteById(ID);

		// Then
		dietServiceImpl.delete(ID);

		verify(dietRepository, times(1)).deleteById(anyLong());
	}
}
