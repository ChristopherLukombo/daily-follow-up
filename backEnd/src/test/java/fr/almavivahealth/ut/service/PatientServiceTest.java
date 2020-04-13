package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.mock.web.MockMultipartFile;

import fr.almavivahealth.dao.AllergyRepository;
import fr.almavivahealth.dao.DietRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.dao.RoomRepository;
import fr.almavivahealth.dao.TextureRepository;
import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.domain.Texture;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.impl.PatientServiceImpl;
import fr.almavivahealth.service.mapper.PatientMapper;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {
	
	private static final String EMAIL = "ben.zotito@gmail.com";

	private static final long ID = 1L;
	
	private static final String LASTNAME = "Zotito";

	private static final String TEXTURE_NAME = "Sel";

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private PatientMapper patientMapper;
	
	@Mock
	private TextureRepository textureRepository;

	@Mock
	private DietRepository dietRepository;

	@Mock
	private AllergyRepository allergyRepository;

	@Mock
	private RoomRepository roomRepository;
	
	@InjectMocks
	private PatientServiceImpl patientServiceImpl;
	
	private static Patient getPatient() {
		return Patient.builder()
				.id(ID)
				.firstName("Ben")
				.lastName(LASTNAME)
				.email(EMAIL)
				.state(true)
				.texture(getTexture())
				.build();
	}
	
	private static PatientDTO getPatientDTO() {
		return PatientDTO.builder()
				.id(ID)
				.state(true)
				.build();
	}
	
	private static Texture getTexture() {
		return Texture.builder()
				.id(ID)
				.name(TEXTURE_NAME)
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
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);
		
		// Then
		assertThat(patientServiceImpl.findAll()).isNotEmpty();
	}
	
	@Test
	public void shouldGetAllPatientsWhenIsEmpty() {
		// Given
		final List<Patient> patients = Collections.emptyList();
		
		// Then
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);
		
		// Then
		assertThat(patientServiceImpl.findAll()).isEmpty();
	}
	
	@Test
	public void shouldGetAllPatientsWhenIsNull() {
		// Given
		final List<Patient> patients = null;
		
		// Then
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);
		
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
	
	@Test
	public void shouldImportPatientsWhenIsOk() throws DailyFollowUpException {
		// Given
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" + 
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220;67 avenue du pdt,montreuil,93100";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", data.getBytes());
		final Texture texture = new Texture();
		final List<Patient> patients = Arrays.asList(getPatient());
		final PatientDTO patientDTO = getPatientDTO();
		
		// When
        when(textureRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.ofNullable(texture));
        when(dietRepository.findAllByNameIgnoreCaseIn(anySet())).thenReturn(Collections.emptyList());
        when(allergyRepository.findAllByNameIgnoreCaseIn(anySet())).thenReturn(Collections.emptyList());
        when(roomRepository.findByNumberIgnoreCase(anyString())).thenReturn(Optional.ofNullable(null));
        when(patientRepository.saveAll(anyIterable())).thenReturn(patients);
        when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);
        
		// Then
		assertThat(patientServiceImpl.importPatientFile(file)).isNotEmpty();
	}
	
	@Test
	public void shouldImportPatientsWhenColumnAddressExceed3Columns() throws DailyFollowUpException {
		// Given
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" + 
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220;67 avenue du pdt,montreuil,93100,dede";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", data.getBytes());
		final Texture texture = new Texture();
		
		// When
        when(textureRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.ofNullable(texture));
        when(dietRepository.findAllByNameIgnoreCaseIn(anySet())).thenReturn(Collections.emptyList());
        when(allergyRepository.findAllByNameIgnoreCaseIn(anySet())).thenReturn(Collections.emptyList());
        when(roomRepository.findByNumberIgnoreCase(anyString())).thenReturn(Optional.ofNullable(null));
        
		// Then
    	assertThatThrownBy(() -> patientServiceImpl.importPatientFile(file))
		.isInstanceOf(IndexOutOfBoundsException.class);
	}
	
	@Test
	public void shouldImportPatientsWhenOneLineInCsvDoesNotHaveCorrectNumber() throws DailyFollowUpException {
		// Given
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" + 
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220;67 avenue du pdt,montreuil,93100;bibi";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", data.getBytes());
        
		// Then
        assertThatThrownBy(() -> patientServiceImpl.importPatientFile(file))
		.isInstanceOf(DailyFollowUpException.class);
	}
	
	@Test
	public void shouldImportPatientsWhenIsOkWithNoAdress() throws DailyFollowUpException {
		// Given
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" + 
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220; ";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", data.getBytes());
		final Texture texture = new Texture();
		final List<Patient> patients = Arrays.asList(getPatient());
		final PatientDTO patientDTO = getPatientDTO();
		
		// When
        when(textureRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.ofNullable(texture));
        when(dietRepository.findAllByNameIgnoreCaseIn(anySet())).thenReturn(Collections.emptyList());
        when(allergyRepository.findAllByNameIgnoreCaseIn(anySet())).thenReturn(Collections.emptyList());
        when(roomRepository.findByNumberIgnoreCase(anyString())).thenReturn(Optional.ofNullable(null));
        when(patientRepository.saveAll(anyIterable())).thenReturn(patients);
        when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);
        
		// Then
		assertThat(patientServiceImpl.importPatientFile(file)).isNotEmpty();
	}
}
