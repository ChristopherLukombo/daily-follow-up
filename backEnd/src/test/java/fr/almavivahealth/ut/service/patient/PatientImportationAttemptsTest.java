package fr.almavivahealth.ut.service.patient;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import fr.almavivahealth.service.impl.patient.PatientImportationAttemptsImpl;

/**
 *
 * @author christopher
 * @version 16
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PatientImportationAttemptsTest {

	@InjectMocks
	private PatientImportationAttemptsImpl patientImportationAttempts;

	@Test
	public void shouldVerifyIfhasMadeAnAttempt() {
		// Given
		final String ip = "192.168.0.18";
		final String pseudo = "Ben";

		// When
		patientImportationAttempts.storeFailedAttempts(ip, pseudo);

		// Then
		assertThat(patientImportationAttempts.hasMadeAnAttempt(ip, pseudo)).isTrue();
	}

	@Test
	public void shouldValidateAttempt() {
		// Given
		final String ip = "192.168.0.18";
		final String pseudo = "Ben";

		// Then
		patientImportationAttempts.validateAttempt(ip, pseudo);
	}

}
