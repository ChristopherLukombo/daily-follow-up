package fr.almavivahealth.service.impl.day;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.DayRepository;
import fr.almavivahealth.domain.entity.Day;
import fr.almavivahealth.service.DayService;
import fr.almavivahealth.service.dto.DayDTO;
import fr.almavivahealth.service.mapper.DayMapper;

/**
 * Service Implementation for managing Day.
 *
 * @author christopher
 * @version 16
 */
@Service
@Transactional
public class DayServiceImpl implements DayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DayServiceImpl.class);

	private final DayRepository dayRepository;

	private final DayMapper dayMapper;

    @Autowired
	public DayServiceImpl(final DayRepository dayRepository, final DayMapper dayMapper) {
		this.dayRepository = dayRepository;
		this.dayMapper = dayMapper;
	}

	/**
	 * Save a day.
	 *
	 * @param dayDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public DayDTO save(final DayDTO dayDTO) {
		LOGGER.debug("Request to save Day : {}", dayDTO);
		Day day = dayMapper.dayDTOToDay(dayDTO);
		day = dayRepository.save(day);
		return dayMapper.dayToDayDTO(day);
	}

	/**
	 * Update a day.
	 *
	 * @param dayDTO the day DTO
	 * @return the persisted entity
	 */
	@Override
	public DayDTO update(final DayDTO dayDTO) {
		LOGGER.debug("Request to update Day : {}", dayDTO);
		Day day = dayMapper.dayDTOToDay(dayDTO);
		day = dayRepository.saveAndFlush(day);
		return dayMapper.dayToDayDTO(day);
	}

	/**
	 * Get all the days.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DayDTO> findAll() {
		LOGGER.debug("Request to get all Days");
		return dayRepository.findAllByOrderByIdDesc().stream()
				.map(dayMapper::dayToDayDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" day.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DayDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Day : {}", id);
		return dayRepository.findById(id)
				.map(dayMapper::dayToDayDTO);
	}

	/**
	 * Delete the "id" day.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Day : {}", id);
		dayRepository.deleteById(id);
	}
}
