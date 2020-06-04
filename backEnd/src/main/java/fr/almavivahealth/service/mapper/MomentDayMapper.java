package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.service.dto.MomentDayDTO;

/**
 * Mapper for the entity MomentDay and its DTO called MomentDayDTO.
 *
 * @author christopher
 */
@Mapper(componentModel = "spring", uses = ContentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MomentDayMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "contents", target = "contents")
	MomentDayDTO momentDayToMomentDayDTO(MomentDay momentDay);

	@InheritInverseConfiguration
	MomentDay momentDayDTOToMomentDay(MomentDayDTO momentDayDTO);
}
