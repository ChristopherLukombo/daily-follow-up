package fr.almavivahealth.service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.MomentDay;
import fr.almavivahealth.service.dto.MomentDayDTO;

/**
 * Mapper for the entity MomentDay and its DTO called MomentDayDTO.
 *
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(MomentDayMapperDecorator.class)
public interface MomentDayMapper {

	MomentDayDTO momentDayToMomentDayDTO(MomentDay momentDay);

	MomentDay momentDayDTOToMomentDay(MomentDayDTO momentDayDTO);
}
