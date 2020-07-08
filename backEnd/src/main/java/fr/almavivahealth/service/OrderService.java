package fr.almavivahealth.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.OrderDTO;

/**
 * The Interface OrderService.
 */
public interface OrderService {

	/**
	 * Save a order.
	 *
	 * @param orderDTO the entity to save
	 * @return the persisted entity
	 */
	OrderDTO save(OrderDTO orderDTO);

	/**
	 * Update a order.
	 *
	 * @param orderDTO the order DTO
	 * @return the persisted entity
	 */
	OrderDTO update(OrderDTO orderDTO);

	/**
	 * Get all the orders.
	 *
	 * @param selectedDate the selected date
	 * @return the list of entities
	 */
	List<OrderDTO> findAllForWeek(LocalDate date);

	/**
	 * Get the "id" order.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<OrderDTO> findOne(Long id);

	/**
	 * Delete the "id" order.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);


	/**
	 * Find orders by patient id.
	 *
	 * @param patientId the patient id
	 * @return the list of entities
	 */
	List<OrderDTO> findOrdersByPatientId(Long patientId);


	/**
	 * Find all orders between startDate and endDate.
	 *
	 * @param date the date
	 * @return the list of entities
	 */
	List<OrderDTO> findAllOrdersBetween(LocalDate date);
}
