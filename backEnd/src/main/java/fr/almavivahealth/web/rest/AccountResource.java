package fr.almavivahealth.web.rest;

import static fr.almavivahealth.constants.ErrorMessage.ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_OCCURRED_WHILE_TRYING_TO_CREATE_AN_USER;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_OCCURRED_WHILE_TRYING_TO_UPDATE_AN_USER;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_USER_MUST_HAVE_AN_ID;
import static fr.almavivahealth.constants.ErrorMessage.THE_USER_DOES_NOT_EXIST;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.domain.entity.User;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.service.dto.UserPassDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing the current user's account.
 * @author christopher
 */
@Api("Account")
@RestController
@RequestMapping("/api")
public class AccountResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

	private final UserService userService;

	private final MessageSource messageSource;

	@Autowired
	public AccountResource(final UserService userService, final MessageSource messageSource) {
		this.userService = userService;
		this.messageSource = messageSource;
	}

	/**
	 * POST  /register : create a new user.
	 *
	 * @param userDTO the user
	 * @param locale the locale
	 * @return ResponseEntity
	 * @throws URISyntaxException the URI syntax exception
	 * @throws DailyFollowUpException the daily follow up exception
	 */
    @ApiOperation("Create a new user.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 422, message = "Unprocessable entity")
            })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(
    		@RequestBody @Valid final UserDTO userDTO, @ApiIgnore final Locale locale)
    		throws URISyntaxException, DailyFollowUpException {
    	LOGGER.debug("REST request to create User: {}", userDTO);
    	if (null != userDTO.getId()) {
    		throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
    				messageSource.getMessage(ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID, null, locale));
    	}
    	try {
    		final UserDTO userCreated = userService.save(userDTO);
    		return ResponseEntity.created(new URI("/api/users/" + userCreated.getId()))
    				.body(userCreated);
    	} catch (final DailyFollowUpException e) {
    		throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				messageSource.getMessage(ERROR_OCCURRED_WHILE_TRYING_TO_CREATE_AN_USER, null, locale), e);
    	}
    }

    /**
	 * POST  /update : update an user.
	 *
	 * @param userDTO the user
	 * @param locale the locale
	 * @return ResponseEntity
	 * @throws URISyntaxException the URI syntax exception
	 * @throws DailyFollowUpException the daily follow up exception
	 */
    @ApiOperation("Update an user.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 422, message = "Unprocessable entity")
            })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
    @PutMapping("/users/update")
    public ResponseEntity<UserDTO> updateUser(
    		@RequestBody @Valid final UserDTO userDTO, @ApiIgnore final Locale locale)
    		throws URISyntaxException, DailyFollowUpException {
    	LOGGER.debug("REST request to update User: {}", userDTO);
    	if (null == userDTO.getId()) {
    		throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
    				messageSource.getMessage(ERROR_USER_MUST_HAVE_AN_ID, null, locale));
    	}
    	try {
    		final UserDTO userUpdated = userService.update(userDTO);
    		return ResponseEntity.ok().body(userUpdated);
    	} catch (final DailyFollowUpException e) {
    		throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
    				messageSource.getMessage(ERROR_OCCURRED_WHILE_TRYING_TO_UPDATE_AN_USER, null, locale), e);
    	}
    }

    /**
	 * DELETE /users/:id : Delete the "id" user.
	 *
	 * @param id the id of the userDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
     * @throws DailyFollowUpException
	 */
	@ApiOperation("Delete the \"id\" user.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable final Long id) throws DailyFollowUpException {
		LOGGER.debug("REST request to delete User : {}", id);
		final Optional<User> patient = userService.delete(id);
		if (!patient.isPresent()) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"No user was found for this id");
		}
		return ResponseEntity.noContent().build();
	}

    /**
     * GET  /authenticate : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request
     * @return the login if the user is authenticated
     */
    @ApiOperation("Check if the user is authenticated, and return its login.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        })
    @GetMapping("/authenticate")
    public ResponseEntity<String> isAuthenticated(final HttpServletRequest request) {
        LOGGER.debug("REST request to check if the current user is authenticated");
        return ResponseEntity.ok().body(request.getRemoteUser());
    }

    /**
     * POST  /register/profilePicture/{userId} : upload file from userId.
     *
     * @param file the file
     * @param userId the user id
     * @return String the status
     * @throws DailyFollowUpException the daily follow up exception
     */
    @ApiOperation("Upload profile picture from userId.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server"),
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
    @PostMapping("/register/profilePicture/{userId}")
	public ResponseEntity<HttpStatus> uploadFile(
			@RequestPart final MultipartFile file, @PathVariable final Long userId)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to upload file");
		try {
			userService.uploadProfilePicture(file, userId);
			return ResponseEntity.ok().body(HttpStatus.OK);
		} catch (final DailyFollowUpException e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"An error occurred while trying to upload the picture profile", e);
		}
	}

    /**
     * GET  /users/profilePicture/{userId} : retrieve profile picture from userId.
     *
     * @param userId the user id
     * @return the profilePicture
     */
    @ApiOperation("Retrieve profile picture from userId.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping(
			value = "/users/profilePicture/{userId}",
			produces = { "image/jpg", "image/jpeg", "image/gif", "image/png", "image/tif" })
	public ResponseEntity<byte[]> getProfilePicture(@PathVariable final Long userId) {
		LOGGER.debug("REST request to get profile picture");
		final byte[] profilePicture = userService.findProfilePicture(userId);
		return ResponseEntity.ok().body(profilePicture);
	}

	 /**
	 * GET /users/pass : Update password of user.
	 *
	 * @param userPassDTO the user pass DTO
	 * @param locale      the locale
	 * @return HttpStatus
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Update password of user.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PatchMapping("/users/pass")
	public ResponseEntity<HttpStatus> updatePassword(
			@RequestBody final UserPassDTO userPassDTO, @ApiIgnore final Locale locale) throws DailyFollowUpException {
		final Optional<User> user = userService.updatePassword(userPassDTO , locale);
		if (!user.isPresent()) {
			throw new DailyFollowUpException(
					HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(THE_USER_DOES_NOT_EXIST, null, locale));
		}
		return ResponseEntity.ok().body(HttpStatus.OK);
	}

	 /**
	 * GET /users/pass/userId : Returns true if user has updated its password.
	 *
	 * @param userId the user id
	 * @return Boolean
	 */
	@ApiOperation("Returns true if user has updated its password.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/users/pass/{userId}")
	public ResponseEntity<Boolean> hasUpdatePassword(@PathVariable final Long userId) {
		final boolean hasChangedPassword = userService.hasChangedPassword(userId);
		return ResponseEntity.ok().body(hasChangedPassword);
	}
}
