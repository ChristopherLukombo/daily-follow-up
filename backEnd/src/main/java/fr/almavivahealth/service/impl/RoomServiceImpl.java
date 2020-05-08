package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.RoomRepository;
import fr.almavivahealth.domain.Room;
import fr.almavivahealth.service.RoomService;
import fr.almavivahealth.service.dto.RoomDTO;
import fr.almavivahealth.service.mapper.RoomMapper;

/**
 * Service Implementation for managing Room.
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

	private final RoomRepository roomRepository;

	private final RoomMapper roomMapper;

	@Autowired
	public RoomServiceImpl(final RoomRepository roomRepository, final RoomMapper roomMapper) {
		this.roomRepository = roomRepository;
		this.roomMapper = roomMapper;
	}

	/**
	 * Save a room.
	 *
	 * @param roomDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public RoomDTO save(final RoomDTO roomDTO) {
		LOGGER.debug("Request to save Room : {}", roomDTO);
		Room room = roomMapper.roomDTOToRoom(roomDTO);
		room = roomRepository.save(room);
		return roomMapper.roomToRoomDTO(room);
	}

	/**
	 * Update a room.
	 *
	 * @param roomDTO the room DTO
	 * @return the persisted entity
	 */
	@Override
	public RoomDTO update(final RoomDTO roomDTO) {
		LOGGER.debug("Request to update Room : {}", roomDTO);
		Room room = roomMapper.roomDTOToRoom(roomDTO);
		room = roomRepository.saveAndFlush(room);
		return roomMapper.roomToRoomDTO(room);
	}

	/**
	 * Get all the rooms.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<RoomDTO> findAll() {
		LOGGER.debug("Request to get all Rooms");
		return roomRepository.findAllByOrderByIdDesc().stream()
				.map(roomMapper::roomToRoomDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" romm.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<RoomDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Room : {}", id);
		return roomRepository.findById(id)
				.map(roomMapper::roomToRoomDTO);
	}

	/**
	 * Delete the "id" room.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Room : {}", id);
		roomRepository.deleteById(id);
	}

	/**
	 * Get the room by patient id.
	 *
	 * @param patientId the patient id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<RoomDTO> findByPatientId(final Long patientId) {
		LOGGER.debug("Request to get Room : {}", patientId);
		return roomRepository.findByPatientId(patientId)
				.map(roomMapper::roomToRoomDTO);
	}

}
