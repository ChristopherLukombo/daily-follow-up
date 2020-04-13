package fr.almavivahealth.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.config.Constants;
import fr.almavivahealth.dao.AllergyRepository;
import fr.almavivahealth.dao.DietRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.dao.RoomRepository;
import fr.almavivahealth.dao.TextureRepository;
import fr.almavivahealth.domain.Address;
import fr.almavivahealth.domain.Allergy;
import fr.almavivahealth.domain.Diet;
import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.domain.Room;
import fr.almavivahealth.domain.Texture;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.PatientService;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.mapper.PatientMapper;
import fr.almavivahealth.util.DateUtils;

/**
 * Service Implementation for managing Patient.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

	private final PatientRepository patientRepository;

	private final PatientMapper patientMapper;
	
	private final TextureRepository textureRepository;

	private final DietRepository dietRepository;

	private final AllergyRepository allergyRepository;

	private final RoomRepository roomRepository;

    @Autowired
	public PatientServiceImpl(final PatientRepository patientRepository, final PatientMapper patientMapper,
			final TextureRepository textureRepository, final DietRepository dietRepository, final AllergyRepository allergyRepository,
			final RoomRepository roomRepository) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
		this.textureRepository = textureRepository;
		this.dietRepository = dietRepository;
		this.allergyRepository = allergyRepository;
		this.roomRepository = roomRepository;
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
		return patientRepository.findAllByStateTrueOrderByIdDesc().stream()
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
	
	/**
	 * Import patient file in database.
	 *
	 * @param fileToImport the file to import
	 * @return the list of entities
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public List<PatientDTO> importPatientFile(final MultipartFile fileToImport)
			throws DailyFollowUpException {
		LOGGER.debug("Request to import File : {}", fileToImport.getName());
		final List<String> lines = retrieveLines(fileToImport);
		final boolean validCSV = lines.stream().allMatch(this::isValidLine);
		if (!validCSV) {
			throw new DailyFollowUpException(
					"One or more of the lines in the file do not have the correct number of columns");
		}
		final Set<Patient> patients = toPatients(lines);
		
		return patientRepository.saveAll(patients).stream()
				.map(patientMapper::patientToPatientDTO)
				.collect(Collectors.toList());
	}

	private List<String> retrieveLines(final MultipartFile fileToImport) throws DailyFollowUpException {
		try {
			return IOUtils.readLines(fileToImport.getInputStream(), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			throw new DailyFollowUpException(
					"An error occurred while trying to read the contents of the file : " + fileToImport.getName(), e);
		}
	}
	
	private boolean isValidLine(final String line) {
		return !StringUtils.isBlank(line) && 18 == line.split(Constants.SEMICOLON).length;
	}
	
	private Set<Patient> toPatients(final List<String> lines) {
		return lines.stream()
				.skip(1)
				.map(this::buildPatient)
				.collect(Collectors.toSet());
	}
	
	private Patient buildPatient(final String line) {
		final String[] columns = line.split(Constants.SEMICOLON);
		
	    final Set<String> dietNames = stringToSet(columns[14]);
	    final Set<String> allergyNames = stringToSet(columns[15]);

		final Texture texture = textureRepository.findByNameIgnoreCase(getField(columns, 13)).orElseGet(() -> null);
	    final List<Diet> diets = dietRepository.findAllByNameIgnoreCaseIn(dietNames);
	    final List<Allergy> allergies = allergyRepository.findAllByNameIgnoreCaseIn(allergyNames);
		final Room room = roomRepository.findByNumberIgnoreCase(getField(columns, 16)).orElseGet(() -> null);
	    
		final Address address = buildAddress(columns);
	    
		return Patient.builder()
				.firstName(getField(columns, 0))
				.lastName(getField(columns, 1))
				.email(getField(columns, 2))
				.situation(getField(columns, 3))
				.dateOfBirth(DateUtils.convertToDate(columns[4]))
				.address(address)
				.phoneNumber(getField(columns, 5))
				.mobilePhone(getField(columns, 6))
				.job(getField(columns, 7))
				.bloodGroup(getField(columns, 8))
				.height(Double.parseDouble(getField(columns, 9)))
				.weight(Double.parseDouble(getField(columns, 10)))
				.sex(getField(columns, 11))
				.state(Boolean.parseBoolean(getField(columns, 12)))
				.texture(texture)
				.diets(diets)
				.allergies(allergies)
				.room(room)
				.build();
	}

	private Address buildAddress(final String[] columns) {
		final String[] addressValues = columns[17].split(Constants.COMMA);
		if (addressValues.length > 3) {
			throw new IndexOutOfBoundsException(
					"The address field must not exceed 3 columns : " + Arrays.toString(addressValues));
		}
		return (addressValues.length > 0)
				? Address.builder()
						.streetName(getField(addressValues, 0))
						.city(getField(addressValues, 1))
						.postalCode(getField(addressValues, 2)).build()
				: null;
	}

	private String getField(final String[] array, final int index) {
		return array.length > index ? array[index].trim() : null;
	}
	
	private Set<String> stringToSet(final String value) {
		return Stream.of(value.split(Constants.COMMA))
				.map(String::trim)
				.collect(Collectors.toSet());
	}

}
