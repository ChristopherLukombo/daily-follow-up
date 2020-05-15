package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Content;
import fr.almavivahealth.service.dto.ContentDTO;

/**
 * Mapper for the entity Content and its DTO called ContentDTO.
 *
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "code", target = "code")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "groupName", target = "groupName")
	@Mapping(source = "subGroupName", target = "subGroupName")
	@Mapping(source = "subSubGroupName", target = "subSubGroupName")
	@Mapping(source = "calories", target = "calories")
	@Mapping(source = "protein", target = "protein")
	@Mapping(source = "carbohydrate", target = "carbohydrate")
	@Mapping(source = "lipids", target = "lipids")
	@Mapping(source = "sugars", target = "sugars")
	@Mapping(source = "foodFibres", target = "foodFibres")
	@Mapping(source = "agSaturates", target = "agSaturates")
	@Mapping(source = "salt", target = "salt")
	@Mapping(source = "imageUrl", target = "imageUrl")
	ContentDTO contentToContentDTO(Content content);

	@InheritInverseConfiguration
	Content contentDTOToContent(ContentDTO contentDTO);

}
