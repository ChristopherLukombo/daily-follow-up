package fr.almavivahealth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
import fr.almavivahealth.service.TextureService;
import fr.almavivahealth.service.dto.TextureDTO;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;

/**
 * REST controller for managing Texture.
 * 
 * @author christopher
 */
@Api(value = "Texture")
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
	@PostMapping("/textures")
	@Timed
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
	@PutMapping("/textures")
	@Timed
	public ResponseEntity<TextureDTO> updateTexture(@Valid @RequestBody final TextureDTO textureDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Texture : {}", textureDTO);
		if (textureDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new texture cannot already have an ID idexists " + textureDTO.getId());
		}
		final TextureDTO result = textureService.save(textureDTO);
		return ResponseEntity.ok().body(result);
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
	@GetMapping("/textures/{id}")
	@Timed
	public ResponseEntity<TextureDTO> getTexture(@PathVariable final Long id) {
		LOGGER.debug("REST request to update Texture : {}", id);
		final Optional<TextureDTO> result = textureService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /textures/:id : delete the "id" texture.
	 *
	 * @param id the id of the textureDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@DeleteMapping("/textures/{id}")
	@Timed
	public ResponseEntity<Void> deleteTexture(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Texture : {}", id);
		textureService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
