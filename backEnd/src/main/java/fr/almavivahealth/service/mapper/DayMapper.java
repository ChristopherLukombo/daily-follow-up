package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Day;
import fr.almavivahealth.service.dto.DayDTO;

/**
 * Mapper for the entity Day and its DTO called DayDTO.
 *
 * @author christopher
 */
@Mapper(componentModel = "spring", uses = MomentDayMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DayMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "momentDays", target = "momentDays")
	DayDTO dayToDayDTO(Day day);

	@InheritInverseConfiguration
	Day dayDTOToDay(DayDTO dayDTO);
}
