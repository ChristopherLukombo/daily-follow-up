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

import fr.almavivahealth.dao.DayRepository;
import fr.almavivahealth.domain.Day;
import fr.almavivahealth.service.dto.DayDTO;
import fr.almavivahealth.service.impl.DayServiceImpl;
import fr.almavivahealth.service.mapper.DayMapper;

@RunWith(MockitoJUnitRunner.class)
public class DayServiceTest {
	
	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private DayRepository dayRepository;

	@Mock
	private DayMapper dayMapper; 
	
	@InjectMocks
	private DayServiceImpl dayServiceImpl;
	
	private static Day createDay() {
		return Day.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	private static DayDTO createDayDTO() {
		return DayDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	@Test
	public void shouldSaveDayWhenIsOk() {
		// Given
		final Day day = createDay();
		final DayDTO dayDTO = createDayDTO();

		// When
		when(dayRepository.save((Day) any())).thenReturn(day);
		when(dayMapper.dayToDayDTO((Day) any())).thenReturn(dayDTO);

		// Then
		assertThat(dayServiceImpl.save(dayDTO)).isEqualTo(dayDTO);
	}

	@Test
	public void shouldSaveDayWhenIsKo() {
		// Given
		final Day day = null;
		final DayDTO dayDTO = null;

		// When
		when(dayRepository.save((Day) any())).thenReturn(day);
		when(dayMapper.dayToDayDTO((Day) any())).thenReturn(dayDTO);

		// Then
		assertThat(dayServiceImpl.save(dayDTO)).isEqualTo(dayDTO);
	}
	
	@Test
	public void shouldUpdateDayWhenIsOk() {
		// Given
		final Day day = createDay();
		final DayDTO dayDTO = createDayDTO();

		// When
		when(dayRepository.saveAndFlush((Day) any())).thenReturn(day);
		when(dayMapper.dayToDayDTO((Day) any())).thenReturn(dayDTO);

		// Then
		assertThat(dayServiceImpl.update(dayDTO)).isEqualTo(dayDTO);
	}

	@Test
	public void shouldUpdateDayWhenIsKo() {
		// Given
		final Day day = null;
		final DayDTO dayDTO = null;

		// When
		when(dayRepository.saveAndFlush((Day) any())).thenReturn(day);
		when(dayMapper.dayToDayDTO((Day) any())).thenReturn(dayDTO);

		// Then
		assertThat(dayServiceImpl.update(dayDTO)).isEqualTo(dayDTO);
	}
	
	@Test
	public void shouldGetAllDaysWhenIsOk() {
		// Given
		final List<Day> days = Arrays.asList(createDay());

		// Then
		when(dayRepository.findAllByOrderByIdDesc()).thenReturn(days);

		// Then
		assertThat(dayServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllDaysWhenIsEmpty() {
		// Given
		final List<Day> days = Collections.emptyList();

		// Then
		when(dayRepository.findAllByOrderByIdDesc()).thenReturn(days);

		// Then
		assertThat(dayServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllDaysWhenIsNull() {
		// Given
		final List<Day> days = null;

		// Then
		when(dayRepository.findAllByOrderByIdDesc()).thenReturn(days);

		// Then
		assertThatThrownBy(() -> dayServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetDayWhenIsOk() {
		// Given
		final Day day = createDay();
		final DayDTO dayDTO = createDayDTO();

		// When
		when(dayRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(day));
		when(dayMapper.dayToDayDTO((Day) any())).thenReturn(dayDTO);
		
		// Then
		assertThat(dayServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(dayDTO));
	}
	
	@Test
	public void shouldGetDayWhenIsNull() {
		// Given
		final Day day = null;
		final DayDTO dayDTO = null;

		// When
		when(dayRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(day));
		
		// Then
		assertThat(dayServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(dayDTO));
	}
	
	@Test
	public void shouldDeleteDayWhenIsOk() {
		// When
		doNothing().when(dayRepository).deleteById(ID);

		// Then
		dayServiceImpl.delete(ID);
		
		verify(dayRepository, times(1)).deleteById(anyLong());
	}
}
