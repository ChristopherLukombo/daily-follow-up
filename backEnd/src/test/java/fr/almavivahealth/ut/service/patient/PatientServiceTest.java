package fr.almavivahealth.ut.service.patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;

import fr.almavivahealth.dao.AllergyRepository;
import fr.almavivahealth.dao.DietRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.dao.RoomRepository;
import fr.almavivahealth.dao.TextureRepository;
import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.PatientImportationAttempts;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.impl.patient.PatientServiceImpl;
import fr.almavivahealth.service.mapper.PatientMapper;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

	private static final int NUMBER = 1;

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

	@Mock
	private PatientImportationAttempts patientImportationAttempts;

	@Mock
	private MessageSource messageSource;

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
	public void shouldGetAllActivePatientsWhenIsOk() {
		// Given
		final List<Patient> patients = Arrays.asList(getPatient());

		// Then
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);

		// Then
		assertThat(patientServiceImpl.findAllActivePatients()).isNotEmpty();
	}

	@Test
	public void shouldGetAllActivePatientsWhenIsEmpty() {
		// Given
		final List<Patient> patients = Collections.emptyList();

		// Then
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);

		// Then
		assertThat(patientServiceImpl.findAllActivePatients()).isEmpty();
	}

	@Test
	public void shouldGetAllActivePatientsWhenIsNull() {
		// Given
		final List<Patient> patients = null;

		// Then
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);

		// Then
		assertThatThrownBy(() -> patientServiceImpl.findAllActivePatients())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetAllFormerPatientsWhenIsOk() {
		// Given
		final List<Patient> patients = Arrays.asList(getPatient());

		// Then
		when(patientRepository.findAllByStateFalseOrderByIdDesc()).thenReturn(patients);

		// Then
		assertThat(patientServiceImpl.findAllFormerPatients()).isNotEmpty();
	}

	@Test
	public void shouldGetAllFormerPatientsWhenIsEmpty() {
		// Given
		final List<Patient> patients = Collections.emptyList();

		// Then
		when(patientRepository.findAllByStateFalseOrderByIdDesc()).thenReturn(patients);

		// Then
		assertThat(patientServiceImpl.findAllFormerPatients()).isEmpty();
	}

	@Test
	public void shouldGetAllFormerPatientsWhenIsNull() {
		// Given
		final List<Patient> patients = null;

		// Then
		when(patientRepository.findAllByStateFalseOrderByIdDesc()).thenReturn(patients);

		// Then
		assertThatThrownBy(() -> patientServiceImpl.findAllFormerPatients())
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
		final String data = "Prénom;Nom;Sexe;Texture des plats;Alimentation du patient;Allergies;Numéro de chambre\\r\\n\" +\r\n" +
				"				\"bibi;BOccerdedtrRDIN;Homme;Normale;Normale;Chocolat;220\\r\\n\" +\r\n" +
				"				\"Gertrude;Pingo;Homme;Normale;Normale;Chocolat;22";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", data.getBytes());
		final Texture texture = new Texture();
		final List<Patient> patients = Arrays.asList(getPatient());
		final PatientDTO patientDTO = getPatientDTO();
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(HttpHeaders.HOST, "myhost.com");
		request.setLocalPort(8081);
		request.setRemoteAddr("127.0.0.1");

		// When
		when(textureRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.ofNullable(texture));
		when(dietRepository.findAllByNameIgnoreCaseIn(anySet())).thenReturn(Collections.emptyList());
		when(roomRepository.findByNumberIgnoreCase(anyString())).thenReturn(Optional.ofNullable(null));
		when(patientRepository.saveAll(anyIterable())).thenReturn(patients);
		when(patientMapper.patientToPatientDTO((Patient) any())).thenReturn(patientDTO);

		// Then
		assertThat(patientServiceImpl.importPatientFile(file, request)).isNotNull();
	}

	@Test
	public void shouldImportPatientsWhenOneLineInCsvDoesNotHaveCorrectNumber() throws DailyFollowUpException {
		// Given
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" +
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220;67 avenue du pdt,montreuil,93100;bibi";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", data.getBytes());
		final MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(HttpHeaders.HOST, "myhost.com");
		request.setLocalPort(8081);
		request.setRemoteAddr("127.0.0.1");

		// Then
        assertThatThrownBy(() -> patientServiceImpl.importPatientFile(file, request))
		.isInstanceOf(DailyFollowUpException.class);
	}

	@Test
	public void shouldIsCSVWhenIsOkForTextPlain() {
		// Given
		final String data = "test";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/plain", data.getBytes());

		// Then
		assertThat(patientServiceImpl.isCSV(file)).isTrue();
	}

	@Test
	public void shouldIsCSVWhenIsOkForCsv() {
		// Given
		final String data = "test";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", data.getBytes());

		// Then
		assertThat(patientServiceImpl.isCSV(file)).isTrue();
	}

	@Test
	public void shouldIsCSVWhenIsOkForVndmsExcel() {
		// Given
		final String data = "test";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "application/vnd.ms-excel", data.getBytes());

		// Then
		assertThat(patientServiceImpl.isCSV(file)).isTrue();
	}

	@Test
	public void shouldReturnFalseWhenCSVHasNotValidMimeType() {
		// Given
		final String data = "test";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "image/bmp", data.getBytes());

		// Then
		assertThat(patientServiceImpl.isCSV(file)).isFalse();
	}

	@Test
	public void shouldReturnFalseWhenCSVHasNotValidExtension() {
		// Given
		final String data = "test";
		final MockMultipartFile file = new MockMultipartFile("file", "filename.cs", "application/vnd.ms-excel", data.getBytes());

		// Then
		assertThat(patientServiceImpl.isCSV(file)).isFalse();
	}

	@Test
	public void shouldReactivatePatientWhenPatientExist() {
		// Given
		final Patient patient = getPatient();

		// When
		when(patientRepository.findById(anyLong())).thenReturn(Optional.ofNullable(patient));
		when(patientRepository.saveAndFlush((Patient) any())).thenReturn(patient);

		// Then
		patientServiceImpl.reactivatePatient(ID);

		verify(patientRepository, times(1)).findById(anyLong());
		verify(patientRepository, times(1)).saveAndFlush((Patient) any());
	}

	@Test
	public void shouldNotReactivatePatientWhenPatientNotExist() {
		// Given
		final Patient patient = null;

		// When
		when(patientRepository.findById(anyLong())).thenReturn(Optional.ofNullable(patient));

		// Then
		patientServiceImpl.reactivatePatient(ID);

		verify(patientRepository, times(1)).findById(anyLong());
		verify(patientRepository, times(0)).saveAndFlush((Patient) any());
	}

	@Test
	public void shouldFindAllByFloorNumber() {
		// Given
		final List<Patient> patients = Arrays.asList(getPatient());

		// Then
		when(patientRepository.findAllByFloorNumber(anyInt())).thenReturn(patients);

		// Then
		assertThat(patientServiceImpl.findAllByFloorNumber(NUMBER)).isNotEmpty();
	}

	@Test
	public void shouldReturnsEmptyListWhenTryingToFindAllByFloorNumber() {
		// Given
		final List<Patient> patients = Collections.emptyList();

		// Then
		when(patientRepository.findAllByFloorNumber(anyInt())).thenReturn(patients);

		// Then
		assertThat(patientServiceImpl.findAllByFloorNumber(NUMBER)).isEmpty();
	}

	@Test
	public void shouldThrowWhenTryingToFindAllByFloorNumber() {
		// Given
		final List<Patient> patients = null;

		// Then
		when(patientRepository.findAllByFloorNumber(anyInt())).thenReturn(patients);

		// Then
		assertThatThrownBy(() -> patientServiceImpl.findAllByFloorNumber(NUMBER))
		.isInstanceOf(NullPointerException.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldChangeRooms() {
		// Given
		final Patient firstPatient = getPatient();
		final Patient secondPatient = getPatient();

		// When
		when(patientRepository.findById(anyLong())).thenReturn(Optional.of(firstPatient), Optional.of(secondPatient));

		// Then
		assertThat(patientServiceImpl.changeRooms(1L, 2L)).isTrue();
	}

	@Test
	public void shouldReturnsFalseWhenPatientNotExistInTryingToChangeRooms() {
		// Then
		assertThat(patientServiceImpl.changeRooms(1L, 2L)).isFalse();
	}
}
