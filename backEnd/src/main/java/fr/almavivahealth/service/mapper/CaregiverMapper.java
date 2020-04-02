package fr.almavivahealth.service.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import fr.almavivahealth.domain.Caregiver;
import fr.almavivahealth.service.dto.CaregiverDTO;

/**
 * Mapper for the entity Caregiver and its DTO called CaregiverDTO.
 * 
 * @author christopher
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CaregiverMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "floor.id", target = "floorId")
	CaregiverDTO caregiverToCaregiverDTO(Caregiver caregiver);

	@InheritInverseConfiguration
	Caregiver caregiverDTOToCaregiver(CaregiverDTO caregiverDTO);
}
