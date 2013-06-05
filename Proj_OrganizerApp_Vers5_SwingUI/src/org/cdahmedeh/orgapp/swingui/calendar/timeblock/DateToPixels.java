package org.cdahmedeh.orgapp.swingui.calendar.timeblock;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Converts Dates and Times to equivalent pixel positions in calendar.
 *  
 * @author Ahmed El-Hajjar
 */
public class DateToPixels {
	public static int getHorizontalPositionFromDate(LocalDate date, int xArea, View view){
		LocalDate firstDayInView = view.getStartDate();
		LocalDate dateAtMidnight = date.toDateMidnight().toLocalDate();
		int numberOfDaysFromFirst = Days.daysBetween(firstDayInView, dateAtMidnight).getDays();
		int numberOfDaysInView = view.getNumberOfDaysVisible();
		
		double relativePosition = (double)numberOfDaysFromFirst/numberOfDaysInView;
		int actualPosition = (int)(relativePosition*xArea);
				
		return actualPosition;
	}
	
	public static int getVerticalPositionFromTime(LocalTime time, int yArea){
		int millisPerDay = DateTimeConstants.MILLIS_PER_DAY; 
		int millisOfDayOfTime = time.getMillisOfDay();
		
		double relativePosition = (double)(millisOfDayOfTime)/millisPerDay;
		int actualPosition = (int)(relativePosition*yArea);
		
		return actualPosition;
	}
	
	public static int getHeightFromInterval(LocalTime begin, LocalTime end, int yArea, View view){
		return getVerticalPositionFromTime(end, yArea) - getVerticalPositionFromTime(begin, yArea);
	}
}
