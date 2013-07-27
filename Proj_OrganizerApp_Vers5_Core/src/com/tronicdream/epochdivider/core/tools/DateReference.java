package com.tronicdream.epochdivider.core.tools;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
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
	 * @return A DateMidminght reference to the current day at midnight.
	 */
	public static DateMidnight getTodayAtMidnight(){
		return getNow().toDateMidnight();
	}
	
	/**
	 * @return A LocalDate reference to the current day.
	 */
	public static LocalDate getToday() {
		return getNow().toLocalDate();
	}
	
	/**
	 * @return A LocalDate instance to the beginning of this week
	 */
	public static LocalDate getSundayOfThisWeek(){
		return getToday().withDayOfWeek(DateTimeConstants.SUNDAY);
	}
	
	/**
	 * @return A LocalDate instance to the end of this week
	 */
	public static LocalDate getSaturdayOfThisWeek(){
		return getSundayOfThisWeek().plusDays(DateTimeConstants.DAYS_PER_WEEK-1);
	}
}
