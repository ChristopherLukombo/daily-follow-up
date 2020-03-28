package fr.almavivahealth.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.service.PatientService;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.mapper.PatientMapper;

/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

	private final PatientRepository patientRepository;

	private final PatientMapper patientMapper;

	@Autowired
	public PatientServiceImpl(final PatientRepository patientRepository, final PatientMapper patientMapper) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
	}

	/**
	 * Save a patient.
	 *
	 * @param patientDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public PatientDTO save(final PatientDTO patientDTO) {
		LOGGER.debug("Request to save Patient : {}", patientDTO);
        Patient patient = patientMapper.patientDTOToPatient(patientDTO);
        patient = patientRepository.save(patient); 
		return patientMapper.patientToPatientDTO(patient);
	}

	/**
	 * Update a patient.
	 *
	 * @param patientDTO the patient DTO
	 * @return the persisted entity
	 */
	@Override
	public PatientDTO update(final PatientDTO patientDTO) {
		LOGGER.debug("Request to update Patient : {}", patientDTO);
		Patient patient = patientMapper.patientDTOToPatient(patientDTO);
		patient = patientRepository.saveAndFlush(patient);
		return patientMapper.patientToPatientDTO(patient);
	}

	/**
	 * Get all the patients.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientDTO> findAll() {
		LOGGER.debug("Request to get all Patients");
		return patientRepository.findAll().stream()
				.map(patientMapper::patientToPatientDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get the "id" patient.
	 *
	 * @param id the id
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PatientDTO> findOne(final Long id) {
		LOGGER.debug("Request to get Patient : {}", id);
		return patientRepository.findById(id)
				.map(patientMapper::patientToPatientDTO);
	}

	/**
	 * Delete the "id" patient.
	 *
	 * @param id the id of the entity
	 */
	@Override
	public void delete(final Long id) {
		LOGGER.debug("Request to delete Patient : {}", id);
		patientRepository.findById(id).ifPresent(patient -> {
			patient.setState(false);
			patientRepository.saveAndFlush(patient);
		});
	}
}
