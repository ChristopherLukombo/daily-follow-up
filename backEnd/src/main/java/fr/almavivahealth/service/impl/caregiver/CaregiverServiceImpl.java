package fr.almavivahealth.service.impl.caregiver;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.CaregiverRepository;
import fr.almavivahealth.dao.RoleRepository;
import fr.almavivahealth.domain.entity.Caregiver;
import fr.almavivahealth.domain.entity.Role;
import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.CaregiverService;
import fr.almavivahealth.service.dto.CaregiverDTO;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.mapper.CaregiverMapper;

/**
 * Service Implementation for managing Caregiver.
 *
 * @author christopher
 * @version 16
 */
@Service
@Transactional
public class CaregiverServiceImpl implements CaregiverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaregiverServiceImpl.class);

	private final CaregiverRepository caregiverRepository;

	private final CaregiverMapper caregiverMapper;

	private final PasswordEncoder passwordEncoder;

	private final RoleRepository roleRepository;

    @Autowired
	public CaregiverServiceImpl(
			final CaregiverRepository caregiverRepository,
			final CaregiverMapper caregiverMapper,
			final PasswordEncoder passwordEncoder,
			final RoleRepository roleRepository) {
		this.caregiverRepository = caregiverRepository;
		this.caregiverMapper = caregiverMapper;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	/**
	 * Save a caregiver.
	 *
	 * @param caregiverDTO the entity to save
	 * @return the persisted entity
	 * @throws DailyFollowUpException
	 */
	@Override
	public CaregiverDTO save(final CaregiverDTO caregiverDTO) throws DailyFollowUpException {
		LOGGER.debug("Request to save Caregiver : {}", caregiverDTO);
		Caregiver caregiver = caregiverMapper.caregiverDTOToCaregiver(caregiverDTO);
		final User user = findUser(caregiver);
		if (user != null) {
			final Role role = findRole(caregiverDTO);
			user.setRole(role);
			caregiver.setUser(user);
		}
		caregiver = caregiverRepository.save(caregiver);
		return caregiverMapper.caregiverToCaregiverDTO(caregiver);
	}

	/**
	 * Update a caregiver.
	 *
	 * @param caregiverDTO the caregiver DTO
	 * @return the persisted entity
	 * @throws DailyFollowUpException
	 */
	@Override
	public CaregiverDTO update(final CaregiverDTO caregiverDTO) throws DailyFollowUpException {
		LOGGER.debug("Request to update Caregiver : {}", caregiverDTO);
		Caregiver caregiver = caregiverMapper.caregiverDTOToCaregiver(caregiverDTO);
		final User user = findUser(caregiver);
		if (user != null) {
			final Role role = findRole(caregiverDTO);
			user.setRole(role);
			caregiver.setUser(user);
		}
		caregiver = caregiverRepository.saveAndFlush(caregiver);
		return caregiverMapper.caregiverToCaregiverDTO(caregiver);
	}

	private User findUser(final Caregiver caregiver) {
		return Optional.ofNullable(caregiver)
				.map(Caregiver::getUser)
				.map(this::encryptPassword)
				.orElse(null);
	}

	private User encryptPassword(final User user) {
		if (user == null || (user != null && user.getPassword() == null)) {
			return null;
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return user;
	}

	private Role findRole(final CaregiverDTO caregiverDTO) throws DailyFollowUpException {
		final String role = findRoleName(caregiverDTO);
		return roleRepository.findByName(role)
				.orElseThrow(() -> new DailyFollowUpException("Role not found"));
	}

	private String findRoleName(final CaregiverDTO caregiverDTO) {
		return Optional.ofNullable(caregiverDTO)
				.map(CaregiverDTO::getUser)
				.map(UserDTO::getRoleName)
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Get all the caregivers.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CaregiverDTO> findAll() {
		LOGGER.debug("Request to get all Caregivers");
		return caregiverRepository.findAllByOrderByIdDesc().stream()
				.map(caregiverMapper::caregiverToCaregiverDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" caregiver.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<CaregiverDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Caregiver : {}", id);
		return caregiverRepository.findById(id)
				.map(caregiverMapper::caregiverToCaregiverDTO);
	}

	/**
	 * Delete the "id" caregiver.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Caregiver : {}", id);
		caregiverRepository.deleteById(id);
	}

	/**
	 * Find all by floor number.
	 *
	 * @param number the number
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CaregiverDTO> findAllByFloorNumber(final Integer number) {
	   LOGGER.debug("Request to get all Caregivers for floor: {}", number);
	   return caregiverRepository.findAllByFloorNumber(number).stream()
			   .map(caregiverMapper::caregiverToCaregiverDTO)
			   .collect(Collectors.toList());
	}

	/**
	 * Get the "userId" caregiver.
	 *
	 * @param userId the user id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<CaregiverDTO> findByUserId(final Long userId) {
		LOGGER.debug("Request to get Caregiver by user id : {}", userId);
		return caregiverRepository.findByUserId(userId)
				.map(caregiverMapper::caregiverToCaregiverDTO);
	}
}
