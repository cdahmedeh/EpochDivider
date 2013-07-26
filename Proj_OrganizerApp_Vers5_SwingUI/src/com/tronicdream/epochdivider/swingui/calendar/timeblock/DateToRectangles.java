package com.tronicdream.epochdivider.swingui.calendar.timeblock;

import com.tronicdream.epochdivider.core.types.view.View;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class DateToRectangles {
	public static BRectangle rectangleForTimeBlockMiddle(int panelWidth, int panelHeight,
			LocalTime midnight, LocalTime endOfDay, LocalDate tBeginDate, int i, View view) {
		return new BRectangle(
				DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i), panelWidth, view), 
				DateToPixels.getVerticalPositionFromTime(midnight, panelHeight),
				DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i).plusDays(1), panelWidth, view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i), panelWidth,view), 
				DateToPixels.getHeightFromInterval(midnight, endOfDay, panelHeight, view) + 1 // TODO: +1 temp
		);
	}

	public static BRectangle rectangelForTimeBlockLeft(int panelWidth, int panelHeight,
			LocalTime endOfDay, LocalDate tBeginDate, LocalTime tBeginTime, View view) {
		return new BRectangle(
				DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
				DateToPixels.getVerticalPositionFromTime(tBeginTime, panelHeight),
				DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth, view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
				DateToPixels.getHeightFromInterval(tBeginTime, endOfDay, panelHeight, view) + 1 // TODO: +1 temp
		);
	}

	public static BRectangle rectangleForTimeBlockRight(int panelWidth, int panelHeight, 
			LocalDate tBeginDate, LocalTime tBeginTime, LocalTime tEndTime, View view) {
		return new BRectangle(
				DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
				DateToPixels.getVerticalPositionFromTime(tBeginTime, panelHeight),
				DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth, view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
				DateToPixels.getHeightFromInterval(tBeginTime, tEndTime, panelHeight, view));
	}
}
