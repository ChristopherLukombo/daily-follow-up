package fr.almavivahealth.ut.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.almavivahealth.service.CaregiverService;
import fr.almavivahealth.service.dto.CaregiverDTO;
import fr.almavivahealth.service.dto.UserDTO;
import fr.almavivahealth.web.handler.RestResponseEntityExceptionHandler;
import fr.almavivahealth.web.rest.CaregiverResource;

/**
 *
 * @author christopher
 * @version 17
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CaregiverResourceTest {

	private static final long ID = 1L;

	private MockMvc mockMvc;

	@Mock
	private CaregiverService caregiverService;

	@InjectMocks
	private CaregiverResource caregiverResource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(caregiverResource)
				.setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
	}

	private static CaregiverDTO createCaregiverDTO() {
		return CaregiverDTO.builder()
				.id(ID)
				.user(new UserDTO())
				.floorId(null)
				.build();
	}

	@Test
	public void shouldCreateCaregiverWhenIsOk() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = createCaregiverDTO();
		caregiverDTO.setId(null);

		// When
		when(caregiverService.save((CaregiverDTO) any())).thenReturn(caregiverDTO);

		// Then
		mockMvc.perform(post("/api/caregivers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isCreated());
		verify(caregiverService, times(1)).save(any(CaregiverDTO.class));
	}

	@Test
	public void shouldCreateCaregiverWhenIsKo() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = createCaregiverDTO();

		// Then
		mockMvc.perform(post("/api/caregivers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isBadRequest());
		verify(caregiverService, times(0)).save(caregiverDTO);
	}

	@Test
	public void shouldUpdateCaregiverWhenIsOk() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = createCaregiverDTO();

		// When
		when(caregiverService.update((CaregiverDTO) any())).thenReturn(caregiverDTO);

		// Then
		mockMvc.perform(put("/api/caregivers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isOk());
		verify(caregiverService, times(1)).update(any(CaregiverDTO.class));
	}

	@Test
	public void shouldUpdateCaregiverWhenIsKo() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = createCaregiverDTO();
		caregiverDTO.setId(null);

		// Then
		mockMvc.perform(put("/api/caregivers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isBadRequest());
		verify(caregiverService, times(0)).update(caregiverDTO);
	}

	@Test
	public void shouldGetAllCaregiversWhenIsOk() throws IOException, Exception {
		// Given
		final List<CaregiverDTO> caregiversDTO = Arrays.asList(createCaregiverDTO());

		// When
		when(caregiverService.findAll()).thenReturn(caregiversDTO);

		// Then
		mockMvc.perform(get("/api/caregivers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(caregiverService, times(1)).findAll();
	}

	@Test
	public void shouldGetAllCaregiversWhenIsEmpty() throws IOException, Exception {
		// Given
		final List<CaregiverDTO> caregiversDTO =  Collections.emptyList();

		// When
		when(caregiverService.findAll()).thenReturn(caregiversDTO);

		// Then
		mockMvc.perform(get("/api/caregivers")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(caregiverService, times(1)).findAll();
	}

	@Test
	public void shouldGetCaregiverWhenIsOk() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = createCaregiverDTO();

		// When
		when(caregiverService.findOne(anyLong())).thenReturn(Optional.ofNullable(caregiverDTO));

		// Then
		mockMvc.perform(get("/api/caregivers/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isOk());
		verify(caregiverService, times(1)).findOne(1L);
	}

	@Test
	public void shouldGetCaregiverWhenIsNotFOund() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = null;

		// When
		when(caregiverService.findOne(anyLong())).thenReturn(Optional.ofNullable(caregiverDTO));

		// Then
		mockMvc.perform(get("/api/caregivers/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isNoContent());
		verify(caregiverService, times(1)).findOne(2L);
	}

	@Test
	public void shouldDeleteCaregiverWhenIsOk() throws Exception {
		// Given
		final Long id = ID;

		// When
		doNothing().when(caregiverService).delete(id);

		// Then
		mockMvc.perform(delete("/api/caregivers/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(caregiverService, times(1)).delete(id);
	}

	@Test
	public void shouldGetAllByFloorNumber() throws Exception {
		// Given
		final List<CaregiverDTO> caregiversDTO = Arrays.asList(createCaregiverDTO());

		// When
		when(caregiverService.findAllByFloorNumber(anyInt())).thenReturn(caregiversDTO);

		// Then
		mockMvc.perform(get("/api/caregivers/floor/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
		verify(caregiverService, times(1)).findAllByFloorNumber(anyInt());
	}

	@Test
	public void shouldReturn204WhenTryingToGetAllByFloorNumber() throws Exception {
		// Given
		final List<CaregiverDTO> caregiversDTO = Collections.emptyList();

		// When
		when(caregiverService.findAllByFloorNumber(anyInt())).thenReturn(caregiversDTO);

		// Then
		mockMvc.perform(get("/api/caregivers/floor/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent());
		verify(caregiverService, times(1)).findAllByFloorNumber(anyInt());
	}

	@Test
	public void shouldGetCaregiverByUserId() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = createCaregiverDTO();

		// When
		when(caregiverService.findByUserId(anyLong())).thenReturn(Optional.ofNullable(caregiverDTO));

		// Then
		mockMvc.perform(get("/api/caregivers/user/1")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isOk());
		verify(caregiverService, times(1)).findByUserId(1L);
	}

	@Test
	public void shouldReturn204WhenTryingToGetCaregiverByUserId() throws IOException, Exception {
		// Given
		final CaregiverDTO caregiverDTO = null;

		// When
		when(caregiverService.findByUserId(anyLong())).thenReturn(Optional.ofNullable(caregiverDTO));

		// Then
		mockMvc.perform(get("/api/caregivers/user/2")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(caregiverDTO)))
		.andExpect(status().isNoContent());
		verify(caregiverService, times(1)).findByUserId(2L);
	}

}
