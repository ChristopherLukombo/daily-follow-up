package fr.almavivahealth.service.impl.user;

import static fr.almavivahealth.constants.Constants.SLASH;
import static fr.almavivahealth.constants.ErrorMessage.NEW_PASSWORD_MUST_NOT_MATCH_OLD_PASSWORD;
import static fr.almavivahealth.util.functional.FunctionWithException.wrapper;
import static java.time.temporal.ChronoUnit.DAYS;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.dao.RoleRepository;
import fr.almavivahealth.dao.UserRepository;
import fr.almavivahealth.domain.entity.Role;
import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.dto.UserPassDTO;
import fr.almavivahealth.service.mapper.UserMapper;
import fr.almavivahealth.service.propeties.UserProperties;

/**
 * Service Implementation for managing User.
 *
 * @author christopher
 * @version 17
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserProperties userProperties;

    private final MessageSource messageSource;

    @Autowired
	public UserServiceImpl(
			final UserRepository userRepository,
			final RoleRepository roleRepository,
			final UserMapper userMapper,
			final PasswordEncoder passwordEncoder,
			final UserProperties userProperties,
			final MessageSource messageSource) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.userProperties = userProperties;
		this.messageSource = messageSource;
	}

	/**
	 * Save the user.
	 *
	 * @param userDTO the user DTO
	 * @return the user DTO
	 * @throws DailyFollowUpException
	 */
	@Override
	public UserDTO save(final UserDTO userDTO) throws DailyFollowUpException {
		try {
			LOGGER.debug("Request to save the user: {}", userDTO);

			final User newUser = new User();
			newUser.setPseudo(userDTO.getPseudo());
			final String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
			// new user gets initially a generated password
			newUser.setPassword(encryptedPassword);
			newUser.setFirstName(userDTO.getFirstName());
			newUser.setLastName(userDTO.getLastName());
			newUser.setImageUrl(userDTO.getImageUrl());
			newUser.setCreateDate(userDTO.getCreateDate());
			newUser.setHasChangedPassword(false);
			final Role role = findRole(userDTO.getRoleName());
			newUser.setRole(role);
			newUser.setStatus(true);

			final User user = userRepository.save(newUser);
			return userMapper.userToUserDTO(user);
		} catch (final DailyFollowUpException e) {
			throw new DailyFollowUpException("An error occurred while saving the user", e);
		}
	}

	private Role findRole(final String roleName) throws DailyFollowUpException {
		return roleRepository.findByName(roleName)
				.orElseThrow(() -> new DailyFollowUpException("Role not found with roleName : " + roleName));
	}

	/**
	 * Update the user.
	 *
	 * @param userDTO the user DTO
	 * @return the user DTO
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public UserDTO update(final UserDTO userDTO) throws DailyFollowUpException {
		LOGGER.debug("Request to update User : {}", userDTO);
		User user = userMapper.userDTOToUser(userDTO);
		final Role role = findRole(userDTO.getRoleName());
		final String password = findPassword(userDTO);
		user.setRole(role);
		user.setPassword(password);
		user = userRepository.saveAndFlush(user);
		return userMapper.userToUserDTO(user);
	}

	private String findPassword(final UserDTO userDTO) {
		if (!StringUtils.isBlank(userDTO.getPassword())) {
			return passwordEncoder.encode(userDTO.getPassword());
		}
		return userRepository.findById(userDTO.getId())
				.map(User::getPassword)
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Delete the user.
	 *
	 * @param id the id
	 * @return the user
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public Optional<User> delete(final Long id) throws DailyFollowUpException {
		LOGGER.debug("Request to delete User : {}", id);
		return userRepository.findById(id).map(user -> {
			// disable given user for the id.
			user.setStatus(false);
			user.setHasChangedPassword(false);
			userRepository.saveAndFlush(user);
			LOGGER.debug("Disabled user : {}", id);
			return user;
		});
	}

	/**
	 * Find one by pseudo.
	 *
	 * @param pseudo the pseudo
	 * @return the user DTO
	 */
	@Override
	@Transactional(readOnly = true)
	public UserDTO findOneByPseudo(final String pseudo) {
		return userRepository.findOneByPseudoIgnoreCase(pseudo)
				.map(userMapper::userToUserDTO)
				.orElseGet(() -> null);
	}

	/**
	 * Upload profile picture.
	 *
	 * @param file the file
	 * @param userId the user id
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public void uploadProfilePicture(final MultipartFile file, final Long userId) throws DailyFollowUpException {
		try {
			LOGGER.debug("Request to upload profile picture : {}", userId);
			final String pathName = userProperties.getPathProfiles() + userId;
			createDirectory(pathName); // create directory if not exist
			sendFileToFolder(file, Paths.get(pathName));
			userRepository.setImageUrl(file.getOriginalFilename(), userId);
		} catch (final IOException e) {
			throw new DailyFollowUpException("An error occurred while trying to upload the file", e);
		}
	}

	private void createDirectory(final String pathName) throws IOException {
		final File directory = new File(pathName);
		if (!directory.exists()) {
			Files.createDirectories(Paths.get(pathName));
		}
	}

	private void sendFileToFolder(final MultipartFile file, final Path path) throws IOException {
		final Path resolve = path.resolve(file.getOriginalFilename());
		final InputStream inputStream = file.getInputStream();
		Files.copy(inputStream, resolve, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Find profile picture.
	 *
	 * @param userId the user id
	 * @return the byte[]
	 */
	@Override
	@Transactional(readOnly = true)
	public byte[] findProfilePicture(final Long userId) {
		LOGGER.debug("Request to get profile picture : {}", userId);
		return userRepository.findById(userId)
				.map(User::getImageUrl)
				.map(imageUrl -> readFile(userId, imageUrl))
				.orElseGet(() -> ArrayUtils.EMPTY_BYTE_ARRAY);
	}

	private byte[] readFile(final Long userId, final String imageUrl) {
		try {
			final String pathName = fetchPathName(userId, imageUrl);
			return Files.readAllBytes(new File(pathName).toPath());
		} catch (final IOException e) {
			return ArrayUtils.EMPTY_BYTE_ARRAY;
		}
	}

	private String fetchPathName(final Long userId, final String imageUrl) {
		return new StringBuilder()
				.append(userProperties.getPathProfiles())
				.append(userId)
				.append(SLASH)
				.append(imageUrl)
				.toString();
	}

	/**
	 * Update password of user.
	 *
	 * @param userPassDTO the user pass DTO
	 * @param locale      the locale
	 * @return the optional
	 */
	@Override
	public Optional<User> updatePassword(final UserPassDTO userPassDTO, final Locale locale) {
		return userRepository.findById(userPassDTO.getUserId()).map(wrapper(user -> {
			if (passwordEncoder.matches(userPassDTO.getPassword(), user.getPassword())) {
				throw new DailyFollowUpException(
						messageSource.getMessage(NEW_PASSWORD_MUST_NOT_MATCH_OLD_PASSWORD, null, locale));
			}
			user.setPassword(passwordEncoder.encode(userPassDTO.getPassword()));
			user.setHasChangedPassword(true);
			userRepository.saveAndFlush(user);
			return user;
		}));
	}

	/**
	 * Checks for changed password.
	 *
	 * @param pseudo the pseudo
	 * @return true, if successful
	 */
	@Override
	public boolean hasChangedPassword(final String pseudo) {
		return userRepository.findOneByPseudoIgnoreCase(pseudo)
				.map(User::getHasChangedPassword)
				.orElse(false);
	}

	/**
	 * Find all active users.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<UserDTO> findAllActiveUsers() {
		LOGGER.debug("Request to get all Users");
		return userRepository.findAllByStatusTrueOrderByIdDesc().stream()
				.map(userMapper::userToUserDTO)
				.collect(Collectors.toList());
	}

	/**
     * Users must reset their password after being inactive after a period of time after 31 days.
     *
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
	@Override
    @Scheduled(cron = "0 0 1 * * ?")
	public void forceUsersToResetPasswordAfterBeingInactive() {
		final List<User> users = userRepository.findAllByStatusIsTrueAndLastLoginDateBefore(LocalDateTime.now().minus(31, DAYS));
		for (final User user : users) {
			LOGGER.debug("User not actif: {}", user.getPseudo());
			user.setHasChangedPassword(false);
			userRepository.saveAndFlush(user);
		}
	}
}
