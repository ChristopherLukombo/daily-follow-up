package fr.almavivahealth.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.almavivahealth.service.AuditEventService;
import fr.almavivahealth.service.dto.CaregiverHistoryDTO;
import fr.almavivahealth.service.dto.MenuHistoryDTO;
import fr.almavivahealth.service.dto.PatientHistoryDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller for getting the audit events.
 *
 * @author christopher
 * @version 16
 */
@RestController
@RequestMapping("/management/audits")
public class AuditResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuditResource.class);

	private final AuditEventService auditEventService;

	@Autowired
	public AuditResource(final AuditEventService auditEventService) {
		this.auditEventService = auditEventService;
	}

	 /**
     * GET /patient/history/{patientId} : get all patient_historys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the page of Page<PatientHistoryDTO> in body
     */
	@ApiOperation("Get all patient_historys.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
    @GetMapping(value = "/patient/history/{patientId}", params = {"page", "size"})
	public ResponseEntity<Page<PatientHistoryDTO>> getAllPatientHistorysByPatientId(
			@PathVariable(required = true) final Long patientId,
			@RequestParam(required = true) final Integer page,
			@RequestParam(required = true) final Integer size) {
		LOGGER.debug("REST request to get All PatientHistorys");
		final Page<PatientHistoryDTO> patientHistorys = auditEventService.findAllPatientHistorysByPatientId(patientId, page, size);
		if (patientHistorys.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(patientHistorys);
	}

	 /**
     * GET /menu/history/{menuId} : get all menu_history.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the page of Page<MenuHistoryDTO> in body
     */
	@ApiOperation("Get all menu_historys.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
    @GetMapping(value = "/menu/history/{menuId}", params = {"page", "size"})
	public ResponseEntity<Page<MenuHistoryDTO>> getAllMenuHistorysByMenuId(
			@PathVariable(required = true) final Long menuId,
			@RequestParam(required = true) final Integer page,
			@RequestParam(required = true) final Integer size) {
		LOGGER.debug("REST request to get All MenuHistorys");
		final Page<MenuHistoryDTO> menuHistorys = auditEventService.findAllMenuHistorysByMenuId(menuId, page, size);
		if (menuHistorys.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(menuHistorys);
	}

	 /**
     * GET /caregiver/history/{caregiverId} : get all caregiver_history.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the page of Page<CaregiverHistoryDTO> in body
     */
	@ApiOperation("Get all caregiver_historys.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
    @GetMapping(value = "/caregiver/history/{caregiverId}", params = {"page", "size"})
	public ResponseEntity<Page<CaregiverHistoryDTO>> getAllCaregiverHistorysByCaregiverId(
			@PathVariable(required = true) final Long caregiverId,
			@RequestParam(required = true) final Integer page,
			@RequestParam(required = true) final Integer size) {
		LOGGER.debug("REST request to get All CaregiverHistorys");
		final Page<CaregiverHistoryDTO> caregiverHistories = auditEventService.findAllCaregiverHistorysByCaregiverId(caregiverId, page, size);
		if (caregiverHistories.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(caregiverHistories);
	}
}
