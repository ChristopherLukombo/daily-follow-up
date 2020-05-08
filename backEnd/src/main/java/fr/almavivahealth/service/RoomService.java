package fr.almavivahealth.service;

import java.util.List;
import java.util.Optional;

import fr.almavivahealth.service.dto.RoomDTO;

/**
 * The Interface RoomService.
 */
public interface RoomService {

	/**
	 * Save a room.
	 *
	 * @param roomDTO the entity to save
	 * @return the persisted entity
	 */
	RoomDTO save(RoomDTO roomDTO);

	/**
	 * Update a room.
	 *
	 * @param roomDTO the room DTO
	 * @return the persisted entity
	 */
	RoomDTO update(RoomDTO roomDTO);

	/**
	 * Get all the rooms.
	 *
	 * @return the list of entities
	 */
	List<RoomDTO> findAll();

	/**
	 * Get the "id" romm.
	 *
	 * @param id the id
	 * @return the entity
	 */
	Optional<RoomDTO> findOne(Long id);

	/**
	 * Delete the "id" room.
	 *
	 * @param id the id of the entity
	 */
	void delete(Long id);

	/**
	 * Get the room by patient id.
	 *
	 * @param patientId the patient id
	 * @return the entity
	 */
	Optional<RoomDTO> findByPatientId(Long patientId);

}
