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
import fr.almavivahealth.service.FloorService;
import fr.almavivahealth.service.dto.FloorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Floor.
 *
 * @author christopher
 */
@Api("Floor")
@RestController
@RequestMapping("/api")
public class FloorResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(FloorResource.class);

	private final FloorService floorService;

	@Autowired
	public FloorResource(final FloorService floorService) {
		this.floorService = floorService;
	}

	/**
	 * POST /floors : Create a new floor.
	 *
	 * @param floorDTO the floorDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         floorDTO, or with status 400 (Bad Request) if the floor has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new floor.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/floors")
	public ResponseEntity<FloorDTO> createFloor(@Valid @RequestBody final FloorDTO floorDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Floor : {}", floorDTO);
		if (floorDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new floor cannot already have an ID idexists " + floorDTO.getId());
		}
		final FloorDTO result = floorService.save(floorDTO);
		return ResponseEntity.created(new URI("/api/floors/" + result.getId())).body(result);
	}

	/**
	 * PUT /floors : Update a floor.
	 *
	 * @param floorDTO the floorDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         floorDTO, or with status 400 (Bad Request) if the floor has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a floor.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PutMapping("/floors")
	public ResponseEntity<FloorDTO> updateFloor(@Valid @RequestBody final FloorDTO floorDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Floor : {}", floorDTO);
		if (floorDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A floor must have an ID idexists " + floorDTO.getId());
		}
		final FloorDTO result = floorService.update(floorDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /floors : Get all the floors.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of floors in body
	 * or with status 204 (No Content) if there is no floor.
	 *
	 */
	@ApiOperation("Get all the floors.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/floors")
	public ResponseEntity<List<FloorDTO>> getAllFloors() {
		LOGGER.debug("REST request to get All Floors");
		final List<FloorDTO> floors = floorService.findAll();
		if (floors.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(floors);
	}

	/**
	 * GET /floors/:id : Get the "id" floor.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the floor does not exist.
	 *
	 */
	@ApiOperation("Get the \"id\" floor.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/floors/{id}")
	public ResponseEntity<FloorDTO> getFloor(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Floor : {}", id);
		final Optional<FloorDTO> result = floorService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /floors/:id : Delete the "id" floor.
	 *
	 * @param id the id of the floorDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" floor.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping("/floors/{id}")
	public ResponseEntity<Void> deleteFloor(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Floor : {}", id);
		floorService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
