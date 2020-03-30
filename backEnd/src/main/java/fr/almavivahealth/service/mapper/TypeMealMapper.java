package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.TypeMeal;
import fr.almavivahealth.service.dto.TypeMealDTO;

/**
 * Mapper for the entity Texture and its DTO called TextureDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TypeMealMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name"),
	})
	TypeMealDTO typeMealToTypeMealDTO(TypeMeal typeMeal);

	@InheritInverseConfiguration
	TypeMeal typeMealDTOToTypeMeal(TypeMealDTO typeMealDTO);
}
