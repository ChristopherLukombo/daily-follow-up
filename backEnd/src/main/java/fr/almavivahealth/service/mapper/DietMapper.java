package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Diet;
import fr.almavivahealth.service.dto.DietDTO;

/**
 * Mapper for the entity Diet and its DTO called DayDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DietMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name")
	})
	DietDTO dietToDietDTO(Diet diet);

	@InheritInverseConfiguration
	Diet dietDTOToDiet(DietDTO dietDTO);
}
