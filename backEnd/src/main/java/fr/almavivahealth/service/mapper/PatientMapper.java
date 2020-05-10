package fr.almavivahealth.service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.service.dto.PatientDTO;

/**
 * Mapper for the entity Patient and its DTO called PatientDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(PatientMapperDecorator.class)
public interface PatientMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "firstName", target = "firstName")
	@Mapping(source = "lastName", target = "lastName")
	@Mapping(source = "email", target = "email")
	@Mapping(source = "situation", target = "situation")
	@Mapping(source = "dateOfBirth", target = "dateOfBirth")
	@Mapping(source = "address", target = "address")
	@Mapping(source = "phoneNumber", target = "phoneNumber")
	@Mapping(source = "mobilePhone", target = "mobilePhone")
	@Mapping(source = "job", target = "job")
	@Mapping(source = "bloodGroup", target = "bloodGroup")
	@Mapping(source = "height", target = "height")
	@Mapping(source = "weight", target = "weight")
	@Mapping(source = "sex", target = "sex")
	@Mapping(source = "state", target = "state")
	@Mapping(source = "texture", target = "texture")
	@Mapping(source = "comment", target = "comment")
	@Mapping(source = "room.id", target = "roomId")
	PatientDTO patientToPatientDTO(Patient patient);

	@InheritInverseConfiguration
	Patient patientDTOToPatient(PatientDTO patientDTO);
}
