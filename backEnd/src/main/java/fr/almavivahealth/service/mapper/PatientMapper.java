package fr.almavivahealth.service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.service.dto.PatientDTO;

/**
 * Mapper for the entity Patient and its DTO called PatientDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(PatientMapperDecorator.class)
public interface PatientMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "firstName", target = "firstName"),
		@Mapping(source = "lastName", target = "lastName"),
		@Mapping(source = "email", target = "email"),
		@Mapping(source = "situation", target = "situation"),
		@Mapping(source = "address", target = "address"),
		@Mapping(source = "postalCode", target = "postalCode"),
		@Mapping(source = "phoneNumber", target = "phoneNumber"),
		@Mapping(source = "mobilePhone", target = "mobilePhone"),
		@Mapping(source = "job", target = "job"),
		@Mapping(source = "bloodGroup", target = "bloodGroup"),
		@Mapping(source = "sex", target = "sex"),
		@Mapping(source = "state", target = "state"),
		@Mapping(source = "texture", target = "texture"),
	})
	PatientDTO patientToPatientDTO(Patient patient);

	@InheritInverseConfiguration
	Patient patientDTOToPatient(PatientDTO patientDTO);
}
