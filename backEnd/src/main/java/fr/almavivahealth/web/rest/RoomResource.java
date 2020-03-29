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
import fr.almavivahealth.service.RoomService;
import fr.almavivahealth.service.dto.RoomDTO;
import io.swagger.annotations.Api;

/**
 * REST controller for managing Room.
 * 
 * @author christopher
 */
@Api(value = "Room")
@RestController
@RequestMapping("/api")
public class RoomResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoomResource.class);
	
	private final RoomService roomService;

	@Autowired
	public RoomResource(final RoomService roomService) {
		this.roomService = roomService;
	}

	/**
	 * POST /rooms : Create a new room.
	 *
	 * @param roomDTO the roomDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         roomDTO, or with status 400 (Bad Request) if the room has
	 *         already an ID
	 * @throws URISyntaxException  if the Location URI syntax is incorrect
	 * @throws DailyFollowUpException
	 */
	@PostMapping("/rooms")
	public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody final RoomDTO roomDTO)
			throws URISyntaxException, DailyFollowUpException {
		LOGGER.debug("REST request to save Room : {}", roomDTO);
		if (roomDTO.getId() != null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A new room cannot already have an ID idexists " + roomDTO.getId());
		}
		final RoomDTO result = roomService.save(roomDTO);
		return ResponseEntity.created(new URI("/api/rooms/" + result.getId())).body(result);
	}
	
	/**
	 * PUT /rooms : Update a room.
	 *
	 * @param roomDTO the roomDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         roomDTO, or with status 400 (Bad Request) if the room has not
	 *         already an ID
	 * @throws DailyFollowUpException
	 */
	@PutMapping("/rooms")
	public ResponseEntity<RoomDTO> updateRoom(@Valid @RequestBody final RoomDTO roomDTO)
			throws DailyFollowUpException {
		LOGGER.debug("REST request to update Room : {}", roomDTO);
		if (roomDTO.getId() == null) {
			throw new DailyFollowUpException(HttpStatus.BAD_REQUEST.value(),
					"A room must have an ID idexists " + roomDTO.getId());
		}
		final RoomDTO result = roomService.save(roomDTO);
		return ResponseEntity.ok().body(result);
	}
	
	/**
	 * GET /rooms : Get all the rooms.
	 *
	 * @return the ResponseEntity with status 200 (Ok) and the list of rooms in body
	 * or with status 204 (No Content) if there is no room.
	 *         
	 */
	@GetMapping("/rooms")
	public ResponseEntity<List<RoomDTO>> getAllRooms() {
		LOGGER.debug("REST request to get all Rooms");
		final List<RoomDTO> rooms = roomService.findAll();
		if (rooms.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(rooms);
	}
	
	/**
	 * GET /rooms/:id : Get the "id" room.
	 *
	 * @return the ResponseEntity with status 200 (Ok)
	 * or with status 204 (No Content) if the room does not exist.
	 *         
	 */
	@GetMapping("/rooms/{id}")
	public ResponseEntity<RoomDTO> getRoom(@PathVariable final Long id) {
		LOGGER.debug("REST request to get Room : {}", id);
		final Optional<RoomDTO> result = roomService.findOne(id);
		if (result.isPresent()) {
			return ResponseEntity.ok().body(result.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * DELETE /rooms/:id : Delete the "id" room.
	 *
	 * @param id the id of the roomDTO to delete
	 * @return the ResponseEntity with status 204 (OK)
	 */
	@DeleteMapping("/rooms/{id}")
	public ResponseEntity<Void> deleteRoom(@PathVariable final Long id) {
		LOGGER.debug("REST request to delete Room : {}", id);
		roomService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
