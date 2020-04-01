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
import fr.almavivahealth.service.TypeMealService;
import fr.almavivahealth.service.dto.TypeMealDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing TypeMeal.
 * 
 * @author christopher
 */
@Api("TypeMeal")
@RestController
@RequestMapping("/api")
public class TypeMealResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeMealResource.class);
	
	private final TypeMealService typeMealService;

	@Autowired
	public TypeMealResource(final TypeMealService typeMealService) {
		this.typeMealService = typeMealService;
	}
	
	/**
	 * POST /typeMeals : Create a new typeMeal.
	 *
	 * @param typeMealDTO the typeMealDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         typeMealDTO, or with status 400 (Bad Request) if the typeMeal has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new typeMeal.")
	@PostMapping("/typeMeals")
	public ResponseEntity<TypeMealDTO> createTypeMeal(@Valid @RequestBody final TypeMealDTO typeMealTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save TypeMeal : {}", typeMealTO);
		if (typeMealTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new typeMeal cannot already have an ID idexists " + typeMealTO.getId());
		}
		final TypeMealDTO result = typeMealService.save(typeMealTO);
		return ResponseEntity.created(new URI("/api/typeMeals/" + result.getId())).body(result);
	}

	/**
	 * PUT /typeMeals : Update a typeMeal.
	 *
	 * @param typeMealDTO thetypeMealDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         typeMealDTO, or with status 400 (Bad Request) if the typeMeal has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a typeMeal.")
	@PutMapping("/typeMeals")
	public ResponseEntity<TypeMealDTO> updateTypeMeal(@Valid @RequestBody final TypeMealDTO typeMealDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update TypeMeal : {}", typeMealDTO);
		if (typeMealDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A typeMeal must have an ID idexists " + typeMealDTO.getId());
		}
		final TypeMealDTO result = typeMealService.update(typeMealDTO);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * GET /typeMeals : Get all the typeMeals.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of typeMeals in body
	 * or with status 204 (No Content) if there is no typeMeal.
	 *         
	 */
	@ApiOperation("Get all the typeMeals.")
	@GetMapping("/typeMeals")
	public ResponseEntity<List<TypeMealDTO>> getAllTypeMeals() {
		LOGGER.debug("REST request to get all TypeMeals");
		final List<TypeMealDTO> typeMeals = typeMealService.findAll();
		if (typeMeals.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(typeMeals);
	}
	
	/**
	 * GET /typeMeals/:id : Get the "id" typeMeal.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the typeMeal does not exist.
	 *         
	 */
	@ApiOperation("Get the \"id\" typeMeal.")
	@GetMapping("/typeMeals/{id}")
	public ResponseEntity<TypeMealDTO> getTypeMeal(@PathVariable final Long id) {
		LOGGER.debug("REST request to get TypeMeal : {}", id);
		final Optional<TypeMealDTO> result = typeMealService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}
	
	/**
	 * DELETE /typeMeals/:id : Delete the "id" typeMeal.
	 *
	 * @param id the id of the typeMealDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" typeMeal.")
	@DeleteMapping("/typeMeals/{id}")
	public ResponseEntity<Void> deleteTypeMeal(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete TypeMeal : {}", id);
		typeMealService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
