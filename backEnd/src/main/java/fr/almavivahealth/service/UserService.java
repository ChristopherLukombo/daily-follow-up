package fr.almavivahealth.service;

import org.springframework.web.multipart.MultipartFile;

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
	
	/**
	 * Upload profile picture.
	 *
	 * @param file the file
	 * @param userId the user id
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	void uploadProfilePicture(MultipartFile file, Long userId) throws DailyFollowUpException;
	
	/**
	 * Find image.
	 *
	 * @param userId the user id
	 * @return the byte[]
	 */
	byte[] findProfilePicture(Long userId);
	
}
