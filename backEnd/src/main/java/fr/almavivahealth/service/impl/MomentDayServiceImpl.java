package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.MomentDayRepository;
import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.service.MomentDayService;
import fr.almavivahealth.service.dto.MomentDayDTO;
import fr.almavivahealth.service.mapper.MomentDayMapper;

/**
 * Service Implementation for managing MomentDay.
 */
@Service
@Transactional
public class MomentDayServiceImpl implements MomentDayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MomentDayServiceImpl.class);
	
	private final MomentDayRepository momentDayRepository;
	
	private final MomentDayMapper momentDayMapper;
	
	@Autowired
	public MomentDayServiceImpl(final MomentDayRepository momentDayRepository, final MomentDayMapper momentDayMapper) {
		this.momentDayRepository = momentDayRepository;
		this.momentDayMapper = momentDayMapper;
	}

	/**
	 * Save a momentDay.
	 *
	 * @param momentDay the entity to save
	 * @return the persisted entity
	 */
	@Override
	public MomentDayDTO save(final MomentDayDTO momentDayDTO) {
		LOGGER.debug("Request to save MomentDay : {}", momentDayDTO);
		MomentDay momentDay = momentDayMapper.momentDayDTOToMomentDay(momentDayDTO);
		momentDay = momentDayRepository.save(momentDay);
		return momentDayMapper.momentDayToMomentDayDTO(momentDay);
	}

	/**
	 * Update a momentDay.
	 *
	 * @param momentDayDTO the day DTO
	 * @return the persisted entity
	 */
	@Override
	public MomentDayDTO update(final MomentDayDTO momentDayDTO) {
		LOGGER.debug("Request to update MomentDay : {}", momentDayDTO);
		MomentDay momentDay = momentDayMapper.momentDayDTOToMomentDay(momentDayDTO);
		momentDay = momentDayRepository.saveAndFlush(momentDay);
		return momentDayMapper.momentDayToMomentDayDTO(momentDay);
	}

	/**
	 * Get all the momentDays.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MomentDayDTO> findAll() {
		LOGGER.debug("Request to get all MomentDays");
		return momentDayRepository.findAllByOrderByIdDesc().stream()
				.map(momentDayMapper::momentDayToMomentDayDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" momentDay.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<MomentDayDTO> findOne(final Long id) {
		LOGGER.debug("Request to get MomentDay : {}", id);
		return momentDayRepository.findById(id)
				.map(momentDayMapper::momentDayToMomentDayDTO);
	}

	/**
	 * Delete the "id" momentDay.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete MomentDay : {}", id);
		momentDayRepository.deleteById(id);
	}
}
