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

import fr.almavivahealth.dao.AllergyRepository;
import fr.almavivahealth.domain.entity.Allergy;
import fr.almavivahealth.service.dto.AllergyDTO;
import fr.almavivahealth.service.impl.allergy.AllergyServiceImpl;
import fr.almavivahealth.service.mapper.AllergyMapper;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AllergyServiceTest {

	private static final long ID = 1L;

    private static final String NAME = "TEST";

	@Mock
    private AllergyRepository allergyRepository;

    @Mock
    private AllergyMapper allergyMapper;

    @InjectMocks
    private AllergyServiceImpl allergyServiceImpl;

    private Allergy createAllergy() {
    	return Allergy.builder()
    			.id(ID)
    			.name(NAME)
    			.build();
    }

    private AllergyDTO createAllergyDTO() {
    	return AllergyDTO.builder()
    			.id(ID)
    			.name(NAME)
    			.build();
    }

    @Test
    public void shouldSaveAllergyWhenIsOk() {
    	// Given
    	final Allergy allergy = createAllergy();
    	final AllergyDTO allergyDTO = createAllergyDTO();

    	// When
		when(allergyRepository.save((Allergy) any())).thenReturn(allergy);
		when(allergyMapper.allergyToAllergyDTO((Allergy) any())).thenReturn(allergyDTO);

    	// Then
    	assertThat(allergyServiceImpl.save(allergyDTO)).isEqualTo(allergyDTO);
    }

    @Test
    public void shouldSaveAllergyWhenIsKo() {
    	// Given
    	final Allergy allergy = null;
    	final AllergyDTO allergyDTO = null;

    	// When
		when(allergyRepository.save((Allergy) any())).thenReturn(allergy);
		when(allergyMapper.allergyToAllergyDTO((Allergy) any())).thenReturn(allergyDTO);

    	// Then
    	assertThat(allergyServiceImpl.save(allergyDTO)).isEqualTo(allergyDTO);
    }

    @Test
    public void shouldUpdateAllergyWhenIsOk() {
    	// Given
    	final Allergy allergy = createAllergy();
    	final AllergyDTO allergyDTO = createAllergyDTO();

    	// When
		when(allergyRepository.saveAndFlush((Allergy) any())).thenReturn(allergy);
		when(allergyMapper.allergyToAllergyDTO((Allergy) any())).thenReturn(allergyDTO);

    	// Then
    	assertThat(allergyServiceImpl.update(allergyDTO)).isEqualTo(allergyDTO);
    }

    @Test
    public void shouldUpdateAllergyWhenIsKo() {
    	// Given
    	final Allergy allergy = null;
    	final AllergyDTO allergyDTO = null;

    	// When
		when(allergyRepository.saveAndFlush((Allergy) any())).thenReturn(allergy);
		when(allergyMapper.allergyToAllergyDTO((Allergy) any())).thenReturn(allergyDTO);

    	// Then
    	assertThat(allergyServiceImpl.update(allergyDTO)).isEqualTo(allergyDTO);
    }

    @Test
   	public void shouldGetAllAllergyWhenIsOk() {
   		// Given
   		final List<Allergy> allergys = Arrays.asList(createAllergy());

   		// Then
   		when(allergyRepository.findAllByOrderByIdDesc()).thenReturn(allergys);

   		// Then
   		assertThat(allergyServiceImpl.findAll()).isNotEmpty();
   	}

   	@Test
   	public void shouldGetAllAllergysWhenIsEmpty() {
   		// Given
   		final List<Allergy> allergys = Collections.emptyList();

   		// Then
   		when(allergyRepository.findAllByOrderByIdDesc()).thenReturn(allergys);

   		// Then
   		assertThat(allergyServiceImpl.findAll()).isEmpty();
   	}

   	@Test
   	public void shouldGetAllAllergysWhenIsNull() {
   		// Given
   		final List<Allergy> allergys = null;

   		// Then
   		when(allergyRepository.findAllByOrderByIdDesc()).thenReturn(allergys);

   		// Then
   		assertThatThrownBy(() -> allergyServiceImpl.findAll())
   		.isInstanceOf(NullPointerException.class);
   	}

   	@Test
	public void shouldGetAllergyWhenIsOk() {
		// Given
		final Allergy allergy = createAllergy();
		final AllergyDTO allergyDTO = createAllergyDTO();

		// When
		when(allergyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(allergy));
		when(allergyMapper.allergyToAllergyDTO((Allergy) any())).thenReturn(allergyDTO);

		// Then
		assertThat(allergyServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(allergyDTO));
	}

	@Test
	public void shouldGetAllergyWhenIsNull() {
		// Given
		final Allergy allergy = null;
		final AllergyDTO allergyDTO = null;

		// When
		when(allergyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(allergy));

		// Then
		assertThat(allergyServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(allergyDTO));
	}

	@Test
	public void shouldDeleteAllergyWhenIsOk() {
		// When
		doNothing().when(allergyRepository).deleteById(ID);

		// Then
		allergyServiceImpl.delete(ID);

		verify(allergyRepository, times(1)).deleteById(anyLong());
	}
}
