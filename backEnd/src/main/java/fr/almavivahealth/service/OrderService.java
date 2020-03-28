package fr.almavivahealth.service;

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
	 * @return the list of entities
	 */
	List<OrderDTO> findAll();

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
}
