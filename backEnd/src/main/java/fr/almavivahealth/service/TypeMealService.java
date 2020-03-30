package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.TypeMealDTO;

/**
 * The Interface TypeMealService.
 */
public interface TypeMealService {

	/**
	 * Save a typeMeal.
	 *
	 * @param typeMealDTO the entity to save
	 * @return the persisted entity
	 */
	TypeMealDTO save(TypeMealDTO typeMealDTO);

	/**
	 * Update a typeMeal.
	 *
	 * @param typeMealDTO the typeMeal DTO
	 * @return the persisted entity
	 */
	TypeMealDTO update(TypeMealDTO typeMealDTO);

	/**
	 * Get all the typeMeals.
	 *
	 * @return the list of entities
	 */
	List<TypeMealDTO> findAll();

	/**
	 * Get the "id" typeMeal.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<TypeMealDTO> findOne(Long id);

	/**
	 * Delete the "id" typeMeal.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);
}
