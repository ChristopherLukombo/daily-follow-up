package fr.almavivahealth.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.service.StatsService;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for managing Stats.
 * 
 * @author christopher
 */
@Api("Stats")
@RestController
@RequestMapping("/api")
public class StatsRessource {

	private static final Logger LOGGER = LoggerFactory.getLogger(StatsRessource.class);
	
	private final StatsService statsService;

	@Autowired
	public StatsRessource(final StatsService statsService) {
		this.statsService = statsService;
	}
	
	/**
	 * GET /stats/patientsPerAllergy : Get number of patients per Allergy.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patientsPerAllergy in body
	 * or with status 204 (No Content) if there is no patientsPerAllergy.
	 *         
	 */
	@ApiOperation("Get number of patients per Allergy.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/stats/patientsPerAllergy")
	public ResponseEntity<List<PatientsPerAllergyDTO>> getNumberOfPatientsPerAllergy() {
		LOGGER.debug("REST request to get number of patients per Allergy");
		final List<PatientsPerAllergyDTO> patientsPerAllergies = statsService.findNumberOfPatientsPerAllergy();
		if (patientsPerAllergies.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patientsPerAllergies);
	}
	
	/**
	 * GET /stats/patientsPerDiet : Get number of patients per Diet.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patientsPerDiet in body
	 * or with status 204 (No Content) if there is no patientsPerDiet.
	 *         
	 */
	@ApiOperation("Get number of patients per Diet.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/stats/patientsPerDiet")
	public ResponseEntity<List<PatientsPerDietDTO>> getNumberOfPatientsPerDiet() {
		LOGGER.debug("REST request to get number of patients per Diet");
		final List<PatientsPerDietDTO> patientsPerDiets = statsService.findNumberOfPatientsPerDiet();
		if (patientsPerDiets.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patientsPerDiets);
	}
	
	/**
	 * GET /stats/patientsByStatus : Get number of patients by status.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of patientsByStatus in body
	 * or with status 204 (No Content) if there is no PatientsByStatus.
	 *         
	 */
	@ApiOperation("Get number of patients by status.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@GetMapping("/stats/patientsByStatus")
	public ResponseEntity<List<PatientsByStatusDTO>> getNumberOfPatientsByStatus() {
		LOGGER.debug("REST request to get number of patients by status");
		final List<PatientsByStatusDTO> patientsByStatus = statsService.findNumberOfPatientsByStatus();
		if (patientsByStatus.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patientsByStatus);
	}
	
}