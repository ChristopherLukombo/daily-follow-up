package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Content;
import fr.almavivahealth.service.dto.ContentDTO;

/**
 * Mapper for the entity Content and its DTO called ContentDTO.
 * 
 * @author christopher
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContentMapper {
	
	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name"),
		@Mapping(source = "texture.id", target = "textureId"),
		@Mapping(source = "salt", target = "salt"),
		@Mapping(source = "sugar", target = "sugar")
	})
	ContentDTO contentToContentDTO(Content content);

	@InheritInverseConfiguration
	Content contentDTOToContent(ContentDTO contentDTO);
}