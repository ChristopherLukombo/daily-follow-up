package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.MenuRepository;
import fr.almavivahealth.domain.Menu;
import fr.almavivahealth.service.MenuService;
import fr.almavivahealth.service.dto.MenuDTO;
import fr.almavivahealth.service.mapper.MenuMapper;

/**
 * Service Implementation for managing Menu.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	private final MenuRepository menuRepository;
	
	private final MenuMapper menuMapper;
	
	@Autowired
	public MenuServiceImpl(final MenuRepository menuRepository, final MenuMapper menuMapper) {
		this.menuRepository = menuRepository;
		this.menuMapper = menuMapper;
	}

	/**
	 * Save a menu.
	 *
	 * @param menuDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public MenuDTO save(final MenuDTO menuDTO) {
		LOGGER.debug("Request to save Menu : {}", menuDTO);
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
		menu = menuRepository.save(menu);
		return menuMapper.menuToMenuDTO(menu);
	}

	/**
	 * Update a menu.
	 *
	 * @param menuDTO the menu DTO
	 * @return the persisted entity
	 */
	@Override
	public MenuDTO update(final MenuDTO menuDTO) {
		LOGGER.debug("Request to update Menu : {}", menuDTO);
		Menu menu = menuMapper.menuDTOToMenu(menuDTO);
		menu = menuRepository.saveAndFlush(menu);
		return menuMapper.menuToMenuDTO(menu);
	}

	/**
	 * Get all the menus.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findAll() {
		LOGGER.debug("Request to get all Menus");
		return menuRepository.findAll().stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" menu.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<MenuDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Menu : {}", id);
		return menuRepository.findById(id)
				.map(menuMapper::menuToMenuDTO);
	}

	/**
	 * Delete the "id" menu.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Menu : {}", id);
		menuRepository.deleteById(id);
	}
}
