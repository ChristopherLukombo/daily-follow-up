package fr.almavivahealth.service;

import java.util.Locale;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.dto.UserPassDTO;

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
	 * Update the user.
	 *
	 * @param userDTO the user DTO
	 * @return the user DTO
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	UserDTO update(UserDTO userDTO) throws DailyFollowUpException;

	/**
	 * Delete the user.
	 *
	 * @param id the id
	 * @return the user
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	Optional<User> delete(Long id) throws DailyFollowUpException;

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
	 * @param file   the file
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

	/**
	 * Update password of user.
	 *
	 * @param userPassDTO the user pass DTO
	 * @param locale      the locale
	 * @return the optional
	 */
	Optional<User> updatePassword(final UserPassDTO userPassDTO, Locale locale);

	/**
	 * Checks for changed password.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	boolean hasChangedPassword(Long userId);
}
