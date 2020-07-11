package fr.almavivahealth.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.MenuHistory;
import fr.almavivahealth.domain.enums.Action;
import fr.almavivahealth.service.dto.MenuHistoryDTO;

/**
 * Mapper for the entity MenuHistory and its DTO called MenuHistoryDTO.
 *
 * @author christopher
 * @version 17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuHistoryMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "menu", target = "menu")
	@Mapping(source = "modifiedBy", target = "modifiedBy")
	@Mapping(source = "modifiedDate", target = "modifiedDate")
	@Mapping(source = "action", target = "action", qualifiedByName = "toAction")
	MenuHistoryDTO menuHistoryToMenuHistoryDTO(MenuHistory menuHistory);

	@Named("toAction")
	default String toAction(final Enum<Action> action) {
		if (action == null) {
			return null;
		}
		return action.toString();
	}
}
