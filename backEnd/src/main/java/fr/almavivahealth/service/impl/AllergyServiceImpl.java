package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.AllergyRepository;
import fr.almavivahealth.domain.Allergy;
import fr.almavivahealth.service.AllergyService;
import fr.almavivahealth.service.dto.AllergyDTO;
import fr.almavivahealth.service.mapper.AllergyMapper;

/**
 * Service Implementation for managing Allergy.
 */
@Service
@Transactional
public class AllergyServiceImpl implements AllergyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AllergyServiceImpl.class);
	
	private final AllergyRepository allergyRepository;
	
	private final AllergyMapper allergyMapper;
	
	@Autowired
	public AllergyServiceImpl(final AllergyRepository allergyRepository, final AllergyMapper allergyMapper) {
		this.allergyRepository = allergyRepository;
		this.allergyMapper = allergyMapper;
	}

	/**
	 * Save a allergy.
	 *
	 * @param allergyDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public AllergyDTO save(final AllergyDTO allergyDTO) {
		LOGGER.debug("Request to save Allergy : {}", allergyDTO);
		Allergy allergy = allergyMapper.allergyDTOToAllergy(allergyDTO);
		allergy = allergyRepository.save(allergy);
		return allergyMapper.allergyToAllergyDTO(allergy);
	}

	/**
	 * Update a allergy.
	 *
	 * @param allergyDTO the allergy DTO
	 * @return the persisted entity
	 */
	@Override
	public AllergyDTO update(final AllergyDTO allergyDTO) {
		LOGGER.debug("Request to update Allergy : {}", allergyDTO);
		Allergy allergy = allergyMapper.allergyDTOToAllergy(allergyDTO);
		allergy = allergyRepository.saveAndFlush(allergy);
		return allergyMapper.allergyToAllergyDTO(allergy);
	}

	/**
	 * Get all the allergys.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<AllergyDTO> findAll() {
		LOGGER.debug("Request to get all Allergys");
		return allergyRepository.findAllByOrderByIdDesc().stream()
				.map(allergyMapper::allergyToAllergyDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" allergy.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<AllergyDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Allergy : {}", id);
		return allergyRepository.findById(id)
				.map(allergyMapper::allergyToAllergyDTO);
	}

	/**
	 * Delete the "id" allergy.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Allergy : {}", id);
		allergyRepository.deleteById(id);
	}
}
