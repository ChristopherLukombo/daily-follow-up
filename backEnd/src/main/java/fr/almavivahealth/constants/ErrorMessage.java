package fr.almavivahealth.constants;

/**
 * Application constants for managing Errors Message.
 *
 * @author christopher
 *
 */
public final class ErrorMessage {

	public static final String ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID = "error.new.user.cannot.already.have.an.id";

	// Patient
	public static final String ONE_OR_MORE_PATIENTS_ALREADY_EXIST = "error.patient.patients_already_exist";
	public static final String AN_ERROR_OCCURRED_WHILE_TRYING_TO_READ_THE_CONTENTS_OF_THE_FILE = "error.patient.occurred_while_trying_to_read_the_contents";
	public static final String ONE_OR_MORE_OF_THE_LINES_IN_THE_FILE_DO_NOT_HAVE_THE_CORRECT_NUMBER_OF_COLUMNS = "error.patient.LinesOfFileDoesHaveCorrectColumns";
	public static final String THE_NUMBER_OF_PATIENTS_TO_BE_INSERTED_MAY_NOT_EXCEED_60 = "error.patient.numberExceed60";
	public static final String AN_ERROR_OCCURRED_WHILE_TRYING_TO_IMPORT_THE_PATIENTS = "error.patient.tryingToImportThePatients";
	public static final String THE_FILE_MUST_BE_OF_TYPE_CSV = "error.patient.the_file_must_be_of_type_csv";

	// Private constructor to prevent instantiation
	private ErrorMessage() {
		throw new UnsupportedOperationException();
	}

}
