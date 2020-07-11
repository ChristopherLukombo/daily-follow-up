package fr.almavivahealth.service.impl.floor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.FloorRepository;
import fr.almavivahealth.domain.entity.Floor;
import fr.almavivahealth.service.FloorService;
import fr.almavivahealth.service.dto.FloorDTO;
import fr.almavivahealth.service.mapper.FloorMapper;

/**
 * Service Implementation for managing Floor.
 *
 * @author christopher
 * @version 16
 */
@Service
@Transactional
public class FloorServiceImpl implements FloorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FloorServiceImpl.class);

	private final FloorRepository floorRepository;

	private final FloorMapper floorMapper;

	@Autowired
	public FloorServiceImpl(final FloorRepository floorRepository, final FloorMapper floorMapper) {
		this.floorRepository = floorRepository;
		this.floorMapper = floorMapper;
	}

	/**
	 * Save a floor.
	 *
	 * @param floorDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public FloorDTO save(final FloorDTO floorDTO) {
		LOGGER.debug("Request to save Floor : {}", floorDTO);
		Floor floor = floorMapper.floorDTOToFloor(floorDTO);
		floor = floorRepository.save(floor);
		return floorMapper.floorToFloorDTO(floor);
	}

	/**
	 * Update a floor.
	 *
	 * @param floorDTO the floor DTO
	 * @return the persisted entity
	 */
	@Override
	public FloorDTO update(final FloorDTO floorDTO) {
		LOGGER.debug("Request to update Floor : {}", floorDTO);
		Floor floor = floorMapper.floorDTOToFloor(floorDTO);
		floor = floorRepository.saveAndFlush(floor);
		return floorMapper.floorToFloorDTO(floor);
	}

	/**
	 * Get all the floors.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<FloorDTO> findAll() {
		LOGGER.debug("Request to get all Floors");
		return floorRepository.findAllByOrderByIdDesc().stream()
				.map(floorMapper::floorToFloorDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" floor.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<FloorDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Floor : {}", id);
		return floorRepository.findById(id)
				.map(floorMapper::floorToFloorDTO);
	}

	/**
	 * Delete the "id" floor.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Floor : {}", id);
		floorRepository.deleteById(id);
	}
}
