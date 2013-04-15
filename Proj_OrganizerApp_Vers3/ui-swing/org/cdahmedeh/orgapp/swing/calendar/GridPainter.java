package org.cdahmedeh.orgapp.swing.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class GridPainter {
	public static void drawTimeGrid(PaintEvent e, Canvas canvas, View view) {
		Rectangle clientArea = canvas.getClientArea();
		
		int days = view.getNumberOfDaysVisible();
		int hours = view.getLastHour().getHourOfDay()-view.getFirstHour().getHourOfDay()+1;
		
		e.gc.setAlpha(50);
		
        for (int i = 0; i<=days; i++){
            e.gc.drawLine(
            		(clientArea.width)*i/days, 
            		0, 
            		(clientArea.width)*i/days,
            		clientArea.height);
        }
        
        for (double i = 0; i<hours; i=i+0.25){
        	if ((i*4)%4!=0){
        		e.gc.setAlpha(10);
        	} else {
        		e.gc.setAlpha(50);
        	}
        	
            e.gc.drawLine(
            		0, 
            		(int) ((clientArea.height)*i/hours), 
            		clientArea.width,
            		(int) ((clientArea.height)*i/hours));
        }
	}
	
	public static void drawDays(PaintEvent e, Canvas canvas, View view) {
		Rectangle clientArea = canvas.getClientArea();
		
		e.gc.setAlpha(233);
		
        for (int i = 0; i<=view.getNumberOfDaysVisible(); i++){
        	e.gc.drawText(view.getStartDate().plusDays(i).toString("E d/M"), (clientArea.width)*i/view.getNumberOfDaysVisible()+5, 5, true);
        }
        
		e.gc.setAlpha(50);
        
        for (int i = 0; i<=view.getNumberOfDaysVisible(); i++){
            e.gc.drawLine(
            		(clientArea.width)*i/view.getNumberOfDaysVisible(), 
            		0, 
            		(clientArea.width)*i/view.getNumberOfDaysVisible(),
            		clientArea.height);
        }
	}
	
	public static void drawHours(PaintEvent e, Canvas canvas, View view) {
		int hours = view.getLastHour().getHourOfDay()-view.getFirstHour().getHourOfDay()+1;
		
		Rectangle clientArea = canvas.getClientArea();
		
        for (double i = 0; i<hours; i=i+0.25){
        	if ((i*4)%4!=0){
        		e.gc.setAlpha(25);
        	} else {
        		e.gc.setAlpha(200);
        		e.gc.drawText((int)i+view.getFirstHour().getHourOfDay()+":00", 5, (int) ((clientArea.height)*i/hours) + 3, true);
        		e.gc.setAlpha(50);
        	}
        	
            e.gc.drawLine(
            		0, 
            		(int) ((clientArea.height)*i/hours), 
            		clientArea.width,
            		(int) ((clientArea.height)*i/hours));
        }
	}

	public static void drawCurrentTime(Display display, Composite scheduleCanvas, View currentView, PaintEvent e){
        e.gc.setAlpha(255);
        e.gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
        
        if (true){
        	e.gc.drawRectangle(DateToPixels.getHorizontalPositionFromDate(new LocalDate(), scheduleCanvas.getClientArea().width, currentView),
        						DateToPixels.getVerticalPositionFromTime(new LocalTime(), scheduleCanvas.getClientArea().height, currentView),
        						DateToPixels.getWidthBasedOnView(scheduleCanvas.getClientArea().width, currentView),
        						1);
        }
	}

	public static void drawTimeGrid(Graphics e, CalendarPanel calendarPanel,
			View currentView) {
		Dimension clientArea = calendarPanel.getSize();
		
		int days = currentView.getNumberOfDaysVisible();
		int hours = currentView.getLastHour().getHourOfDay()-currentView.getFirstHour().getHourOfDay()+1;
		
		e.setColor(new Color(0,0, 0, 255/4));
		
        for (int i = 0; i<=days; i++){
            e.drawLine(
            		(clientArea.width)*i/days, 
            		0, 
            		(clientArea.width)*i/days,
            		clientArea.height);
        }
        
        for (double i = 0; i<hours; i=i+0.25){
        	if ((i*4)%4!=0){
        		e.setColor(new Color(0,0, 0, 255/10));
//        		e.gc.setAlpha(10);
        	} else {
        		e.setColor(new Color(0,0, 0, 255/4));
//        		e.gc.setAlpha(50);
        	}
        	
            e.drawLine(
            		0, 
            		(int) ((clientArea.height)*i/hours), 
            		clientArea.width,
            		(int) ((clientArea.height)*i/hours));
        }
		
	}
}
