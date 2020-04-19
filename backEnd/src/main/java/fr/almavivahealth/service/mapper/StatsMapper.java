package fr.almavivahealth.service.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.PatientsByStatus;
import fr.almavivahealth.domain.PatientsPerAllergy;
import fr.almavivahealth.domain.PatientsPerDiet;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;

/**
 * The Interface StatsMapper.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(StatsMapperDecorator.class)
public interface StatsMapper {

	PatientsPerAllergyDTO patientsPerAllergyToPatientsPerAllergyDTO(PatientsPerAllergy patientsPerAllergy);

	PatientsPerDietDTO patientsPerDietToPatientsPerDietDTO(PatientsPerDiet patientsPerDiet);

	PatientsByStatusDTO patientsByStatusToPatientsByStatusDTO(PatientsByStatus patientsByStatus);

}
