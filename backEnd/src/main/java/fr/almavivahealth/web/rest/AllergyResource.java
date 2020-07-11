package fr.almavivahealth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.AllergyService;
import fr.almavivahealth.service.dto.AllergyDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Allergy.
 *
 * @author christopher
 * @version 16
 */
@Api("Allergy")
@RestController
@RequestMapping("/api")
public class AllergyResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AllergyResource.class);

	private final AllergyService allergyService;

	@Autowired
	public AllergyResource(final AllergyService allergyService) {
		this.allergyService = allergyService;
	}

	/**
	 * POST /allergys : Create a new allergy.
	 *
	 * @param allergyDTO the allergyDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         allergyDTO, or with status 400 (Bad Request) if the allergy has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new allergy.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 422, message = "Unprocessable entity")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/allergys")
	public ResponseEntity<AllergyDTO> createAllergy(@Valid @RequestBody final AllergyDTO allergyDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Allergy : {}", allergyDTO);
		if (allergyDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new allergy cannot already have an ID idexists " + allergyDTO.getId());
		}
		final AllergyDTO result = allergyService.save(allergyDTO);
		return ResponseEntity.created(new URI("/api/allergys/" + result.getId())).body(result);
	}

	/**
	 * PUT /allergys : Update a allergy.
	 *
	 * @param allergyDTO the allergyDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         allergyDTO, or with status 400 (Bad Request) if the allergy has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a allergy.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 422, message = "Unprocessable entity")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PutMapping("/allergys")
	public ResponseEntity<AllergyDTO> updateAllergy(@Valid @RequestBody final AllergyDTO allergyDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Allergy : {}", allergyDTO);
		if (allergyDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A allergy must have an ID idexists " + allergyDTO.getId());
		}
		final AllergyDTO result = allergyService.update(allergyDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /allergys : Get all the allergys.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of allergys in body
	 * or with status 204 (No Content) if there is no allergy.
	 *
	 */
	@ApiOperation("Get all the allergys.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/allergys")
	public ResponseEntity<List<AllergyDTO>> getAllAllergys() {
		LOGGER.debug("REST request to get All Allergys");
		final List<AllergyDTO> allergys = allergyService.findAll();
		if (allergys.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(allergys);
	}

	/**
	 * GET /allergys/:id : Get the "id" allergy.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the allergy does not exist.
	 *
	 */
	@ApiOperation("Get the \"id\" allergy.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/allergys/{id}")
	public ResponseEntity<AllergyDTO> getAllergy(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Allergy : {}", id);
		final Optional<AllergyDTO> result = allergyService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /allergys/:id : Delete the "id" allergy.
	 *
	 * @param id the id of the allergyDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" allergy.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping("/allergys/{id}")
	public ResponseEntity<Void> deleteAllergy(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Allergy : {}", id);
		allergyService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
