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

import fr.almavivahealth.dao.MomentDayRepository;
import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.service.dto.MomentDayDTO;
import fr.almavivahealth.service.impl.MomentDayServiceImpl;
import fr.almavivahealth.service.mapper.MomentDayMapper;

@RunWith(MockitoJUnitRunner.class)
public class MomentDayServiceTest {

	private static final String NAME = "TEST";

	private static final long ID = 1L;

	@Mock
	private MomentDayRepository momentDayRepository;
	
	@Mock
	private MomentDayMapper momentDayMapper;
	
	@InjectMocks
	private MomentDayServiceImpl momentDayServiceImpl;

	private static MomentDay createMomentDay() {
		return MomentDay.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	private static MomentDayDTO createMomentDayDTO() {
		return MomentDayDTO.builder()
				.id(ID)
				.name(NAME)
				.build();
	}
	
	@Test
	public void shouldSaveMomentDayWhenIsOk() {
		// Given
		final MomentDay momentDay = createMomentDay();
		final MomentDayDTO momentDayDTO = createMomentDayDTO();
		
		// When
		when(momentDayRepository.save((MomentDay) any())).thenReturn(momentDay);
		when(momentDayMapper.momentDayToMomentDayDTO((MomentDay) any())).thenReturn(momentDayDTO);
		
		// Then
		assertThat(momentDayServiceImpl.save(momentDayDTO)).isEqualTo(momentDayDTO);
	}
	
	@Test
	public void shouldSaveMomentDayWhenIsKo() {
		// Given
		final MomentDay momentDay = null;
		final MomentDayDTO momentDayDTO = null;
		
		// When
		when(momentDayRepository.save((MomentDay) any())).thenReturn(momentDay);
		when(momentDayMapper.momentDayToMomentDayDTO((MomentDay) any())).thenReturn(momentDayDTO);
		
		// Then
		assertThat(momentDayServiceImpl.save(momentDayDTO)).isEqualTo(momentDayDTO);
	}
	
	@Test
	public void shouldUpdateMomentDayWhenIsOk() {
		// Given
		final MomentDay momentDay = createMomentDay();
		final MomentDayDTO momentDayDTO = createMomentDayDTO();
		
		// When
		when(momentDayRepository.saveAndFlush((MomentDay) any())).thenReturn(momentDay);
		when(momentDayMapper.momentDayToMomentDayDTO((MomentDay) any())).thenReturn(momentDayDTO);
		
		// Then
		assertThat(momentDayServiceImpl.update(momentDayDTO)).isEqualTo(momentDayDTO);
	}
	
	@Test
	public void shouldUpdateMomentDayWhenIsKo() {
		// Given
		final MomentDay momentDay = null;
		final MomentDayDTO momentDayDTO = null;
		
		// When
		when(momentDayRepository.saveAndFlush((MomentDay) any())).thenReturn(momentDay);
		when(momentDayMapper.momentDayToMomentDayDTO((MomentDay) any())).thenReturn(momentDayDTO);
		
		// Then
		assertThat(momentDayServiceImpl.update(momentDayDTO)).isEqualTo(momentDayDTO);
	}
	
	@Test
	public void shouldGetAllMomentDayWhenIsOk() {
		// Given
		final List<MomentDay> momentDays = Arrays.asList(createMomentDay());

		// Then
		when(momentDayRepository.findAllByOrderByIdDesc()).thenReturn(momentDays);

		// Then
		assertThat(momentDayServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllMomentDaysWhenIsEmpty() {
		// Given
		final List<MomentDay> momentDays = Collections.emptyList();

		// Then
		when(momentDayRepository.findAllByOrderByIdDesc()).thenReturn(momentDays);

		// Then
		assertThat(momentDayServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllMomentDaysWhenIsNull() {
		// Given
		final List<MomentDay> momentDays = null;

		// Then
		when(momentDayRepository.findAllByOrderByIdDesc()).thenReturn(momentDays);

		// Then
		assertThatThrownBy(() -> momentDayServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetMomentDayWhenIsOk() {
		// Given
		final MomentDay momentDay = createMomentDay();
		final MomentDayDTO momentDayDTO = createMomentDayDTO();

		// When
		when(momentDayRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(momentDay));
		when(momentDayMapper.momentDayToMomentDayDTO((MomentDay) any())).thenReturn(momentDayDTO);

		// Then
		assertThat(momentDayServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(momentDayDTO));
	}

	@Test
	public void shouldGetMomentDayWhenIsNull() {
		// Given
		final MomentDay momentDay = null;
		final MomentDayDTO momentDayDTO = null;

		// When
		when(momentDayRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(momentDay));

		// Then
		assertThat(momentDayServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(momentDayDTO));
	}

	@Test
	public void shouldDeleteMomentDayWhenIsOk() {
		// When
		doNothing().when(momentDayRepository).deleteById(ID);

		// Then
		momentDayServiceImpl.delete(ID);

		verify(momentDayRepository, times(1)).deleteById(anyLong());
	}
}
