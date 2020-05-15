package fr.almavivahealth.constants;

/**
 * Application constants for managing Errors Message.
 *
 * @author christopher
 *
 */
public final class ErrorMessage {

	// User
	public static final String ERROR_NEW_USER_CANNOT_ALREADY_HAVE_AN_ID = "error.user.canot_new_user_cannot_already_have_an_id";
	public static final String ERROR_USER_MUST_HAVE_AN_ID = "error.user.must_have_an_id";
	public static final String ERROR_OCCURRED_WHILE_TRYING_TO_CREATE_AN_USER = "error.user.occurred_while_trying_to_create_an_user";
	public static final String ERROR_OCCURRED_WHILE_TRYING_TO_UPDATE_AN_USER = "error.user.occurred_while_trying_to_update_an_user";


	// Patient
	public static final String ONE_OR_MORE_PATIENTS_ALREADY_EXIST = "error.patient.patients_already_exist";
	public static final String AN_ERROR_OCCURRED_WHILE_TRYING_TO_READ_THE_CONTENTS_OF_THE_FILE = "error.patient.occurred_while_trying_to_read_the_contents";
	public static final String ONE_OR_MORE_OF_THE_LINES_IN_THE_FILE_DO_NOT_HAVE_THE_CORRECT_NUMBER_OF_COLUMNS = "error.patient.LinesOfFileDoesHaveCorrectColumns";
	public static final String THE_NUMBER_OF_PATIENTS_TO_BE_INSERTED_MAY_NOT_EXCEED_60 = "error.patient.numberExceed60";
	public static final String AN_ERROR_OCCURRED_WHILE_TRYING_TO_IMPORT_THE_PATIENTS = "error.patient.tryingToImportThePatients";
	public static final String THE_FILE_MUST_BE_OF_TYPE_CSV = "error.patient.the_file_must_be_of_type_csv";

	// Content
	public static final String ERROR_CONTENT_DEFINED = "error.content.defined";

	// Private constructor to prevent instantiation
	private ErrorMessage() {
		throw new UnsupportedOperationException();
	}

}