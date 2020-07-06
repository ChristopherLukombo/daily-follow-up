package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Replacement;
import fr.almavivahealth.service.dto.ReplacementDTO;

/**
 * Mapper for the entity Replacement and its DTO called ReplacementDTO.
 *
 * @author christopher
 */
@Mapper(componentModel = "spring", uses = { ContentMapper.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReplacementMapper {

	@Mapping(source = "entries", target = "entries")
	@Mapping(source = "dishes", target = "dishes")
	@Mapping(source = "desserts", target = "desserts")
	@Mapping(source = "dairyProducts", target = "dairyProducts")
	ReplacementDTO replacementToReplacementDTO(Replacement replacement);

	@InheritInverseConfiguration
	Replacement replacementDTOToReplacement(ReplacementDTO menuDTO);
}
