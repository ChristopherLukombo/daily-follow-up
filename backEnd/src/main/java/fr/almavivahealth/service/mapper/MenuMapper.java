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
@Mapper(
		componentModel = "spring",
		uses = { ReplacementMapper.class, DietMapper.class, WeekMapper.class },
		unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "startDate", target = "startDate")
	@Mapping(source = "endDate", target = "endDate")
	@Mapping(source = "texture", target = "texture")
	@Mapping(source = "replacement", target = "replacement")
	@Mapping(source = "diet", target = "diet")
	@Mapping(source = "weeks", target = "weeks")
	@Mapping(source = "lastModifiedBy", target = "lastModifiedBy")
	@Mapping(source = "lastModificationDateBy", target = "lastModificationDateBy")
	MenuDTO menuToMenuDTO(Menu menu);

	@InheritInverseConfiguration
	Menu menuDTOToMenu(MenuDTO menuDTO);
}
