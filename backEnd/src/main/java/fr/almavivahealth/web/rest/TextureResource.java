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
import fr.almavivahealth.service.TextureService;
import fr.almavivahealth.service.dto.TextureDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Texture.
 *
 * @author christopher
 */
@Api("Texture")
@RestController
@RequestMapping("/api")
public class TextureResource {
	private static final Logger LOGGER = LoggerFactory.getLogger(TextureResource.class);

	private final TextureService textureService;

    @Autowired
	public TextureResource(final TextureService textureService) {
		this.textureService = textureService;
	}

	/**
	 * POST /textures : Create a new texture.
	 *
	 * @param textureDTO the texturetDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         textureDTO, or with status 400 (Bad Request) if the texture has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
    @ApiOperation("Create a new texture.")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/textures")
	public ResponseEntity<TextureDTO> createTexture(@Valid @RequestBody final TextureDTO textureDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Texture : {}", textureDTO);
		if (textureDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new texture cannot already have an ID idexists " + textureDTO.getId());
		}
		final TextureDTO result = textureService.save(textureDTO);
		return ResponseEntity.created(new URI("/api/textures/" + result.getId())).body(result);
	}

	/**
	 * PUT /textures : Update a texture.
	 *
	 * @param textureDTO the textureDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the new
	 *         textureDTO, or with status 400 (Bad Request) if the texture has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
    @ApiOperation("Update a texture.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PutMapping("/textures")
	public ResponseEntity<TextureDTO> updateTexture(@Valid @RequestBody final TextureDTO textureDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Texture : {}", textureDTO);
		if (textureDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new texture cannot already have an ID idexists " + textureDTO.getId());
		}
		final TextureDTO result = textureService.update(textureDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /textures : Get all the textures.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of textures in body
	 * or with status 204 (No Content) if there is no texture.
	 *
	 */
    @ApiOperation("Get all the textures.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/textures")
	public ResponseEntity<List<TextureDTO>> getAllTextures() {
		LOGGER.debug("REST request to get all Textures");
		final List<TextureDTO> textures = textureService.findAll();
		if (textures.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(textures);
	}

	/**
	 * GET /textures : Get a texture.
	 *
	 * @param textureDTO the textureDTO to get
	 * @return the ResponseEntity with status 200 (Ok) and with body the new textureDTO,
	 * or with status 204 (No Content) if the texture does not exist.
	 *
	 * @throws DailyFollowUpException
	 */
    @ApiOperation("Get a texture.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/textures/{id}")
	public ResponseEntity<TextureDTO> getTexture(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Texture : {}", id);
		final Optional<TextureDTO> result = textureService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /textures/:id : Delete the "id" texture.
	 *
	 * @param id the id of the textureDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
    @ApiOperation("Delete the \"id\" texture.")
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping("/textures/{id}")
	public ResponseEntity<Void> deleteTexture(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Texture : {}", id);
		textureService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
