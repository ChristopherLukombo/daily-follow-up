package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import fr.almavivahealth.domain.projection.PatientsByStatus;
import fr.almavivahealth.domain.projection.PatientsPerAllergy;
import fr.almavivahealth.domain.projection.PatientsPerDiet;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.service.impl.stats.StatsServiceImpl;
import fr.almavivahealth.service.mapper.StatsMapper;

@RunWith(MockitoJUnitRunner.class)
public class StatsServiceTest {

	private static final long TOTAL_PATIENTS = 13L;

	private static final long INACTIVE_PATIENTS = 23L;

	private static final long ACTIVE_PATIENTS = 12L;

	private static final String DIET_NAME = "SANS SEL";

	private static final int PERCENTAGE = 11;

	private static final long NUMBER_PATIENTS = 8L;

	private static final String ALLERGY_NAME = "SOJA";

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private StatsMapper statsMapper;

	@InjectMocks
	private StatsServiceImpl statsServiceImpl;

	private static PatientsPerAllergyDTO createPatientsPerAllergyDTO() {
		return PatientsPerAllergyDTO.builder()
				.allergyName(ALLERGY_NAME)
				.numberPatients(NUMBER_PATIENTS)
				.percentage(BigDecimal.valueOf(PERCENTAGE))
				.build();
	}

	private static PatientsPerAllergy createPatientsPerAllergy() {
		return null;
	}

	private static PatientsPerDietDTO createPatientsPerDietDTO() {
		return PatientsPerDietDTO.builder()
				.dietName(DIET_NAME)
				.numberPatients(NUMBER_PATIENTS)
				.percentage(BigDecimal.valueOf(PERCENTAGE))
				.build();
	}

	private static PatientsPerDiet createPatientsPerDiet() {
		return null;
	}

	private static PatientsByStatusDTO createPatientsByStatusDTO() {
		return PatientsByStatusDTO.builder()
				.activePatients(ACTIVE_PATIENTS)
				.inactivePatients(INACTIVE_PATIENTS)
				.totalPatients(TOTAL_PATIENTS)
				.build();
	}

	private static PatientsByStatus createPatientsByStatus() {
		return null;
	}

	@Test
	public void shouldFindNumberOfPatientsPerAllergyWhenIsOk() {
		// Given
		final PatientsPerAllergy patientsPerAllergy = createPatientsPerAllergy();
		final List<PatientsPerAllergy> patientsPerAllergies = Arrays.asList(patientsPerAllergy);
		final PatientsPerAllergyDTO patientsPerAllergyDTO = createPatientsPerAllergyDTO();

		// When
		when(patientRepository.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);
		when(statsMapper.patientsPerAllergyToPatientsPerAllergyDTO((PatientsPerAllergy) any()))
		.thenReturn(patientsPerAllergyDTO);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerAllergy()).isNotEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsPerAllergyWhenIsNull() {
		// Given
		final List<PatientsPerAllergy> patientsPerAllergies = null;

		// When
		when(patientRepository.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);

		// Then
		assertThatThrownBy(() -> statsServiceImpl.findNumberOfPatientsPerAllergy())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldFindNumberOfPatientsPerAllergyWhenIsEmpty() {
		// Given
		final List<PatientsPerAllergy> patientsPerAllergies = Collections.emptyList();

		// When
		when(patientRepository.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerAllergy()).isEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsPerDietWhenIsOk() {
		// Given
		final PatientsPerDiet patientsPerDiet = createPatientsPerDiet();
		final List<PatientsPerDiet> patientsPerDiets = Arrays.asList(patientsPerDiet);
		final PatientsPerDietDTO patientsPerDietDTO = createPatientsPerDietDTO();

		// When
		when(patientRepository.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);
		when(statsMapper.patientsPerDietToPatientsPerDietDTO((PatientsPerDiet) any())).thenReturn(patientsPerDietDTO);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerDiet()).isNotEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsPerDietWhenIsNull() {
		// Given
		final List<PatientsPerDiet> patientsPerDiets = null;

		// When
		when(patientRepository.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);

		// Then
		assertThatThrownBy(() -> statsServiceImpl.findNumberOfPatientsPerDiet())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldFindNumberOfPatientsPerDietWhenIsEmpty() {
		// Given
		final List<PatientsPerDiet> patientsPerDiets = Collections.emptyList();

		// When
		when(patientRepository.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerDiet()).isEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsByStatusWhenIsOk() {
		// Given
		final PatientsByStatus patientsByStatus = createPatientsByStatus();

		// When
		when(patientRepository.findNumberOfPatientsByStatus()).thenReturn(Optional.ofNullable(patientsByStatus));

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsByStatus()).isEmpty();
	}
}
