package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.CaregiverRepository;
import fr.almavivahealth.domain.entity.Caregiver;
import fr.almavivahealth.service.CaregiverService;
import fr.almavivahealth.service.dto.CaregiverDTO;
import fr.almavivahealth.service.mapper.CaregiverMapper;

/**
 * Service Implementation for managing Caregiver.
 */
@Service
@Transactional
public class CaregiverServiceImpl implements CaregiverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaregiverServiceImpl.class);
	
	private final CaregiverRepository caregiverRepository;
	
	private final CaregiverMapper caregiverMapper;
	
	@Autowired
	public CaregiverServiceImpl(final CaregiverRepository caregiverRepository, final CaregiverMapper caregiverMapper) {
		this.caregiverRepository = caregiverRepository;
		this.caregiverMapper = caregiverMapper;
	}

	/**
	 * Save a caregiver.
	 *
	 * @param caregiverDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public CaregiverDTO save(final CaregiverDTO caregiverDTO) {
		LOGGER.debug("Request to save Caregiver : {}", caregiverDTO);
		Caregiver caregiver = caregiverMapper.caregiverDTOToCaregiver(caregiverDTO);
		caregiver = caregiverRepository.save(caregiver);
		return caregiverMapper.caregiverToCaregiverDTO(caregiver);
	}

	/**
	 * Update a caregiver.
	 *
	 * @param caregiverDTO the caregiver DTO
	 * @return the persisted entity
	 */
	@Override
	public CaregiverDTO update(final CaregiverDTO caregiverDTO) {
		LOGGER.debug("Request to update Caregiver : {}", caregiverDTO);
		Caregiver caregiver = caregiverMapper.caregiverDTOToCaregiver(caregiverDTO);
		caregiver = caregiverRepository.saveAndFlush(caregiver);
		return caregiverMapper.caregiverToCaregiverDTO(caregiver);
	}

	/**
	 * Get all the caregivers.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CaregiverDTO> findAll() {
		LOGGER.debug("Request to get all Caregivers");
		return caregiverRepository.findAllByOrderByIdDesc().stream()
				.map(caregiverMapper::caregiverToCaregiverDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" caregiver.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<CaregiverDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Caregiver : {}", id);
		return caregiverRepository.findById(id)
				.map(caregiverMapper::caregiverToCaregiverDTO);
	}

	/**
	 * Delete the "id" caregiver.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Caregiver : {}", id);
		caregiverRepository.deleteById(id);
	}
}
