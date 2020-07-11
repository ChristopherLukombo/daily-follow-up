package fr.almavivahealth.web.rest;

import static fr.almavivahealth.constants.ErrorMessage.ERROR_PATIENT_HAS_AN_ORDER;

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
import fr.almavivahealth.service.OrderService;
import fr.almavivahealth.service.dto.OrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for managing Order.
 *
 * @author christopher
 * @version 16
 */
@Api("Order")
@RestController
@RequestMapping("/api")
public class OrderResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

	private final OrderService orderService;

	private final MessageSource messageSource;

    @Autowired
	public OrderResource(
			final OrderService orderService,
			final MessageSource messageSource) {
		this.orderService = orderService;
		this.messageSource = messageSource;
	}

	/**
	 * POST /orders : Create a new order.
	 *
	 * @param orderDTO the orderDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         orderDTO, or with status 400 (Bad Request) if the order has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Create a new order.")
	@ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@PostMapping("/orders")
	public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody final OrderDTO orderDTO, @ApiIgnore final Locale locale)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Order : {}", orderDTO);
		if (orderDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new order cannot already have an ID idexists " + orderDTO.getId());
		}
		final boolean isCreated = orderService.isCreated(orderDTO);
		if (isCreated) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					messageSource.getMessage(ERROR_PATIENT_HAS_AN_ORDER, null, locale));
		}
		final OrderDTO result = orderService.save(orderDTO);
		return ResponseEntity.created(new URI("/api/orders/" + result.getId())).body(result);
	}

	/**
	 * PUT /orders : Update a order.
	 *
	 * @param orderDTO the orderDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         orderDTO, or with status 400 (Bad Request) if the order has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@ApiOperation("Update a order.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@PutMapping("/orders")
	public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody final OrderDTO orderDTO, @ApiIgnore final Locale locale)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Order : {}", orderDTO);
		if (orderDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A order must have an ID idexists " + orderDTO.getId());
		}
		final boolean isCreated = orderService.isCreated(orderDTO);
		if (isCreated) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					ERROR_PATIENT_HAS_AN_ORDER);
		}
		final OrderDTO result = orderService.update(orderDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /orders : Get all the orders for week.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of orders in body
	 * or with status 204 (No Content) if there is no order.
	 *
	 */
	@ApiOperation("Get all the orders for week.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping(value = "/orders", params = { "selectedDate" })
	public ResponseEntity<List<OrderDTO>> getAllOrdersForWeek(
			@ApiParam("YYYY-MM-DD") @DateTimeFormat(iso = ISO.DATE) @RequestParam final LocalDate selectedDate) {
		LOGGER.debug("REST request to get All Orders for week");
		final List<OrderDTO> orders = orderService.findAllForWeek(selectedDate);
		if (orders.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(orders);
	}

	/**
	 * GET /orders/:id : Get the "id" order.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the order does not exist.
	 *
	 */
	@ApiOperation("Get the \"id\" order.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping("/orders/{id}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Order : {}", id);
		final Optional<OrderDTO> result = orderService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /orders/:id : Delete the "id" order.
	 *
	 * @param id the id of the orderDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@ApiOperation("Delete the \"id\" order.")
	@ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Order : {}", id);
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * GET /orders : Get all orders by patient id.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of orders in body
	 * or with status 204 (No Content) if there is no order.
	 *
	 */
	@ApiOperation("Get all orders by patient id.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping(value = "/orders/patient/{patientId}")
	public ResponseEntity<List<OrderDTO>> getAllOrdersByPatientId(@PathVariable final Long patientId) {
		LOGGER.debug("REST Request to find Orders by patient id: {}", patientId);
		final List<OrderDTO> orders = orderService.findOrdersByPatientId(patientId);
		if (orders.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(orders);
	}

	/**
	 * GET /orders/between : Get all orders between date.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of orders in body
	 * or with status 204 (No Content) if there is no order.
	 *
	 */
	@ApiOperation("Get all orders between date.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping(value = "/orders/between", params = { "date" })
	public ResponseEntity<List<OrderDTO>> getAllOrdersBetween(
			@ApiParam("YYYY-MM-DD") @DateTimeFormat(iso = ISO.DATE) @RequestParam final LocalDate date
			) {
		LOGGER.debug("REST Request to get all Orders between {}", date);
		final List<OrderDTO> orders = orderService.findAllOrdersBetween(date);
		if (orders.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(orders);
	}

	/**
	 * GET /orders/forDate : Get all orders by date.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of orders in body
	 * or with status 204 (No Content) if there is no order.
	 *
	 */
	@ApiOperation("Get all orders by date.")
	@ApiResponses({
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden")
        })
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping(value = "/orders/forDate", params = { "date" })
	public ResponseEntity<List<OrderDTO>> getAllOrdersForDate(
			@ApiParam("YYYY-MM-DD") @DateTimeFormat(iso = ISO.DATE) @RequestParam final LocalDate date
			) {
		LOGGER.debug("REST Request to get all Orders for date: {}", date);
		final List<OrderDTO> orders = orderService.findAllOrdersByDate(date);
		if (orders.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(orders);
	}

	/**
	 * GET /orders/coupons : Generate coupons.
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CAREGIVER') or hasRole('ROLE_DIET')")
	@GetMapping(value = "/orders/coupons", params = { "momentName", "selectedDate" })
	public ResponseEntity<byte[]> generateCoupons(
			@RequestParam final String momentName,
			@ApiParam("YYYY-MM-DD") @DateTimeFormat(iso = ISO.DATE) @RequestParam final LocalDate selectedDate)
					throws DailyFollowUpException {
		LOGGER.debug("REST request to generate coupons");
		try {
			final byte[] pdfCoupons = orderService.generateCoupons(momentName, selectedDate);

			final HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("Content-Disposition", "attachment; filename=coupons.pdf");
			responseHeaders.setContentType(MediaType.APPLICATION_PDF);

			return ResponseEntity.ok().headers(responseHeaders).body(pdfCoupons);
		} catch (final DailyFollowUpException e) {
			throw new DailyFollowUpException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
					"An error occurred during the generation of the coupons", e);
		}
	}
}
