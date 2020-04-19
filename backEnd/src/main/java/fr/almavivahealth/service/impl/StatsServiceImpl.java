package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.service.StatsService;
import fr.almavivahealth.service.dto.PatientsByStatusDTO;
import fr.almavivahealth.service.dto.PatientsPerAllergyDTO;
import fr.almavivahealth.service.dto.PatientsPerDietDTO;
import fr.almavivahealth.service.mapper.StatsMapper;

/**
 * Service Implementation for managing Stats.
 */
@Service
@Transactional
public class StatsServiceImpl implements StatsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StatsServiceImpl.class);
	
	private final PatientRepository patientRepository;
	
	private final StatsMapper statsMapper;
	
    @Autowired
	public StatsServiceImpl(final PatientRepository patientRepository, final StatsMapper allergyPerPatientMapper) {
		this.patientRepository = patientRepository;
		this.statsMapper = allergyPerPatientMapper;
	}

	/**
	 * Find number of patients per allergy.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientsPerAllergyDTO> findNumberOfPatientsPerAllergy() {
		LOGGER.debug("Request to get number of patients per Allergy");
		return patientRepository.findNumberOfPatientsPerAllergy().stream()
				.map(statsMapper::patientsPerAllergyToPatientsPerAllergyDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find number of patients per diet.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientsPerDietDTO> findNumberOfPatientsPerDiet() {
		LOGGER.debug("Request to get number of patients per Diet");
		return patientRepository.findNumberOfPatientsPerDiet().stream()
				.map(statsMapper::patientsPerDietToPatientsPerDietDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Find number of patients by status.
	 *
	 * @return the list
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientsByStatusDTO> findNumberOfPatientsByStatus() {
		LOGGER.debug("Request to get number of patients by status");
		return patientRepository.findNumberOfPatientsByStatus().stream()
				.map(statsMapper::patientsByStatusToPatientsByStatusDTO)
				.collect(Collectors.toList());
	}
	
}
