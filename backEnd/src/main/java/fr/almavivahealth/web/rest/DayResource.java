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
import fr.almavivahealth.service.DayService;
import fr.almavivahealth.service.dto.DayDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Day.
 *
 * @author christopher
 */
@Api("Day")
@RestController
@RequestMapping("/api")
public class DayResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DayResource.class);

	private final DayService dayService;

	@Autowired
	public DayResource(final DayService dayService) {
		this.dayService = dayService;
	}

	/**
	 * POST /days : Create a new day.
	 *
	 * @param dayDTO the dayDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         dayDTO, or with status 400 (Bad Request) if the day has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new day.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/days")
	public ResponseEntity<DayDTO> createDay(@Valid @RequestBody final DayDTO day)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Day : {}", day);
		if (day.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new day cannot already have an ID idexists " + day.getId());
		}
		final DayDTO result = dayService.save(day);
		return ResponseEntity.created(new URI("/api/days/" + result.getId())).body(result);
	}

	/**
	 * PUT /days : Update a day.
	 *
	 * @param dayDTO the dayDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         dayDTO, or with status 400 (Bad Request) if the day has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a day.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PutMapping("/days")
	public ResponseEntity<DayDTO> updateDay(@Valid @RequestBody final DayDTO dayDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Day : {}", dayDTO);
		if (dayDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A day must have an ID idexists " + dayDTO.getId());
		}
		final DayDTO result = dayService.update(dayDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /days : Get all the days.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of days in body
	 * or with status 204 (No Content) if there is no day.
	 *
	 */
	@ApiOperation("Get all the days.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/days")
	public ResponseEntity<List<DayDTO>> getAllDays() {
		LOGGER.debug("REST request to get All Days");
		final List<DayDTO> days = dayService.findAll();
		if (days.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(days);
	}

	/**
	 * GET /days/:id : Get the "id" day.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the day does not exist.
	 *
	 */
	@ApiOperation("Get the \"id\" day.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/days/{id}")
	public ResponseEntity<DayDTO> getDay(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Day : {}", id);
		final Optional<DayDTO> result = dayService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /days/:id : Delete the "id" day.
	 *
	 * @param id the id of the dayDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" day.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping("/days/{id}")
	public ResponseEntity<Void> deleteDay(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Day : {}", id);
		dayService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
