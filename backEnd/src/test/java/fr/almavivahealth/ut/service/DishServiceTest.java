package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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

import fr.almavivahealth.dao.DishRepository;
import fr.almavivahealth.domain.entity.Dish;
import fr.almavivahealth.service.dto.DishDTO;
import fr.almavivahealth.service.impl.dish.DishServiceImpl;
import fr.almavivahealth.service.mapper.DishMapper;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class DishServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private DishRepository dishRepository;

	@Mock
	private DishMapper dishMapper;

	@InjectMocks
	private DishServiceImpl dishServiceImpl;

	private static Dish createDish() {
		return Dish.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	private static DishDTO createDishDTO() {
		return DishDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}

	@Test
	public void shouldFindFirst5ByNameByName() {
		// Given
		final List<Dish> dishes = Arrays.asList(createDish());
		final Page<Dish> page = new PageImpl<>(dishes);
		final DishDTO dishDTO = createDishDTO();

		// When
		when(dishRepository.findByNameIgnoreCaseContaining(anyString(), any(Pageable.class))).thenReturn(page);
		when(dishMapper.dishToDishDTO((Dish) any())).thenReturn(dishDTO);

		// Then
		assertThat(dishServiceImpl.findFirst5ByName("Salade")).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyWhenNameIsNotPresent() {
		// Given
		final List<Dish> dishes = Collections.emptyList();
		final Page<Dish> page = new PageImpl<>(dishes);

		// When
		when(dishRepository.findByNameIgnoreCaseContaining(anyString(), any(Pageable.class))).thenReturn(page);

		// Then
		assertThat(dishServiceImpl.findFirst5ByName("Salade")).isEmpty();
	}

	@Test
	public void shouldfindDishByName() {
		// Given
		final Optional<Dish> dish = Optional.ofNullable(createDish());
		final DishDTO dishDTO = createDishDTO();

		// When
		when(dishRepository.findByName(anyString())).thenReturn(dish);
		when(dishMapper.dishToDishDTO((Dish) any())).thenReturn(dishDTO);

		// Then
		assertThat(dishServiceImpl.findByName("test")).isPresent();
	}

	@Test
	public void shouldReturnEmptyOptionalWhenIsNotPresent() {
		// Given
		final Optional<Dish> dish = Optional.empty();

		// When
		when(dishRepository.findByName(anyString())).thenReturn(dish);

		// Then
		assertThat(dishServiceImpl.findByName("test")).isNotPresent();
	}
}
