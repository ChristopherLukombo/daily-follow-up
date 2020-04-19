package fr.almavivahealth.enums;

/**
 * The Enum Action.
 */
public enum Action {

	/** The inserted. */
	INSERTED("INSERTED"),
	
	/** The updated. */
	UPDATED("UPDATED"),
	
	/** The deleted. */
	DELETED("DELETED");
	
	private final String name;
	
	private Action(final String value) {
		this.name = value;
	}
	
	public String value() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
