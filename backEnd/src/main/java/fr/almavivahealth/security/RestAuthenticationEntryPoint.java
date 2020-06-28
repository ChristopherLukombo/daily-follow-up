package fr.almavivahealth.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The Class RestAuthenticationEntryPoint to set response after authentication fails.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {
		LOGGER.debug("Pre-authenticated entry rejecting access");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
	}

	@ExceptionHandler(value = { AccessDeniedException.class })
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AccessDeniedException accessDeniedException) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Authorization Failed : " + accessDeniedException.getMessage());
	}

}
