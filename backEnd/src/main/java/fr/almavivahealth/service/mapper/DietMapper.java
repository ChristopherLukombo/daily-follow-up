package fr.almavivahealth.service.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.service.dto.DietDTO;

/**
 * Mapper for the entity Diet and its DTO called DayDTO.
 *
 * @author christopher
 * @version 16
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DietMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "elementsToCheck", target = "elementsToCheck", qualifiedByName = "toElementsToCheck")
	DietDTO dietToDietDTO(Diet diet);

	@InheritInverseConfiguration
	Diet dietDTOToDiet(DietDTO dietDTO);

	@Named("toElementsToCheck")
	default List<String> toElementsToCheck(final List<String> elementsToCheck) {
		if (null == elementsToCheck || elementsToCheck.isEmpty()) {
			return Collections.emptyList();
		}
		final List<String> elementsToCheckDTOs = new ArrayList<>(elementsToCheck.size());
		for (final String typeMeal : elementsToCheck) {
			elementsToCheckDTOs.add(typeMeal);
		}
		return elementsToCheckDTOs;
	}
}
