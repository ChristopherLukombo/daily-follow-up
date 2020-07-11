package fr.almavivahealth.domain.enums;

/**
 * The Enum Action.
 *
 * @author christopher
 * @version 17
 */
public enum Action {

	/** The inserted. */
	INSERTED("INSERTED"),

	/** The updated. */
	UPDATED("UPDATED"),

	/** The deleted. */
	DELETED("DELETED"),

	/** The recreated. */
	RECREATED("RECREATED");

	private final String name;

	private Action(final String name) {
		this.name = name;
	}

	public String value() {
		return this.name;
	}

	@Override
	public String toString() {
		return name;
	}

}
