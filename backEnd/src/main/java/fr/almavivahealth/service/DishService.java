package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.DishDTO;

/**
 * The Interface DishService.
 */
public interface DishService {

	/**
	 * Find the first 5 dishes by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	List<DishDTO> findFirst5ByName(String name);

	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the optional
	 */
	Optional<DishDTO> findByCode(Integer code);
}
