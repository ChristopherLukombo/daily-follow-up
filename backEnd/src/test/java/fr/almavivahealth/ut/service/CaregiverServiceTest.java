package fr.almavivahealth.ut.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.almavivahealth.dao.CaregiverRepository;
import fr.almavivahealth.dao.RoleRepository;
import fr.almavivahealth.domain.entity.Caregiver;
import fr.almavivahealth.domain.entity.Role;
import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.CaregiverDTO;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.impl.caregiver.CaregiverServiceImpl;
import fr.almavivahealth.service.mapper.CaregiverMapper;

@RunWith(MockitoJUnitRunner.class)
public class CaregiverServiceTest {

	private static final int NUMBER = 1;

	private static final long ID = 1L;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private CaregiverRepository caregiverRepository;

	@Mock
	private CaregiverMapper caregiverMapper;

	@InjectMocks
	private CaregiverServiceImpl caregiverServiceImpl;

	private static Caregiver createCaregiver() {
		return Caregiver.builder()
				.id(ID)
				.user(createUser())
				.floor(null)
				.build();

	}

	private static User createUser() {
		final User user = new User();
		user.setId(1L);
		user.setPassword("ddsdsd");
		return user;
	}

	private static CaregiverDTO createCaregiverDTO() {
		return CaregiverDTO.builder()
				.id(ID)
				.user(new UserDTO())
				.floorId(null)
				.build();
	}

    @Test
    public void shouldSaveCaregiverWhenIsOk() throws DailyFollowUpException {
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
    public void shouldSaveCaregiverWhenIsKo() throws DailyFollowUpException {
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
    public void shouldUpdateCaregiverWhenIsOk() throws DailyFollowUpException {
    	// Given
    	final Caregiver caregiver = createCaregiver();
    	final CaregiverDTO caregiverDTO = createCaregiverDTO();

    	// When
    	when(passwordEncoder.encode(anyString())).thenReturn("testce");
    	when(roleRepository.findByName(anyString())).thenReturn(Optional.ofNullable(new Role()));
     	when(caregiverMapper.caregiverDTOToCaregiver(any(CaregiverDTO.class))).thenReturn(caregiver);
    	when(caregiverRepository.saveAndFlush((Caregiver) any())).thenReturn(caregiver);
    	when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);

    	// Then
    	assertThat(caregiverServiceImpl.update(caregiverDTO)).isEqualTo(caregiverDTO);
    }

    @Test
    public void shouldUpdateCaregiverWhenIsKo() throws DailyFollowUpException {
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
		when(caregiverRepository.findAllByOrderByIdDesc()).thenReturn(caregivers);

		// Then
		assertThat(caregiverServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllCaregiversWhenIsEmpty() {
		// Given
		final List<Caregiver> caregivers = Collections.emptyList();

		// Then
		when(caregiverRepository.findAllByOrderByIdDesc()).thenReturn(caregivers);

		// Then
		assertThat(caregiverServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllCaregiversWhenIsNull() {
		// Given
		final List<Caregiver> caregivers = null;

		// Then
		when(caregiverRepository.findAllByOrderByIdDesc()).thenReturn(caregivers);

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
		when(caregiverRepository.findById(anyLong())).thenReturn(Optional.ofNullable(caregiver));
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
		when(caregiverRepository.findById(anyLong())).thenReturn(Optional.ofNullable(caregiver));

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

	@Test
	public void shouldFindAllByFloorNumber() {
		// Given
		final List<Caregiver> caregivers = Arrays.asList(createCaregiver());
		final CaregiverDTO caregiverDTO = createCaregiverDTO();

		// Then
		when(caregiverRepository.findAllByFloorNumber(anyInt())).thenReturn(caregivers);
		when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);

		// Then
		assertThat(caregiverServiceImpl.findAllByFloorNumber(NUMBER)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyListWhenTryingFindAllByFloorNumber() {
		// Given
		final List<Caregiver> caregivers = Collections.emptyList();

		// Then
		when(caregiverRepository.findAllByFloorNumber(anyInt())).thenReturn(caregivers);

		// Then
		assertThat(caregiverServiceImpl.findAllByFloorNumber(NUMBER)).isEmpty();
	}

	@Test
	public void shouldFindByUserId() {
		// Given
		final Caregiver caregiver = createCaregiver();
		final CaregiverDTO caregiverDTO = createCaregiverDTO();

		// When
		when(caregiverRepository.findByUserId(anyLong())).thenReturn(Optional.ofNullable(caregiver));
		when(caregiverMapper.caregiverToCaregiverDTO((Caregiver) any())).thenReturn(caregiverDTO);

		// Then
		assertThat(caregiverServiceImpl.findByUserId(ID)).isEqualTo(Optional.ofNullable(caregiverDTO));
	}

	@Test
	public void shouldFindByUserIdWhenIsNull() {
		// Given
		final Caregiver caregiver = null;
		final CaregiverDTO caregiverDTO = null;

		// When
		when(caregiverRepository.findByUserId(anyLong())).thenReturn(Optional.ofNullable(caregiver));

		// Then
		assertThat(caregiverServiceImpl.findByUserId(ID)).isEqualTo(Optional.ofNullable(caregiverDTO));
	}
}
