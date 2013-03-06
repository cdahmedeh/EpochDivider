package org.cdahmedeh.orgapp.runs;

import org.joda.time.LocalDate;

import com.google.ical.compat.jodatime.LocalDateIteratorFactory;

/**
 * Trying recurrence library.
 */
public class Run003_RecurrenceLibTest {
	public static void main(String[] args) throws Exception {
		LocalDate start = new LocalDate(2013, 01, 01);
		
		String rule = "RRULE:FREQ=MONTHLY"
						+ ";BYDAY=FR"
						+ ";COUNT=10";
		
		for (LocalDate date: LocalDateIteratorFactory.createLocalDateIterable(rule, start, true)){
			System.out.println(date);
		}
	}
}
