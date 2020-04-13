package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Floor;
import fr.almavivahealth.service.dto.FloorDTO;

/**
 * Mapper for the entity Floor and its DTO called FloorDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FloorMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "number", target = "number")
	@Mapping(source = "state", target = "state")
	FloorDTO floorToFloorDTO(Floor floor);

	@InheritInverseConfiguration
	Floor floorDTOToFloor(FloorDTO floorDTO);
}
