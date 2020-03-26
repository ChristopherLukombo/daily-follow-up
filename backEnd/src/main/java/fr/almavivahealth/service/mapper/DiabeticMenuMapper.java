package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.DiabeticMenu;
import fr.almavivahealth.service.dto.DiabeticMenuDTO;

/**
 * Mapper for the entity DiabeticMenu and its DTO called DiabeticMenuDTO.
 * 
 * @author christopher
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiabeticMenuMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "startDate", target = "startDate"),
		@Mapping(source = "endDate", target = "endDate"),
		@Mapping(source = "texture.id", target = "textureId"),
		@Mapping(source = "diet.id", target = "dietId")
	})
	DiabeticMenuDTO diabeticMenuToDiabeticMenuDTO(DiabeticMenu diabeticMenu);

	@InheritInverseConfiguration
	DiabeticMenu diabeticMenuDTOToDiabeticMenu(DiabeticMenuDTO diabeticMenuDTO);
}
