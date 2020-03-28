package fr.almavivahealth.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.RoleRepository;
import fr.almavivahealth.dao.UserRepository;
import fr.almavivahealth.domain.Role;
import fr.almavivahealth.domain.User;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.mapper.UserMapper;

/**
 * Service Implementation for managing User.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	private final UserMapper userMapper;
	
    private final PasswordEncoder passwordEncoder;

    @Autowired
	public UserServiceImpl(final UserRepository userRepository, final RoleRepository roleRepository, final UserMapper userMapper,
			final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Save the user.
	 *
	 * @param userDTO the user DTO
	 * @return the user DTO
	 */
	@Override
	public UserDTO save(final UserDTO userDTO) {
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
		final Optional<Role> role = roleRepository.findById(userDTO.getRoleId());
		if (role.isPresent()) {
			newUser.setRole(role.get());
		}
		newUser.setStatus(true);

		final User user = userRepository.save(newUser);
		return userMapper.userToUserDTO(user);
	}
}
