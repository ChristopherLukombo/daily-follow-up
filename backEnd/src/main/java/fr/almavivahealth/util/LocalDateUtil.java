package fr.almavivahealth.util;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 *
 * @author christopher
 * @version 16
 */
public final class LocalDateUtil {

	// Private constructor to prevent instantiation
	private LocalDateUtil() {
		throw new UnsupportedOperationException();
	}

	public static LocalDate findDateFromDay(final LocalDate date, final int day) {
		final TemporalField temporalField = WeekFields.of(Locale.FRANCE).dayOfWeek();
		return date.with(temporalField, day);
	}

	public static List<LocalDate> getDateRange(final LocalDate start, final LocalDate end) {
		final List<LocalDate> ret = new ArrayList<>();
		LocalDate tmp = start;
		while (tmp.isBefore(end) || tmp.equals(end)) {
			ret.add(tmp);
			tmp = tmp.plusDays(1);
		}
		return ret;
	}
}
