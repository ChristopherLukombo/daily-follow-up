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

import fr.almavivahealth.dao.FloorRepository;
import fr.almavivahealth.domain.entity.Floor;
import fr.almavivahealth.service.dto.FloorDTO;
import fr.almavivahealth.service.impl.FloorServiceImpl;
import fr.almavivahealth.service.mapper.FloorMapper;

@RunWith(MockitoJUnitRunner.class)
public class FloorServiceTest {

	private static final long ID = 1L;

	@Mock
	private FloorRepository floorRepository;
	
	@Mock
	private FloorMapper floorMapper;
	
	@InjectMocks
	private FloorServiceImpl floorServiceImpl;
	
	private static Floor createFloor() {
		return Floor.builder()
				.id(1L)
				.build();
	}
	
	private static FloorDTO createFloorDTO() {
		return FloorDTO.builder()
				.id(ID)
				.build();
	}
	
    @Test
    public void shouldSaveFloorWhenIsOk() {
    	// Given
    	final Floor floor = createFloor();
    	final FloorDTO floorDTO = createFloorDTO();
    	
    	// When
    	when(floorRepository.save((Floor) any())).thenReturn(floor);
    	when(floorMapper.floorToFloorDTO((Floor) any())).thenReturn(floorDTO);
    	
    	// Then
    	assertThat(floorServiceImpl.save(floorDTO)).isEqualTo(floorDTO);
    }
	
    @Test
    public void shouldSaveFloorWhenIsKo() {
    	// Given
    	final Floor floor = null;
    	final FloorDTO floorDTO = null;
    	
    	// When
    	when(floorRepository.save((Floor) any())).thenReturn(floor);
    	when(floorMapper.floorToFloorDTO((Floor) any())).thenReturn(floorDTO);
    	
    	// Then
    	assertThat(floorServiceImpl.save(floorDTO)).isEqualTo(floorDTO);
    }
    
    @Test
    public void shouldUpdateFloorWhenIsOk() {
    	// Given
    	final Floor floor = createFloor();
    	final FloorDTO floorDTO = createFloorDTO();
    	
    	// When
    	when(floorRepository.saveAndFlush((Floor) any())).thenReturn(floor);
    	when(floorMapper.floorToFloorDTO((Floor) any())).thenReturn(floorDTO);
    	
    	// Then
    	assertThat(floorServiceImpl.update(floorDTO)).isEqualTo(floorDTO);
    }
	
    @Test
    public void shouldUpdateFloorWhenIsKo() {
    	// Given
    	final Floor floor = null;
    	final FloorDTO floorDTO = null;
    	
    	// When
    	when(floorRepository.saveAndFlush((Floor) any())).thenReturn(floor);
    	when(floorMapper.floorToFloorDTO((Floor) any())).thenReturn(floorDTO);
    	
    	// Then
    	assertThat(floorServiceImpl.update(floorDTO)).isEqualTo(floorDTO);
    }

    @Test
	public void shouldGetAllFloorsWhenIsOk() {
		// Given
		final List<Floor> floors = Arrays.asList(createFloor());
		
		// Then
		when(floorRepository.findAllByOrderByIdDesc()).thenReturn(floors);
		
		// Then
		assertThat(floorServiceImpl.findAll()).isNotEmpty();
	}
	
	@Test
	public void shouldGetAllFloorsWhenIsEmpty() {
		// Given
		final List<Floor> floors = Collections.emptyList();
		
		// Then
		when(floorRepository.findAllByOrderByIdDesc()).thenReturn(floors);
		
		// Then
		assertThat(floorServiceImpl.findAll()).isEmpty();
	}
	
	@Test
	public void shouldGetAllFloorsWhenIsNull() {
		// Given
		final List<Floor> floors = null;
		
		// Then
		when(floorRepository.findAllByOrderByIdDesc()).thenReturn(floors);
		
		// Then
		assertThatThrownBy(() -> floorServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetFloorWhenIsOk() {
		// Given
		final Floor floor = createFloor();
		final FloorDTO floorDTO = createFloorDTO();

		// When
		when(floorRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(floor));
		when(floorMapper.floorToFloorDTO((Floor) any())).thenReturn(floorDTO);
		
		// Then
		assertThat(floorServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(floorDTO));
	}
	
	@Test
	public void shouldGetFloorWhenIsNull() {
		// Given
		final Floor floor = null;
		final FloorDTO floorDTO = null;

		// When
		when(floorRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(floor));
		
		// Then
		assertThat(floorServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(floorDTO));
	}
	
	@Test
	public void shouldDeleteFloorWhenIsOk() {
		// When
		doNothing().when(floorRepository).deleteById(ID);

		// Then
		floorServiceImpl.delete(ID);
		
		verify(floorRepository, times(1)).deleteById(anyLong());
	}
}
