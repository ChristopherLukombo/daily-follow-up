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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.DietService;
import fr.almavivahealth.service.dto.DietDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Diet.
 * 
 * @author christopher
 */
@Api("Diet")
@RestController
@RequestMapping("/api")
public class DietResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DietResource.class);
	
	private final DietService dietService;
	
	@Autowired
	public DietResource(final DietService dietService) {
		this.dietService = dietService;
	}

	/**
	 * POST /diets : Create a new diet.
	 *
	 * @param dietDTO the dietDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         dietDTO, or with status 400 (Bad Request) if the diet has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new diet.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PostMapping("/diets")
	public ResponseEntity<DietDTO> createDiet(@Valid @RequestBody final DietDTO dietDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Diet : {}", dietDTO);
		if (dietDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new diet cannot already have an ID idexists " + dietDTO.getId());
		}
		final DietDTO result = dietService.save(dietDTO);
		return ResponseEntity.created(new URI("/api/diets/" + result.getId())).body(result);
	}
	
	/**
	 * PUT /diets : Update a diet.
	 *
	 * @param dietDTO the dietDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         dietDTO, or with status 400 (Bad Request) if the diet has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a diet.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PutMapping("/diets")
	public ResponseEntity<DietDTO> updateDiet(@Valid @RequestBody final DietDTO dietDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Diet : {}", dietDTO);
		if (dietDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A diet must have an ID idexists " + dietDTO.getId());
		}
		final DietDTO result = dietService.update(dietDTO);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * GET /diets : Get all the diets.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of diets in body
	 * or with status 204 (No Content) if there is no diet.
	 *         
	 */
	@ApiOperation("Get all the diets.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/diets")
	public ResponseEntity<List<DietDTO>> getAllDiets() {
		LOGGER.debug("REST request to get All Diets");
		final List<DietDTO> diets = dietService.findAll();
		if (diets.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(diets);
	}
	
	/**
	 * GET /diets/:id : Get the "id" diet.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the diet does not exist.
	 *         
	 */
	@ApiOperation("Get the \"id\" diet.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/diets/{id}")
	public ResponseEntity<DietDTO> getDiet(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Diet : {}", id);
		final Optional<DietDTO> result = dietService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /diets/:id : Delete the "id" diet.
	 *
	 * @param id the id of the dietDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" diet.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@DeleteMapping("/diets/{id}")
	public ResponseEntity<Void> deleteDiet(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Diet : {}", id);
		dietService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
