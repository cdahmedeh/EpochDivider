package org.cdahmedeh.orgapp.ui.calendar;

import org.cdahmedeh.orgapp.view.View;
import org.eclipse.swt.graphics.Rectangle;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class DateToPixels {
	public static int getHorizontalPositionFromDate(LocalDate date, int xArea, View view){
		LocalDate firstDayInView = view.getStart();
		LocalDate dateAtMidnight = date.toDateMidnight().toLocalDate();
		int numberOfDaysFromFirst = Days.daysBetween(firstDayInView, dateAtMidnight).getDays();
		int numberOfDaysInView = view.getNumberOfDaysVisible();
		
		double relativePosition = (double)numberOfDaysFromFirst/numberOfDaysInView;
		int actualPosition = (int)(relativePosition*xArea);
				
		return actualPosition;
	}
	
	public static int getVerticalPositionFromTime(LocalTime time, int yArea, View view){
		return (int) ((double)time.getMillisOfDay()/DateTimeConstants.MILLIS_PER_DAY*yArea);
	}
	
	public static int getHeightFromInterval(LocalTime begin, LocalTime end, int yArea, View view){
		return getVerticalPositionFromTime(end, yArea, view) - getVerticalPositionFromTime(begin, yArea, view);
	}

	public static int getWidthBasedOnView(int xArea, View view) {
		return xArea/view.getNumberOfDaysVisible();
	}
}
