package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.almavivahealth.dao.RoleRepository;
import fr.almavivahealth.dao.UserRepository;
import fr.almavivahealth.domain.Role;
import fr.almavivahealth.domain.User;
import fr.almavivahealth.enums.RoleName;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.impl.UserServiceImpl;
import fr.almavivahealth.service.mapper.UserMapper;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private static final String PSEUDO = "Damien";

	private static final long ID = 1L;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	private static User createUser() {
		final User user = new User();
		user.setId(ID);
		user.setPseudo(PSEUDO);
		return user;
	}

	private static UserDTO createUserDTO() {
		return UserDTO.builder().id(ID).build();
	}

	private static Role createRole() {
		final Role role = new Role();
        role.setId(ID);
        role.setName(RoleName.ROLE_ADMIN.name());
		return role;
	}

	@Test
	public void shouldSaveUserWhenIsOk() throws DailyFollowUpException {
		// Given
		final User user = createUser();
		final Role role = createRole();
		final UserDTO userDTO = createUserDTO();
		userDTO.setRoleName(RoleName.ROLE_ADMIN.name());

		// When
		when(roleRepository.findByName(anyString())).thenReturn(Optional.ofNullable(role));
		when(userRepository.save((User) any())).thenReturn(user);
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		assertThat(userServiceImpl.save(userDTO)).isEqualTo(userDTO);
	}

	@Test
	public void shouldSaveWhenUserHasNoRoleIsKo() throws DailyFollowUpException {
		// Given
		final UserDTO userDTO = createUserDTO();
		 
		// Then
		assertThatThrownBy(() -> userServiceImpl.save(userDTO))
		.isInstanceOf(DailyFollowUpException.class);
	}

	@Test
	public void shouldRegisterWhenIsKo() {
		// Given
		final UserDTO userDTO = null;

		// Then
		assertThatThrownBy(() -> userServiceImpl.save(userDTO))
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldFindOneByPseudoWhenIsOk() {
		// Given
		final User user = createUser();
		final Role role = createRole();
		user.setRole(role);
		final UserDTO userDTO = createUserDTO();
		
		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);
		
		// Then
		assertThat(userServiceImpl.findOneByPseudo(PSEUDO)).isNotNull();
	}
	
	@Test
	public void shouldFindOneByPseudoWhenIsNull() {
		// Given
		final User user = null;
		
		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));
		
		// Then
		assertThat(userServiceImpl.findOneByPseudo(PSEUDO)).isNull();
	}
}
