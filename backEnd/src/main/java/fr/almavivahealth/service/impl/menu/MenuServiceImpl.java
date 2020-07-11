package fr.almavivahealth.service.impl.menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.MenuRepository;
import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.service.MenuService;
import fr.almavivahealth.service.dto.MenuDTO;
import fr.almavivahealth.service.mapper.MenuMapper;

/**
 * Service Implementation for managing Menu.
 *
 * @author christopher
 * @version 16
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

	private final MenuRepository menuRepository;

	private final MenuMapper menuMapper;

    @Autowired
	public MenuServiceImpl(
			final MenuRepository menuRepository,
			final MenuMapper menuMapper) {
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
		return menuRepository.findAllByOrderByIdAsc().stream()
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

	/**
	 * Find current menus.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findCurrentMenus() {
		return menuRepository.findCurrentMenus(LocalDate.now()).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Check specifications.
	 *
	 * @param menuDTO the menu DTO
	 * @return true, if successful
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean checkSpecifications(final MenuDTO menuDTO) {
		return menuRepository.findAll().stream()
				.filter(menu -> isOverlapped(menuDTO, menu))
				.anyMatch(menu -> isTheSameCharacteristics(menuDTO, menu));
	}

	private boolean isOverlapped(final MenuDTO menuDTO, final Menu menu) {
		if (null == menu.getStartDate() || null == menu.getEndDate()) {
			return false;
		}

		boolean overlapped = false;
		if ((menuDTO.getStartDate().compareTo(menu.getStartDate()) < 0)
				&& (menuDTO.getEndDate().compareTo(menu.getStartDate()) < 0)) {
			overlapped = false;
		} else if ((menu.getStartDate().compareTo(menuDTO.getStartDate()) < 0)
				&& (menu.getEndDate().compareTo(menuDTO.getStartDate()) < 0)) {
			overlapped = false;
		} else {
			overlapped = true;
		}
		return overlapped;
	}

	private boolean isTheSameCharacteristics(final MenuDTO menuDTO, final Menu menu) {
		if (menuDTO.getId() != null && menuDTO.getId().equals(menu.getId())) {
			return false;
		}

		return menu.getDiets().containsAll(menuDTO.getDiets())
				&& findString(menu.getTexture()).equalsIgnoreCase(menuDTO.getTexture());
	}

	private String findString(final String value) {
		return Optional.ofNullable(value)
				.map(String::trim)
				.orElse(StringUtils.EMPTY);
	}

	/**
	 * Find Future menus.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findFutureMenus() {
		return menuRepository.findFutureMenus(LocalDate.now()).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find Past menus.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findPastMenus() {
		return menuRepository.findPastMenus(LocalDate.now()).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Delete by ids.
	 *
	 * @param ids the ids
	 */
	@Override
	public void deleteByIds(final List<Long> ids) {
		for (final Long id : ids) {
			menuRepository.deleteById(id);
		}
	}

	/**
	 * Find menus for date.
	 *
	 * @param date the date
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MenuDTO> findMenusForDate(final LocalDate date) {
		return menuRepository.findCurrentMenus(date).stream()
				.map(menuMapper::menuToMenuDTO)
				.collect(Collectors.toList());
	}
}
