package org.cdahmedeh.orgapp.tools;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Contains a reference to DateTime.now(). Can be changed for testing 
 * purposes.
 * 
 * @author Ahmed El-Hajjar
 */
public class DateReference {
	
	/**
	 * @return A DateTime reference to the current time.
	 */
	public static DateTime getNow() {
		return DateTime.now();
	}
	
	/**
	 * @return A LocalDate reference to the current day.
	 */
	public static LocalDate getToday() {
		return getNow().toLocalDate();
	}
}
