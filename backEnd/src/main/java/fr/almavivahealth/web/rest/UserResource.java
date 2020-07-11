package fr.almavivahealth.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.almavivahealth.security.jwt.JWTConfigurer;
import fr.almavivahealth.security.jwt.TokenProvider;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.web.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * UserResource to authenticate users.
 *
 * @author christopher
 * @version 16
 */
@Api("User")
@RestController
@RequestMapping("/api")
public class UserResource {

	private final TokenProvider tokenProvider;

	private final AuthenticationManager authenticationManager;

	private final UserService userService;

    @Autowired
	public UserResource(
			final TokenProvider tokenProvider,
			final AuthenticationManager authenticationManager,
			final UserService userService) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	/**
	 * Authenticate the user and return the token which identify him.
	 *
	 * @param login
	 * @return JWTToken
	 */
	@ApiOperation("Authenticate the user and return the token which identify him.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 422, message = "Unprocessable entity")
        })
	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody final Login login) {

		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				login.getUsername(), login.getPassword());

		final Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		final SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		final String jwt = tokenProvider.createToken(authentication);
		final boolean hasChangedPassword = userService.hasChangedPassword(login.getUsername());
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt, hasChangedPassword), httpHeaders, HttpStatus.OK);
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		private boolean hasChangedPassword;

		JWTToken(final String idToken, final boolean hasChangedPassword) {
			this.idToken = idToken;
			this.hasChangedPassword = hasChangedPassword;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(final String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("has_changed_password")
		boolean isHasChanged() {
			return hasChangedPassword;
		}

		void setHasChanged(final boolean hasChanged) {
			this.hasChangedPassword = hasChanged;
		}
	}
}
