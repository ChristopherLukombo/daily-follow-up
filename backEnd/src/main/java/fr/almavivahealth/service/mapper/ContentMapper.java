package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.domain.enums.TypeMeal;
import fr.almavivahealth.service.dto.ContentDTO;

/**
 * Mapper for the entity Content and its DTO called ContentDTO.
 *
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "texture.id", target = "textureId")
	@Mapping(source = "typeMeal", target = "typeMeal", qualifiedByName = "toTypeMeal")
	@Mapping(source = "salt", target = "salt")
	@Mapping(source = "sugar", target = "sugar")
	ContentDTO contentToContentDTO(Content content);

	@InheritInverseConfiguration
	Content contentDTOToContent(ContentDTO contentDTO);

	@Named("toTypeMeal")
	default String toTypeMeal(final Enum<TypeMeal> typeMeal) {
		if (typeMeal == null) {
			return null;
		}
		return typeMeal.toString();
	}

}
