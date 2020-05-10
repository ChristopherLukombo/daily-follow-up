package fr.almavivahealth.domain.enums;

/**
 * The Enum MaritalStatus.
 */
public enum MaritalStatus {
	
	/** The married. */
	MARRIED("Marié"),
	
	/** The pacs. */
	PACS("Pascé"),
	
	/** The divorced. */
	DIVORCED("Divorcé"),
	
	/** The separated. */
	SEPARATED("Séparé"),
	
	/** The single. */
	SINGLE("Célibataire"),
	
	/** The widower. */
	WIDOWER("Veuf");
	
	private String label;

	/**
	 * Instantiates a new marital status.
	 *
	 * @param label the label
	 */
	MaritalStatus(final String label) {
		this.label = label;
	}

	/**
	 * Label.
	 *
	 * @return the string
	 */
	public String label() {
		return label;
	}

}
