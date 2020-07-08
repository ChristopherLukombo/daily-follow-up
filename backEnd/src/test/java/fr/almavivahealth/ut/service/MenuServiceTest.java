package fr.almavivahealth.ut.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
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
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.entity.Day;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.domain.entity.Week;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.dto.MenuDTO;
import fr.almavivahealth.service.impl.menu.MenuServiceImpl;
import fr.almavivahealth.service.mapper.MenuMapper;
import fr.almavivahealth.service.propeties.MenuProperties;

@RunWith(MockitoJUnitRunner.class)
public class MenuServiceTest {

	private static final long ID = 1L;

	private static final String EMAIL = "ben.zotito@gmail.com";

	private static final String LASTNAME = "Zotito";

	private static final String TEXTURE_NAME = "Sel";

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private MenuMapper menuMapper;

	@Mock
	private MenuProperties menuProperties;

	@Mock
	private PatientRepository patientRepository;

	@InjectMocks
	private MenuServiceImpl menuServiceImpl;

	private static Content createContent() {
		return Content.builder()
				.id(ID)
				.name("Saucisse")
				.build();
	}

	private static MomentDay createMomentDay() {
		return MomentDay.builder()
				.id(ID)
				.name("DEJEUNER")
				.entry(createContent())
				.build();
	}

	private static Week createWeek() {
		return Week.builder()
				.id(ID)
				.number(1)
				.days(Arrays.asList(createDay()))
				.build();
	}

	private static Day createDay() {
		return Day.builder()
				.id(ID)
				.name("Samedi")
				.momentDays(Arrays.asList(createMomentDay()))
				.build();
	}

	private static Menu createMenu() {
		return Menu.builder()
				.id(ID)
				.repetition(1)
				.startDate(LocalDate.of(2020, Month.APRIL, 6))
				.endDate(LocalDate.of(2020, Month.APRIL, 12))
				.weeks(Arrays.asList(createWeek()))
				.build();
	}

	private static MenuDTO createMenuDTO() {
		return MenuDTO.builder()
				.id(ID)
				.startDate(LocalDate.now())
				.build();
	}

	private static Patient createPatient() {
		return Patient.builder()
				.id(ID)
				.firstName("Ben")
				.lastName(LASTNAME)
				.email(EMAIL)
				.state(true)
				.texture(getTexture())
				.diets(Arrays.asList(getDiet()))
				.build();
	}

	private static Texture getTexture() {
		return Texture.builder()
				.id(ID)
				.name(TEXTURE_NAME)
				.build();
	}

	private static Diet getDiet() {
		return Diet.builder()
				.id(ID)
				.name("Normal")
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
		when(menuRepository.findAllByOrderByIdAsc()).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllMenusWhenIsEmpty() {
		// Given
		final List<Menu> menus = Collections.emptyList();

		// Then
		when(menuRepository.findAllByOrderByIdAsc()).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllMenusWhenIsNull() {
		// Given
		final List<Menu> menus = null;

		// Then
		when(menuRepository.findAllByOrderByIdAsc()).thenReturn(menus);

		// Then
		assertThatThrownBy(() -> menuServiceImpl.findAll()).isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldGetMenuWhenIsOk() {
		// Given
		final Menu floor = createMenu();
		final MenuDTO floorDTO = createMenuDTO();

		// When
		when(menuRepository.findById(anyLong())).thenReturn(Optional.ofNullable(floor));
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
		when(menuRepository.findById(anyLong())).thenReturn(Optional.ofNullable(floor));

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

	@Test
	public void shouldGenerateCouponsWhenIsOk() throws DailyFollowUpException {
		// Given
		final List<Patient> patients = Arrays.asList(createPatient());
		final List<Menu> menus = Arrays.asList(createMenu());
		final Path path = Paths.get("src", "main", "resources", "images", "logo-almaviva-sante.png");

		// When
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);
		when(menuRepository.findAllByWeek(anyString(), anyList(), (LocalDate) any()))
				.thenReturn(menus);
		when(menuProperties.getImagesPath()).thenReturn(path.toString());

		// Then
		assertThat(menuServiceImpl.generateCoupons("DEJEUNER", LocalDate.of(2020, Month.APRIL, 11))).isNotEmpty();
	}

	@Test
	public void shouldEmptyGenerateCoupons() throws DailyFollowUpException {
		// Given
		final List<Patient> patients = Arrays.asList(createPatient());
		final List<Menu> menus = Arrays.asList(createMenu());
		final Path path = Paths.get("src", "main", "resources", "images", "logo-almaviva-sante.png");

		// When
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);
		when(menuRepository.findAllByWeek(anyString(), anyList(), (LocalDate) any()))
				.thenReturn(menus);
		when(menuProperties.getImagesPath()).thenReturn(path.toString());

		// Then
		assertThat(menuServiceImpl.generateCoupons("DINER", LocalDate.of(2020, Month.APRIL, 12))).isNotEmpty();
	}

	@Test
	public void shouldGenerateCouponsEmptyWhenMenuIsNotFound() throws DailyFollowUpException {
		// Given
		final List<Patient> patients = Arrays.asList(createPatient());
		final List<Menu> menus = Collections.emptyList();
		final Path path = Paths.get("src", "main", "resources", "images", "logo-almaviva-sante.png");

		// When
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);
		when(menuRepository.findAllByWeek(anyString(), anyList(), (LocalDate) any()))
				.thenReturn(menus);
		when(menuProperties.getImagesPath()).thenReturn(path.toString());

		// Then
		assertThat(menuServiceImpl.generateCoupons("DINER", LocalDate.of(2020, Month.APRIL, 12))).isNotEmpty();
	}

	@Test
	public void shouldThrowWhenCouponsAreNotGenerated() throws DailyFollowUpException {
		// Given
		final List<Patient> patients = Arrays.asList(createPatient());
		final String filename = null;

		// When
		when(patientRepository.findAllByStateTrueOrderByIdDesc()).thenReturn(patients);
		when(menuProperties.getImagesPath()).thenReturn(filename);

		// Then
		assertThatThrownBy(() -> menuServiceImpl.generateCoupons("DEJEUNER", LocalDate.of(2020, Month.APRIL, 11)))
				.isInstanceOf(DailyFollowUpException.class);
	}

	@Test
	public void shouldGetCurrentMenus() {
		// Given
		final List<Menu> menus = Arrays.asList(createMenu());

		// Then
		when(menuRepository.findCurrentMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findCurrentMenus()).isNotEmpty();
	}


	@Test
	public void shouldReturnEmptyListWhenIsNotFillCurrentMenus() {
		// Given
		final List<Menu> menus = Collections.emptyList();

		// Then
		when(menuRepository.findCurrentMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findCurrentMenus()).isEmpty();
	}

	@Test
	public void shouldReturnFalseWhenCheckSpecificationsOfMenuIsNotValid() {
		// Given
		final List<Menu> menus = Arrays.asList(createMenu());
		final MenuDTO menu = createMenuDTO();

		// When
		when(menuRepository.findAll()).thenReturn(menus);

		// Then

		assertThat(menuServiceImpl.checkSpecifications(menu)).isFalse();
	}

	@Test
	public void shouldReturnFalseWhenCheckSpecificationsOfMenuDateIsNotValid() {
		// Given
		final Menu firstMenu = new Menu();
		firstMenu.setDiets(Arrays.asList("test"));
		firstMenu.setTexture("jfjd");
		final Menu secondMenu = new Menu();
		secondMenu.setDiets(Arrays.asList("bibi"));
		secondMenu.setTexture("test");

		final List<Menu> menus = Arrays.asList(firstMenu, secondMenu);
		final MenuDTO menuDTO = createMenuDTO();
		menuDTO.setDiets(Arrays.asList("bibi"));
		menuDTO.setTexture("test");

		// When
		when(menuRepository.findAll()).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.checkSpecifications(menuDTO)).isFalse();
	}

	@Test
	public void shouldCheckSpecificationsOfMenu() {
		// Given
		final Menu firstMenu = new Menu();
		firstMenu.setDiets(Arrays.asList("Normal"));
		firstMenu.setTexture("Normal");
		firstMenu.setStartDate(LocalDate.of(2019, Month.AUGUST, 1));
		firstMenu.setEndDate(LocalDate.of(2019, Month.AUGUST, 20));
		final Menu secondMenu = new Menu();
		secondMenu.setDiets(Arrays.asList("Normal"));
		secondMenu.setTexture("Mixe");
		secondMenu.setStartDate(LocalDate.of(2019, Month.SEPTEMBER, 1));
		secondMenu.setEndDate(LocalDate.of(2019, Month.OCTOBER, 1));

		final List<Menu> menus = Arrays.asList(firstMenu, secondMenu);
		final MenuDTO menuDTO = createMenuDTO();
		menuDTO.setDiets(Arrays.asList("bibi"));
		menuDTO.setTexture("test");
		menuDTO.setDiets(Arrays.asList("Normal"));
		menuDTO.setTexture("Mixe");
		menuDTO.setStartDate(LocalDate.of(2019, Month.SEPTEMBER, 1));
		menuDTO.setEndDate(LocalDate.of(2019, Month.NOVEMBER, 12));

		// When
		when(menuRepository.findAll()).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.checkSpecifications(menuDTO)).isTrue();
	}

	@Test
	public void shouldFindFutureMenus() {
		// Given
		final List<Menu> menus = Arrays.asList(createMenu());

		// When
		when(menuRepository.findFutureMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findFutureMenus()).isNotEmpty();
	}

	@Test
	public void shoulReturnsEmptyListWhenTryingToFindFutureMenus() {
		// Given
		final List<Menu> menus = Collections.emptyList();

		// When
		when(menuRepository.findFutureMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findFutureMenus()).isEmpty();
	}

	@Test
	public void shouldFindPastMenus() {
		// Given
		final List<Menu> menus = Arrays.asList(createMenu());

		// When
		when(menuRepository.findPastMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findPastMenus()).isNotEmpty();
	}

	@Test
	public void shoulReturnsEmptyListWhenTryingToFindPastMenus() {
		// Given
		final List<Menu> menus = Collections.emptyList();

		// When
		when(menuRepository.findPastMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findPastMenus()).isEmpty();
	}

	@Test
	public void shouldDeleteByIds() {
		// Given
		final List<Long> ids = Arrays.asList(1L, 2L);

		// When
		doNothing().when(menuRepository).deleteById(anyLong());

		// Then
		menuServiceImpl.deleteByIds(ids);

		verify(menuRepository, times(2)).deleteById(anyLong());
	}

	@Test
	public void shouldNotDeleteWhenThereIsNoIds() {
		// Given
		final List<Long> ids = Collections.emptyList();

		// Then
		menuServiceImpl.deleteByIds(ids);

		verify(menuRepository, times(0)).deleteById(anyLong());
	}

	@Test
	public void shouldGetMenusByDate() {
		// Given
		final List<Menu> menus = Arrays.asList(createMenu());
		final LocalDate date = LocalDate.now();

		// Then
		when(menuRepository.findCurrentMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findMenusForDate(date)).isNotEmpty();
	}

	@Test
	public void shouldReturnEmptyListWhenTryingToGetMenusByDate() {
		// Given
		final List<Menu> menus = Collections.emptyList();
		final LocalDate date = LocalDate.now();

		// Then
		when(menuRepository.findCurrentMenus((LocalDate) any())).thenReturn(menus);

		// Then
		assertThat(menuServiceImpl.findMenusForDate(date)).isEmpty();
	}
}
