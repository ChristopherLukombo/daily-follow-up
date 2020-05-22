package fr.almavivahealth.web.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.service.DishService;
import fr.almavivahealth.service.dto.DishDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Dish from Ciqual.
 *
 * @author christopher
 */
@Api("Dish")
@RestController
@RequestMapping("/api")
public class DishResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(DishResource.class);

	private final DishService dishService;

    @Autowired
	public DishResource(final DishService dishService) {
		this.dishService = dishService;
	}

	/**
	 * GET /dishes/search : Get the first 5 dishes by name.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of dishes in body
	 * or with status 204 (No Content) if there is no dish.
	 *
	 */
	@ApiOperation("Get the first 5 dishes by name.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping(value = "/dishes/search", params = { "name" })
	public ResponseEntity<List<DishDTO>> getFirst5ByName(@RequestParam final String name) {
		LOGGER.debug("REST request to get first 5 by name: {}", name);
		final List<DishDTO> dishes = dishService.findFirst5ByName(name);
		if (dishes.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(dishes);
	}

	/**
	 * GET /dishes/find/{name} : Get dish by name.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the dish in body
	 * or with status 204 (No Content) if there is no dish.
	 *
	 */
	@ApiOperation("Get dish by name.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping(value = "/dishes/find", params = { "name" })
	public ResponseEntity<DishDTO> getDishByName(@RequestParam final String name) {
		LOGGER.debug("REST request to get Dish by name: {}", name);
		final Optional<DishDTO> dish = dishService.findByName(name);
        if (!dish.isPresent()) {
			return ResponseEntity.noContent().build();
		}
        return ResponseEntity.ok().body(dish.get());
	}
}
