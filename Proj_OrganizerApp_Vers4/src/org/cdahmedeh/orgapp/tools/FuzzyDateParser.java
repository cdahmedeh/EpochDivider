package org.cdahmedeh.orgapp.tools;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

import com.joestelmach.natty.CalendarSource;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * Helper class for dealing with fuzzy date displays and parsing. 
 * 
 * @author Ahmed El-Hajjar
 */
public class FuzzyDateParser {
	/**
	 * Returns a human readable string for a certain date.
	 */
	public static String dateTimeToFuzzyString(DateTime dateTime){
		DateTime today = DateTime.now();

		//Get number of days between today and 'dateTime'.
		Duration diff = new Duration(today.toDateMidnight(), dateTime.toDateMidnight());
		long days = diff.getStandardDays();
		
		//The time of the day is only shown if it is not midnight.
		String time = dateTime.toLocalTime().isEqual(LocalTime.MIDNIGHT) ? "" : dateTime.toString(" @ HH:mm");
		
		if (days < -1) return -days + " days ago"  + time;
		else if (days == -1) return "Yesterday" + time;
		else if (days == 0) return "Today" + time;
		else if (days == 1) return "Tomorrow"  + time;
		else if (days > 1 && days <= 7) return "In " + days + " days"  + time;
		else if (dateTime.getYear() == today.getYear()) return dateTime.toString("d MMM") + time;
		else return dateTime.toString("d MMM YYYY") + time;
	}

	/**
	 * Given a human readable date, converts it to a DateTime object. If parsing
	 * fails, then a null value is returned.
	 */
	public static DateTime fuzzyStringToDateTime(String fuzzy){
		Parser parser = new Parser();
		Date baseDate = DateTime.now().toDateMidnight().toDate();
		CalendarSource.setBaseDate(baseDate);
		List<DateGroup> parsed = parser.parse(fuzzy);
		
		if (parsed.size() > 0){
			List<Date> dates = parsed.get(0).getDates();
			if (dates.size() > 0){
				return new DateTime(dates.get(0));
			}
		}
		
		return null;
	}
	
	/**
	 * Returns a human readable string for a certain duration.
	 */
	public static String durationToFuzzyString(Duration duration){
		long minutes = duration.getStandardMinutes();
		return new DecimalFormat("#0.0").format((double)minutes/DateTimeConstants.MINUTES_PER_HOUR); 
	}
	
	public static Duration fuzzyStringToDuration(String fuzzy){
		try {
			double parseDouble = Double.parseDouble(fuzzy);
			return new Duration((long)(parseDouble*DateTimeConstants.MILLIS_PER_HOUR));
		} catch (NumberFormatException e){
			
		}
				
		return Duration.ZERO;
	}
}
