package fr.almavivahealth.web.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.service.StatsService;
import fr.almavivahealth.service.dto.OrdersPerDay;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.service.dto.TopTrendyMenuDTO;
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/stats/patientsByStatus")
	public ResponseEntity<PatientsByStatusDTO> getNumberOfPatientsByStatus() {
		LOGGER.debug("REST request to get number of patients by status");
		final Optional<PatientsByStatusDTO> patientsByStatus = statsService.findNumberOfPatientsByStatus();
		if (patientsByStatus.isPresent()) {
			return ResponseEntity.ok().body(patientsByStatus.get());
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * GET /stats/ordersPerDay : Get number of orders per day.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the map of list in body
	 * or with status 204 (No Content) if there is no PatientsByStatus.
	 *
	 */
	@ApiOperation("Get number of orders per day.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/stats/ordersPerDay")
	public ResponseEntity<Map<String, List<OrdersPerDay>>> getNumberOfOrdersPerDay() {
		LOGGER.debug("REST request to get number of orders per day");
		final Map<String, List<OrdersPerDay>> orderPerStatus = statsService.findAllForNextDays();
		if (orderPerStatus.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(orderPerStatus);
	}

	/**
	 * GET /stats/trendyDiets : Get trendy diets.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list in body
	 * or with status 204 (No Content) if there is no TopTrendyMenu.
	 *
	 */
	@ApiOperation("Get trendy diets.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/stats/trendyDiets")
	public ResponseEntity<List<TopTrendyMenuDTO>> getTrendyDiets() {
		LOGGER.debug("REST request to get trendy diets");
		final List<TopTrendyMenuDTO> trendyDiets = statsService.findTrendyDiets();
		if (trendyDiets.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(trendyDiets);
	}

	/**
	 * GET /stats/trendyContents : Get trendy contents.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the linkedHashMap in body
	 * or with status 204 (No Content) if there is no TopTrendyMenu.
	 *
	 */
	@ApiOperation("Get trendy contents.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_NUTRITIONIST')")
	@GetMapping("/stats/trendyContents")
	public ResponseEntity<LinkedHashMap<String, Long>> getAllTrendyContents() {
		LOGGER.debug("REST request to get trendy contents");
		final LinkedHashMap<String, Long> trendyContents = statsService.findAllTrendyContents();
		if (trendyContents.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(trendyContents);
	}
}
