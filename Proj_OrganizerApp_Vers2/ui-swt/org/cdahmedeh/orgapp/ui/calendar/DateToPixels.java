package org.cdahmedeh.orgapp.ui.calendar;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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
	
	public static int getVerticalPositionFromTime(LocalTime time, int yArea, View view){
		int millisInView = view.getLastHour().getMillisOfDay()-view.getFirstHour().getMillisOfDay(); 
		
		return (int) ((double)(time.getMillisOfDay() - view.getFirstHour().getMillisOfDay())/millisInView*yArea);
	}
	
	public static int getHeightFromInterval(LocalTime begin, LocalTime end, int yArea, View view){
		return getVerticalPositionFromTime(end, yArea, view) - getVerticalPositionFromTime(begin, yArea, view);
	}

	public static int getWidthBasedOnView(int xArea, View view) {
		return xArea/view.getNumberOfDaysVisible();
	}
}
