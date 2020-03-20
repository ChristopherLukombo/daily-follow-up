package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Texture;
import fr.almavivahealth.service.dto.TextureDTO;

/**
 * Mapper for the entity Texture and its DTO called TextureDTO.
 * 
 * @author christopher
 */
@Mapper(uses = { Texture.class }, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TextureMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "name", target = "name"),
	})
	TextureDTO textureToTextureDTO(Texture texture);

	@InheritInverseConfiguration
	Texture textureDTOToTexture(TextureDTO textureDTO);
}
