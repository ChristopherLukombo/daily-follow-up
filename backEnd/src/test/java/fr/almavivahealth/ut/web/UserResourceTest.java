package fr.almavivahealth.ut.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.almavivahealth.security.jwt.TokenProvider;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.web.Login;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.UserResource;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

	private static final String PASSWORD = "test";

	private static final String USERNAME = "Ben";

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Mock
	private TokenProvider tokenProvider;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private UserResource userResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static Login createLogin() {
		final Login login = new Login();
		login.setUsername(USERNAME);
		login.setPassword(PASSWORD);
		return login;
	}

	@Test
	public void shouldAuthenticateWhenIsOk() throws IOException, Exception {
		// Given
		final Login login = createLogin();

		// Then
		mockMvc.perform(post("/api/authenticate")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(login)))
		        .andExpect(status().isOk());
	}
}
