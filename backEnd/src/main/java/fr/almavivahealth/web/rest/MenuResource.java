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
import fr.almavivahealth.service.MenuService;
import fr.almavivahealth.service.dto.MenuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * REST controller for managing Menu.
 * 
 * @author christopher
 */
@Api("Menu")
@RestController
@RequestMapping("/api")
public class MenuResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuResource.class);
	
	private final MenuService menuService;

	@Autowired
	public MenuResource(final MenuService menuService) {
		this.menuService = menuService;
	}
	
	/**
	 * POST /menus : Create a new menu.
	 *
	 * @param menuDTO the menuDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         menuDTO, or with status 400 (Bad Request) if the menu has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new menu.")
	@PostMapping("/menus")
	public ResponseEntity<MenuDTO> createMenu(@Valid @RequestBody final MenuDTO menuDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Menu : {}", menuDTO);
		if (menuDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new menu cannot already have an ID idexists " + menuDTO.getId());
		}
		final MenuDTO result = menuService.save(menuDTO);
		return ResponseEntity.created(new URI("/api/menus/" + result.getId())).body(result);
	}
	
	/**
	 * PUT /menus : Update a menu.
	 *
	 * @param menuDTO the menuDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         menuDTO, or with status 400 (Bad Request) if the menu has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a menu.")
	@PutMapping("/menus")
	public ResponseEntity<MenuDTO> updateMenu(@Valid @RequestBody final MenuDTO menuDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Menu : {}", menuDTO);
		if (menuDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A menu must have an ID idexists " + menuDTO.getId());
		}
		final MenuDTO result = menuService.update(menuDTO);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * GET /menus : Get all the menus.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of menus in body
	 * or with status 204 (No Content) if there is no menu.
	 *         
	 */
	@ApiOperation("Get all the menus.")
	@GetMapping("/menus")
	public ResponseEntity<List<MenuDTO>> getAllMenus() {
		LOGGER.debug("REST request to get All Menus");
		final List<MenuDTO> menus = menuService.findAll();
		if (menus.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(menus);
	}
	
	/**
	 * GET /menus/:id : Get the "id" menu.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the menu does not exist.
	 *         
	 */
	@ApiOperation("Get the \"id\" menu.")
	@GetMapping("/menus/{id}")
	public ResponseEntity<MenuDTO> getMenu(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Menu : {}", id);
		final Optional<MenuDTO> result = menuService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /menus/:id : Delete the "id" menu.
	 *
	 * @param id the id of the menuDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" menu.")
	@DeleteMapping("/menus/{id}")
	public ResponseEntity<Void> deleteMenu(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Menu : {}", id);
		menuService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
