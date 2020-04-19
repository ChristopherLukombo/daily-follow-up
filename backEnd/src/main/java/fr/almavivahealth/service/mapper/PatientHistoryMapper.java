package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.PatientHistory;
import fr.almavivahealth.service.dto.PatientHistoryDTO;

/**
 * Mapper for the entity PatientHistory and its DTO called PatientHistoryDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientHistoryMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "patient", target = "patient")
	@Mapping(source = "modifiedBy", target = "modifiedBy")
	@Mapping(source = "modifiedDate", target = "modifiedDate")
	@Mapping(source = "action", target = "action")
	PatientHistoryDTO patientHistoryToPatientHistoryDTO(PatientHistory patientHistory);

	@InheritInverseConfiguration
	PatientHistory patientHistoryDTOToPatientHistory(PatientHistoryDTO patientHistoryDTO);

}
