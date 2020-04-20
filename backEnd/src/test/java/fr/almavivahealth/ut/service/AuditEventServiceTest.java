package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import fr.almavivahealth.dao.PatientHistoryRepository;
import fr.almavivahealth.domain.PatientHistory;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
import fr.almavivahealth.service.impl.AuditEventServiceImpl;
import fr.almavivahealth.service.mapper.PatientHistoryMapper;

@RunWith(MockitoJUnitRunner.class)
public class AuditEventServiceTest {

	@Mock
    private PatientHistoryRepository patientHistoryRepository;
 	
	@Mock
	private PatientHistoryMapper  patientHistoryMapper;
	
	@InjectMocks
	private AuditEventServiceImpl auditEventServiceImpl;
	
	private static PatientHistory createPatientHistory() {
		final PatientHistory patientHistory = new PatientHistory();
		return patientHistory;
	}
	
	private static PatientHistoryDTO createPatientHistoryDTO() {
		final PatientHistoryDTO patientHistoryDTO = new PatientHistoryDTO();
		return patientHistoryDTO;
	}
	
	@Test
	public void shouldFindAllPatientHistorysWhenIsOk() {
		// Given
		final List<PatientHistory> content = Arrays.asList(createPatientHistory());
		final Page<PatientHistory> patientHistorys = new PageImpl<>(content);
		final PatientHistoryDTO patientHistoryDTO = createPatientHistoryDTO();

		// When
		when(patientHistoryRepository.findAllByPatientId(anyLong(), ((org.springframework.data.domain.Pageable) any())))
				.thenReturn(patientHistorys);
		when(patientHistoryMapper.patientHistoryToPatientHistoryDTO((PatientHistory) any()))
				.thenReturn(patientHistoryDTO);

		// Then
		assertThat(auditEventServiceImpl.findAllPatientHistorysByPatientId(1L, 0, 10)).isNotEmpty();
	}
	
	@Test
	public void shouldFindAllPatientHistorysWhenIsEmpty() {
		// Given
		final List<PatientHistory> content = new ArrayList<>();
		final Page<PatientHistory> patientHistorys = new PageImpl<>(content);

		// When
		when(patientHistoryRepository.findAllByPatientId(anyLong(), ((org.springframework.data.domain.Pageable) any())))
				.thenReturn(patientHistorys);

		// Then
		assertThat(auditEventServiceImpl.findAllPatientHistorysByPatientId(1L, 0, 10)).isEmpty();
	}
	
	@Test
	public void shouldFindAllPatientHistorysWhenIsNull() {
		// Given
		final Page<PatientHistory> patientHistorys = null;

		// When
		when(patientHistoryRepository.findAllByPatientId(anyLong(), ((org.springframework.data.domain.Pageable) any())))
				.thenReturn(patientHistorys);

		// Then
		assertThatThrownBy(() -> auditEventServiceImpl.findAllPatientHistorysByPatientId(1L, 0, 10))
		.isInstanceOf(NullPointerException.class);
	}
	
}
