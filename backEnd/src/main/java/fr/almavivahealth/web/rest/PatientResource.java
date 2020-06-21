package fr.almavivahealth.web.rest;

import static fr.almavivahealth.constants.ErrorMessage.AN_ERROR_OCCURRED_WHILE_TRYING_TO_IMPORT_THE_PATIENTS;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_PATIENT_ROOM_CHANGE;
import static fr.almavivahealth.constants.ErrorMessage.THE_FILE_MUST_BE_OF_TYPE_CSV;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
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

import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.PatientService;
import fr.almavivahealth.service.dto.BulkResult;
import fr.almavivahealth.service.dto.PatientDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing Patient.
 *
 * @author christopher
 */
@Api("Patient")
@RestController
@RequestMapping("/api")
public class PatientResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientResource.class);

	private final PatientService patientService;

	private final MessageSource messageSource;

    @Autowired
	public PatientResource(final PatientService patientService, final MessageSource messageSource) {
		this.patientService = patientService;
		this.messageSource = messageSource;
	}

	/**
	 * POST /patients : Create a new patient.
	 *
	 * @param patientDTO the patientDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         patientDTO, or with status 400 (Bad Request) if the patient has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Create a new patient.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 422, message = "Unprocessable entity")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/patients")
	public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody final PatientDTO patientDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Patient : {}", patientDTO);
		if (patientDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new patient cannot already have an ID idexists " + patientDTO.getId());
		}
		final PatientDTO result = patientService.save(patientDTO);
		return ResponseEntity.created(new URI("/api/patients/" + result.getId())).body(result);
	}

	/**
	 * PUT /patients : Update a patient.
	 *
	 * @param patientDTO the patientDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         patientDTO, or with status 400 (Bad Request) if the patient has not
	 *         already an ID
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Update a patient.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 422, message = "Unprocessable entity")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PutMapping("/patients")
	public ResponseEntity<PatientDTO> updatePatient(@Valid @RequestBody final PatientDTO patientDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Patient : {}", patientDTO);
		if (patientDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A patient must have an ID idexists " + patientDTO.getId());
		}
		final PatientDTO result = patientService.update(patientDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /patients : Get all active patients.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patients in body
	 * or with status 204 (No Content) if there is no patient.
	 *
	 */
	@ApiOperation("Get all active patients.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/patients")
	public ResponseEntity<List<PatientDTO>> getAllActivePatients() {
		LOGGER.debug("REST request to get all active patients");
		final List<PatientDTO> patients = patientService.findAllActivePatients();
		if (patients.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patients);
	}

	/**
	 * GET /patients : Get all former patients.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patients in body
	 * or with status 204 (No Content) if there is no patient.
	 *
	 */
	@ApiOperation(" Get all former patients.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/patients/former")
	public ResponseEntity<List<PatientDTO>> getAllFormerPatients() {
		LOGGER.debug("REST request to get all former patients");
		final List<PatientDTO> patients = patientService.findAllFormerPatients();
		if (patients.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patients);
	}

	/**
	 * GET /patients/:id : Get the "id" patient.
	 *
	 * @param id the id
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the patient does not exist.
	 */
	@ApiOperation("Get the \"id\" patient")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/patients/{id}")
	public ResponseEntity<PatientDTO> getPatient(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Patient : {}", id);
		final Optional<PatientDTO> result = patientService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /patients/:id : Delete the "id" patient.
	 *
	 * @param id the id of the patientDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" patient.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Patient : {}", id);
		patientService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * POST /patients/import : Import patient file.
	 *
	 * @param inputfile the inputfile
	 * @param request the request
	 * @return the ResponseEntity with status 200 (Ok) and bulkResult in body
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Import patient file.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 409, message = "Conflict"),
        @ApiResponse(code = 422, message = "Unprocessable entity"),
        @ApiResponse(code = 500, message = "Internal Server"),
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@PostMapping("/patients/import")
	public ResponseEntity<BulkResult> importPatientFile(
			@RequestPart final MultipartFile inputfile,
			final HttpServletRequest request) throws DailyFollowUpException {
		LOGGER.debug("Request to import patient file : {}", inputfile.getName());
		if (!patientService.isCSV(inputfile)) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(THE_FILE_MUST_BE_OF_TYPE_CSV, null, request.getLocale()));
		}
		try {
			final BulkResult bulkResult = patientService.importPatientFile(inputfile, request);
			return ResponseEntity.ok().body(bulkResult);
		} catch (final DailyFollowUpException | IndexOutOfBoundsException e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), messageSource
					.getMessage(AN_ERROR_OCCURRED_WHILE_TRYING_TO_IMPORT_THE_PATIENTS, null, request.getLocale()), e);
		}
	}

	/**
	 * GET /patients/reactivate/:id : Reactivate patient.
	 *
	 * @param id the id of the patient to reactivate
	 * @return the ResponseEntity with status 200 (OK)
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@ApiOperation("Reactivate patient.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal Server")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/patients/reactivate/{id}")
	public ResponseEntity<Void> reactivatePatient(@PathVariable final Long id) throws DailyFollowUpException {
		LOGGER.debug("REST request to reactivate Patient : {}", id);
		final Optional<Patient> patient = patientService.reactivatePatient(id);
		if (!patient.isPresent()) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"No patient was found for this id");
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * GET /patients/floor/{number} : Get all by floor number.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patients in body
	 * or with status 204 (No Content) if there is no patient.
	 *
	 */
	@ApiOperation("Get all by floor number.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/patients/floor/{number}")
	public ResponseEntity<List<PatientDTO>> getAllByFloorNumber(@PathVariable final Integer number) {
		LOGGER.debug("REST request to get all Patients for floor: {}", number);
		final List<PatientDTO> patients = patientService.findAllByFloorNumber(number);
		if (patients.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patients);
	}

	/**
	 * POST /patients/changeRoom/{firstPatientId}/{secondPatientId} : Change rooms.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patients in body
	 * or with status 204 (No Content) if there is no patient.
	 * @throws DailyFollowUpException
	 *
	 */
	@ApiOperation("Change rooms of patients.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
		@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
		@PostMapping("/patients/changesRoom/{firstPatientId}/{secondPatientId}")
		public ResponseEntity<HttpStatus> changeRooms(@PathVariable final Long firstPatientId,
				@PathVariable final Long secondPatientId, @ApiIgnore final Locale locale) throws DailyFollowUpException {
			LOGGER.debug("REST request to get all Patients for floor: {} {}", firstPatientId, secondPatientId);
			final boolean changeRooms = patientService.changeRooms(firstPatientId, secondPatientId);
			if (!changeRooms) {
				throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(ERROR_PATIENT_ROOM_CHANGE, null, locale));
			}
			return ResponseEntity.ok().build();
		}
}
