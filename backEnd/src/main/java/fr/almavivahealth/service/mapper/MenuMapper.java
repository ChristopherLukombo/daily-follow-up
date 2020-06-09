package fr.almavivahealth.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
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
	@Mapping(source = "diets", target = "diets", qualifiedByName = "toDiets")
	@Mapping(source = "weeks", target = "weeks")
	@Mapping(source = "lastModifiedBy", target = "lastModifiedBy")
	@Mapping(source = "lastModificationDateBy", target = "lastModificationDateBy")
	MenuDTO menuToMenuDTO(Menu menu);

	@InheritInverseConfiguration
	Menu menuDTOToMenu(MenuDTO menuDTO);

	@Named("toDiets")
	default List<String> toTextures(final List<String> diets) {
		if (null == diets || diets.isEmpty()) {
			return Collections.emptyList();
		}
		final List<String> dietsDTOs = new ArrayList<>(diets.size());
		for (final String diet : diets) {
			dietsDTOs.add(diet);
		}
		return dietsDTOs;
	}
}
