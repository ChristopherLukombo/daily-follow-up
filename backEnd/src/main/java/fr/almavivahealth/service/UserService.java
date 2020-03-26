package fr.almavivahealth.service;

import fr.almavivahealth.service.dto.UserDTO;

/**
 * Service Interface for managing User.
 */
public interface UserService {

	
	/**
	 * Save the user.
	 *
	 * @param userDTO the user DTO
	 * @return the user DTO
	 */
	UserDTO save(UserDTO userDTO);
}