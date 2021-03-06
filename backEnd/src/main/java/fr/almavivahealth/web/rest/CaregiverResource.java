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
import fr.almavivahealth.service.CaregiverService;
import fr.almavivahealth.service.dto.CaregiverDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Caregiver.
 *
 * @author christopher
 * @version 17
 */
@Api("Caregiver")
@RestController
@RequestMapping("/api")
public class CaregiverResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(CaregiverResource.class);

	private final CaregiverService caregiverService;

	@Autowired
	public CaregiverResource(final CaregiverService caregiverService) {
		this.caregiverService = caregiverService;
	}

	/**
	 * POST /caregivers : Create a new caregiver.
	 *
	 * @param caregiverDTO the caregiverDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         caregiverDTO, or with status 400 (Bad Request) if the caregiver has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new caregiver.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DIET')")
	@PostMapping("/caregivers")
	public ResponseEntity<CaregiverDTO> createCaregiver(@Valid @RequestBody final CaregiverDTO caregiver)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Caregiver : {}", caregiver);
		if (caregiver.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new caregiver cannot already have an ID idexists " + caregiver.getId());
		}
		final CaregiverDTO result = caregiverService.save(caregiver);
		return ResponseEntity.created(new URI("/api/caregivers/" + result.getId())).body(result);
	}

	/**
	 * PUT /caregivers : Update a caregiver.
	 *
	 * @param caregiverDTO the caregiverDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         caregiverDTO, or with status 400 (Bad Request) if the caregiver has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a caregiver.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DIET')")
	@PutMapping("/caregivers")
	public ResponseEntity<CaregiverDTO> updateCaregiver(@Valid @RequestBody final CaregiverDTO caregiverDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Caregiver : {}", caregiverDTO);
		if (caregiverDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A caregiver must have an ID idexists " + caregiverDTO.getId());
		}
		final CaregiverDTO result = caregiverService.update(caregiverDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /caregivers : Get all the caregivers.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of caregivers in body
	 * or with status 204 (No Content) if there is no caregiver.
	 *
	 */
	@ApiOperation("Get all the caregivers.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/caregivers")
	public ResponseEntity<List<CaregiverDTO>> getAllCaregivers() {
		LOGGER.debug("REST request to get All Caregivers");
		final List<CaregiverDTO> caregivers = caregiverService.findAll();
		if (caregivers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(caregivers);
	}

	/**
	 * GET /caregivers/:id : Get the "id" caregiver.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the caregiver does not exist.
	 *
	 */
	@ApiOperation("Get the \"id\" caregiver.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/caregivers/{id}")
	public ResponseEntity<CaregiverDTO> getCaregiver(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Caregiver : {}", id);
		final Optional<CaregiverDTO> result = caregiverService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /caregivers/:id : Delete the "id" caregiver.
	 *
	 * @param id the id of the caregiverDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" caregiver.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DIET')")
	@DeleteMapping("/caregivers/{id}")
	public ResponseEntity<Void> deleteCaregiver(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Caregiver : {}", id);
		caregiverService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * GET /caregivers/floor/{number} : Get all the caregivers for floor.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of caregivers in body
	 * or with status 204 (No Content) if there is no caregiver.
	 *
	 */
	@ApiOperation("Get all the caregivers for floor.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/caregivers/floor/{number}")
	public ResponseEntity<List<CaregiverDTO>> getAllByFloorNumber(@PathVariable final Integer number) {
		LOGGER.debug("REST request to get all Caregivers for floor: {}", number);
		final List<CaregiverDTO> caregivers = caregiverService.findAllByFloorNumber(number);
		if (caregivers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(caregivers);
	}


	/**
	 * GET /caregivers/user/:userId : Get the "userId" caregiver.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the caregiver does not exist.
	 *
	 */
	@ApiOperation("Get the \"userId\" caregiver.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/caregivers/user/{userId}")
	public ResponseEntity<CaregiverDTO> getCaregiverByUserId(@PathVariable final Long userId) {
		LOGGER.debug("REST request to get Caregiver by user id : {}", userId);
		final Optional<CaregiverDTO> result = caregiverService.findByUserId(userId);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
