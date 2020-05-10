package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Menu;
import fr.almavivahealth.service.dto.MenuDTO;

/**
 * Mapper for the entity Menu and its DTO called MenuDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "startDate", target = "startDate")
	@Mapping(source = "endDate", target = "endDate")
	@Mapping(source = "texture.id", target = "textureId")
	@Mapping(source = "diet.id", target = "dietId")
	MenuDTO menuToMenuDTO(Menu menu);

	@InheritInverseConfiguration
	Menu menuDTOToMenu(MenuDTO menuDTO);
}
