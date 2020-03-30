package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.TypeMealRepository;
import fr.almavivahealth.domain.TypeMeal;
import fr.almavivahealth.service.TypeMealService;
import fr.almavivahealth.service.dto.TypeMealDTO;
import fr.almavivahealth.service.mapper.TypeMealMapper;

/**
 * Service Implementation for managing TypeMeal.
 */
@Service
@Transactional
public class TypeMealServiceImpl implements TypeMealService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypeMealServiceImpl.class);
	
    private final TypeMealRepository typeMealRepository;
    
    private final TypeMealMapper typeMealMapper;
	
	@Autowired
	public TypeMealServiceImpl(final TypeMealRepository typeMealRepository, final TypeMealMapper typeMealMapper) {
		this.typeMealRepository = typeMealRepository;
		this.typeMealMapper = typeMealMapper;
	}

	/**
	 * Save a typeMeal.
	 *
	 * @param typeMealDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public TypeMealDTO save(final TypeMealDTO typeMealDTO) {
		LOGGER.debug("Request to save TypeMeal : {}", typeMealDTO);
		TypeMeal typeMeal = typeMealMapper.typeMealDTOToTypeMeal(typeMealDTO);
		typeMeal = typeMealRepository.save(typeMeal);
		return typeMealMapper.typeMealToTypeMealDTO(typeMeal);
	}

	/**
	 * Update a typeMeal.
	 *
	 * @param typeMealDTO the typeMeal DTO
	 * @return the persisted entity
	 */
	@Override
	public TypeMealDTO update(final TypeMealDTO typeMealDTO) {
		LOGGER.debug("Request to update TypeMeal : {}", typeMealDTO);
		TypeMeal typeMeal = typeMealMapper.typeMealDTOToTypeMeal(typeMealDTO);
		typeMeal = typeMealRepository.saveAndFlush(typeMeal);
		return typeMealMapper.typeMealToTypeMealDTO(typeMeal);
	}

	/**
	 * Get all the typeMeals.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TypeMealDTO> findAll() {
		LOGGER.debug("Request to get all TypeMeals");
		return typeMealRepository.findAll().stream()
				.map(typeMealMapper::typeMealToTypeMealDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" typeMeal.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<TypeMealDTO> findOne(final Long id) {
		LOGGER.debug("Request to get TypeMeal : {}", id);
		return typeMealRepository.findById(id)
				.map(typeMealMapper::typeMealToTypeMealDTO);
	}

	/**
	 * Delete the "id" typeMeal.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete TypeMeal : {}", id);
		typeMealRepository.deleteById(id);
	}
}
