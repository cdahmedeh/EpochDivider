package org.cdahmedeh.orgapp.swing.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import org.cdahmedeh.orgapp.swing.helpers.GraphicsHelper;
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
	public static void drawDays(Graphics g, DayLinePanel dayLinePanel, View view) {
		Rectangle clientArea = new Rectangle(0, 0, dayLinePanel.getWidth(), dayLinePanel.getHeight());
		GraphicsHelper.enableAntiAliasing(g);

		g.setColor(new Color(0, 0, 0, 233));
		
        for (int i = 0; i<=view.getNumberOfDaysVisible(); i++){
        	g.drawString(view.getStartDate().plusDays(i).toString("E d/M"), (clientArea.width)*i/view.getNumberOfDaysVisible()+5, 15);
        }
        
		g.setColor(new Color(0, 0, 0, 50));
        
        for (int i = 0; i<=view.getNumberOfDaysVisible(); i++){
            g.drawLine(
            		(clientArea.width)*i/view.getNumberOfDaysVisible(), 
            		0, 
            		(clientArea.width)*i/view.getNumberOfDaysVisible(),
            		clientArea.height);
        }
	}
	
	public static void drawHours(PaintEvent e, Canvas canvas, View view) {
		int hours = 24;
		
		Rectangle clientArea = canvas.getClientArea();
		
        for (double i = 0; i<hours; i=i+0.25){
        	if ((i*4)%4!=0){
        		e.gc.setAlpha(25);
        	} else {
        		e.gc.setAlpha(200);
        		e.gc.drawText((int)i+":00", 5, (int) ((clientArea.height)*i/hours) + 3, true);
        		e.gc.setAlpha(50);
        	}
        	
            e.gc.drawLine(
            		0, 
            		(int) ((clientArea.height)*i/hours), 
            		clientArea.width,
            		(int) ((clientArea.height)*i/hours));
        }
	}

	public static void drawHours(Graphics e, JComponent canvas, View view) {
		int hours = 24;
		GraphicsHelper.enableAntiAliasing(e);

		Rectangle clientArea = new Rectangle(0, 0, canvas.getWidth(), canvas.getHeight());
		
        for (double i = 0; i<hours; i=i+0.25){
        	if ((i*4)%4!=0){
        		e.setColor(new Color(0, 0, 0, 25));
        	} else {
        		e.setColor(new Color(0, 0, 0, 200));
        		e.drawString((int)i+":00", 5, (int) ((clientArea.height)*i/hours) + 3);
        		e.setColor(new Color(0, 0, 0, 50));
        	}
        	
            e.drawLine(
            		0, 
            		(int) ((clientArea.height)*i/hours), 
            		clientArea.width,
            		(int) ((clientArea.height)*i/hours));
        }
	}
	
	public static void drawCurrentTime(JComponent scheduleCanvas, View currentView, Graphics e){
        e.setColor(Color.RED);
        
        if (true){
        	e.drawRect(DateToPixels.getHorizontalPositionFromDate(new LocalDate(), scheduleCanvas.getWidth(), currentView),
        						DateToPixels.getVerticalPositionFromTime(new LocalTime(), scheduleCanvas.getHeight(), currentView),
        						DateToPixels.getWidthBasedOnView(scheduleCanvas.getWidth(), currentView),
        						1);
        }
	}

	public static void drawTimeGrid(Graphics e, CalendarPanel calendarPanel,
			View currentView) {
		Dimension clientArea = calendarPanel.getSize();
		GraphicsHelper.enableAntiAliasing(e);

		int days = currentView.getNumberOfDaysVisible();
		int hours = 24;
		
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
