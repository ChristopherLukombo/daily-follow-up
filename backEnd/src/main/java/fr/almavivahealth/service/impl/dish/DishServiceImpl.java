package fr.almavivahealth.service.impl.dish;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.DishRepository;
import fr.almavivahealth.service.DishService;
import fr.almavivahealth.service.dto.DishDTO;
import fr.almavivahealth.service.mapper.DishMapper;

/**
 * Service Implementation for managing Dish.
 */
@Service
@Transactional
public class DishServiceImpl implements DishService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DishServiceImpl.class);

	private final DishRepository dishRepository;

	private final DishMapper dishMapper;

    @Autowired
	public DishServiceImpl(final DishRepository dishRepository, final DishMapper dishMapper) {
		this.dishRepository = dishRepository;
		this.dishMapper = dishMapper;
	}

	/**
	 * Find the first 5 dishes by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<DishDTO> findFirst5ByName(final String name) {
		LOGGER.debug("Request to find first 5 Dishes by name: {}", name);
		if (StringUtils.isBlank(name)) {
			return Collections.emptyList();
		}
		return dishRepository.findByNameIgnoreCaseContaining(name, PageRequest.of(0, 5))
				.map(dishMapper::dishToDishDTO)
				.getContent();
	}

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the dish
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<DishDTO> findByName(final String name) {
		LOGGER.debug("Request to fin Dish by name: {}", name);
		return dishRepository.findByName(name)
				.map(dishMapper::dishToDishDTO);
	}
}
