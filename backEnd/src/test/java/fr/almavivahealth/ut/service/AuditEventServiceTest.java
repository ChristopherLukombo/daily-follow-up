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

import fr.almavivahealth.dao.MenuHistoryRepository;
import fr.almavivahealth.dao.PatientHistoryRepository;
import fr.almavivahealth.domain.entity.MenuHistory;
import fr.almavivahealth.domain.entity.PatientHistory;
import fr.almavivahealth.service.dto.MenuHistoryDTO;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
import fr.almavivahealth.service.impl.audit.AuditEventServiceImpl;
import fr.almavivahealth.service.mapper.MenuHistoryMapper;
import fr.almavivahealth.service.mapper.PatientHistoryMapper;

@RunWith(MockitoJUnitRunner.class)
public class AuditEventServiceTest {

	@Mock
	private MenuHistoryRepository menuHistoryRepository;

	@Mock
	private MenuHistoryMapper menuHistoryMapper;

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

	private static MenuHistory createMenuHistory() {
		final MenuHistory menuHistory = new MenuHistory();
		return menuHistory;
	}

	private static MenuHistoryDTO createMenuHistoryDTO() {
		final MenuHistoryDTO menuHistoryDTO = new MenuHistoryDTO();
		return menuHistoryDTO;
	}

	@Test
	public void shouldFindAllPatientHistorysWhenIsOk() {
		// Given
		final List<PatientHistory> content = Arrays.asList(createPatientHistory());
		final Page<PatientHistory> patientHistorys = new PageImpl<>(content);
		final PatientHistoryDTO patientHistoryDTO = createPatientHistoryDTO();

		// When
		when(patientHistoryRepository.findAllByPatientIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
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
		when(patientHistoryRepository.findAllByPatientIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(patientHistorys);

		// Then
		assertThat(auditEventServiceImpl.findAllPatientHistorysByPatientId(1L, 0, 10)).isEmpty();
	}

	@Test
	public void shouldFindAllPatientHistorysWhenIsNull() {
		// Given
		final Page<PatientHistory> patientHistorys = null;

		// When
		when(patientHistoryRepository.findAllByPatientIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(patientHistorys);

		// Then
		assertThatThrownBy(() -> auditEventServiceImpl.findAllPatientHistorysByPatientId(1L, 0, 10))
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldFindAllMenuHistorys() {
		// Given
		final List<MenuHistory> content = Arrays.asList(createMenuHistory());
		final Page<MenuHistory> menuHistorys = new PageImpl<>(content);
		final MenuHistoryDTO menuHistoryDTO = createMenuHistoryDTO();

		// When
		when(menuHistoryRepository.findAllByMenuIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(menuHistorys);
		when(menuHistoryMapper.menuHistoryToMenuHistoryDTO((MenuHistory) any()))
				.thenReturn(menuHistoryDTO);

		// Then
		assertThat(auditEventServiceImpl.findAllMenuHistorysByMenuId(1L, 0, 10)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyPageWhenTryingToFindAllMenuHistorys() {
		// Given
		final List<MenuHistory> content = new ArrayList<>();
		final Page<MenuHistory> menuHistorys = new PageImpl<>(content);

		// When
		when(menuHistoryRepository.findAllByMenuIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(menuHistorys);

		// Then
		assertThat(auditEventServiceImpl.findAllMenuHistorysByMenuId(1L, 0, 10)).isEmpty();
	}

	@Test
	public void shouldThrowPageWhenTryingToFindAllMenuHistorys() {
		// Given
		final Page<MenuHistory> menuHistorys = null;

		// When
		when(menuHistoryRepository.findAllByMenuIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(menuHistorys);

		// Then
		assertThatThrownBy(() -> auditEventServiceImpl.findAllMenuHistorysByMenuId(1L, 0, 10))
		.isInstanceOf(NullPointerException.class);
	}
}
