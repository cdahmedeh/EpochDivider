package org.cdahmedeh.orgapp.swingui.calendar;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

/**
 * Converts pixel positions to equivalent Dates and Times in calendar.
 *  
 * @author Ahmed El-Hajjar
 */
public class PixelsToDate {
	public static LocalDate getDateFromHorizontal(int xPosition, int xArea, View view){
		LocalDate firstDayInView = view.getStartDate();
		int numberOfDaysInView = view.getNumberOfDaysVisible();
		double relativeXPosition = (double)xPosition/xArea;
		
		int numberOfDaysFromFirst = (int)(relativeXPosition*numberOfDaysInView);
		
		return firstDayInView.plusDays(numberOfDaysFromFirst);
	}
	
	public static long getMillisFromVertical(int yPosition, int yArea, View view){
		int numberOfMinutesInView = DateTimeConstants.MILLIS_PER_DAY;
		double relativeYPosition = (double)yPosition/yArea;
		
		long numberOfMinutesFromMidnight = (long)(relativeYPosition*numberOfMinutesInView);
		
		return numberOfMinutesFromMidnight;
	}
	
	public static DateTime getTimeFromPosition(int xPosition, int yPosition, int xArea, int yArea, View view){
		LocalDate date = getDateFromHorizontal(xPosition, xArea, view);
		long millisFromMidnight = getMillisFromVertical(yPosition, yArea, view);
		
		DateTime dateTime = date.toDateTimeAtStartOfDay().plus(millisFromMidnight);
		
		return dateTime;
	}
	
	public static DateTime roundToMins(DateTime date, int minutes){
		DateTime timeFromPosition = date;
		
		int minuteOfHour = timeFromPosition.getMinuteOfHour();
		minuteOfHour = minuteOfHour/minutes * minutes;
		
		timeFromPosition = timeFromPosition.withTime(timeFromPosition.getHourOfDay(), minuteOfHour, 0, 0);
		
		return timeFromPosition;
	}
}
