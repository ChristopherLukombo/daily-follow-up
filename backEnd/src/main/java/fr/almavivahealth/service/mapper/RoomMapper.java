package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Room;
import fr.almavivahealth.service.dto.RoomDTO;

/**
 * Mapper for the entity Room and its DTO called RoomDTO.
 * 
 * @author christopher
 */
@Mapper(uses = { Room.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "number", target = "number"),
		@Mapping(source = "state", target = "state")
	})
	RoomDTO roomToRoomDTO(Room room);

	@InheritInverseConfiguration
	Room roomDTOToRoom(RoomDTO roomDTO);
}
