package fr.almavivahealth.web.rest;

import static fr.almavivahealth.constants.ErrorMessage.A_MENU_ALREADY_EXISTS_WITH_THE_SAME_CHARACTERISTICS;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.MenuService;
import fr.almavivahealth.service.dto.MenuDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

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

	private final MessageSource messageSource;

	@Autowired
	public MenuResource(final MenuService menuService, final MessageSource messageSource) {
		this.menuService = menuService;
		this.messageSource = messageSource;
	}

	/**
	 * POST /menus : Create a new menu.
	 *
	 * @param menuDTO the menuDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         menuDTO, or with status 400 (Bad Request) if the menu has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Create a new menu.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Created"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/menus")
	public ResponseEntity<MenuDTO> createMenu(@Valid @RequestBody final MenuDTO menuDTO, @ApiIgnore final Locale locale)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Menu : {}", menuDTO);
		if (menuDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new menu cannot already have an ID idexists " + menuDTO.getId());
		}
		if (menuService.checkSpecifications(menuDTO)) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(A_MENU_ALREADY_EXISTS_WITH_THE_SAME_CHARACTERISTICS, null, locale));
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
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Update a menu.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PutMapping("/menus")
	public ResponseEntity<MenuDTO> updateMenu(@Valid @RequestBody final MenuDTO menuDTO, @ApiIgnore final Locale locale)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Menu : {}", menuDTO);
		if (menuDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A menu must have an ID idexists " + menuDTO.getId());
		}
		if (menuService.checkSpecifications(menuDTO)) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(A_MENU_ALREADY_EXISTS_WITH_THE_SAME_CHARACTERISTICS, null, locale));
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
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
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
	 * @param id the id
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the menu does not exist.
	 */
	@ApiOperation("Get the \"id\" menu.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
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
	 * @return the ResponseEntity with status 204 (NO CONTENT)
	 */
	@ApiOperation("Delete the \"id\" menu.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping("/menus/{id}")
	public ResponseEntity<Void> deleteMenu(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Menu : {}", id);
		menuService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * GET /menus/coupons : Generate coupons.
	 *
	 * @param momentName the moment name
	 * @param selectedDate the selected date
	 * @return the ResponseEntity with status 200 (OK)
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Generate coupons")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 500, message = "Internal Server")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping(value = "/menus/coupons", params = { "momentName", "selectedDate" })
	public ResponseEntity<byte[]> generateCoupons(
			@RequestParam final String momentName,
			@ApiParam("YYYY-MM-DD") @DateTimeFormat(iso = ISO.DATE) @RequestParam final LocalDate selectedDate)
					throws DailyFollowUpException {
		LOGGER.debug("REST request to generate coupons");
		try {
			final byte[] pdfCoupons = menuService.generateCoupons(momentName, selectedDate);

			final HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Disposition", "attachment; filename=coupons.pdf");
			responseHeaders.setContentType(MediaType.APPLICATION_PDF);

			return ResponseEntity.ok().headers(responseHeaders).body(pdfCoupons);
		} catch (final DailyFollowUpException e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"An error occurred during the generation of the coupons", e);
		}
	}

	/**
	 * GET /menus/current : Get current menus.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of menus in body
	 * or with status 204 (No Content) if there is no menu.
	 *
	 */
	@ApiOperation("Get current menus.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/menus/current")
	public ResponseEntity<List<MenuDTO>> getCurrentMenus() {
		LOGGER.debug("REST request to get current Menus");
		final List<MenuDTO> menus = menuService.findCurrentMenus();
		if (menus.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(menus);
	}

	/**
	 * GET /menus/future : Get future menus.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of menus in body
	 * or with status 204 (No Content) if there is no menu.
	 *
	 */
	@ApiOperation("Get future menus.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/menus/future")
	public ResponseEntity<List<MenuDTO>> getFutureMenus() {
		LOGGER.debug("REST request to get future Menus");
		final List<MenuDTO> menus = menuService.findFutureMenus();
		if (menus.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(menus);
	}

	/**
	 * GET /menus/past : Get past menus.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of menus in body
	 * or with status 204 (No Content) if there is no menu.
	 *
	 */
	@ApiOperation("Get past menus.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/menus/past")
	public ResponseEntity<List<MenuDTO>> getPastMenus() {
		LOGGER.debug("REST request to get past Menus");
		final List<MenuDTO> menus = menuService.findPastMenus();
		if (menus.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(menus);
	}

	/**
	 * DELETE /menus : Delete the menus by ids.
	 *
	 * @param ids the ids
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the menus by ids.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping(value = "/menus" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteByIds(@RequestBody final List<Long> ids) {
		LOGGER.debug("REST request to delete Menus : {}", ids);
		menuService.deleteByIds(ids);
		return ResponseEntity.noContent().build();
	}
}
