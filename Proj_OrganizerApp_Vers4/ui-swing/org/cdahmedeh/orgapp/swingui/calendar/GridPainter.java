package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayDeque;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class GridPainter {
	public static void drawTimeLines(Graphics g, int width, int height, Color darkColor, Color lightColor, int minutesPrecision, boolean drawText) {
		LocalTime time = LocalTime.MIDNIGHT;
		
		do{
			if (time.getMinuteOfHour() == 0) g.setColor(darkColor);
			else g.setColor(lightColor);
			
			int verticalPositionFromTime = DateToPixels.getVerticalPositionFromTime(time, height);
			g.drawLine(0, verticalPositionFromTime, width, verticalPositionFromTime);

			if (drawText) drawTimeLineText(g, width, verticalPositionFromTime, new Color(100,100,100), time.toString("HH:mm"));
			
			time = time.plusMinutes(minutesPrecision);
		} while (!time.equals(LocalTime.MIDNIGHT));
		
		g.setColor(darkColor);
		g.drawLine(0, height-1, width, height-1);
	}
	
	public static void drawTimeLineText(Graphics g, int width, int height, Color colorOfText, String time) {
		g.setColor(colorOfText);
		int stringWidth = g.getFontMetrics().stringWidth(time);
		g.drawString(time, width - stringWidth - 5, height + g.getFontMetrics().getHeight());
	}
	
	public static void drawDateLines(Graphics g, int width, int height, Color color, View view, boolean drawText){
		for (int i = 0; i < view.getNumberOfDaysVisible(); i++){
			g.setColor(color);
			
			int lineXPosition = (int) ((width-1) * i/(double)7);
			g.drawLine(lineXPosition, 0, lineXPosition, height);
			
			if (drawText) {
			ArrayDeque<String> formats = new ArrayDeque<>();
			formats.add("EEE d MMM");
			formats.add("EEE d");
			formats.add("EEE");
			formats.add("d");
			
			g.setColor(new Color(100, 100, 100));
			drawText(g, i, lineXPosition, formats, view.getStartDate(), view.getNumberOfDaysVisible(), width);
			}
		}
		
		g.setColor(color);
		g.drawLine(width-1, 0, width-1, height);
	}
	
	//See if the text fits, if it doesn't, try drawing something smaller.
	private static void drawText(Graphics g, int i, int widthOfTextArea, ArrayDeque<String> formats, LocalDate firstDay, int numberOfDaysVisible, int width) {
		int defaultMargin = 5;
		int defaultTextYPosition = 15;
		
		String stringToPrint = firstDay.plusDays(i).toString(formats.pop());
		int stringToPrintWidth = g.getFontMetrics().stringWidth(stringToPrint);
		if (stringToPrintWidth < - defaultMargin*2 + (width-1)/(double)numberOfDaysVisible) {
			g.drawString(stringToPrint, widthOfTextArea+defaultMargin, defaultTextYPosition);
		} else {
			if (formats.isEmpty()) return;
			drawText(g, i, widthOfTextArea, formats, firstDay, numberOfDaysVisible, width);
		}
	}
}
