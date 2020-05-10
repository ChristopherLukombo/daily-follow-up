package fr.almavivahealth.service.impl;

import static fr.almavivahealth.constants.Constants.SLASH;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import fr.almavivahealth.service.mapper.UserMapper;
import fr.almavivahealth.service.propeties.UserProperties;

/**
 * Service Implementation for managing User.
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

    @Autowired
	public UserServiceImpl(
			final UserRepository userRepository,
			final RoleRepository roleRepository,
			final UserMapper userMapper,
			final PasswordEncoder passwordEncoder,
			final UserProperties userProperties) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.userProperties = userProperties;
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
			newUser.setEmail(userDTO.getEmail());
			newUser.setImageUrl(userDTO.getImageUrl());
			newUser.setBirthDay(userDTO.getBirthDay());
			newUser.setCreateDate(userDTO.getCreateDate());
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

	private void sendFileToFolder(final MultipartFile file, final Path path) throws DailyFollowUpException {
		final Path resolve = path.resolve(file.getOriginalFilename());
		try (final InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, resolve, StandardCopyOption.REPLACE_EXISTING);
		} catch (final IOException e) {
			throw new DailyFollowUpException("An error occurred while trying to copy the file", e);
		}
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

}
