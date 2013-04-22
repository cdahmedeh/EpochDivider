package org.cdahmedeh.orgapp.tools;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class FuzzyDateParser {
	public static String dateTimeToFuzzyString(DateTime localDate){
		DateTime today = DateTime.now();

		Duration diff = new Duration(today.toDateMidnight(), localDate.toDateMidnight());
		long days = diff.getStandardDays();
		
		String time = localDate.toLocalTime().isEqual(LocalTime.MIDNIGHT) ? "" : localDate.toString(" @ HH:mm");
		if (days == 0) return "Today" + time;
		else if (days == 1) return "Tomorrow"  + time;
		else if (days > 1 && days <= 7) return "In " + days + " days"  + time;
		else if (days > 7) return localDate.toString("d MMM") + time;
		
		return "unknown";
	}
	
	public static DateTime fuzzyStringToDateTime(String fuzzy){
		Parser parser = new Parser();
		List<DateGroup> parsed = parser.parse(fuzzy);
		
		if (parsed.size() > 0){
			List<Date> dates = parsed.get(0).getDates();
			if (dates.size() > 0){
				return new DateTime(dates.get(0));
			}
		}
		
		return null;
	}
}
