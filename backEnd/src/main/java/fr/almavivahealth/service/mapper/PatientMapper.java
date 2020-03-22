package fr.almavivahealth.service.mapper;

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
@Mapper(uses = { Patient.class }, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

	@Mappings({
		@Mapping(source = "id", target = "id"),
		@Mapping(source = "user.id", target = "userId"),
		@Mapping(source = "state", target = "state"),
		@Mapping(source = "texture.id", target = "textureId"),
	})
	PatientDTO patientToPatientDTO(Patient patient);

	@InheritInverseConfiguration
	Patient patientDTOToPatient(PatientDTO patientDTO);
}
