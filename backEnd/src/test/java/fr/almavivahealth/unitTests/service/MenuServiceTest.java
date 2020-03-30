package fr.almavivahealth.unitTests.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.almavivahealth.dao.MenuRepository;
import fr.almavivahealth.domain.Menu;
import fr.almavivahealth.service.dto.MenuDTO;
import fr.almavivahealth.service.impl.MenuServiceImpl;
import fr.almavivahealth.service.mapper.MenuMapper;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceTest {

	private static final long ID = 1L;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private MenuMapper menuMapper;
	
	@InjectMocks
	private MenuServiceImpl menuServiceImpl;

	private static Menu createMenu() {
		return Menu.builder()
				.id(ID)
				.startDate(LocalDate.now())
				.build();
	}
	
	private static MenuDTO createMenuDTO() {
		return MenuDTO.builder()
				.id(ID)
				.startDate(LocalDate.now())
				.build();
	}
	
	@Test
	public void shouldSaveMenuWhenIsOk() {
		// Given
		final Menu menu = createMenu();
		final MenuDTO menuDTO = createMenuDTO();
		
		// When
		when(menuRepository.save((Menu) any())).thenReturn(menu);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);
		
		// Then
        assertThat(menuServiceImpl.save(menuDTO)).isEqualTo(menuDTO);		
	}
	
	@Test
	public void shouldSaveMenuWhenIsKo() {
		// Given
		final Menu menu = null;
		final MenuDTO menuDTO = null;
		
		// When
		when(menuRepository.save((Menu) any())).thenReturn(menu);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);
		
		// Then
        assertThat(menuServiceImpl.save(menuDTO)).isEqualTo(menuDTO);		
	}
	
	@Test
	public void shouldUpdateMenuWhenIsOk() {
		// Given
		final Menu menu = createMenu();
		final MenuDTO menuDTO = createMenuDTO();
		
		// When
		when(menuRepository.saveAndFlush((Menu) any())).thenReturn(menu);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);
		
		// Then
        assertThat(menuServiceImpl.update(menuDTO)).isEqualTo(menuDTO);		
	}
	
	@Test
	public void shouldUpdateMenuWhenIsKo() {
		// Given
		final Menu menu = null;
		final MenuDTO menuDTO = null;

		// When
		when(menuRepository.saveAndFlush((Menu) any())).thenReturn(menu);
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(menuDTO);

		// Then
		assertThat(menuServiceImpl.update(menuDTO)).isEqualTo(menuDTO);
	}

	@Test
	public void shouldGetAllMenusWhenIsOk() {
		// Given
		final List<Menu> menus = Arrays.asList(createMenu());

		// Then
		when(menuRepository.findAll()).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllMenusWhenIsEmpty() {
		// Given
		final List<Menu> menus = Collections.emptyList();

		// Then
		when(menuRepository.findAll()).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllMenusWhenIsNull() {
		// Given
		final List<Menu> menus = null;

		// Then
		when(menuRepository.findAll()).thenReturn(menus);

		// Then
		assertThatThrownBy(() -> menuServiceImpl.findAll()).isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetMenuWhenIsOk() {
		// Given
		final Menu floor = createMenu();
		final MenuDTO floorDTO = createMenuDTO();

		// When
		when(menuRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(floor));
		when(menuMapper.menuToMenuDTO((Menu) any())).thenReturn(floorDTO);
		
		// Then
		assertThat(menuServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(floorDTO));
	}
	
	@Test
	public void shouldGetMenuWhenIsNull() {
		// Given
		final Menu floor = null;
		final MenuDTO floorDTO = null;

		// When
		when(menuRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(floor));
		
		// Then
		assertThat(menuServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(floorDTO));
	}
	
	@Test
	public void shouldDeleteMenuWhenIsOk() {
		// When
		doNothing().when(menuRepository).deleteById(ID);

		// Then
		menuServiceImpl.delete(ID);
		
		verify(menuRepository, times(1)).deleteById(anyLong());
	}
}
