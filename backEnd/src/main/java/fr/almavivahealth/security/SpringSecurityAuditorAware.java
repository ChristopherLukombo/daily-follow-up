package fr.almavivahealth.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation of AuditorAware based on Spring Security.
 *
 * @author christopher
 * @version 17
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	/**
	 * Gets the current auditor.
	 *
	 * @return the current auditor
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return Optional.ofNullable(authentication)
				.map(Authentication::getPrincipal)
				.filter(p -> p instanceof UserDetails)
				.map(this::getUserName);
	}

	private String getUserName(final Object principal) {
		return ((UserDetails) principal).getUsername();
	}

}
