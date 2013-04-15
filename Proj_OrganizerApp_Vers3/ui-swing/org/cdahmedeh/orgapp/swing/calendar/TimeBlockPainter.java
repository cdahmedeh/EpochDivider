package org.cdahmedeh.orgapp.swing.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import org.cdahmedeh.orgapp.swing.calendar.CalendarPanel;
import org.cdahmedeh.orgapp.swing.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.eclipse.swt.graphics.Rectangle;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class TimeBlockPainter {
	public static ArrayList<Rectangle> draw(TimeBlock timeBlock, Task task, View view, Graphics e, CalendarPanel canvas) {

		GraphicsHelper.enableAntiAliasing(e);
		
		ArrayList<Rectangle> rectangles = new ArrayList<>();

		LocalDate taskBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime taskBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate taskEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime taskEndTime = timeBlock.getEnd().toLocalTime();
		
		Dimension clientArea = canvas.getSize();
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

//		e.gc.setAlpha(200);
//		e.gc.setAlpha(255);	
		
//		e.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		
		for (Rectangle rect: rectangles){
			if (task.isEvent()){
//				e.setColor(new Color((task.getContext().getColor()), 0.1f, 0.9f));
				Color color = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, 0.1f, 0.9f));
				e.setColor(new Color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.5f));
			} else {
//				e.setColor(new Color((task.getContext().getColor()), 0.5f, 1f));
				Color color = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, 0.5f, 0.9f));
				e.setColor(new Color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.5f));
			}
			
			e.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 2, 2);
			
			e.setColor(new Color(0f, 0f, 0f, 1f));
			
			e.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 2, 2);
	
			e.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			
			if (rect.height > 40){
				e.drawString(task.getTitle(), rect.x+5, rect.y+15);
				e.drawString(taskBeginTime.toString("HH:mm") + "-" + taskEndTime.toString("HH:mm"), rect.x+5, rect.y+30);
			} else {
				e.drawString(task.getTitle(), rect.x+5, rect.y+15);
			}
		}
		
//		e.gc.setAlpha(255);
		
		return rectangles;
	}
}
