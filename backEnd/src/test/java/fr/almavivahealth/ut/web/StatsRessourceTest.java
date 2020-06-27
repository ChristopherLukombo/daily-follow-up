package fr.almavivahealth.ut.web;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.almavivahealth.service.StatsService;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.StatsRessource;

@RunWith(MockitoJUnitRunner.class)
public class StatsRessourceTest {

	private static final long TOTAL_PATIENTS = 13L;

	private static final long INACTIVE_PATIENTS = 23L;

	private static final long ACTIVE_PATIENTS = 12L;

	private static final String DIET_NAME = "SANS SEL";

	private static final int PERCENTAGE = 11;

	private static final long NUMBER_PATIENTS = 8L;

	private static final String ALLERGY_NAME = "SOJA";

	private MockMvc mockMvc;

	@Mock
	private StatsService statsService;

	@InjectMocks
	private StatsRessource statsRessource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(statsRessource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static PatientsPerAllergyDTO createPatientsPerAllergyDTO() {
		return PatientsPerAllergyDTO.builder()
				.allergyName(ALLERGY_NAME)
				.numberPatients(NUMBER_PATIENTS)
				.percentage(BigDecimal.valueOf(PERCENTAGE))
				.build();
	}

	private static PatientsPerDietDTO createPatientsPerDietDTO() {
		return PatientsPerDietDTO.builder()
				.dietName(DIET_NAME)
				.numberPatients(NUMBER_PATIENTS)
				.percentage(BigDecimal.valueOf(PERCENTAGE))
				.build();
	}

	private static PatientsByStatusDTO createPatientsByStatusDTO() {
		return PatientsByStatusDTO.builder()
				.activePatients(ACTIVE_PATIENTS)
				.inactivePatients(INACTIVE_PATIENTS)
				.totalPatients(TOTAL_PATIENTS)
				.build();
	}

	@Test
	public void shouldgetNumberOfPatientsPerAllergyWhenIsOk() throws Exception {
		// Given
		final PatientsPerAllergyDTO patientsPerAllergyDTO = createPatientsPerAllergyDTO();
		final List<PatientsPerAllergyDTO> patientsPerAllergies = Arrays.asList(patientsPerAllergyDTO);

		// When
		when(statsService.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);

		// Then
		mockMvc.perform(get("/api/stats/patientsPerAllergy")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
    	verify(statsService, times(1)).findNumberOfPatientsPerAllergy();
	}

	@Test
	public void shouldgetNumberOfPatientsPerAllergyWhenIsEmpty() throws Exception {
		// Given
		final List<PatientsPerAllergyDTO> patientsPerAllergies = Collections.emptyList();

		// When
		when(statsService.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);

		// Then
		mockMvc.perform(get("/api/stats/patientsPerAllergy")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
    	verify(statsService, times(1)).findNumberOfPatientsPerAllergy();
	}

	@Test
	public void shouldGetNumberOfPatientsPerDietWhenIsOk() throws Exception {
		// Given
		final PatientsPerDietDTO patientsPerDietDTO = createPatientsPerDietDTO();
		final List<PatientsPerDietDTO> patientsPerDiets = Arrays.asList(patientsPerDietDTO);

		// When
		when(statsService.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);

		// Then
		mockMvc.perform(get("/api/stats/patientsPerDiet")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
    	verify(statsService, times(1)).findNumberOfPatientsPerDiet();
	}

	@Test
	public void shouldGetNumberOfPatientsPerDietWhenIsEmpty() throws Exception {
		// Given
		final List<PatientsPerDietDTO> patientsPerDiets = Collections.emptyList();

		// When
		when(statsService.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);

		// Then
		mockMvc.perform(get("/api/stats/patientsPerDiet")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
    	verify(statsService, times(1)).findNumberOfPatientsPerDiet();
	}

	@Test
	public void shouldGetNumberOfPatientsByStatusWhenIsOk() throws Exception {
		// Given
		final PatientsByStatusDTO patientsPerDietDTO = createPatientsByStatusDTO();

		// When
		when(statsService.findNumberOfPatientsByStatus()).thenReturn(Optional.ofNullable(patientsPerDietDTO));

		// Then
		mockMvc.perform(get("/api/stats/patientsByStatus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
    	verify(statsService, times(1)).findNumberOfPatientsByStatus();
	}

	@Test
	public void shouldGetNumberOfPatientsByStatusWhenIsEmpty() throws Exception {
		// Given

		// When
		when(statsService.findNumberOfPatientsByStatus()).thenReturn(Optional.empty());

		// Then
		mockMvc.perform(get("/api/stats/patientsByStatus")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
    	verify(statsService, times(1)).findNumberOfPatientsByStatus();
	}

}
