package fr.almavivahealth.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.ContentService;
import fr.almavivahealth.service.dto.ContentDTO;
import fr.almavivahealth.service.dto.ContentList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing Content.
 *
 * @author christopher
 */
@Api("Content")
@RestController
@RequestMapping("/api")
public class ContentResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentResource.class);

	private final ContentService contentService;

    @Autowired
	public ContentResource(final ContentService contentService) {
		this.contentService = contentService;
	}

	/**
	 * POST /contents : Create a new content.
	 *
	 * @param contentDTO the contentDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         contentDTO, or with status 400 (Bad Request) if the content has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new content.")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Created"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 422, message = "Unprocessable entity")
	})
	@PostMapping("/contents")
	public ResponseEntity<ContentDTO> createContent(@Valid @RequestBody final ContentDTO contentDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Content : {}", contentDTO);
		if (contentDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new content cannot already have an ID idexists " + contentDTO.getId());
		}
		final ContentDTO result = contentService.save(contentDTO);
		return ResponseEntity.created(new URI("/api/contents/" + result.getId())).body(result);
	}

	/**
	 * PUT /contents : Update a content.
	 *
	 * @param contentDTO the contentDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         contentDTO, or with status 400 (Bad Request) if the content has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a content.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 422, message = "Unprocessable entity")
	})
	@PutMapping("/contents")
	public ResponseEntity<ContentDTO> updateContent(@Valid @RequestBody final ContentDTO contentDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Content : {}", contentDTO);
		if (contentDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A content must have an ID idexists " + contentDTO.getId());
		}
		final ContentDTO result = contentService.update(contentDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /contents : Get all the contents.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of contents in body
	 * or with status 204 (No Content) if there is no content.
	 *
	 */
	@ApiOperation("Get all the contents.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@GetMapping("/contents")
	public ResponseEntity<List<ContentDTO>> getAllContents() {
		LOGGER.debug("REST request to get All Contents");
		final List<ContentDTO> contents = contentService.findAll();
		if (contents.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(contents);
	}

	/**
	 * GET /contents/:id : Get the "id" content.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the content does not exist.
	 *
	 */
	@ApiOperation("Get the \"id\" content.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@GetMapping("/contents/{id}")
	public ResponseEntity<ContentDTO> getContent(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Content : {}", id);
		final Optional<ContentDTO> result = contentService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /contents/:id : Delete the "id" content.
	 *
	 * @param id the id of the contentDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" content.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@DeleteMapping("/contents/{id}")
	public ResponseEntity<Void> deleteContent(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Content : {}", id);
		contentService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * POST /contents/contentList : Save all contents
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 200 (No Content) if the content does not exist.
	 * @throws DailyFollowUpException
	 *
	 */
	@ApiOperation("Save all contents")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@PostMapping("/contents/contentList")
	public ResponseEntity<List<ContentDTO>> saveAll(
			@RequestBody @Valid final ContentList contentList,
			@ApiIgnore final Locale locale) {
		LOGGER.debug("REST request to save all contents");
		final List<ContentDTO> contents = contentService.saveAll(contentList);
		return ResponseEntity.ok().body(contents);
	}

	/**
	 * GET /contents/search : Get the first 5 contents by name.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of contents in body
	 * or with status 204 (No Content) if there is no content.
	 *
	 */
	@ApiOperation("Get the first 5 contents by name.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Ok"),
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden")
	})
	@GetMapping(value = "/contents/search", params = { "name" })
	public ResponseEntity<List<ContentDTO>> getFirst5ByName(@RequestParam final String name) {
		LOGGER.debug("REST request to get All Contents");
		final List<ContentDTO> contents = contentService.findFirst5ByName(name);
		if (contents.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(contents);
	}
}
