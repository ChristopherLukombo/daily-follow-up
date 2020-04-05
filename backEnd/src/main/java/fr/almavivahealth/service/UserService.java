package fr.almavivahealth.service;

import fr.almavivahealth.exception.DailyFollowUpException;
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
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	UserDTO save(UserDTO userDTO) throws DailyFollowUpException;
	
	/**
	 * Find one by pseudo.
	 *
	 * @param pseudo the pseudo
	 * @return the user DTO
	 */
	UserDTO findOneByPseudo(String pseudo);
}
