package fr.almavivahealth.service.impl.patient;

import static fr.almavivahealth.constants.Constants.COMMA;
import static fr.almavivahealth.constants.Constants.CSV;
import static fr.almavivahealth.constants.Constants.SEMICOLON;
import static fr.almavivahealth.constants.ErrorMessage.AN_ERROR_OCCURRED_WHILE_TRYING_TO_READ_THE_CONTENTS_OF_THE_FILE;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_PATIENT_DIET_NOT_EXIST;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_PATIENT_GENDER_NOT_VALID;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_PATIENT_ROOM_NOT_EXIST;
import static fr.almavivahealth.constants.ErrorMessage.ERROR_PATIENT_TEXTURE_NOT_EXIST;
import static fr.almavivahealth.constants.ErrorMessage.ONE_OR_MORE_OF_THE_LINES_IN_THE_FILE_DO_NOT_HAVE_THE_CORRECT_NUMBER_OF_COLUMNS;
import static fr.almavivahealth.constants.ErrorMessage.ONE_OR_MORE_PATIENTS_ALREADY_EXIST;
import static fr.almavivahealth.constants.ErrorMessage.THE_NUMBER_OF_PATIENTS_TO_BE_INSERTED_MAY_NOT_EXCEED_60;
import static fr.almavivahealth.util.MimeTypes.MIME_APPLICATION_VND_MSEXCEL;
import static fr.almavivahealth.util.MimeTypes.MIME_TEXT_CSV;
import static fr.almavivahealth.util.MimeTypes.MIME_TEXT_PLAIN;
import static fr.almavivahealth.util.functional.FunctionWithException.wrapper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.any23.encoding.TikaEncodingDetector;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fr.almavivahealth.dao.DietRepository;
import fr.almavivahealth.dao.OrderRepository;
import fr.almavivahealth.dao.PatientRepository;
import fr.almavivahealth.dao.RoomRepository;
import fr.almavivahealth.dao.TextureRepository;
import fr.almavivahealth.domain.entity.Allergy;
import fr.almavivahealth.domain.entity.Diet;
import fr.almavivahealth.domain.entity.Order;
import fr.almavivahealth.domain.entity.Patient;
import fr.almavivahealth.domain.entity.Room;
import fr.almavivahealth.domain.entity.Texture;
import fr.almavivahealth.domain.enums.Gender;
import fr.almavivahealth.exception.DailyFollowUpException;
import fr.almavivahealth.service.PatientImportationAttempts;
import fr.almavivahealth.service.PatientService;
import fr.almavivahealth.service.dto.BulkResult;
import fr.almavivahealth.service.dto.PatientDTO;
import fr.almavivahealth.service.mapper.PatientMapper;
import fr.almavivahealth.util.MimeTypes;

/**
 * Service Implementation for managing Patient.
 *
 * @author christopher
 * @version 17
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

	private final OrderRepository orderRepository;

	private final PatientImportationAttempts patientImportationAttempts;

	private final MessageSource messageSource;

	@Autowired
	public PatientServiceImpl(
			final PatientRepository patientRepository,
			final PatientMapper patientMapper,
			final TextureRepository textureRepository,
			final DietRepository dietRepository,
			final RoomRepository roomRepository,
			final OrderRepository orderRepository,
			final PatientImportationAttempts patientImportationAttempts,
			final MessageSource messageSource) {
		this.patientRepository = patientRepository;
		this.patientMapper = patientMapper;
		this.textureRepository = textureRepository;
		this.dietRepository = dietRepository;
		this.roomRepository = roomRepository;
		this.orderRepository = orderRepository;
		this.patientImportationAttempts = patientImportationAttempts;
		this.messageSource = messageSource;
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

			// delete all orders
			final List<Order> orders = orderRepository.findAllByPatientId(id);
			orderRepository.deleteAll(orders);

			patientRepository.saveAndFlush(patient);
		});
	}

	/**
	 * Import patient file in database.
	 *
	 * @param fileToImport the file to import
	 * @param request the request
	 * @return the bulk result
	 * @throws DailyFollowUpException the daily follow up exception
	 */
	@Override
	public BulkResult importPatientFile(final MultipartFile fileToImport, final HttpServletRequest request)
			throws DailyFollowUpException {
		LOGGER.debug("Request to import File : {}", fileToImport.getName());
		final Locale locale = request.getLocale();
		final List<String> lines = retrieveLines(fileToImport, locale);
		final boolean validLineCount = isValidLineCount(lines);
		if (!validLineCount) {
			throw new DailyFollowUpException(
					messageSource.getMessage(THE_NUMBER_OF_PATIENTS_TO_BE_INSERTED_MAY_NOT_EXCEED_60, null, locale));
		}
		final boolean validCSV = lines.stream().allMatch(this::isValidLine);
		if (!validCSV) {
			throw new DailyFollowUpException(messageSource.getMessage(
					ONE_OR_MORE_OF_THE_LINES_IN_THE_FILE_DO_NOT_HAVE_THE_CORRECT_NUMBER_OF_COLUMNS, null, locale));
		}
		final Set<Patient> patients = toPatients(lines, locale);

		final String ip = request.getRemoteAddr();
		final String pseudo = request.getRemoteUser();
		return saveOrUpdatePatients(patients, ip, pseudo, locale);
	}

	private List<String> retrieveLines(final MultipartFile fileToImport, final Locale locale)
			throws DailyFollowUpException {
		try {
			final Charset charset = guessCharset(fileToImport.getInputStream());
			return IOUtils.readLines(fileToImport.getInputStream(), charset);
		} catch (final IOException e) {
			throw new DailyFollowUpException(
					messageSource.getMessage(AN_ERROR_OCCURRED_WHILE_TRYING_TO_READ_THE_CONTENTS_OF_THE_FILE, null,
							locale) + fileToImport.getName(),
					e);
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

	private Set<Patient> toPatients(final List<String> lines, final Locale locale) {
		return lines.stream()
				.skip(1)
				.map(wrapper(line -> buildPatient(line, locale)))
				.collect(Collectors.toSet());
	}

	private Patient buildPatient(final String line, final Locale locale) throws DailyFollowUpException {
		final String[] columns = line.split(SEMICOLON);

		final Set<String> dietNames = stringToSet(columns[4]);
		final Set<String> allergyNames = stringToSet(columns[5]);

		final Texture texture = textureRepository.findByNameIgnoreCase(getField(columns, 3)).orElse(null);
		if (null == texture) {
			throw new DailyFollowUpException(
					messageSource.getMessage(ERROR_PATIENT_TEXTURE_NOT_EXIST, null, locale));
		}

		final List<Diet> diets = dietRepository.findAllByNameIgnoreCaseIn(dietNames);
		if (dietNames.isEmpty()) {
			throw new DailyFollowUpException(
					messageSource.getMessage(ERROR_PATIENT_DIET_NOT_EXIST, null, locale));
		}
		final List<Allergy> allergies = createAllergies(allergyNames);

		final Room room = roomRepository.findByNumberIgnoreCase(getField(columns, 6)).orElse(null);
		if (room == null) {
			throw new DailyFollowUpException(
					messageSource.getMessage(ERROR_PATIENT_ROOM_NOT_EXIST, null, locale));
		}

		final String gender = Stream.of(Gender.values())
				.filter(g -> g.label().equalsIgnoreCase(getField(columns, 2)))
				.map(Gender::label)
				.findFirst()
				.orElse(null);

		if (gender == null) {
			throw new DailyFollowUpException(
					messageSource.getMessage(ERROR_PATIENT_GENDER_NOT_VALID, null, locale));
		}

		return Patient.builder()
				.firstName(getField(columns, 0))
				.lastName(getField(columns, 1))
				.sex(gender)
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

	private BulkResult saveOrUpdatePatients(final Set<Patient> patients, final String ip, final String pseudo, final Locale locale) throws DailyFollowUpException {
		final Set<Patient> savedPatients = new HashSet<>();
		final Set<Patient> updatedPatients = new HashSet<>();

		// Patient preparation
		for (final Patient patient : patients) {
			// We are trying to search for old patients in the database and update their info.
			// In the other case, we save him.
			final Patient patientResult = patientRepository
					.findByFirstNameAndLastName(patient.getFirstName(), patient.getLastName())
					.map(foundPatient -> buildPatientToUpdate(patient, foundPatient))
					.orElse(patient);

			if (patientResult.getId() != null && !patientImportationAttempts.hasMadeAnAttempt(ip, pseudo)) {
				// If patient is found in database and user made a try,
				// We warn user that there are patients.
				throw new DailyFollowUpException(
						messageSource.getMessage(ONE_OR_MORE_PATIENTS_ALREADY_EXIST, null, locale));
			} else if (patientResult.getId() != null && patientImportationAttempts.hasMadeAnAttempt(ip, pseudo)) {
				// If the user tries again we add the patient in the list to update
				updatedPatients.add(patientResult);
			} else if (patientResult.getId() == null) {
				// If patient is not found in database, we added to the list to save.
				savedPatients.add(patientResult);
			}
		}

		return BulkResult.builder()
				.savedPatients(saveAll(savedPatients))
				.updatedPatients(saveAll(updatedPatients))
				.build();

	}

	private Patient buildPatientToUpdate(final Patient patient, Patient patientFound) {
		final Patient patientTmp = patientFound;
		patientFound = patient;
		patientFound.setId(patientTmp.getId());
		patientFound.setState(true);
		patientFound.setComment(patientTmp.getComment());
		patientFound.setSituation(patientTmp.getSituation());
		patientFound.setHeight(patientTmp.getHeight());
		patientFound.setBloodGroup(patientTmp.getBloodGroup());
		patientFound.setEmail(patientTmp.getEmail());
		patientFound.setDateOfBirth(patientTmp.getDateOfBirth());
		patientFound.setMobilePhone(patientTmp.getMobilePhone());
		patientFound.setPhoneNumber(patientTmp.getPhoneNumber());
		patientFound.setJob(patientTmp.getJob());
		patientFound.setWeight(patientTmp.getWeight());
		patientFound.setJob(patientTmp.getJob());
		return patientFound;
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

	/**
	 * Find all by floor number.
	 *
	 * @param number the number
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PatientDTO> findAllByFloorNumber(final Integer number) {
		LOGGER.debug("Request to get all Patients for floor: {}", number);
		return patientRepository.findAllByFloorNumber(number).stream()
				.map(patientMapper::patientToPatientDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Change rooms.
	 *
	 * @param firstPatientId  the first patient id
	 * @param secondPatientId the second patient id
	 * @return true, if successful
	 */
	@Override
	public boolean changeRooms(final Long firstPatientId, final Long secondPatientId) {
		LOGGER.debug("Request to change room for patients: {} {}", firstPatientId, secondPatientId);
		final Patient firstPatient = patientRepository.findById(firstPatientId).orElse(null);
		final Room roomFirstPatient = findRoom(firstPatient);

		final Patient secondPatient = patientRepository.findById(secondPatientId).orElse(null);
		final Room roomSecondPatient = findRoom(secondPatient);

		if (null == firstPatient || null == secondPatient) {
			return false;
		}

		// Free rooms
		firstPatient.setRoom(null);
		secondPatient.setRoom(null);

		patientRepository.saveAndFlush(firstPatient);
		patientRepository.saveAndFlush(secondPatient);

		// Change rooms
		firstPatient.setRoom(roomSecondPatient);
		patientRepository.saveAndFlush(firstPatient);

		secondPatient.setRoom(roomFirstPatient);
		patientRepository.saveAndFlush(secondPatient);

		return true;
	}

	private Room findRoom(final Patient patient) {
		return Optional.ofNullable(patient)
				.map(Patient::getRoom)
				.orElse(null);
	}

	/**
	 * Find patient by order id.
	 *
	 * @param orderId the order id
	 * @return the optional
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<PatientDTO> findPatientByOrderId(final Long orderId) {
		return patientRepository.findPatientByOrderId(orderId)
				.map(patientMapper::patientToPatientDTO);
	}
}