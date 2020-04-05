package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.PatientService;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.PatientResource;

@RunWith(MockitoJUnitRunner.class)
public class PatientResourceTest {

	private static final String EMAIL = "ben.zotito@gmail.com";

	private static final long ID = 1L;
	
	private static final String LASTNAME = "Zotito";
	
	private MockMvc mockMvc;
	
	@Mock
	private PatientService patientService;
	
	@InjectMocks
	private PatientResource patientResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(patientResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}
	
	private static PatientDTO createPatientDTO() {
		return PatientDTO.builder()
				.id(ID)
				.firstName("Ben")
				.lastName(LASTNAME)
				.email(EMAIL)
				.state(true)
				.build();
	}

	@Test
	public void shouldCreatePatientWhenIsOk() throws IOException, Exception {
		// Given
		final PatientDTO patientDTO = createPatientDTO();
		patientDTO.setId(null);

		// When
		when(patientService.save((PatientDTO) any())).thenReturn(patientDTO);

		// Then
		mockMvc.perform(post("/api/patients")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(patientDTO)))
		.andExpect(status().isCreated());
		verify(patientService, times(1)).save(patientDTO);
	}

	@Test
	public void shouldCreatePatientWhenIsKo() throws IOException, Exception {
		// Given
		final PatientDTO patientDTO = createPatientDTO();

		// Then
		mockMvc.perform(post("/api/patients")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(patientDTO)))
		.andExpect(status().isBadRequest());
		verify(patientService, times(0)).save(patientDTO);
	}
	
	@Test
	public void shouldCreatePatientWhenIsNotValid() throws IOException, Exception {
		// Given
		final PatientDTO patientDTO = createPatientDTO();
		patientDTO.setId(null);
		patientDTO.setEmail("dsds");

		// Then
		mockMvc.perform(post("/api/patients")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(patientDTO)))
		.andExpect(status().isUnprocessableEntity());
		verify(patientService, times(0)).save(patientDTO);
	}

	@Test
	public void shouldUpdatePatientWhenIsOk() throws IOException, Exception {
		// Given
		final PatientDTO patientDTO = createPatientDTO();

		// When
		when(patientService.update((PatientDTO) any())).thenReturn(patientDTO);

		// Then
		mockMvc.perform(put("/api/patients")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(patientDTO)))
		.andExpect(status().isOk());
		verify(patientService, times(1)).update(patientDTO);
	}

	@Test
	public void shouldUpdatePatientWhenIsKo() throws IOException, Exception {
		// Given
		final PatientDTO patientDTO = createPatientDTO();
		patientDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/patients")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(patientDTO)))
		.andExpect(status().isBadRequest());
		verify(patientService, times(0)).update(patientDTO);
	}

	@Test
	public void shouldGetAllPatientsWhenIsOk() throws IOException, Exception {
		// Given
		final List<PatientDTO> patientsDTO = Arrays.asList(createPatientDTO());

		// When
		when(patientService.findAll()).thenReturn(patientsDTO);

		// Then
		mockMvc.perform(get("/api/patients")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(patientService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllPatientsWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<PatientDTO> patientsDTO =  Collections.emptyList();

		// When
		when(patientService.findAll()).thenReturn(patientsDTO);

		// Then
		mockMvc.perform(get("/api/patients")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(patientService, times(1)).findAll();
	}

	@Test
	public void shouldGetPatientWhenIsOk() throws IOException, Exception {
		// Given
		final PatientDTO patientDTO = createPatientDTO();

		// When
		when(patientService.findOne(anyLong())).thenReturn(Optional.ofNullable(patientDTO));

		// Then
		mockMvc.perform(get("/api/patients/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(patientDTO)))
		.andExpect(status().isOk());
		verify(patientService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetPatientWhenIsNotFOund() throws IOException, Exception {
		// Given
		final PatientDTO patientDTO = null;

		// When
		when(patientService.findOne(anyLong())).thenReturn(Optional.ofNullable(patientDTO));

		// Then
		mockMvc.perform(get("/api/patients/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(patientDTO)))
		.andExpect(status().isNoContent());
		verify(patientService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeletePatientWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(patientService).delete(id);

		// Then
		mockMvc.perform(delete("/api/patients/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(patientService, times(1)).delete(id);
	}
	
	@Test
	public void shouldImportPatientsWhenIsOk() throws IOException, Exception {
		// Given
		final List<PatientDTO> patientsDTO = Arrays.asList(createPatientDTO());
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" + 
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220;67 avenue du pdt,montreuil,93100;bibi";
		final MockMultipartFile file = new MockMultipartFile("filename", "filename.csv", "application/vnd.ms-excel", data.getBytes());
		final MockPart part = new MockPart("inputfile", "filename.csv", file.getBytes());

		// When
		when(patientService.importPatientFile((MultipartFile) any())).thenReturn(patientsDTO);

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/patients/import")
				.part(part))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@Test
	public void shouldImportPatientsWhenIsNotCSV() throws IOException, Exception {
		// Given
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" + 
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220;67 avenue du pdt,montreuil,93100;bibi";
		final MockMultipartFile file = new MockMultipartFile("filename", "filename.csv", "application/vnd.ms-excel", data.getBytes());
		final MockPart part = new MockPart("inputfile", "filename.pdf", file.getBytes());

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/patients/import")
				.part(part))
		        .andExpect(status().isInternalServerError());
	}
	
	@Test
	public void shouldImportPatientsWhenThereIsInternalServerError() throws IOException, Exception {
		// Given
		final String data = "first_name;last_name;email;situation;date_of_birth;phone_number;mobile_phone;job;blood_group;height;weight;sex;state;texture;diets;allergyes;room;addresse\r\n" + 
				"Valérie;BORDIN;bordin.v@gmail.com;Marié;13/02/1974;0126642363;0652148965;Retraité;B+;163;51.1;Femme;true;Normale;Normale;Céréales,Gluten;220;67 avenue du pdt,montreuil,93100;bibi";
		final MockMultipartFile file = new MockMultipartFile("filename", "filename.csv", "application/vnd.ms-excel", data.getBytes());
		final MockPart part = new MockPart("inputfile", "filename.csv", file.getBytes());

		// When
		when(patientService.importPatientFile((MultipartFile) any())).thenThrow(DailyFollowUpException.class);

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/patients/import")
				.part(part))
		        .andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$").isNotEmpty());
	}
}
