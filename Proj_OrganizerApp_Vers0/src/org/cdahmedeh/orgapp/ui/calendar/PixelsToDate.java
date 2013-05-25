package org.cdahmedeh.orgapp.ui.calendar;

import org.cdahmedeh.orgapp.view.View;
import org.eclipse.swt.graphics.Rectangle;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class PixelsToDate {
	public static LocalDate getDateFromHorizontal(int xPosition, int xArea, View view){
		LocalDate firstDayInView = view.getStart();
		int numberOfDaysInView = view.getNumberOfDaysVisible();
		double relativeXPosition = (double)xPosition/xArea;
		
		int numberOfDaysFromFirst = (int)(relativeXPosition*numberOfDaysInView);
		
		return firstDayInView.plusDays(numberOfDaysFromFirst);
	}
	
	public static LocalTime getTimeFromVertical(int yPosition, int yArea, View view){
		int numberOfMinutesInADay = DateTimeConstants.MINUTES_PER_DAY;
		double relativeYPosition = (double)yPosition/yArea;
		
		int numberOfMinutesFromMidnight = (int)(relativeYPosition*numberOfMinutesInADay);
		
		return new LocalTime(0, 0).plusMinutes(numberOfMinutesFromMidnight);
	}
	
	public static DateTime getTimeFromPosition(int xPosition, int yPosition, int xArea, int yArea, View view){
		LocalDate date = getDateFromHorizontal(xPosition, xArea, view);
		LocalTime time = getTimeFromVertical(yPosition, yArea, view);
		
		DateTime dateTime = date.toDateTime(time);
		
		return dateTime;
	}
	
	public static DateTime getTimeFromPosition(int xPosition, int yPosition, Rectangle rect, View view){
		return getTimeFromPosition(xPosition, yPosition, rect.width, rect.height, view);
	}
	
	public static DateTime roundToMins(DateTime date, int minutes){
		DateTime timeFromPosition = date;
		
		int minuteOfHour = timeFromPosition.getMinuteOfHour();
		minuteOfHour = minuteOfHour/minutes * minutes;
		
		timeFromPosition = timeFromPosition.withTime(timeFromPosition.getHourOfDay(), minuteOfHour, 0, 0);
		
		return timeFromPosition;
	}
}
