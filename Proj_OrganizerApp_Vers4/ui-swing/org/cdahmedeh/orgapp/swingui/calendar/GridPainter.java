package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Graphics;

public class GridPainter {
	public static void drawTimeLines(Graphics g, Color darkColor, Color lightColor, int width, int height) {
		for (int i = 0; i<24*4; i++){
			int y1 = (int) (i/(double)(24*4)*(height-1));
			if (i%4 == 0){
				g.setColor(darkColor);	
			} else {
				g.setColor(lightColor);
			}
			g.drawLine(0, y1 , width, y1);
		}
		g.drawLine(0, height-1, width, height-1);
	}
	
	public static void drawTimeLineText(Graphics g, int width, int height, Color colorOfText) {
		g.setColor(colorOfText);
		for (int i = 0; i<24; i++){
			int y1 = (int) (i/(double)(24)*(height-1));
			int stringWidth = g.getFontMetrics().stringWidth(i+"00");
			g.drawString(i + ":00", width - stringWidth - 5, y1+15);
		}
	}
}
