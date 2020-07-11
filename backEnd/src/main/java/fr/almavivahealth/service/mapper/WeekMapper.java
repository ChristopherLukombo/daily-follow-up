package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Week;
import fr.almavivahealth.service.dto.WeekDTO;

/**
 * Mapper for the entity Week and its DTO called WeekDTO.
 *
 * @author christopher
 * @version 16
 */
@Mapper(componentModel = "spring", uses = { DayMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeekMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "number", target = "number")
	@Mapping(source = "days", target = "days")
	WeekDTO weekToWeekDTO(Week week);

	@InheritInverseConfiguration
	Week weekDTOToWeek(WeekDTO weekDTO);
}
