package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;

import org.cdahmedeh.orgapp.tools.DateReference;
import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

/**
 * Used to paint the grids behind the various part of the Calendar UI.
 * 
 * TODO: Code is still a bit messy. Needs some cleanup.
 * 
 * @author Ahmed El-Hajjar
 */
public class GridPainter {
	public static void drawTimeLines(Graphics g, int width, int height, Color darkColor, Color lightColor, int minutesPrecision, boolean drawText) {
		LocalTime time = LocalTime.MIDNIGHT;
		
		do{
			//Use dark color for hour lines, and lighter color for minute lines.
			if (time.getMinuteOfHour() == 0) g.setColor(darkColor);
			else g.setColor(lightColor);
			
			int verticalPositionFromTime = DateToPixels.getVerticalPositionFromTime(time, height-1);
			g.drawLine(0, verticalPositionFromTime, width, verticalPositionFromTime);

			if (drawText) drawTimeLineText(g, width, verticalPositionFromTime, CalendarConstants.TIMELINE_TEXT_COLOR, time.toString(CalendarConstants.TIMELINE_TIME_FORMAT));
			
			time = time.plusMinutes(minutesPrecision);
		} while (!time.equals(LocalTime.MIDNIGHT));
		
		//Draw last line at the bottom.
		g.setColor(darkColor);
		g.drawLine(0, height-1, width, height-1);
	}
	
	public static void drawTimeLineText(Graphics g, int width, int height, Color colorOfText, String time) {
		g.setColor(colorOfText);
		int stringWidthOfTimeText = g.getFontMetrics().stringWidth(time);
		//Align to the right, and move down slightly.
		g.drawString(time, width - stringWidthOfTimeText - CalendarConstants.TIMELINE_TEXT_MARGIN, height + g.getFontMetrics().getHeight());
	}
	
	public static void drawDateLines(Graphics g, int width, int height, Color color, View view, boolean drawText){
		int numberOfDaysVisible = view.getNumberOfDaysVisible();
		for (int i = 0; i < numberOfDaysVisible; i++){
			g.setColor(color);
			
			int lineXPosition = (int) ((width-1) * i/(double)numberOfDaysVisible);
			g.drawLine(lineXPosition, 0, lineXPosition, height);
			
			if (drawText) {
				ArrayDeque<String> formats = MiscHelper.toArrayDeque(CalendarConstants.DAYLINE_DATE_FORMATS);
				g.setColor(CalendarConstants.DAYLINE_TEXT_COLOR);
				drawText(g, i, lineXPosition, formats, view.getStartDate(), numberOfDaysVisible, width);
			}
		}
		
		//Draw last line all the way at the right.
		g.setColor(color);
		g.drawLine(width-1, 0, width-1, height);
	}
	
	//See if the text fits, if it doesn't, try drawing something smaller.
	//TODO: Code is quite cryptic. Needs fixing.
	private static void drawText(Graphics g, int i, int widthOfTextArea, ArrayDeque<String> formats, LocalDate firstDay, int numberOfDaysVisible, int width) {
		int defaultMargin = CalendarConstants.DAYLINE_TEXT_MARGIN;
		int defaultTextYPosition = g.getFontMetrics().getHeight();
		
		String stringToPrint = firstDay.plusDays(i).toString(formats.pop());
		int stringToPrintWidth = g.getFontMetrics().stringWidth(stringToPrint);
		if (stringToPrintWidth < - defaultMargin*2 + (width-1)/(double)numberOfDaysVisible) {
			g.drawString(stringToPrint, widthOfTextArea+defaultMargin, defaultTextYPosition);
		} else {
			if (formats.isEmpty()) return;
			drawText(g, i, widthOfTextArea, formats, firstDay, numberOfDaysVisible, width);
		}
	}
	
	public static void drawCurrentTime(Graphics g, int width, int height, View view){
		LocalDate today = DateReference.getNow().toLocalDate();
		LocalTime now = DateReference.getNow().toLocalTime();
		
        g.setColor(CalendarConstants.CURRENT_TIME_LINE_COLOR);
        
        if (true){
        	g.drawRect(DateToPixels.getHorizontalPositionFromDate(today, width-1, view),
        						DateToPixels.getVerticalPositionFromTime(now, height-1),
        						DateToPixels.getHorizontalPositionFromDate(today.plusDays(1), width-1 , view) - DateToPixels.getHorizontalPositionFromDate(today, width-1 , view),
        						1);
        }
	}
}
