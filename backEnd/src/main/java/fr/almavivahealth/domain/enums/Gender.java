package fr.almavivahealth.domain.enums;

/**
 * The Enum Gender.
 *
 * @author christopher
 * @version 17
 */
public enum Gender {

	MAN("Homme"),

	WOMEN("Femme");

	private String label;

	/**
	 * Instantiates a new gender status.
	 *
	 * @param label the label
	 */
	Gender(final String label) {
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
