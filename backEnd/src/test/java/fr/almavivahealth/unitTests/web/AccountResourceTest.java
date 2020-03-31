package fr.almavivahealth.unitTests.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.almavivahealth.enums.RoleName;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.AccountResource;

@RunWith(MockitoJUnitRunner.class)
public class AccountResourceTest {
	
	private static final long ID = 1L;
	
	private static final String PSEUDO = "Ben";
	private static final String EMAIL = "ben.montreuil@gmail.com";
	private static final String PASSWORD = "benenede";
	
	private MockMvc mockMvc;
	
	@Mock
	private UserService userService;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private AccountResource accountResource;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(accountResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static UserDTO createUserDTO() {
		return UserDTO.builder()
				.id(ID)
				.pseudo(PSEUDO)
				.email(EMAIL)
				.password(PASSWORD)
				.roleName(RoleName.ROLE_ADMIN.name())
				.build();
	}

	@Test
	public void shouldCreateUserWhenIsOk() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();
		userDTO.setId(null);
		
		// When
		when(userService.save((UserDTO) any())).thenReturn(userDTO);
		
		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isCreated());
	}
	
	@Test
	public void shouldCreateUserWhenIsKo() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();
		
		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isBadRequest());
		verify(userService, times(0)).save(userDTO);
	}
	
	@Test
	public void shouldCreateUserWhenIsNotValid() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();
		userDTO.setId(null);
		userDTO.setPseudo(null);
		
		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isUnprocessableEntity());
		verify(userService, times(0)).save(userDTO);
	}
}
