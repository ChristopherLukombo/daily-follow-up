package fr.almavivahealth.service.impl.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.CaregiverHistoryRepository;
import fr.almavivahealth.dao.MenuHistoryRepository;
import fr.almavivahealth.dao.PatientHistoryRepository;
import fr.almavivahealth.service.AuditEventService;
import fr.almavivahealth.service.dto.CaregiverHistoryDTO;
import fr.almavivahealth.service.dto.MenuHistoryDTO;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
import fr.almavivahealth.service.mapper.CaregiverHistoryMapper;
import fr.almavivahealth.service.mapper.MenuHistoryMapper;
import fr.almavivahealth.service.mapper.PatientHistoryMapper;

/**
 * Service Implementation for managing all AuditEvent.
 */
@Service
@Transactional
public class AuditEventServiceImpl implements AuditEventService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditEventServiceImpl.class);

	private final PatientHistoryRepository patientHistoryRepository;

	private final PatientHistoryMapper  patientHistoryMapper;

	private final MenuHistoryRepository menuHistoryRepository;

	private final MenuHistoryMapper menuHistoryMapper;

	private final CaregiverHistoryRepository caregiverHistoryRepository;

	private final CaregiverHistoryMapper caregiverHistoryMapper;

   @Autowired
	public AuditEventServiceImpl(
			final PatientHistoryRepository patientHistoryRepository,
			final PatientHistoryMapper patientHistoryMapper,
			final MenuHistoryRepository menuHistoryRepository,
			final MenuHistoryMapper menuHistoryMapper,
			final CaregiverHistoryRepository caregiverHistoryRepository,
			final CaregiverHistoryMapper caregiverHistoryMapper) {
		this.patientHistoryRepository = patientHistoryRepository;
		this.patientHistoryMapper = patientHistoryMapper;
		this.menuHistoryRepository = menuHistoryRepository;
		this.menuHistoryMapper = menuHistoryMapper;
		this.caregiverHistoryRepository = caregiverHistoryRepository;
		this.caregiverHistoryMapper = caregiverHistoryMapper;
	}


	/**
	 * Get all patient_historys.
	 *
	 * @param patientId the patientId
	 * @param page the page
	 * @param size the size
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<PatientHistoryDTO> findAllPatientHistorysByPatientId(final Long patientId, final Integer page, final Integer size) {
		LOGGER.debug("Request to get all PatientHistorys");
		return patientHistoryRepository.findAllByPatientIdOrderByModifiedDateDesc(patientId, PageRequest.of(page, size))
				.map(patientHistoryMapper::patientHistoryToPatientHistoryDTO);
	}


	/**
	 * Get all menu historys by menu id.
	 *
	 * @param menuId the menu id
	 * @param page   the page
	 * @param size   the size
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<MenuHistoryDTO> findAllMenuHistorysByMenuId(final Long menuId, final Integer page, final Integer size) {
		LOGGER.debug("Request to get all MenuHistorys");
		return menuHistoryRepository.findAllByMenuIdOrderByModifiedDateDesc(menuId, PageRequest.of(page, size))
				.map(menuHistoryMapper::menuHistoryToMenuHistoryDTO);
	}

	/**
	 * Get all menu historys by caregiver id.
	 *
	 * @param caregiverId the caregiver id
	 * @param page   the page
	 * @param size   the size
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<CaregiverHistoryDTO> findAllCaregiverHistorysByCaregiverId(final Long caregiverId, final Integer page,
			final Integer size) {
		LOGGER.debug("Request to get all CaregiverHistorys");
	    return caregiverHistoryRepository.findAllByCaregiverIdOrderByModifiedDateDesc(caregiverId, PageRequest.of(page, size))
				.map(caregiverHistoryMapper::caregiverHistoryToCaregiverHistoryDTO);
	}
}
