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
import fr.almavivahealth.service.PatientService;
import fr.almavivahealth.service.dto.PatientDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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

	@Autowired
	public PatientResource(final PatientService patientService) {
		this.patientService = patientService;
	}

	/**
	 * POST /patients : Create a new patient.
	 *
	 * @param patientDTO the patientDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         patientDTO, or with status 400 (Bad Request) if the patient has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new patient.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 422, message = "Unprocessable entity")
        })
	@PostMapping("/patients")
	public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody final PatientDTO patientDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Patient : {}", patientDTO);
		if (patientDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new patient cannot already have an ID idexists {}" + patientDTO.getId());
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
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a patient.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 422, message = "Unprocessable entity")
        })
	@PutMapping("/patients")
	public ResponseEntity<PatientDTO> updatePatient(@Valid @RequestBody final PatientDTO patientDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Patient : {}", patientDTO);
		if (patientDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A patient must have an ID idexists {}" + patientDTO.getId());
		}
		final PatientDTO result = patientService.update(patientDTO);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * GET /patients : Get all the patients.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patients in body
	 * or with status 204 (No Content) if there is no patient.
	 *         
	 */
	@ApiOperation("Get all the patients.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/patients")
	public ResponseEntity<List<PatientDTO>> getAllPatients() {
		LOGGER.debug("REST request to get All Patients");
		final List<PatientDTO> patients = patientService.findAll();
		if (patients.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patients);
	}

	/**
	 * GET /patients/:id : Get the "id" patient.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the patient does not exist.
	 *         
	 */
	@ApiOperation("Get all the patients.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
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
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Patient : {}", id);
		patientService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
