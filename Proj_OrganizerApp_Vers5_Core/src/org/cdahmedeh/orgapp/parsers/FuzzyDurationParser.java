package org.cdahmedeh.orgapp.parsers;

import java.text.DecimalFormat;

import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

/**
 * Helper class for dealing with fuzzy duration displays and parsing.
 *  
 * Examples of fuzzy durations: 3 hours, 5 minutes, 10 minutes and 3 seconds.
 * 
 * TODO: Still very basic.
 * 
 * @author Ahmed El-Hajjar
 */
public class FuzzyDurationParser {

	/**
	 * Returns a human readable String for a Duration object.
	 *
	 * Just gives the numbers of hours with one decimal number precision.
	 * (example: 3 hours 30 minutes is rendered as 3.5)
	 * 
	 */
	public static String durationToFuzzyString(Duration duration){
		long minutes = duration.getStandardMinutes();
		return new DecimalFormat("#0.0").format((double)minutes/DateTimeConstants.MINUTES_PER_HOUR); 
	}

	/**
	 * Given a human readable duration, converts it to a Duration object. The
	 * excepted input is a number of hours such as '3.5' hours. 
	 * If parsing fails, then Duration.ZERO is returned.
	 */
	public static Duration fuzzyStringToDuration(String fuzzy){
		try {
			double parsedDouble = Double.parseDouble(fuzzy);
			return new Duration((long)(parsedDouble*DateTimeConstants.MILLIS_PER_HOUR));
		} catch (NumberFormatException e){
			//If parsing fails, just fail gracefully move on to returning a 
			//Duration of zero.
		}
				
		return Duration.ZERO;
	}

}
