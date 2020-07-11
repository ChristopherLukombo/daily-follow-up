package fr.almavivahealth.service.impl.diet;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.DietRepository;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.service.DietService;
import fr.almavivahealth.service.dto.DietDTO;
import fr.almavivahealth.service.mapper.DietMapper;

/**
 * Service Implementation for managing Diet.
 *
 * @author christopher
 * @version 16
 */
@Service
@Transactional
public class DietServiceImpl implements DietService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DietServiceImpl.class);

	private final DietRepository dietRepository;

	private final DietMapper dietMapper;

	@Autowired
	public DietServiceImpl(final DietRepository dietRepository, final DietMapper dietMapper) {
		this.dietRepository = dietRepository;
		this.dietMapper = dietMapper;
	}

	/**
	 * Save a diet.
	 *
	 * @param dietDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public DietDTO save(final DietDTO dietDTO) {
		LOGGER.debug("Request to save Diet : {}", dietDTO);
		Diet diet = dietMapper.dietDTOToDiet(dietDTO);
		diet = dietRepository.save(diet);
		return dietMapper.dietToDietDTO(diet);
	}

	/**
	 * Update a diet.
	 *
	 * @param dietDTO the diet DTO
	 * @return the persisted entity
	 */
	@Override
	public DietDTO update(final DietDTO dietDTO) {
		LOGGER.debug("Request to update Diet : {}", dietDTO);
		Diet diet = dietMapper.dietDTOToDiet(dietDTO);
		diet = dietRepository.saveAndFlush(diet);
		return dietMapper.dietToDietDTO(diet);
	}

	/**
	 * Get all the diets.
	 *
	 * @return the list of entities
	 */
	@Override
	public List<DietDTO> findAll() {
		LOGGER.debug("Request to get all Diets");
		return dietRepository.findAllByOrderByIdAsc().stream()
				.map(dietMapper::dietToDietDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" diet.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	public Optional<DietDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Diet : {}", id);
		return dietRepository.findById(id)
				.map(dietMapper::dietToDietDTO);
	}

	/**
	 * Delete the "id" diet.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Diet : {}", id);
		dietRepository.deleteById(id);
	}
}
