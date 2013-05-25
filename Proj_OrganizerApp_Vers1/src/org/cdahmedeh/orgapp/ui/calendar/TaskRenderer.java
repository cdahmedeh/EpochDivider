package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.calendar.View;
import org.cdahmedeh.orgapp.task.RecurringTask;
import org.cdahmedeh.orgapp.task.Task;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class TaskRenderer {
	public static ArrayList<Rectangle> draw(Task task, View view, PaintEvent e, Canvas canvas){
		
		ArrayList<Rectangle> rectangles = new ArrayList<>();

		if (task.getScheduled() != null && task.getDuration() != null){
			LocalDate taskBeginDate = task.getScheduled().toLocalDate();
			LocalTime taskBeginTime = task.getScheduled().toLocalTime();
			LocalDate taskEndDate = task.getScheduled().plus(task.getDuration()).toLocalDate();
			LocalTime taskEndTime = task.getScheduled().plus(task.getDuration()).toLocalTime();
			
			Rectangle clientArea = canvas.getClientArea();
			int caWidth = clientArea.width;
			int caHeight = clientArea.height;
			
			LocalTime midnight = new LocalTime(0, 0);
			LocalTime endOfDay = new LocalTime(23,59,59,999);
			
			int daysSpanning = task.daysSpaning();
			
			if (daysSpanning == 0){
				rectangles.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate , caWidth , view), 
					DateToPixels.getVerticalPositionFromTime(taskBeginTime , caHeight, view), 
					DateToPixels.getWidthBasedOnView(caWidth, view), 
					DateToPixels.getHeightFromInterval(taskBeginTime, taskEndTime, caHeight, view)
					));
			} else {
				rectangles.add(new Rectangle(
						DateToPixels.getHorizontalPositionFromDate(taskBeginDate, caWidth, view), 
						DateToPixels.getVerticalPositionFromTime(taskBeginTime, caHeight, view), 
						DateToPixels.getWidthBasedOnView(caWidth, view), 
						DateToPixels.getHeightFromInterval(taskBeginTime, endOfDay, caHeight, view)
						));
				rectangles.add( new Rectangle(
						DateToPixels.getHorizontalPositionFromDate(taskEndDate, caWidth, view), 
						DateToPixels.getVerticalPositionFromTime(midnight, caHeight, view), 
						DateToPixels.getWidthBasedOnView(caWidth, view), 
						DateToPixels.getHeightFromInterval(midnight, taskEndTime, caHeight, view)
						));
			}
			
			if (daysSpanning > 1){
				for (int i=1; i<daysSpanning; i++){
				rectangles.add(new Rectangle(
						DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(i) , caWidth, view), 
						DateToPixels.getVerticalPositionFromTime(midnight, caHeight, view), 
						DateToPixels.getWidthBasedOnView(caWidth, view), 
						DateToPixels.getHeightFromInterval(midnight, endOfDay, caHeight, view)
						));
				}
			}
		
			e.gc.setBackground(SWTResourceManager.getColor(task.getColor()));
			
			e.gc.setAlpha(200);
			
			e.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
			
			for (Rectangle rect: rectangles){
				e.gc.fillRoundRectangle(rect.x, rect.y, rect.width, rect.height, 3, 3);
				e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 3, 3);
	
				if (rect.height > 40){
					e.gc.drawText(task.getName() + "\n" + taskBeginTime.toString("HH:mm") + "\n" + taskEndTime.toString("HH:mm"), rect.x+5, rect.y+5, true);
				} else {
					e.gc.drawText(task.getName(), rect.x+5, rect.y+3, true);
				}
			}
	
			e.gc.setAlpha(255);
		}
		
		return rectangles;
	}
}
