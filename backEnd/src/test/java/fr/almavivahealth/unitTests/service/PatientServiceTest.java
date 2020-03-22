package fr.almavivahealth.unitTests.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.domain.Role;
import fr.almavivahealth.domain.Texture;
import fr.almavivahealth.domain.User;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.impl.PatientServiceImpl;
import fr.almavivahealth.service.mapper.PatientMapper;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {
	
	private static final String EMAIL = "ben.zotito@gmail.com";

	private static final String PASSWD = "13kjjl";

	private static final String PSEUDO = "Ben_93";

	private static final long ID = 1L;
	
	private static final String LASTNAME = "Zotito";

	private static final String TEXTURE_NAME = "Sel";

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private PatientMapper patientMapper;
	
	@InjectMocks
	private PatientServiceImpl patientServiceImpl;
	
	private static Patient getPatient() {
		return Patient.builder()
				.id(ID)
				.state(true)
				.texture(getTexture())
				.user(getUser())
				.build();
	}
	
	private static PatientDTO getPatientDTO() {
		return PatientDTO.builder()
				.id(ID)
				.state(true)
				.textureId(ID)
				.userId(ID)
				.build();
	}
	
	private static Texture getTexture() {
		return Texture.builder()
				.id(ID)
				.name(TEXTURE_NAME)
				.build();
	}
	
	private static Role getRole() {
		return Role.builder()
				.id(ID)
				.name("ROLE_ADMIN")
				.build();
	}
	 
	private static User getUser() {
		return User.builder()
				.id(ID)
				.pseudo(PSEUDO)
				.password(PASSWD)
				.firstName("Ben")
				.lastName(LASTNAME)
				.email(EMAIL)
				.createDate(LocalDate.now())
				.status(true)
				.imageUrl(null)
				.birthDay(LocalDate.now())
				.role(getRole())
				.build();
	}
	
	@Test
	public void shouldSavePatientWhenIsOk() {
		// Given
		final Patient patient = getPatient();
		final PatientDTO patientDTO = getPatientDTO();

		// When
		when(patientRepository.save((Patient) any())).thenReturn(patient);
		when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);
		
		// Then
		assertThat(patientServiceImpl.save(patientDTO)).isNotNull();
	}
	
	@Test
	public void shouldSavePatientWhenIsKO() {
		// Given
		final Patient patient = null;
		final PatientDTO patientDTO = null;

		// When
		when(patientRepository.save((Patient) any())).thenReturn(patient);
		when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);

		// Then
		assertThat(patientServiceImpl.save(patientDTO)).isNull();
	}
	
	@Test
	public void shouldUpdatePatientWhenIsOk() {
		// Given
		final Patient patient = getPatient();
		final PatientDTO patientDTO = getPatientDTO();

		// When
		when(patientRepository.saveAndFlush((Patient) any())).thenReturn(patient);
		when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);
		
		// Then
		assertThat(patientServiceImpl.update(patientDTO)).isNotNull();
	}
	
	@Test
	public void shouldUpdatePatientWhenIsKO() {
		// Given
		final Patient patient = null;
		final PatientDTO patientDTO = null;

		// When
		when(patientRepository.saveAndFlush((Patient) any())).thenReturn(patient);
		when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);

		// Then
		assertThat(patientServiceImpl.update(patientDTO)).isNull();
	}
	
	@Test
	public void shouldGetAllPatientsWhenIsOk() {
		// Given
		final List<Patient> patients = Arrays.asList(getPatient());
		
		// Then
		when(patientRepository.findAll()).thenReturn(patients);
		
		// Then
		assertThat(patientServiceImpl.findAll()).isNotEmpty();
	}
	
	@Test
	public void shouldGetAllPatientsWhenIsEmpty() {
		// Given
		final List<Patient> patients = Collections.emptyList();
		
		// Then
		when(patientRepository.findAll()).thenReturn(patients);
		
		// Then
		assertThat(patientServiceImpl.findAll()).isEmpty();
	}
	
	@Test
	public void shouldGetAllPatientsWhenIsNull() {
		// Given
		final List<Patient> patients = null;
		
		// Then
		when(patientRepository.findAll()).thenReturn(patients);
		
		// Then
		assertThatThrownBy(() -> patientServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetPatientWhenIsOk() {
		// Given
		final Patient patient = getPatient();
		final PatientDTO patientDTO = getPatientDTO();

		// When
		when(patientRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(patient));
		when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);
		
		// Then
		assertThat(patientServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(patientDTO));
	}
	
	@Test
	public void shouldGetPatientWhenIsNull() {
		// Given
		final Patient patient = null;
		final PatientDTO patientDTO = null;

		// When
		when(patientRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(patient));
		
		// Then
		assertThat(patientServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(patientDTO));
	}
	
	@Test
	public void shouldDeleteWhenIsOk() {
		// Given
		final Patient patient = getPatient();
		
		// When
		when(patientRepository.findById(anyLong())).thenReturn(Optional.ofNullable(patient));
		when(patientRepository.saveAndFlush((Patient) any())).thenReturn(patient);

		// Then
		patientServiceImpl.delete(ID);
		
		verify(patientRepository, times(1)).findById(anyLong());
		verify(patientRepository, times(1)).saveAndFlush((Patient) any());
	}
}
