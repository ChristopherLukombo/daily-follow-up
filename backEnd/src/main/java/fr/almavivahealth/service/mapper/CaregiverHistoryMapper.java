package fr.almavivahealth.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.CaregiverHistory;
import fr.almavivahealth.domain.enums.Action;
import fr.almavivahealth.service.dto.CaregiverHistoryDTO;

/**
 * Mapper for the entity CaregiverHistory and its DTO called CaregiverHistoryDTO.
 *
 *
 * @author christopher
 * @version 17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CaregiverHistoryMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "caregiver", target = "caregiver")
	@Mapping(source = "modifiedBy", target = "modifiedBy")
	@Mapping(source = "modifiedDate", target = "modifiedDate")
	@Mapping(source = "action", target = "action", qualifiedByName = "toAction")
	CaregiverHistoryDTO caregiverHistoryToCaregiverHistoryDTO(CaregiverHistory caregiverHistory);

	@Named("toAction")
	default String toAction(final Enum<Action> action) {
		if (action == null) {
			return null;
		}
		return action.toString();
	}
}
