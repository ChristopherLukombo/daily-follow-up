package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.MomentDay;
import fr.almavivahealth.service.dto.MomentDayDTO;

/**
 * Mapper for the entity MomentDay and its DTO called MomentDayDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MomentDayMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name"),
	})
	MomentDayDTO momentDayToMomentDayDTO(MomentDay momentDay);

	@InheritInverseConfiguration
	MomentDay momentDayDTOToMomentDay(MomentDayDTO momentDayDTO);
}
