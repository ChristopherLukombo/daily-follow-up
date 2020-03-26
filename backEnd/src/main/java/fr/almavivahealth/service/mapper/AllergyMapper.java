package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Allergy;
import fr.almavivahealth.service.dto.AllergyDTO;

/**
 * Mapper for the entity Allergy and its DTO called AllergyDTO.
 * 
 * @author christopher
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AllergyMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name")
	})
	AllergyDTO allergyToAllergyDTO(Allergy allergy);

	@InheritInverseConfiguration
	Allergy allergyDTOToAllergy(AllergyDTO allergyDTO);
}
