package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.almavivahealth.dao.MenuHistoryRepository;
import fr.almavivahealth.service.AuditEventService;
import fr.almavivahealth.service.dto.CaregiverHistoryDTO;
import fr.almavivahealth.service.dto.MenuHistoryDTO;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
import fr.almavivahealth.service.mapper.MenuHistoryMapper;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.AuditResource;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuditResourceTest {

	private MockMvc mockMvc;

	@Mock
	private MenuHistoryRepository menuHistoryRepository;

	@Mock
	private MenuHistoryMapper menuHistoryMapper;

	@Mock
	private AuditEventService auditEventService;

	@InjectMocks
	private AuditResource auditResource;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(auditResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static PatientHistoryDTO createPatientHistoryDTO() {
		final PatientHistoryDTO patientHistoryDTO = new PatientHistoryDTO();
		return patientHistoryDTO;
	}

	private static MenuHistoryDTO createMenuHistoryDTO() {
		final MenuHistoryDTO menuHistoryDTO = new MenuHistoryDTO();
		return menuHistoryDTO;
	}

	private static CaregiverHistoryDTO createCaregiverHistoryDTO() {
		final CaregiverHistoryDTO caregiverHistoryDTO = new CaregiverHistoryDTO();
		return caregiverHistoryDTO;
	}

	@Test
	public void shouldGetAllPatientHistorysWhenIsOk() throws Exception {
		// Given
		final List<PatientHistoryDTO> content = Arrays.asList(createPatientHistoryDTO());
		final Page<PatientHistoryDTO> patientHistorys = new PageImpl<>(content);

		// When
		when(auditEventService.findAllPatientHistorysByPatientId(anyLong(), anyInt(), anyInt())).thenReturn(patientHistorys);

		// Then
		mockMvc.perform(get("/management/audits/patient/history/1?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(auditEventService, times(1)).findAllPatientHistorysByPatientId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllPatientHistorysWhenIsEmpty() throws Exception {
		// Given
		final List<PatientHistoryDTO> content = Collections.emptyList();
		final Page<PatientHistoryDTO> patientHistorys = new PageImpl<>(content);

		// When
		when(auditEventService.findAllPatientHistorysByPatientId(anyLong(), anyInt(), anyInt())).thenReturn(patientHistorys);

		// Then
		mockMvc.perform(get("/management/audits/patient/history/1?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(auditEventService, times(1)).findAllPatientHistorysByPatientId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllPatientHistorysWhenIsBadRequest() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/patient/history/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllPatientHistorysByPatientId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllPatientHistorysWhenIsNotValidParam() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/patient/history/1?page=a&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllPatientHistorysByPatientId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllMenuHistorysWhenIsOk() throws Exception {
		// Given
		final List<MenuHistoryDTO> content = Arrays.asList(createMenuHistoryDTO());
		final Page<MenuHistoryDTO> menuHistorys = new PageImpl<>(content);

		// When
		when(auditEventService.findAllMenuHistorysByMenuId(anyLong(), anyInt(), anyInt())).thenReturn(menuHistorys);

		// Then
		mockMvc.perform(get("/management/audits/menu/history/1?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(auditEventService, times(1)).findAllMenuHistorysByMenuId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllMenuHistorysWhenIsEmpty() throws Exception {
		// Given
		final List<MenuHistoryDTO> content = Collections.emptyList();
		final Page<MenuHistoryDTO> menuHistorys = new PageImpl<>(content);

		// When
		when(auditEventService.findAllMenuHistorysByMenuId(anyLong(), anyInt(), anyInt())).thenReturn(menuHistorys);

		// Then
		mockMvc.perform(get("/management/audits/menu/history/1?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(auditEventService, times(1)).findAllMenuHistorysByMenuId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllMenuHistorysWhenIsBadRequest() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/menu/history/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllMenuHistorysByMenuId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllMenuHistorysWhenIsNotValidParam() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/menu/history/1?page=a&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllMenuHistorysByMenuId(anyLong(), anyInt(), anyInt());
	}

	//
	@Test
	public void shouldGetAllCaregiverHistorysWhenIsOk() throws Exception {
		// Given
		final List<CaregiverHistoryDTO> content = Arrays.asList(createCaregiverHistoryDTO());
		final Page<CaregiverHistoryDTO> caregiverHistorys = new PageImpl<>(content);

		// When
		when(auditEventService.findAllCaregiverHistorysByCaregiverId(anyLong(), anyInt(), anyInt())).thenReturn(caregiverHistorys);

		// Then
		mockMvc.perform(get("/management/audits/caregiver/history/1?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(auditEventService, times(1)).findAllCaregiverHistorysByCaregiverId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllCaregiverHistorysWhenIsEmpty() throws Exception {
		// Given
		final List<CaregiverHistoryDTO> content = Collections.emptyList();
		final Page<CaregiverHistoryDTO> caregiverHistorys = new PageImpl<>(content);

		// When
		when(auditEventService.findAllCaregiverHistorysByCaregiverId(anyLong(), anyInt(), anyInt())).thenReturn(caregiverHistorys);

		// Then
		mockMvc.perform(get("/management/audits/caregiver/history/1?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(auditEventService, times(1)).findAllCaregiverHistorysByCaregiverId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllCaregiverHistorysWhenIsBadRequest() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/caregiver/history/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllCaregiverHistorysByCaregiverId(anyLong(), anyInt(), anyInt());
	}

	@Test
	public void shouldGetAllCaregiverHistorysWhenIsNotValidParam() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/caregiver/history/1?page=a&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllCaregiverHistorysByCaregiverId(anyLong(), anyInt(), anyInt());
	}
}
