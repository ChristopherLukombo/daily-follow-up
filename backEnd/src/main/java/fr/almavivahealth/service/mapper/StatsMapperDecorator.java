package fr.almavivahealth.service.mapper;

import java.math.BigDecimal;

import fr.almavivahealth.domain.entity.TopTrendyMenu;
import fr.almavivahealth.domain.projection.PatientsByStatus;
import fr.almavivahealth.domain.projection.PatientsPerAllergy;
import fr.almavivahealth.domain.projection.PatientsPerDiet;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.service.dto.TopTrendyMenuDTO;

/**
 * The Class StatsMapperDecorator.
 */
public abstract class StatsMapperDecorator implements StatsMapper {

	@Override
	public PatientsPerAllergyDTO patientsPerAllergyToPatientsPerAllergyDTO(final PatientsPerAllergy patientsPerAllergy) {
		if (patientsPerAllergy == null) {
			return null;
		}
		return PatientsPerAllergyDTO.builder()
				.allergyName(patientsPerAllergy.getAllergyName())
				.numberPatients(patientsPerAllergy.getNumberPatients())
				.percentage(patientsPerAllergy.getPercentage().setScale(2, BigDecimal.ROUND_HALF_UP))
				.build();
	}

	@Override
	public PatientsPerDietDTO patientsPerDietToPatientsPerDietDTO(final PatientsPerDiet patientsPerDiet) {
		if (patientsPerDiet == null) {
			return null;
		}
		return PatientsPerDietDTO.builder()
				.dietName(patientsPerDiet.getDietName())
				.numberPatients(patientsPerDiet.getNumberPatients())
				.percentage(patientsPerDiet.getPercentage().setScale(2, BigDecimal.ROUND_HALF_UP))
				.build();
	}

	@Override
	public PatientsByStatusDTO patientsByStatusToPatientsByStatusDTO(final PatientsByStatus patientByStatus) {
		if (patientByStatus == null) {
			return null;
		}
		return PatientsByStatusDTO.builder()
				.activePatients(patientByStatus.getActivePatients())
				.inactivePatients(patientByStatus.getInactivePatients())
				.totalPatients(patientByStatus.getTotalPatients())
				.build();
	}

	@Override
	public TopTrendyMenuDTO topTrendyMenuToTopTrendyMenuDTO(final TopTrendyMenu topTrendyMenu) {
		if (topTrendyMenu == null) {
			return null;
		}
		return TopTrendyMenuDTO.builder()
				.diets(topTrendyMenu.getDiets())
				.nb(topTrendyMenu.getNb())
				.texture(topTrendyMenu.getTexture())
				.build();
	}

}
