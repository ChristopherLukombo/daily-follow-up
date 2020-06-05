package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.domain.enums.RoleName;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.dto.UserPassDTO;
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

	private static User createUser() {
		final User user = new User();
		user.setId(ID);
		user.setPseudo(PSEUDO);
		return user;
	}

	private static UserDTO createUserDTO() {
		return UserDTO.builder()
				.id(ID)
				.pseudo(PSEUDO)
				.password(PASSWORD)
				.status(true)
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
	public void shouldCreateUserWhenIsInternalError() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();
		userDTO.setId(null);

		// When
		doThrow(DailyFollowUpException.class).when(userService).save((UserDTO) any());

		// Then
		mockMvc.perform(post("/api/register")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isInternalServerError());
		verify(userService, times(1)).save((UserDTO) any());
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

	@Test
	public void shouldUpdateUserWhenIsOk() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();

		// When
		when(userService.update((UserDTO) any())).thenReturn(userDTO);

		// Then
		mockMvc.perform(put("/api/users/update")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isOk());
	}

	@Test
	public void shouldUpdateUserWhenIsKo() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();
		userDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/users/update")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isBadRequest());
		verify(userService, times(0)).update(userDTO);
	}

	@Test
	public void shouldUpdateUserWhenIsInternalError() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();

		// When
		doThrow(DailyFollowUpException.class).when(userService).update((UserDTO) any());

		// Then
		mockMvc.perform(put("/api/users/update")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isInternalServerError());
		verify(userService, times(1)).update((UserDTO) any());
	}

	@Test
	public void shouldUpdateUserWhenIsNotValid() throws IOException, Exception {
		// Given
		final UserDTO userDTO = createUserDTO();
		userDTO.setId(null);
		userDTO.setPseudo(null);

		// Then
		mockMvc.perform(put("/api/users/update")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userDTO)))
		        .andExpect(status().isUnprocessableEntity());
		verify(userService, times(0)).update(userDTO);
	}

	@Test
    public void shouldDeleteUser() throws Exception {
    	// Given
		final User user = createUser();

    	// When
    	when(userService.delete(anyLong())).thenReturn(Optional.ofNullable(user));

    	// Then
    	mockMvc.perform(delete("/api/users/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNoContent());
		verify(userService, times(1)).delete(anyLong());
    }

	@Test
    public void shouldThrowWhenTryingToDeleteUser() throws Exception {
    	// Given
		final User user = null;

    	// When
    	when(userService.delete(anyLong())).thenReturn(Optional.ofNullable(user));

    	// Then
    	mockMvc.perform(delete("/api/users/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isInternalServerError());
		verify(userService, times(1)).delete(anyLong());
    }

	@Test
	public void shouldUploadFileWhenIsOk() throws Exception {
		// Given
		final MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain",
				"some xml".getBytes());
		final MockPart part = new MockPart("file", "filename.csv", file.getBytes());

		// When
		doNothing().when(userService).uploadProfilePicture((MultipartFile) any(), anyLong());

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/register/profilePicture/1")
				.part(part))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$").isNotEmpty());
		verify(userService, times(1)).uploadProfilePicture((MultipartFile) any(), anyLong());
	}

	@Test
	public void shouldUploadFileWhenIsKo() throws Exception {
		// Given
		final MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain",
				"some xml".getBytes());
		final MockPart part = new MockPart("file", "filename.csv", file.getBytes());

		// When
		doThrow(DailyFollowUpException.class).when(userService).uploadProfilePicture((MultipartFile) any(), anyLong());

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/register/profilePicture/1")
				.part(part))
		        .andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$").isNotEmpty());
		verify(userService, times(1)).uploadProfilePicture((MultipartFile) any(), anyLong());
	}

	@Test
	public void shouldUploadFileWhenIsBadRequest() throws Exception {
		// Given
		final MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain",
				"some xml".getBytes());
		final MockPart part = new MockPart("file", "filename.csv", file.getBytes());

		// Then
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/register/profilePicture/test")
				.part(part))
		        .andExpect(status().isBadRequest());
		verify(userService, times(0)).uploadProfilePicture((MultipartFile) any(), anyLong());
	}

    @Test
    public void shouldGetProfilePictureWhenIsOk() throws Exception {
    	// Given
    	final byte[] profilePicture = new byte[] {0};

    	// When
    	when(userService.findProfilePicture(anyLong())).thenReturn(profilePicture);

    	// Then
    	mockMvc.perform(get("/api/users/profilePicture/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		verify(userService, times(1)).findProfilePicture(anyLong());
    }

    @Test
    public void shouldGetProfilePictureWhenIsBadRequest() throws Exception {
    	// Then
    	mockMvc.perform(get("/api/users/profilePicture/test")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isBadRequest());
		verify(userService, times(0)).findProfilePicture(anyLong());
    }

    @Test
	public void shouldUpdatePassword() throws IOException, Exception {
		// Given
    	final User user = createUser();
    	final UserPassDTO  userPassDTO = new UserPassDTO();
    	userPassDTO.setUserId(1L);
    	userPassDTO.setPassword("sddzdz");

		// When
		when(userService.updatePassword((UserPassDTO) any(), (Locale) any())).thenReturn(Optional.ofNullable(user));

		// Then
		mockMvc.perform(patch("/api/users/pass")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(userPassDTO)))
		        .andExpect(status().isOk());
		verify(userService, times(1)).updatePassword((UserPassDTO) any(), (Locale) any());
	}

    @Test
  	public void shouldThrowInternalServerErrorWhenUserNotExist() throws IOException, Exception {
  		// Given
      	final User user = null;
      	final UserPassDTO  userPassDTO = new UserPassDTO();
      	userPassDTO.setUserId(1L);
      	userPassDTO.setPassword("sddzdz");

  		// When
    	when(userService.updatePassword((UserPassDTO) any(), (Locale) any())).thenReturn(Optional.ofNullable(user));

  		// Then
  		mockMvc.perform(patch("/api/users/pass")
  				.contentType(TestUtil.APPLICATION_JSON_UTF8)
  				.content(TestUtil.convertObjectToJsonBytes(userPassDTO)))
  		        .andExpect(status().isInternalServerError());
  		verify(userService, times(1)).updatePassword((UserPassDTO) any(), (Locale) any());
  	}

    @Test
   	public void shouldReturnTrueWhenhasUpdatePassword() throws IOException, Exception {
   		// Given
       	final User user = createUser();

   		// When
   		when(userService.hasChangedPassword(anyLong())).thenReturn(true);

   		// Then
   		mockMvc.perform(get("/api/users/pass/1")
   				.contentType(TestUtil.APPLICATION_JSON_UTF8)
   				.content(TestUtil.convertObjectToJsonBytes(user)))
   		        .andExpect(status().isOk())
   		     .andExpect(jsonPath("$").isBoolean());

   		verify(userService, times(1)).hasChangedPassword(anyLong());
   	}
}
