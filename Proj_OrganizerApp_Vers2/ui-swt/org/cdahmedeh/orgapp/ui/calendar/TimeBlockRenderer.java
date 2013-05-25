package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class TimeBlockRenderer {
	public static ArrayList<Rectangle> draw(TimeBlock timeBlock, Task task, View view, PaintEvent e, Canvas canvas){
		
		ArrayList<Rectangle> rectangles = new ArrayList<>();

		LocalDate taskBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime taskBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate taskEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime taskEndTime = timeBlock.getEnd().toLocalTime();
		
//		if (taskBeginTime.isAfter(view.getLastHour()) || taskEndTime.isBefore(view.getFirstHour())){
//			return rectangles;
//		}
		
		Rectangle clientArea = canvas.getClientArea();
		int caWidth = clientArea.width;
		int caHeight = clientArea.height;
		
		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23,59,59,999);
		
		int daysSpanning = timeBlock.daysSpaning();
		
		if (daysSpanning == 0){
			rectangles.add(new Rectangle(
				DateToPixels.getHorizontalPositionFromDate(taskBeginDate , caWidth , view), 
				DateToPixels.getVerticalPositionFromTime(taskBeginTime , caHeight, view), 
//				DateToPixels.getWidthBasedOnView(caWidth, view),
				DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskBeginDate , caWidth , view), 
				DateToPixels.getHeightFromInterval(taskBeginTime, taskEndTime, caHeight, view)
				));
		} else {
			rectangles.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(taskBeginTime, caHeight, view), 
//					DateToPixels.getWidthBasedOnView(caWidth, view), 
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskBeginDate , caWidth , view), 
					DateToPixels.getHeightFromInterval(taskBeginTime, endOfDay, caHeight, view)
					));
			rectangles.add( new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(taskEndDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight, view), 
//					DateToPixels.getWidthBasedOnView(caWidth, view),
					DateToPixels.getHorizontalPositionFromDate(taskEndDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskEndDate, caWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, taskEndTime, caHeight, view)
					));
		}
		
		if (daysSpanning > 1){
			for (int i=1; i<daysSpanning; i++){
			rectangles.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(i) , caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight, view), 
//					DateToPixels.getWidthBasedOnView(caWidth, view), 
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(i).plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(i) , caWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, endOfDay, caHeight, view)
					));
			}
		}
	
		if (task.isEvent()){
			e.gc.setBackground(SWTResourceManager.getColor(new RGB((task.getContext().getColor()), 0.1f, 0.9f)));
		} else {
			e.gc.setBackground(SWTResourceManager.getColor(new RGB((task.getContext().getColor()), 0.5f, 1f)));
			
		}
		
//		if (task.getMutability() == Mutability.IMMUTABLE){
//		} else {
//			e.gc.setAlpha(150);
//		}
		
		e.gc.setAlpha(200);
//		e.gc.setAlpha(255);	
		
		e.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		
		for (Rectangle rect: rectangles){
			e.gc.fillRoundRectangle(rect.x, rect.y, rect.width, rect.height, 2, 2);
			e.gc.drawRoundRectangle(rect.x, rect.y, rect.width, rect.height, 2, 2);

			if (rect.height > 40){
				e.gc.drawText(task.getTitle() + "\n" + taskBeginTime.toString("HH:mm") + "-" + taskEndTime.toString("HH:mm"), rect.x+5, rect.y+5, true);
			} else {
				e.gc.drawText(task.getTitle(), rect.x+5, rect.y+3, true);
			}
		}
		
		e.gc.setAlpha(255);
		
		return rectangles;
	}
}
