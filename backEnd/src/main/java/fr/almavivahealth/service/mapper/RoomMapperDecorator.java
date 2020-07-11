package fr.almavivahealth.service.mapper;

import fr.almavivahealth.domain.entity.Room;
import fr.almavivahealth.service.dto.RoomDTO;

/**
 * The Class RoomMapperDecorator
 *
 * @author christopher
 * @version 16
 */
public abstract class RoomMapperDecorator implements RoomMapper {

	@Override
	public RoomDTO roomToRoomDTO(final Room room) {
		if (room == null) {
			return null;
		}
        final int numberOfPatients = findNumberOfPatients(room);
        final boolean isFull = checkIsFull(numberOfPatients, room);
		return RoomDTO.builder()
        		.id(room.getId())
        		.number(room.getNumber())
        		.state(room.isState())
        		.maxCapacity(room.getMaxCapacity())
        		.numberOfPatients(numberOfPatients)
        		.isFull(isFull)
        		.build();
	}

	private int findNumberOfPatients(final Room room) {
		if (room.getPatients() == null) {
			return 0;
		}
		return room.getPatients().size();
	}

	private boolean checkIsFull(final Integer numberOfPatients, final Room room) {
		if (room.getMaxCapacity() == null) {
			return false;
		}
		return room.getMaxCapacity().equals(numberOfPatients);
	}

	@Override
	public Room roomDTOToRoom(final RoomDTO roomDTO) {
		if (roomDTO == null) {
			return null;
		}
		 return Room.builder()
	        		.id(roomDTO.getId())
	        		.number(roomDTO.getNumber())
	        		.state(roomDTO.isState())
	        		.maxCapacity(roomDTO.getMaxCapacity())
	        		.build();
	}

}
