package fr.almavivahealth.constants;

/**
 * Application constants for managing constants.
 *
 * @author christopher
 * @version 17
 *
 */
public final class Constants {

    public static final String AUTHORIZATION = "Authorization";
    public static final String HEADER = "header";
    public static final String BEARER = "Bearer ";
    public static final String STRING = "string";

    // Patients
	public static final String CSV = "CSV";
	public static final String SEMICOLON = ";";
	public static final String COMMA = ",";
	public static final String SLASH = "/";
	public static final String API_PATIENTS_IMPORT = "/api/patients/import";

	// Menus
	public static final String CLINIQUE_BERGER = "Clinique Berger";
	public static final String BON_APPETIT = "Bon Appétit !";

	// Contents
	public static final String MENUS_TABLE_CIQUAL2017_EXCEL_FR_2017_11_17_XLS = "menus/TableCiqual2017_ExcelFR_2017_11_17.xls";
	public static final String DASH = "-";

	private Constants() {
		// private constructor needed for Constants class.
	}
}
