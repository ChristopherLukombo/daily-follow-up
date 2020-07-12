package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.DishDTO;

/**
 * The Interface DishService.
 * @author christopher
 * @version 17
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
	 * Find by name.
	 *
	 * @param name the name
	 * @return the optional
	 */
	Optional<DishDTO> findByName(String name);
}
