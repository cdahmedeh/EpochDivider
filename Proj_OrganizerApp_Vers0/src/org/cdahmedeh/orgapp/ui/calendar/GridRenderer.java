package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.view.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;

public class GridRenderer {
	public static void drawGrid(PaintEvent e, Canvas canvas, View view) {
		Rectangle clientArea = canvas.getClientArea();
		
		int days = view.getNumberOfDaysVisible();
		int hours = 24;
		
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
        		e.gc.setAlpha(25);
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
	
	public static void drawDays(PaintEvent e, Canvas canvas, View view, int hours) {
		Rectangle clientArea = canvas.getClientArea();
		
		e.gc.setAlpha(200);
		
        for (int i = 0; i<=view.getNumberOfDaysVisible(); i++){
        	e.gc.drawText(view.getStart().plusDays(i).toString("E d/M"), (clientArea.width)*i/view.getNumberOfDaysVisible()+5, 5, true);
        }
	}
	
	public static void drawHours(PaintEvent e, Canvas canvas, View view, int hours) {
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
}
