package fr.almavivahealth.unitTests.service;
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

import fr.almavivahealth.dao.CaregiverRepository;
import fr.almavivahealth.domain.Caregiver;
import fr.almavivahealth.service.dto.CaregiverDTO;
import fr.almavivahealth.service.impl.CaregiverServiceImpl;
import fr.almavivahealth.service.mapper.CaregiverMapper;

@RunWith(MockitoJUnitRunner.class)
public class CaregiverServiceTest {

	private static final long ID = 1L;

	@Mock
	private CaregiverRepository caregiverRepository;
	
	@Mock
	private CaregiverMapper caregiverMapper;
	
	@InjectMocks
	private CaregiverServiceImpl caregiverServiceImpl;
	
	private static Caregiver createCaregiver() {
		return Caregiver.builder()
				.id(ID)
				.user(null)
				.floor(null)
				.build();
				
	}
	
	private static CaregiverDTO createCaregiverDTO() {
		return CaregiverDTO.builder()
				.id(ID)
				.userId(null)
				.floorId(null)
				.build();
	}
	
    @Test
    public void shouldSaveCaregiverWhenIsOk() {
    	// Given
    	final Caregiver caregiver = createCaregiver();
    	final CaregiverDTO caregiverDTO = createCaregiverDTO();
    	
    	// When
    	when(caregiverRepository.save((Caregiver) any())).thenReturn(caregiver);
    	when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);
    	
    	// Then
    	assertThat(caregiverServiceImpl.save(caregiverDTO)).isEqualTo(caregiverDTO);
    }

    @Test
    public void shouldSaveCaregiverWhenIsKo() {
    	// Given
    	final Caregiver caregiver = null;
    	final CaregiverDTO caregiverDTO = null;
    	
    	// When
    	when(caregiverRepository.save((Caregiver) any())).thenReturn(caregiver);
    	when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);
    	
    	// Then
    	assertThat(caregiverServiceImpl.save(caregiverDTO)).isEqualTo(caregiverDTO);
    }

    @Test
    public void shouldUpdateCaregiverWhenIsOk() {
    	// Given
    	final Caregiver caregiver = createCaregiver();
    	final CaregiverDTO caregiverDTO = createCaregiverDTO();
    	
    	// When
    	when(caregiverRepository.saveAndFlush((Caregiver) any())).thenReturn(caregiver);
    	when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);
    	
    	// Then
    	assertThat(caregiverServiceImpl.update(caregiverDTO)).isEqualTo(caregiverDTO);
    }

    @Test
    public void shouldUpdateCaregiverWhenIsKo() {
    	// Given
    	final Caregiver caregiver = null;
    	final CaregiverDTO caregiverDTO = null;
    	
    	// When
    	when(caregiverRepository.saveAndFlush((Caregiver) any())).thenReturn(caregiver);
    	when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);
    	
    	// Then
    	assertThat(caregiverServiceImpl.update(caregiverDTO)).isEqualTo(caregiverDTO);
    }
    
    @Test
	public void shouldGetAllCaregiversWhenIsOk() {
		// Given
		final List<Caregiver> caregivers = Arrays.asList(createCaregiver());
		
		// Then
		when(caregiverRepository.findAll()).thenReturn(caregivers);
		
		// Then
		assertThat(caregiverServiceImpl.findAll()).isNotEmpty();
	}
	
	@Test
	public void shouldGetAllCaregiversWhenIsEmpty() {
		// Given
		final List<Caregiver> caregivers = Collections.emptyList();
		
		// Then
		when(caregiverRepository.findAll()).thenReturn(caregivers);
		
		// Then
		assertThat(caregiverServiceImpl.findAll()).isEmpty();
	}
	
	@Test
	public void shouldGetAllCaregiversWhenIsNull() {
		// Given
		final List<Caregiver> caregivers = null;
		
		// Then
		when(caregiverRepository.findAll()).thenReturn(caregivers);
		
		// Then
		assertThatThrownBy(() -> caregiverServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetCaregiverWhenIsOk() {
		// Given
		final Caregiver caregiver = createCaregiver();
		final CaregiverDTO caregiverDTO = createCaregiverDTO();

		// When
		when(caregiverRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(caregiver));
		when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);
		
		// Then
		assertThat(caregiverServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(caregiverDTO));
	}
	
	@Test
	public void shouldGetCaregiverWhenIsNull() {
		// Given
		final Caregiver caregiver = null;
		final CaregiverDTO caregiverDTO = null;

		// When
		when(caregiverRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(caregiver));
		
		// Then
		assertThat(caregiverServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(caregiverDTO));
	}
	
	@Test
	public void shouldDeleteCaregiverWhenIsOk() {
		// When
		doNothing().when(caregiverRepository).deleteById(ID);

		// Then
		caregiverServiceImpl.delete(ID);
		
		verify(caregiverRepository, times(1)).deleteById(anyLong());
	}
}
