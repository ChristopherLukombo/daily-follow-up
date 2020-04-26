package fr.almavivahealth.ut.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import fr.almavivahealth.dao.RoomRepository;
import fr.almavivahealth.domain.Room;
import fr.almavivahealth.service.dto.RoomDTO;
import fr.almavivahealth.service.impl.RoomServiceImpl;
import fr.almavivahealth.service.mapper.RoomMapper;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

	private static final long ID = 1L;
	
	private static final boolean STATE = true;

	private static final String NUMBER = "1-1234";

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private RoomMapper roomMapper;

	@InjectMocks
	private RoomServiceImpl roomServiceImpl;

	private static Room createRoom() {
		return Room.builder()
				.id(ID)
				.number(NUMBER)
				.state(true)
				.build();
	}
	
	private static RoomDTO createRoomDTO() {
		return RoomDTO.builder()
				.id(ID)
				.number(NUMBER)
				.state(STATE)
				.build();
	}
	
	@Test
	public void shouldSaveRoomWhenIsOk() {
		// Given
		final Room room = createRoom();
		final RoomDTO roomDTO = createRoomDTO();
		
		// When
		when(roomRepository.save((Room) any())).thenReturn(room);
		when(roomMapper.roomToRoomDTO((Room) any())).thenReturn(roomDTO);
		
		// Then
		assertThat(roomServiceImpl.save(roomDTO)).isEqualTo(roomDTO);
	}
	
	@Test
	public void shouldSaveRoomWhenIsKo() {
		// Given
		final Room room = null;
		final RoomDTO roomDTO = null;
		
		// When
		when(roomRepository.save((Room) any())).thenReturn(room);
		when(roomMapper.roomToRoomDTO((Room) any())).thenReturn(roomDTO);
		
		// Then
		assertThat(roomServiceImpl.save(roomDTO)).isEqualTo(roomDTO);
	}
	
	@Test
	public void shouldUpdateRoomWhenIsOk() {
		// Given
		final Room room = createRoom();
		final RoomDTO roomDTO = createRoomDTO();
		
		// When
		when(roomRepository.saveAndFlush((Room) any())).thenReturn(room);
		when(roomMapper.roomToRoomDTO((Room) any())).thenReturn(roomDTO);
		
		// Then
		assertThat(roomServiceImpl.update(roomDTO)).isEqualTo(roomDTO);
	}
	
	@Test
	public void shouldUpdateRoomWhenIsKo() {
		// Given
		final Room room = null;
		final RoomDTO roomDTO = null;
		
		// When
		when(roomRepository.saveAndFlush((Room) any())).thenReturn(room);
		when(roomMapper.roomToRoomDTO((Room) any())).thenReturn(roomDTO);
		
		// Then
		assertThat(roomServiceImpl.update(roomDTO)).isEqualTo(roomDTO);
	}
	
	@Test
	public void shouldGetAllRoomsWhenIsOk() {
		// Given
		final List<Room> rooms = Arrays.asList(createRoom());

		// Then
		when(roomRepository.findAllByOrderByIdDesc()).thenReturn(rooms);

		// Then
		assertThat(roomServiceImpl.findAll()).isNotEmpty();
	}

	@Test
	public void shouldGetAllRoomsWhenIsEmpty() {
		// Given
		final List<Room> rooms = Collections.emptyList();

		// Then
		when(roomRepository.findAllByOrderByIdDesc()).thenReturn(rooms);

		// Then
		assertThat(roomServiceImpl.findAll()).isEmpty();
	}

	@Test
	public void shouldGetAllRoomsWhenIsNull() {
		// Given
		final List<Room> rooms = null;

		// Then
		when(roomRepository.findAllByOrderByIdDesc()).thenReturn(rooms);

		// Then
		assertThatThrownBy(() -> roomServiceImpl.findAll())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldGetRoomWhenIsOk() {
		// Given
		final Room room = createRoom();
		final RoomDTO roomDTO = createRoomDTO();

		// When
		when(roomRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(room));
		when(roomMapper.roomToRoomDTO((Room) any())).thenReturn(roomDTO);
		
		// Then
		assertThat(roomServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(roomDTO));
	}
	
	@Test
	public void shouldGetRoomWhenIsNull() {
		// Given
		final Room room = null;
		final RoomDTO roomDTO = null;

		// When
		when(roomRepository.findById((anyLong()))).thenReturn(Optional.ofNullable(room));
		
		// Then
		assertThat(roomServiceImpl.findOne(ID)).isEqualTo(Optional.ofNullable(roomDTO));
	}
	
	@Test
	public void shouldDeleteRoomWhenIsOk() {
		// When
		doNothing().when(roomRepository).deleteById(ID);

		// Then
		roomServiceImpl.delete(ID);
		
		verify(roomRepository, times(1)).deleteById(anyLong());
	}
	
	@Test
	public void shouldFindByPatientIdWhenIsOk() {
		// Given
		final Room room  = createRoom();
		final RoomDTO roomDTO  = createRoomDTO();
		
		// When
		when(roomRepository.findByPatientId(anyLong())).thenReturn(Optional.ofNullable(room));
		when(roomMapper.roomToRoomDTO((Room) any())).thenReturn(roomDTO);
		
		// Then
		assertThat(roomServiceImpl.findByPatientId(ID)).isEqualTo(Optional.ofNullable(roomDTO));
	}
	
	@Test
	public void shouldFindByPatientIdWhenIsNull() {
		// Given
		final Room room  = null;
		final RoomDTO roomDTO  = null;
		
		// When
		when(roomRepository.findByPatientId(anyLong())).thenReturn(Optional.ofNullable(room));
		
		// Then
		assertThat(roomServiceImpl.findByPatientId(ID)).isEqualTo(Optional.ofNullable(roomDTO));
	}
	
	@Test
	public void shouldReturnListOfNotEmptyVacantRooms() {
		// Given
		final List<Room> rooms = Arrays.asList(createRoom());
		
		// When
		when(roomRepository.findAllVacantRooms()).thenReturn(rooms);
		
		// Then
		assertThat(roomServiceImpl.findAllVacantRooms()).isNotEmpty();
	}
	
	@Test
	public void shouldThrowWhenListVacantRoomsIsNull() {
		// Given
		final List<Room> rooms = null;
		
		// When
		when(roomRepository.findAllVacantRooms()).thenReturn(rooms);
		
		// Then
		assertThatThrownBy(() -> roomServiceImpl.findAllVacantRooms())
		.isInstanceOf(NullPointerException.class);
	}
	
	@Test
	public void shouldReturnListOfEmptyVacantRooms() {
		// Given
		final List<Room> rooms = Collections.emptyList();
		
		// When
		when(roomRepository.findAllVacantRooms()).thenReturn(rooms);
		
		// Then
		assertThat(roomServiceImpl.findAllVacantRooms()).isEmpty();
	}
	
}
