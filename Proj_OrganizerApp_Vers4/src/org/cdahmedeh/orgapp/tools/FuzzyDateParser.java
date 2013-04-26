package org.cdahmedeh.orgapp.tools;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

import com.joestelmach.natty.CalendarSource;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Helper class for dealing with fuzzy date displays and parsing.
 *  
 * Examples of fuzzy dates: Tomorrow at 7pm, In 3 days, Yesterday, etc.
 * 
 * @author Ahmed El-Hajjar
 */
public class FuzzyDateParser {
	/**
	 * Returns a human readable string for a certain date.
	 */
	public static String dateTimeToFuzzyString(DateTime dateTime){
		DateMidnight todayAtMidnight = DateReference.getNow().toDateMidnight();
		DateMidnight dateTimeAtMidnight = dateTime.toDateMidnight();
		
		//Get number of days between today and 'dateTime'.
		long days = Days.daysBetween(todayAtMidnight, dateTimeAtMidnight).getDays();
		
		//The time of the day is only shown if it is not midnight.
		String time = dateTime.toLocalTime().isEqual(LocalTime.MIDNIGHT) ? "" : dateTime.toString(" @ HH:mm");
		
		if (days < -1) {												//If it is was before yesterday, then show "n days ago".
			return -days + " days ago"  + time;
		} else if (days == -1) {										//Yesterday
			return "Yesterday" + time;
		} else if (days == 0) {											//Today
			return "Today" + time;
		} else if (days == 1) {											//Tomorrow
			return "Tomorrow"  + time;
		} else if (days > 1 && days <= 7) {								//If within 7 days, then show, in n days
			return "In " + days + " days"  + time;
		} else if (dateTime.getYear() == todayAtMidnight.getYear()) {	//If within this year, just show day and month without year 
			return dateTime.toString("d MMM") + time;
		} else {														//Otherwise, show day, month and ago.
			return dateTime.toString("d MMM YYYY") + time;
		}
	}

	/**
	 * Given a human readable date, convert it to a DateTime object. If parsing
	 * fails, then a null value is returned.
	 */
	public static DateTime fuzzyStringToDateTime(String fuzzy){
		//We will be using the natty parser for parsing the date.
		
		//Prepare parser. Set the base date for the parser to today at midnight.
		Date baseDate = DateReference.getNow().toDateMidnight().toDate();
		CalendarSource.setBaseDate(baseDate);
		Parser parser = new Parser();

		//Parse the text, and return the first result that it found. If it exists.
		List<DateGroup> parsed = parser.parse(fuzzy);
		
		if (parsed.size() > 0){
			List<Date> dates = parsed.get(0).getDates();
			if (dates.size() > 0){
				return new DateTime(dates.get(0));
			}
		}
		
		//Methods should check the null value to know that the parser failed.
		return null;
	}
	
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
