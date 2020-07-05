package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import fr.almavivahealth.dao.OrderRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.entity.Day;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.domain.entity.TopTrendyMenu;
import fr.almavivahealth.domain.entity.Week;
import fr.almavivahealth.domain.projection.PatientsByStatus;
import fr.almavivahealth.domain.projection.PatientsPerAllergy;
import fr.almavivahealth.domain.projection.PatientsPerDiet;
import fr.almavivahealth.service.dto.MenuDTO;
import fr.almavivahealth.service.dto.OrderDTO;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.service.dto.TopTrendyMenuDTO;
import fr.almavivahealth.service.impl.stats.StatsServiceImpl;
import fr.almavivahealth.service.mapper.StatsMapper;

@RunWith(MockitoJUnitRunner.class)
public class StatsServiceTest {

	private static final long TOTAL_PATIENTS = 13L;

	private static final long INACTIVE_PATIENTS = 23L;

	private static final long ACTIVE_PATIENTS = 12L;

	private static final String DIET_NAME = "SANS SEL";

	private static final int PERCENTAGE = 11;

	private static final long NUMBER_PATIENTS = 8L;

	private static final String ALLERGY_NAME = "SOJA";

	private static final long ID = 1L;

	private static final String EMAIL = "ben.zotito@gmail.com";

	private static final String LASTNAME = "Zotito";

	private static final String TEXTURE_NAME = "Sel";

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private StatsMapper statsMapper;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private MenuRepository menuRepository;

	@InjectMocks
	private StatsServiceImpl statsServiceImpl;

	private static PatientsPerAllergyDTO createPatientsPerAllergyDTO() {
		return PatientsPerAllergyDTO.builder()
				.allergyName(ALLERGY_NAME)
				.numberPatients(NUMBER_PATIENTS)
				.percentage(BigDecimal.valueOf(PERCENTAGE))
				.build();
	}

	private static PatientsPerAllergy createPatientsPerAllergy() {
		return null;
	}

	private static PatientsPerDietDTO createPatientsPerDietDTO() {
		return PatientsPerDietDTO.builder()
				.dietName(DIET_NAME)
				.numberPatients(NUMBER_PATIENTS)
				.percentage(BigDecimal.valueOf(PERCENTAGE))
				.build();
	}

	private static PatientsPerDiet createPatientsPerDiet() {
		return null;
	}

	private static PatientsByStatusDTO createPatientsByStatusDTO() {
		return PatientsByStatusDTO.builder()
				.activePatients(ACTIVE_PATIENTS)
				.inactivePatients(INACTIVE_PATIENTS)
				.totalPatients(TOTAL_PATIENTS)
				.build();
	}

	private static PatientsByStatus createPatientsByStatus() {
		return null;
	}

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
				.garnish(createContent())
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


	private static Order createOrder() {
		return Order.builder()
				.id(ID)
				.patient(null)
				.dairyProducts(null)
				.build();
	}

	private static OrderDTO createOrderDTO() {
		return OrderDTO.builder()
				.id(ID)
				.deliveryDate(LocalDate.of(2020, Month.JANUARY, 1))
				.patientId(null)
				.build();
	}

	@Test
	public void shouldFindNumberOfPatientsPerAllergyWhenIsOk() {
		// Given
		final PatientsPerAllergy patientsPerAllergy = createPatientsPerAllergy();
		final List<PatientsPerAllergy> patientsPerAllergies = Arrays.asList(patientsPerAllergy);
		final PatientsPerAllergyDTO patientsPerAllergyDTO = createPatientsPerAllergyDTO();

		// When
		when(patientRepository.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);
		when(statsMapper.patientsPerAllergyToPatientsPerAllergyDTO((PatientsPerAllergy) any()))
		.thenReturn(patientsPerAllergyDTO);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerAllergy()).isNotEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsPerAllergyWhenIsNull() {
		// Given
		final List<PatientsPerAllergy> patientsPerAllergies = null;

		// When
		when(patientRepository.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);

		// Then
		assertThatThrownBy(() -> statsServiceImpl.findNumberOfPatientsPerAllergy())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldFindNumberOfPatientsPerAllergyWhenIsEmpty() {
		// Given
		final List<PatientsPerAllergy> patientsPerAllergies = Collections.emptyList();

		// When
		when(patientRepository.findNumberOfPatientsPerAllergy()).thenReturn(patientsPerAllergies);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerAllergy()).isEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsPerDietWhenIsOk() {
		// Given
		final PatientsPerDiet patientsPerDiet = createPatientsPerDiet();
		final List<PatientsPerDiet> patientsPerDiets = Arrays.asList(patientsPerDiet);
		final PatientsPerDietDTO patientsPerDietDTO = createPatientsPerDietDTO();

		// When
		when(patientRepository.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);
		when(statsMapper.patientsPerDietToPatientsPerDietDTO((PatientsPerDiet) any())).thenReturn(patientsPerDietDTO);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerDiet()).isNotEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsPerDietWhenIsNull() {
		// Given
		final List<PatientsPerDiet> patientsPerDiets = null;

		// When
		when(patientRepository.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);

		// Then
		assertThatThrownBy(() -> statsServiceImpl.findNumberOfPatientsPerDiet())
		.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void shouldFindNumberOfPatientsPerDietWhenIsEmpty() {
		// Given
		final List<PatientsPerDiet> patientsPerDiets = Collections.emptyList();

		// When
		when(patientRepository.findNumberOfPatientsPerDiet()).thenReturn(patientsPerDiets);

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsPerDiet()).isEmpty();
	}

	@Test
	public void shouldFindNumberOfPatientsByStatusWhenIsOk() {
		// Given
		final PatientsByStatus patientsByStatus = createPatientsByStatus();

		// When
		when(patientRepository.findNumberOfPatientsByStatus()).thenReturn(Optional.ofNullable(patientsByStatus));

		// Then
		assertThat(statsServiceImpl.findNumberOfPatientsByStatus()).isEmpty();
	}

	@Test
	public void shouldFindTrendyDiets() {
		// Given
		final TopTrendyMenu topTrendyMenu = null;
		final List<TopTrendyMenu> topTrendyMenus = Arrays.asList(topTrendyMenu);
		final TopTrendyMenuDTO topTrendyMenuDTO = new TopTrendyMenuDTO();
		topTrendyMenuDTO.setNb(1L);
		topTrendyMenuDTO.setTexture("Normal");
		topTrendyMenuDTO.setDiets("Normal");

		// When
		when(menuRepository.findTrendyDishes()).thenReturn(topTrendyMenus);
		when(statsMapper.topTrendyMenuToTopTrendyMenuDTO((TopTrendyMenu) any())).thenReturn(topTrendyMenuDTO);

		// Then
		assertThat(statsServiceImpl.findTrendyDiets()).isNotEmpty();
	}

	@Test
	public void shoulReturnsEmptyListWhenTryingToFindTrendyDiets() {
		// Given
		final List<TopTrendyMenu> topTrendyMenus = Collections.emptyList();

		// When
		when(menuRepository.findTrendyDishes()).thenReturn(topTrendyMenus);

		// Then
		assertThat(statsServiceImpl.findTrendyDiets()).isEmpty();
	}

	@Test
	public void shouldFindAllForNextDays() {
		// Given
		final List<Order> orders = Arrays.asList(createOrder());

		// When
		when(orderRepository.findAllForWeekBetween((LocalDate) any(), (LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(statsServiceImpl.findAllForNextDays()).isNotEmpty();
	}

	@Test
	public void shouldReturnsEmptyMapWhenTryingToFindAllForNextDays() {
		// Given
		final List<Order> orders = Collections.emptyList();

		// When
		when(orderRepository.findAllForWeekBetween((LocalDate) any(), (LocalDate) any())).thenReturn(orders);

		// Then
		assertThat(statsServiceImpl.findAllForNextDays()).isNotEmpty();
	}

	@Test
	public void shouldFindAllTrendyContents() {
		// Given
		final Menu menu = createMenu();
		final List<Menu> menus = Arrays.asList(menu);

		// When
		when(menuRepository.findAll()).thenReturn(menus);

		// Then
		assertThat(statsServiceImpl.findAllTrendyContents()).isNotEmpty();
	}

	@Test
	public void shouldReturnsEmptyListWhenTryingToFindAllTrendyContents() {
		// Given
		final List<Menu> menus = Collections.emptyList();

		// When
		when(menuRepository.findAll()).thenReturn(menus);

		// Then
		assertThat(statsServiceImpl.findAllTrendyContents()).isEmpty();
	}
}
