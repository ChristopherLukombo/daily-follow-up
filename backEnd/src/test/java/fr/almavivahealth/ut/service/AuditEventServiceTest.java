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

import fr.almavivahealth.dao.CaregiverHistoryRepository;
import fr.almavivahealth.dao.MenuHistoryRepository;
import fr.almavivahealth.dao.PatientHistoryRepository;
import fr.almavivahealth.domain.entity.CaregiverHistory;
import fr.almavivahealth.domain.entity.MenuHistory;
import fr.almavivahealth.domain.entity.PatientHistory;
import fr.almavivahealth.service.dto.CaregiverHistoryDTO;
import fr.almavivahealth.service.dto.MenuHistoryDTO;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
import fr.almavivahealth.service.impl.audit.AuditEventServiceImpl;
import fr.almavivahealth.service.mapper.CaregiverHistoryMapper;
import fr.almavivahealth.service.mapper.MenuHistoryMapper;
import fr.almavivahealth.service.mapper.PatientHistoryMapper;

/**
 *
 * @author christopher
 * @version 16
 *
 */
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

	@Mock
    private CaregiverHistoryRepository caregiverHistoryRepository;

	@Mock
	private CaregiverHistoryMapper  caregiverHistoryMapper;

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

	private static CaregiverHistory createCaregiverHistory() {
		final CaregiverHistory caregiverHistory = new CaregiverHistory();
		return caregiverHistory;
	}

	private static CaregiverHistoryDTO createCaregiverHistoryDTO() {
		final CaregiverHistoryDTO caregiverHistoryDTO = new CaregiverHistoryDTO();
		return caregiverHistoryDTO;
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

	@Test
	public void shouldFindAllCaregiverHistorys() {
		// Given
		final List<CaregiverHistory> content = Arrays.asList(createCaregiverHistory());
		final Page<CaregiverHistory> caregiverHistorys = new PageImpl<>(content);
		final CaregiverHistoryDTO caregiverHistoryDTO = createCaregiverHistoryDTO();

		// When
		when(caregiverHistoryRepository.findAllByCaregiverIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(caregiverHistorys);
		when(caregiverHistoryMapper.caregiverHistoryToCaregiverHistoryDTO((CaregiverHistory) any()))
				.thenReturn(caregiverHistoryDTO);

		// Then
		assertThat(auditEventServiceImpl.findAllCaregiverHistorysByCaregiverId(1L, 0, 10)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyPageWhenTryingToFindAllCaregiverHistorys() {
		// Given
		final List<CaregiverHistory> content = new ArrayList<>();
		final Page<CaregiverHistory> caregiverHistorys = new PageImpl<>(content);

		// When
		when(caregiverHistoryRepository.findAllByCaregiverIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(caregiverHistorys);

		// Then
		assertThat(auditEventServiceImpl.findAllCaregiverHistorysByCaregiverId(1L, 0, 10)).isEmpty();
	}

	@Test
	public void shouldThrowPageWhenTryingToFindAllCaregiverHistorys() {
		// Given
		final Page<CaregiverHistory> caregiverHistorys = null;

		// When
		when(caregiverHistoryRepository.findAllByCaregiverIdOrderByModifiedDateDesc(anyLong(), (org.springframework.data.domain.Pageable) any()))
				.thenReturn(caregiverHistorys);

		// Then
		assertThatThrownBy(() -> auditEventServiceImpl.findAllCaregiverHistorysByCaregiverId(1L, 0, 10))
		.isInstanceOf(NullPointerException.class);
	}
}
