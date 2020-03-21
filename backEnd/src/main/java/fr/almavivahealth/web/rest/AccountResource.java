package fr.almavivahealth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * REST controller for managing the current user's account.
 * @author christopher
 */
@Api(value = "Account")
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
     * POST  /create : create the user.
     *
     * @param userDTO the user
     * @param lang 
     * @return ResponseEntity
     * @throws URISyntaxException 
	 * @throws DailyFollowUpException 
     */
    @ApiOperation("Create the user.")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(
            @RequestBody @Valid final UserDTO userDTO,
            final Locale locale
    ) throws URISyntaxException, DailyFollowUpException {
    	LOGGER.debug("REST request to create User: {}", userDTO);
        if (null != userDTO.getId()) {
        	  throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
                      messageSource.getMessage(ErrorMessage.ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID, null, locale));
        }

        final UserDTO userSaved = userService.save(userDTO);

        return ResponseEntity.created(new URI("/api/users/" + userSaved.getId()))
        		.body(userSaved);
    }
}
