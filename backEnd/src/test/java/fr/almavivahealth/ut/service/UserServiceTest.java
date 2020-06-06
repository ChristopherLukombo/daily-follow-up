package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.almavivahealth.dao.RoleRepository;
import fr.almavivahealth.dao.UserRepository;
import fr.almavivahealth.domain.entity.Role;
import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.domain.enums.RoleName;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.dto.UserPassDTO;
import fr.almavivahealth.service.impl.user.UserServiceImpl;
import fr.almavivahealth.service.mapper.UserMapper;
import fr.almavivahealth.service.propeties.UserProperties;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	private static final String PSEUDO = "Damien";

	private static final long ID = 1L;

	private static final String IMAGES_FOLDER = "./images";

    private static final String IMAGE_DIRECTORY = ".";

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private UserMapper userMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
    private UserProperties userProperties;

	@Mock
	private MessageSource messageSource;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	private static User createUser() {
		final User user = new User();
		user.setId(ID);
		user.setPseudo(PSEUDO);
		return user;
	}

	private static UserDTO createUserDTO() {
		return UserDTO.builder()
				.id(ID)
				.build();
	}

	private static Role createRole() {
		final Role role = new Role();
        role.setId(ID);
        role.setName(RoleName.ROLE_ADMIN.name());
		return role;
	}

	@Test
	public void shouldSaveUserWhenIsOk() throws DailyFollowUpException {
		// Given
		final User user = createUser();
		final Role role = createRole();
		final UserDTO userDTO = createUserDTO();
		userDTO.setRoleName(RoleName.ROLE_ADMIN.name());

		// When
		when(roleRepository.findByName(anyString())).thenReturn(Optional.ofNullable(role));
		when(userRepository.save((User) any())).thenReturn(user);
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		assertThat(userServiceImpl.save(userDTO)).isEqualTo(userDTO);
	}

	@Test
	public void shouldSaveWhenUserHasNoRoleIsKo() throws DailyFollowUpException {
		// Given
		final UserDTO userDTO = createUserDTO();

		// Then
		assertThatThrownBy(() -> userServiceImpl.save(userDTO))
		.isInstanceOf(DailyFollowUpException.class);
	}

	@Test
	public void shouldRegisterWhenIsKo() {
		// Given
		final UserDTO userDTO = null;

		// Then
		assertThatThrownBy(() -> userServiceImpl.save(userDTO))
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldUpdateUserWithOldPassword() throws DailyFollowUpException {
		// Given
		final User user = createUser();
		user.setPassword("test");
		final Role role = createRole();
		final UserDTO userDTO = createUserDTO();

		userDTO.setRoleName(RoleName.ROLE_ADMIN.name());

		// When
		when(userMapper.userDTOToUser((UserDTO) any())).thenReturn(user);
		when(roleRepository.findByName(anyString())).thenReturn(Optional.ofNullable(role));
		when(userRepository.saveAndFlush((User) any())).thenReturn(user);
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		assertThat(userServiceImpl.update(userDTO)).isNotNull();
	}

	@Test
	public void shouldUpdateUserWithNewPassword() throws DailyFollowUpException {
		// Given
		final User user = createUser();
		user.setPassword("test");
		final Role role = createRole();
		final UserDTO userDTO = createUserDTO();
		userDTO.setPassword("test");

		userDTO.setRoleName(RoleName.ROLE_ADMIN.name());

		// When
		when(userMapper.userDTOToUser((UserDTO) any())).thenReturn(user);
		when(roleRepository.findByName(anyString())).thenReturn(Optional.ofNullable(role));
		when(userRepository.saveAndFlush((User) any())).thenReturn(user);
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		assertThat(userServiceImpl.update(userDTO)).isNotNull();
	}

	@Test
	public void shouldDeleteUser() throws DailyFollowUpException {
		// Given
		final User user = createUser();
		user.setPassword("test");

		// When
		when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
		when(userRepository.saveAndFlush((User) any())).thenReturn(user);

		// Then
		assertThat(userServiceImpl.delete(ID)).isPresent();
	}
	@Test
	public void shouldReturnOptionalEmptyUserWhenTryingToDelete() throws DailyFollowUpException {
		// Given
		final User user = null;

		// When
		when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThat(userServiceImpl.delete(ID)).isNotPresent();
	}

	@Test
	public void shouldFindOneByPseudoWhenIsOk() {
		// Given
		final User user = createUser();
		final Role role = createRole();
		user.setRole(role);
		final UserDTO userDTO = createUserDTO();

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));
		when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

		// Then
		assertThat(userServiceImpl.findOneByPseudo(PSEUDO)).isNotNull();
	}

	@Test
	public void shouldFindOneByPseudoWhenIsNull() {
		// Given
		final User user = null;

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThat(userServiceImpl.findOneByPseudo(PSEUDO)).isNull();
	}

	@Test
	public void shouldUploadProfilePictureWhenIsOK() throws IOException, DailyFollowUpException {
		// Given
		final MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain",
				"some xml".getBytes());
		final User user = createUser();
		final String imageDirectory = IMAGE_DIRECTORY + "/images/";

		// When
		when(userProperties.getPathProfiles()).thenReturn(imageDirectory);
		doNothing().when(userRepository).setImageUrl(anyString(), anyLong());

		// Then
		userServiceImpl.uploadProfilePicture(file, user.getId());

		verify(userProperties, times(1)).getPathProfiles();
		verify(userRepository, times(1)).setImageUrl(anyString(), anyLong());
	}

	@Test
	public void shouldFindProfilePictureWhenIsOk() throws IOException {
		// Given
    	final String imageDirectory = IMAGE_DIRECTORY + "/images/";
    	final User user = createUser();
    	user.setImageUrl("filename.txt");
    	createFoldersAndFile();

    	// When
    	when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
    	when(userProperties.getPathProfiles()).thenReturn(imageDirectory);

    	// Then
		assertThat(userServiceImpl.findProfilePicture(ID)).isNotEmpty();
	}

	@Test
	public void shouldFindProfilePictureWhenUserNotExist() throws IOException {
    	// When
    	when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

    	// Then
		assertThat(userServiceImpl.findProfilePicture(ID)).isEmpty();
	}

	@Test
	public void shouldFindProfilePictureWhenIsEmpty() throws IOException {
		// Given
    	final String imageDirectory = IMAGE_DIRECTORY + "/ded/";
    	final User user = createUser();
    	user.setImageUrl("filename.txt");

    	// When
    	when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
    	when(userProperties.getPathProfiles()).thenReturn(imageDirectory);

    	// Then
		assertThat(userServiceImpl.findProfilePicture(ID)).isEmpty();
	}

	@Test
	public void shouldUpdatePassword() {
		// Given
		final User user = createUser();
		final Role role = createRole();
		user.setRole(role);
		final UserPassDTO userPassDTO = new UserPassDTO();
		userPassDTO.setPassword("test");
		userPassDTO.setUserId(user.getId());

		// When
		when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
		when(passwordEncoder.encode(anyString())).thenReturn("test");
		when(userRepository.saveAndFlush((User) any())).thenReturn(user);

		// Then
		assertThat(userServiceImpl.updatePassword(userPassDTO, (Locale) any())).isPresent();
	}

	@Test
	public void shouldReturnOptionalEmptyWhenUserNotExist() {
		// Given
		final User user = null;
		final UserPassDTO userPassDTO = new UserPassDTO();
		userPassDTO.setUserId(1L);
		userPassDTO.setPassword("test");

		// When
		when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThat(userServiceImpl.updatePassword(userPassDTO, (Locale) any())).isNotPresent();
	}

	@Test
	public void shouldReturnTrueWhenHasChangedPassword() {
		// Given
		final User user = createUser();
		user.setHasChangedPassword(true);

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThat(userServiceImpl.hasChangedPassword("hhdzs")).isTrue();
	}

	@Test
	public void shouldReturnFalseWhenHasChangedPassword() {
		// Given
		final User user = createUser();
		user.setHasChangedPassword(false);

		// When
		when(userRepository.findOneByPseudoIgnoreCase(anyString())).thenReturn(Optional.ofNullable(user));

		// Then
		assertThat(userServiceImpl.hasChangedPassword("hhdzs")).isFalse();
	}

	@Test
	public void shouldFindAllActiveUsers() {
		// Given
		final List<User> users = Arrays.asList(createUser());

		// When
		when(userRepository.findAllByStatusTrueOrderByIdDesc()).thenReturn(users);

		// Then
		assertThat(userServiceImpl.findAllActiveUsers()).isNotEmpty();
	}

	@Test
	public void shouldReturnsEmptyListWhenTryingToFindAllActiveUsers() {
		// Given
		final List<User> users = Collections.emptyList();

		// When
		when(userRepository.findAllByStatusTrueOrderByIdDesc()).thenReturn(users);

		// Then
		assertThat(userServiceImpl.findAllActiveUsers()).isEmpty();
	}

	private void createFoldersAndFile() throws IOException {
		Files.createDirectory(Paths.get("./images"));
		Files.createDirectory(Paths.get("./images/1"));
		final File file = new File("./images/1/filename.txt");
		file.createNewFile();
		final String str = "Hello";
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(str);
		}
	}

    @Before
    @After
    public void deleteFolder() throws IOException {
        if (Files.isDirectory(Paths.get(IMAGES_FOLDER))) {
            deleteFolderAndContents();
        }
    }

    public void deleteFolderAndContents() throws IOException {
        final List<String> fileNames = Arrays.asList(IMAGES_FOLDER);

        for (final String fileName: fileNames) {
        	final File file = new File(fileName);
        	if (file.exists()) {
        		FileUtils.forceDelete(new File(fileName));
        	}
        }
    }

}
