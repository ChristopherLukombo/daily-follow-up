package fr.almavivahealth.service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Room;
import fr.almavivahealth.service.dto.RoomDTO;

/**
 * Mapper for the entity Room and its DTO called RoomDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(RoomMapperDecorator.class)
public interface RoomMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "number", target = "number")
	@Mapping(source = "state", target = "state")
	@Mapping(source = "maxCapacity", target = "maxCapacity")
	RoomDTO roomToRoomDTO(Room room);

	@InheritInverseConfiguration
	Room roomDTOToRoom(RoomDTO roomDTO);

}
