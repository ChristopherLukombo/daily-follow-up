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
import fr.almavivahealth.service.MomentDayService;
import fr.almavivahealth.service.dto.MomentDayDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing MomentDay.
 *
 * @author christopher
 * @version 17
 */
@Api("MomentDay")
@RestController
@RequestMapping("/api")
public class MomentDayResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MomentDayResource.class);

	private final MomentDayService momentDayService;

	@Autowired
	public MomentDayResource(final MomentDayService momentDayService) {
		this.momentDayService = momentDayService;
	}

	/**
	 * POST /momentDays : Create a new momentDay.
	 *
	 * @param momentDayDTO the momentDayDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         momentDayDTO, or with status 400 (Bad Request) if the momentDay has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new momentDay.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@PostMapping("/momentDays")
	public ResponseEntity<MomentDayDTO> createMomentDay(@Valid @RequestBody final MomentDayDTO momentDayDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save MomentDay : {}", momentDayDTO);
		if (momentDayDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new momentDay cannot already have an ID idexists " + momentDayDTO.getId());
		}
		final MomentDayDTO result = momentDayService.save(momentDayDTO);
		return ResponseEntity.created(new URI("/api/momentDays/" + result.getId())).body(result);
	}

	/**
	 * PUT /momentDays : Update a momentDay.
	 *
	 * @param momentDayDTO the momentDayDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         momentDayDTO, or with status 400 (Bad Request) if the momentDay has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a momentDay.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@PutMapping("/momentDays")
	public ResponseEntity<MomentDayDTO> updateMomentDay(@Valid @RequestBody final MomentDayDTO momentDayDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update MomentDay : {}", momentDayDTO);
		if (momentDayDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A momentDay must have an ID idexists " + momentDayDTO.getId());
		}
		final MomentDayDTO result = momentDayService.update(momentDayDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /momentDays : Get all the momentDays.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of momentDays in body
	 * or with status 204 (No Content) if there is no momentDay.
	 *
	 */
	@ApiOperation("Get all the momentDays.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping("/momentDays")
	public ResponseEntity<List<MomentDayDTO>> getAllMomentDays() {
		LOGGER.debug("REST request to get All MomentDays");
		final List<MomentDayDTO> momentDays = momentDayService.findAll();
		if (momentDays.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(momentDays);
	}

	/**
	 * GET /momentDays/:id : Get the "id" momentDay.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the momentDay does not exist.
	 *
	 */
	@ApiOperation("Get the \"id\" momentDay.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping("/momentDays/{id}")
	public ResponseEntity<MomentDayDTO> getMomentDay(@PathVariable final Long id) {
		LOGGER.debug("REST request to get MomentDay : {}", id);
		final Optional<MomentDayDTO> result = momentDayService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /momentDays/:id : Delete the "id" momentDay.
	 *
	 * @param id the id of the momentDayDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" momentDay.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@DeleteMapping("/momentDays/{id}")
	public ResponseEntity<Void> deleteMomentDay(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete MomentDay : {}", id);
		momentDayService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
