package fr.almavivahealth.security.jwt;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.UserRepository;
import fr.almavivahealth.domain.entity.Role;
import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.exception.DailyFollowUpException;

/**
 * Authenticate a user from the database.
 *
 * @author christopher
 * @version 16
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public DomainUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
	public UserDetails loadUserByUsername(final String login) {
		LOGGER.debug("Authenticating {}", login);
		final String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
		final Optional<User> userByLoginFromDatabase = userRepository.findOneByPseudoIgnoreCase(lowercaseLogin);
		return userByLoginFromDatabase.map(user -> {
			try {
				return createSpringSecurityUser(lowercaseLogin, user);
			} catch (final DailyFollowUpException e) {
				return null;
			}
		}).orElseThrow(
				() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " + "database"));
	}

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(final String lowercaseLogin, final User user) throws DailyFollowUpException {
        if (!user.isStatus()) {
            throw new DailyFollowUpException("User " + lowercaseLogin + " was not activated");
        }

        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(getRole(user)));

        return new org.springframework.security.core.userdetails.User(user.getPseudo(),
                user.getPassword(),
                grantedAuthorities);
    }

    private String getRole(final User user) {
    	return Optional.of(user).map(User::getRole)
    			.map(Role::getName)
    			.orElse(StringUtils.EMPTY);
    }

}
