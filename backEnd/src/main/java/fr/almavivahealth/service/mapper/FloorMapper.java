package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Floor;
import fr.almavivahealth.service.dto.FloorDTO;

/**
 * Mapper for the entity Floor and its DTO called FloorDTO.
 *
 * @author christopher
 * @version 17
 */
@Mapper(componentModel = "spring", uses = RoomMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FloorMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "number", target = "number")
	@Mapping(source = "state", target = "state")
	@Mapping(source = "rooms", target = "rooms")
	FloorDTO floorToFloorDTO(Floor floor);

	@InheritInverseConfiguration
	Floor floorDTOToFloor(FloorDTO floorDTO);

}
