package com.tronicdream.epochdivider.core.tools;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * Helper class for simplifying DateTime related operations.
 * 
 * @author Ahmed El-Hajjar
 */
public class TimeHelper {
	/**
	 * Checks if the provided date is at midnight.
	 * 	
	 * @param dateTime The date and time to verify
	 * @return True if at midnight, false otherwise.
	 */
	public static boolean isAtMidnight(DateTime dateTime){
		return dateTime.toLocalTime().isEqual(LocalTime.MIDNIGHT);
	}
}
