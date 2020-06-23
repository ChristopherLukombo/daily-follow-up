package fr.almavivahealth.web.rest;

import static fr.almavivahealth.constants.ErrorMessage.AN_ERROR_OCCURRED_WHILE_TRYING_TO_UPLOAD_THE_PICTURE_CONTENT;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	private final MessageSource messageSource;

    @Autowired
	public ContentResource(final ContentService contentService, final MessageSource messageSource) {
		this.contentService = contentService;
		this.messageSource = messageSource;
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/contents")
	public ResponseEntity<ContentDTO> createContent(
			@Valid @RequestBody final ContentDTO contentDTO,
			@ApiIgnore final Locale locale)
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PutMapping("/contents")
	public ResponseEntity<ContentDTO> updateContent(
			@Valid @RequestBody final ContentDTO contentDTO,
			@ApiIgnore final Locale locale)
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/contents/contentList")
	public ResponseEntity<List<ContentDTO>> saveAll(
			@RequestBody @Valid final ContentList contentList,
			@ApiIgnore final Locale locale) {
		LOGGER.debug("REST request to save all contents");
		final List<ContentDTO> contents = contentService.saveAll(contentList);
		return ResponseEntity.ok().body(contents);
	}

	/**
     * POST  /contents/picture/contentId} : Upload picture from contentId."
     *
     * @param file the file
     * @param contentId the contentId
     * @return String the status
     * @throws DailyFollowUpException the daily follow up exception
     */
    @ApiOperation("Upload content picture from contentId.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server"),
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
    @PostMapping("/contents/picture/{contentId}")
	public ResponseEntity<String> uploadPicture(
			@RequestPart final MultipartFile file,
			@PathVariable final Long contentId,
			@ApiIgnore final Locale locale) throws DailyFollowUpException {
		LOGGER.debug("REST request to upload picture");
		try {
			final String fileName = contentService.uploadPicture(file, contentId, locale);
			return ResponseEntity.ok().body(fileName);
		} catch (final DailyFollowUpException e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), messageSource
					.getMessage(AN_ERROR_OCCURRED_WHILE_TRYING_TO_UPLOAD_THE_PICTURE_CONTENT, null, locale), e);
		}
	}

    /**
     * GET  /contents/picture/{contentId} : retrieve picture from contentId.
     *
     * @param contentId the content id
     * @return the picture
     */
    @ApiOperation("Retrieve picture from contentId.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping(
			value = "/contents/picture/{contentId}",
			produces = { "image/jpg", "image/jpeg", "image/gif", "image/png", "image/tif" })
	public ResponseEntity<byte[]> getPicture(@PathVariable final Long contentId) {
		LOGGER.debug("REST request to get picture");
		final byte[] picture = contentService.findPicture(contentId);
		return ResponseEntity.ok().body(picture);
	}
}
