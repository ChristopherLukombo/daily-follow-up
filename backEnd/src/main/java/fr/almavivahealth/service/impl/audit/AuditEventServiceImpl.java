package fr.almavivahealth.service.impl.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.MenuHistoryRepository;
import fr.almavivahealth.dao.PatientHistoryRepository;
import fr.almavivahealth.service.AuditEventService;
import fr.almavivahealth.service.dto.MenuHistoryDTO;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
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

    @Autowired
	public AuditEventServiceImpl(
			final PatientHistoryRepository patientHistoryRepository,
			final PatientHistoryMapper patientHistoryMapper,
			final MenuHistoryRepository menuHistoryRepository,
			final MenuHistoryMapper menuHistoryMapper) {
		this.patientHistoryRepository = patientHistoryRepository;
		this.patientHistoryMapper = patientHistoryMapper;
		this.menuHistoryRepository = menuHistoryRepository;
		this.menuHistoryMapper = menuHistoryMapper;
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
}
