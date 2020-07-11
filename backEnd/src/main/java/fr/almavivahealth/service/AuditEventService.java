package fr.almavivahealth.service;

import org.springframework.data.domain.Page;

import fr.almavivahealth.service.dto.CaregiverHistoryDTO;
import fr.almavivahealth.service.dto.MenuHistoryDTO;
import fr.almavivahealth.service.dto.PatientHistoryDTO;

/**
 * The Interface AuditEventService.
 * @author christopher
 * @version 17
 */
public interface AuditEventService {

	/**
	 * Get all patient_historys.
	 *
	 * @param patientId the patientId
	 * @param page      the page
	 * @param size      the size
	 * @return the page
	 */
	Page<PatientHistoryDTO> findAllPatientHistorysByPatientId(final Long patientId, final Integer page,
			final Integer size);

	/**
	 * Get all menu historys by menu id.
	 *
	 * @param menuId the menu id
	 * @param page   the page
	 * @param size   the size
	 * @return the page
	 */
	Page<MenuHistoryDTO> findAllMenuHistorysByMenuId(final Long menuId, final Integer page, final Integer size);

	/**
	 * Get all menu historys by caregiver id.
	 *
	 * @param caregiverId the caregiver id
	 * @param page   the page
	 * @param size   the size
	 * @return the page
	 */
	Page<CaregiverHistoryDTO> findAllCaregiverHistorysByCaregiverId(final Long caregiverId, final Integer page, final Integer size);
}
