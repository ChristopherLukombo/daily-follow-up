/*
 *
 */
package fr.almavivahealth.security.listener;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import fr.almavivahealth.dao.UserRepository;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	private final UserRepository userRepository;

    @Autowired
	public AuthenticationSuccessListener(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * On application event.
	 *
	 * @param event the event
	 */
	@Override
	public void onApplicationEvent(final AuthenticationSuccessEvent event) {
		final Authentication authentication = event.getAuthentication();
		final String pseudo = authentication.getName();

		userRepository.findOneByPseudoIgnoreCase(pseudo)
		.map(user -> {
			user.setLastLoginDate(LocalDateTime.now());
			userRepository.saveAndFlush(user);
			return user;
		});
	}
}
