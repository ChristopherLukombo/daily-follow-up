package fr.almavivahealth.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.PatientHistory;
import fr.almavivahealth.domain.enums.Action;
import fr.almavivahealth.service.dto.PatientHistoryDTO;

/**
 * Mapper for the entity PatientHistory and its DTO called PatientHistoryDTO.
 *
 * @author christopher
 * @version 17
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientHistoryMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "patient", target = "patient")
	@Mapping(source = "modifiedBy", target = "modifiedBy")
	@Mapping(source = "modifiedDate", target = "modifiedDate")
	@Mapping(source = "action", target = "action", qualifiedByName = "toAction")
	PatientHistoryDTO patientHistoryToPatientHistoryDTO(PatientHistory patientHistory);

	@Named("toAction")
	default String toAction(final Enum<Action> action) {
		if (action == null) {
			return null;
		}
		return action.toString();
	}

}
