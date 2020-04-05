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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param lang 
     * @return ResponseEntity
     * @throws URISyntaxException 
	 * @throws DailyFollowUpException 
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
		UserDTO result;
		try {
			result = userService.save(userDTO);
		} catch (final Exception e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"Error occurred while trying to create a user", e);
		}
		return ResponseEntity.created(new URI("/api/users/" + result.getId())).body(result);
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
}
