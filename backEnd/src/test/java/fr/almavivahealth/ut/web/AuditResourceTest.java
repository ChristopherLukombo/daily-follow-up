package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.anyInt;
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

import fr.almavivahealth.service.AuditEventService;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.AuditResource;

@RunWith(MockitoJUnitRunner.class)
public class AuditResourceTest {

	private MockMvc mockMvc;
	
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
	
	@Test
	public void shouldGetAllPatientHistorysWhenIsOk() throws Exception {
		// Given
		final List<PatientHistoryDTO> content = Arrays.asList(createPatientHistoryDTO());
		final Page<PatientHistoryDTO> patientHistorys = new PageImpl<>(content);
		
		// When
		when(auditEventService.findAllPatientHistorys(anyInt(), anyInt())).thenReturn(patientHistorys);
		
		// Then
		mockMvc.perform(get("/management/audits/patient/history?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(auditEventService, times(1)).findAllPatientHistorys(anyInt(), anyInt());
	}
	
	@Test
	public void shouldGetAllPatientHistorysWhenIsEmpty() throws Exception {
		// Given
		final List<PatientHistoryDTO> content = Collections.emptyList();
		final Page<PatientHistoryDTO> patientHistorys = new PageImpl<>(content);
		
		// When
		when(auditEventService.findAllPatientHistorys(anyInt(), anyInt())).thenReturn(patientHistorys);
		
		// Then
		mockMvc.perform(get("/management/audits/patient/history?page=0&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(auditEventService, times(1)).findAllPatientHistorys(anyInt(), anyInt());
	}
	
	@Test
	public void shouldGetAllPatientHistorysWhenIsBadRequest() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/patient/history")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllPatientHistorys(anyInt(), anyInt());
	}
	
	@Test
	public void shouldGetAllPatientHistorysWhenIsNotValidParam() throws Exception {
		// Then
		mockMvc.perform(get("/management/audits/patient/history?page=a&size=10")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isBadRequest());
		verify(auditEventService, times(0)).findAllPatientHistorys(anyInt(), anyInt());
	}

}