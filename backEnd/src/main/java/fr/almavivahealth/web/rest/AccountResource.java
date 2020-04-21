package fr.almavivahealth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.config.ErrorMessage;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.UserService;
import fr.almavivahealth.service.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	 * POST  /create : create a new user.
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
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid final UserDTO userDTO, final Locale locale)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to create User: {}", userDTO);
		if (null != userDTO.getId()) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					messageSource.getMessage(ErrorMessage.ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID, null, locale));
		}
		try {
			final UserDTO userCreated = userService.save(userDTO);
			return ResponseEntity.created(new URI("/api/users/" + userCreated.getId()))
					.body(userCreated);
		} catch (final Exception e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Error occurred while trying to create a user", e);
		}
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
    @PostMapping("/register/profilePicture/{userId}")
	public ResponseEntity<HttpStatus> uploadFile(
			@RequestPart final MultipartFile file,
			@PathVariable final Long userId)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to upload file");
		try {
			userService.uploadProfilePicture(file, userId);
		} catch (final Exception e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"An error occurred while trying to upload the picture profile", e);
		}
		return ResponseEntity.ok().body(HttpStatus.OK);
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
	@GetMapping(
			value = "/users/profilePicture/{userId}",
			produces = { "image/jpg", "image/jpeg", "image/gif", "image/png", "image/tif" })
	public ResponseEntity<byte[]> getProfilePicture(@PathVariable final Long userId) {
		LOGGER.debug("REST request to get profile picture");
		final byte[] profilePicture = userService.findProfilePicture(userId);
		return ResponseEntity.ok().body(profilePicture);
	}
    
}
