package fr.almavivahealth.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

	private DateUtil() {
		
	}
	
	public static LocalDate convertToDate(final String date) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(date, formatter);
	}
}
