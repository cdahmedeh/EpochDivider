package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.EventType;
import org.cdahmedeh.orgapp.view.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class EventRenderer {
	public static ArrayList<Rectangle> draw(Event event, View view, PaintEvent e, Canvas canvas){
		final Display display = Display.getDefault();
		Rectangle clientArea = canvas.getClientArea();
		
		ArrayList<Rectangle> rectangles = new ArrayList<>();
		
		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23,59,59,999);
		
		LocalDate eventBeginDate = event.getBegin().toLocalDate();
		LocalTime eventBeginTime = event.getBegin().toLocalTime();
		LocalDate eventEndDate = event.getEnd().toLocalDate();
		LocalTime eventEndTime = event.getEnd().toLocalTime();
		
		int daysSpanning = event.daysSpaning();
		
		int caWidth = clientArea.width;
		int caHeight = clientArea.height;
		
		if (daysSpanning == 0){
			rectangles.add(new Rectangle(
				DateToPixels.getHorizontalPositionFromDate(eventBeginDate , caWidth , view), 
				DateToPixels.getVerticalPositionFromTime(eventBeginTime , caHeight, view), 
				DateToPixels.getWidthBasedOnView(caWidth, view), 
				DateToPixels.getHeightFromInterval(eventBeginTime, eventEndTime, caHeight, view)
				));
		} else {
			rectangles.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(eventBeginDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(eventBeginTime, caHeight, view), 
					DateToPixels.getWidthBasedOnView(caWidth, view), 
					DateToPixels.getHeightFromInterval(eventBeginTime, endOfDay, caHeight, view)
					));
			rectangles.add( new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(eventEndDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight, view), 
					DateToPixels.getWidthBasedOnView(caWidth, view), 
					DateToPixels.getHeightFromInterval(midnight, eventEndTime, caHeight, view)
					));
		}
		
		if (daysSpanning > 1){
			for (int i=1; i<daysSpanning; i++){
			rectangles.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(eventBeginDate.plusDays(i) , caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight, view), 
					DateToPixels.getWidthBasedOnView(caWidth, view), 
					DateToPixels.getHeightFromInterval(midnight, endOfDay, caHeight, view)
					));
			}
		}
		
		if (event.getCategory() == null){
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
		} else {
			e.gc.setBackground(SWTResourceManager.getColor(event.getCategory().getColor()));
		}
		
		if (event.isSelected()){
			e.gc.setAlpha(255);
		} else {
			e.gc.setAlpha(200);
		}
				
		for (Rectangle rect: rectangles){
			e.gc.fillRoundRectangle(rect.x, rect.y, rect.width, rect.height, 5, 5);
			e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 5, 5);
			e.gc.drawText(event.getTitle(), rect.x+5, rect.y+5, true);
			e.gc.drawText(event.getBegin().toString("HH:mm"), rect.x+5, rect.y+15, true);
			e.gc.drawText(event.getEnd().toString("HH:mm"), rect.x+5, rect.y+25, true);
		}

		e.gc.setAlpha(255);
		return rectangles;
	}
}
