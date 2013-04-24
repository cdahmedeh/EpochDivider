package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Graphics;

import org.joda.time.LocalTime;

public class GridPainter {
	public static void drawTimeLines(Graphics g, Color darkColor, Color lightColor, int width, int height, int minutesPrecision, boolean drawText) {
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
}
