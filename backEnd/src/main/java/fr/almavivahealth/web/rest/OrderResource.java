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
import fr.almavivahealth.service.OrderService;
import fr.almavivahealth.service.dto.OrderDTO;
import io.swagger.annotations.Api;

/**
 * REST controller for managing Order.
 * 
 * @author christopher
 */
@Api(value = "Order")
@RestController
@RequestMapping("/api")
public class OrderResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);
	
	private final OrderService orderService;

	@Autowired
	public OrderResource(final OrderService orderService) {
		this.orderService = orderService;
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
	@PostMapping("/orders")
	public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody final OrderDTO orderDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Order : {}", orderDTO);
		if (orderDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new order cannot already have an ID idexists {}" + orderDTO.getId());
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
	@PutMapping("/orders")
	public ResponseEntity<OrderDTO> updateOrder(@Valid @RequestBody final OrderDTO orderDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Order : {}", orderDTO);
		if (orderDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A order must have an ID idexists {}" + orderDTO.getId());
		}
		final OrderDTO result = orderService.save(orderDTO);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * GET /orders : Get all the orders.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of orders in body
	 * or with status 204 (No Content) if there is no order.
	 *         
	 */
	@GetMapping("/orders")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		LOGGER.debug("REST request to get All Orders");
		final List<OrderDTO> orders = orderService.findAll();
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
	@DeleteMapping("/orders/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Order : {}", id);
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
