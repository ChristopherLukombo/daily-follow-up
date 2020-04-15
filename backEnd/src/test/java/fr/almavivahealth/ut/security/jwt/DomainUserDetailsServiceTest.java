package fr.almavivahealth.ut.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.almavivahealth.dao.UserRepository;
import fr.almavivahealth.domain.Role;
import fr.almavivahealth.domain.User;
import fr.almavivahealth.enums.RoleName;
import fr.almavivahealth.security.jwt.DomainUserDetailsService;

@RunWith(MockitoJUnitRunner.class)
public class DomainUserDetailsServiceTest {
	
	private static final String PASSWD = "testParis19!";

	private static final String PSEUDO = "Damien";
	
	private static final long ID = 1L;

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
    private DomainUserDetailsService domainUserDetailsService;
	
	private static User createUser() {
		final User user = new User();
		user.setId(ID);
		user.setPassword(PASSWD);
		user.setPseudo(PSEUDO);
		return user;
	}
	
	private static Role createRole() {
		final Role role = new Role();
        role.setId(ID);
        role.setName(RoleName.ROLE_ADMIN.name());
		return role;
	}

	@Test
	public void shouldLoadUserByUsernameWhenIsOk() {
		// Given
		final User user = createUser();
		user.setRole(createRole());

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThat(domainUserDetailsService.loadUserByUsername(PSEUDO)).isNotNull();
	}
	
	@Test
	public void shouldLoadUserByUsernameWhenIsNotActivated() {
		// Given
		final User user = createUser();
		user.setRole(createRole());
		user.setStatus(false);

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThatThrownBy(() -> domainUserDetailsService.loadUserByUsername(PSEUDO))
		.isInstanceOf(UsernameNotFoundException.class);
	}
	
	@Test
	public void shouldLoadUserByUsernameWhenIsNotFound() {
		// Given
		final User user = null;

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThatThrownBy(() -> domainUserDetailsService.loadUserByUsername(PSEUDO))
		.isInstanceOf(UsernameNotFoundException.class);
	}
	
}
