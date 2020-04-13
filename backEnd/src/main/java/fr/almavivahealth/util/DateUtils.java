package fr.almavivahealth.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

	private DateUtils() {
		
	}
	
	public static LocalDate convertToDate(final String date) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(date, formatter);
	}
}
