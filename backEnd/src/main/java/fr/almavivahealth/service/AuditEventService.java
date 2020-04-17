package fr.almavivahealth.service;

import org.springframework.data.domain.Page;

import fr.almavivahealth.service.dto.PatientHistoryDTO;

/**
 * The Interface AuditEventService.
 */
public interface AuditEventService {

	/**
	 * Get all patient_historys.
	 *
	 * @param page the page
	 * @param size the size
	 * @return the page
	 */
	Page<PatientHistoryDTO> findAllPatientHistorys(final Integer page, final Integer size);

}
