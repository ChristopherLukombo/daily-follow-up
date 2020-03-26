package fr.almavivahealth.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.almavivahealth.security.jwt.JWTConfigurer;
import fr.almavivahealth.security.jwt.TokenProvider;
import fr.almavivahealth.web.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * UserResource to authenticate users.
 * 
 * @author christopher
 */
@Api(value = "User")
@RestController
@RequestMapping("/api")
public class UserResource {

	private final TokenProvider tokenProvider;

	private final AuthenticationManager authenticationManager;

	@Autowired
	public UserResource(final TokenProvider tokenProvider, final AuthenticationManager authenticationManager) {
		this.tokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
	}

	/**
	 * Authenticate the user and return the token which identify him.
	 * 
	 * @param login
	 * @return JWTToken
	 */
	@ApiOperation(value = "Authenticate the user and return the token which identify him.")
	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody final Login login) {

		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				login.getUsername(), login.getPassword());

		final Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String jwt = tokenProvider.createToken(authentication);
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}

	/**
	 * Object to return as body in JWT Authentication.
	 */
	static class JWTToken {

		private String idToken;

		public JWTToken(final String idToken) {
			this.idToken = idToken;
		}

		@JsonProperty("id_token")
		String getIdToken() {
			return idToken;
		}

		void setIdToken(final String idToken) {
			this.idToken = idToken;
		}
	}
}