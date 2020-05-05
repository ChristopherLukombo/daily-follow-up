package fr.almavivahealth.service.impl;

import static fr.almavivahealth.config.Constants.COMMA;
import static fr.almavivahealth.config.Constants.CSV;
import static fr.almavivahealth.config.Constants.SEMICOLON;
import static fr.almavivahealth.utils.MimeTypes.MIME_APPLICATION_VND_MSEXCEL;
import static fr.almavivahealth.utils.MimeTypes.MIME_TEXT_CSV;
import static fr.almavivahealth.utils.MimeTypes.MIME_TEXT_PLAIN;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.any23.encoding.TikaEncodingDetector;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.dao.DietRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.dao.RoomRepository;
import fr.almavivahealth.dao.TextureRepository;
import fr.almavivahealth.domain.Allergy;
import fr.almavivahealth.domain.Diet;
import fr.almavivahealth.domain.Patient;
import fr.almavivahealth.domain.Room;
import fr.almavivahealth.domain.Texture;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.PatientService;
import fr.almavivahealth.service.dto.BulkResult;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.mapper.PatientMapper;
import fr.almavivahealth.utils.MimeTypes;

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

	private final RoomRepository roomRepository;

    @Autowired
	public PatientServiceImpl(
			final PatientRepository patientRepository,
			final PatientMapper patientMapper,
			final TextureRepository textureRepository,
			final DietRepository dietRepository,
			final RoomRepository roomRepository) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
		this.textureRepository = textureRepository;
		this.dietRepository = dietRepository;
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
	 * Get all active patients.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientDTO> findAllActivePatients() {
		LOGGER.debug("Request to get all Patients");
		return patientRepository.findAllByStateTrueOrderByIdDesc().stream()
				.map(patientMapper::patientToPatientDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Get all former patients.
	 *
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientDTO> findAllFormerPatients() {
		LOGGER.debug("Request to get all Patients");
		return patientRepository.findAllByStateFalseOrderByIdDesc().stream()
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
			// free the room
			patient.setRoom(null);
			patientRepository.saveAndFlush(patient);
		});
	}

	/**
	 * Import patient file in database.
	 *
	 * @param fileToImport the file to import
	 * @return BulkResult
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public BulkResult importPatientFile(final MultipartFile fileToImport)
			throws DailyFollowUpException {
		LOGGER.debug("Request to import File : {}", fileToImport.getName());
		final List<String> lines = retrieveLines(fileToImport);
		final boolean validLineCount = isValidLineCount(lines);
		if (!validLineCount) {
			throw new DailyFollowUpException("The number of patients to be inserted may not exceed 60.");
		}
		final boolean validCSV = lines.stream().allMatch(this::isValidLine);
		if (!validCSV) {
			throw new DailyFollowUpException(
					"One or more of the lines in the file do not have the correct number of columns");
		}
		final Set<Patient> patients = toPatients(lines);

		return saveOrUpdatePatients(patients);
	}

	private List<String> retrieveLines(final MultipartFile fileToImport) throws DailyFollowUpException {
		try {
			final Charset charset = guessCharset(fileToImport.getInputStream());
			return IOUtils.readLines(fileToImport.getInputStream(), charset);
		} catch (final IOException e) {
			throw new DailyFollowUpException(
					"An error occurred while trying to read the contents of the file : " + fileToImport.getName(), e);
		}
	}

	private Charset guessCharset(final InputStream is) throws IOException {
		return Charset.forName(new TikaEncodingDetector().guessEncoding(is));
	}

	private boolean isValidLine(final String line) {
		return !StringUtils.isBlank(line) && 7 == line.split(SEMICOLON).length;
	}

	private boolean isValidLineCount(final List<String> lines) {
		return lines.size() <= 61;
	}

	private Set<Patient> toPatients(final List<String> lines) {
		return lines.stream()
				.skip(1)
				.map(this::buildPatient)
				.collect(Collectors.toSet());
	}

	private Patient buildPatient(final String line) {
		final String[] columns = line.split(SEMICOLON);

	    final Set<String> dietNames = stringToSet(columns[4]);
	    final Set<String> allergyNames = stringToSet(columns[5]);

		final Texture texture = textureRepository.findByNameIgnoreCase(getField(columns, 3)).orElseGet(() -> null);
	    final List<Diet> diets = dietRepository.findAllByNameIgnoreCaseIn(dietNames);
	    final List<Allergy> allergies = createAllergies(allergyNames);
		final Room room = roomRepository.findByNumberIgnoreCase(getField(columns, 6)).orElseGet(() -> null);

		return Patient.builder()
				.firstName(getField(columns, 0))
				.lastName(getField(columns, 1))
				.sex(getField(columns, 2))
				.state(true)
				.texture(texture)
				.diets(diets)
				.allergies(allergies)
				.room(room)
				.build();
	}

	private String getField(final String[] array, final int index) {
		return array.length > index ? array[index].trim() : null;
	}

	private Set<String> stringToSet(final String value) {
		return Stream.of(value.split(COMMA))
				.map(String::trim)
				.collect(Collectors.toSet());
	}

	private List<Allergy> createAllergies(final Set<String> allergyNames) {
		return allergyNames.stream()
				.map(this::buildAllergy)
				.collect(Collectors.toList());
	}

	private Allergy buildAllergy(final String name) {
		return Allergy.builder()
				.name(name)
				.build();
	}

	private BulkResult saveOrUpdatePatients(final Set<Patient> patients) {
		final Set<Patient> savedPatients = new HashSet<>();
		final Set<Patient> updatedPatients = new HashSet<>();

		// Preparing the patients
		for (final Patient patient : patients) {
			// We are trying to search for old patients in the database and update their info.
			// In the other case, we save him.
			final Optional<Patient> foundPatient = patientRepository.findByFirstNameAndLastName(
					patient.getFirstName(),
					patient.getLastName());

			if (!foundPatient.isPresent()) { // If patient is not found in database, we added to the list to Save.
				savedPatients.add(patient);
				continue;
			}
			final Patient patientToUpdate = patient;
			patientToUpdate.setId(foundPatient.get().getId());
			patientToUpdate.setState(true);
			patientToUpdate.setComment(foundPatient.get().getComment());
			patientToUpdate.setSituation(foundPatient.get().getSituation());
			patientToUpdate.setHeight(foundPatient.get().getHeight());
			patientToUpdate.setBloodGroup(foundPatient.get().getBloodGroup());
			patientToUpdate.setEmail(foundPatient.get().getEmail());
			patientToUpdate.setDateOfBirth(foundPatient.get().getDateOfBirth());
			patientToUpdate.setMobilePhone(foundPatient.get().getMobilePhone());
			patientToUpdate.setPhoneNumber(foundPatient.get().getPhoneNumber());
			patientToUpdate.setJob(foundPatient.get().getJob());
			patientToUpdate.setWeight(foundPatient.get().getWeight());
			patientToUpdate.setJob(foundPatient.get().getJob());
			updatedPatients.add(patientToUpdate);
		}

		return BulkResult.builder()
				.savedPatients(saveAll(savedPatients))
				.updatedPatients(saveAll(updatedPatients))
				.build();

	}

	private List<PatientDTO> saveAll(final Set<Patient> patients) {
		return patientRepository.saveAll(patients).stream()
		        .map(patientMapper::patientToPatientDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Checks if is csv.
	 *
	 * @param fileToImport the file to import
	 * @return true, if is csv
	 */
	@Override
	public boolean isCSV(final MultipartFile fileToImport) {
		final List<String> mimeTypes = Arrays.asList(MIME_APPLICATION_VND_MSEXCEL, MIME_TEXT_PLAIN, MIME_TEXT_CSV);
		return CSV.equalsIgnoreCase(FilenameUtils.getExtension(fileToImport.getOriginalFilename()))
				&& MimeTypes.isMatchingMimeTypes(mimeTypes, fileToImport);
	}

	/**
	 * Reactivate patient.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	@Override
	public Optional<Patient> reactivatePatient(final Long id) {
		LOGGER.debug("Request to reactivate Patient : {}", id);
		return patientRepository.findById(id)
				.map(patient -> {
			// reactivate given patient for the id.
			patient.setState(true);
			patientRepository.saveAndFlush(patient);
			LOGGER.debug("Activated patient : {}", id);
			return patient;
		});
	}

}