package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class TimeBlockPainter {
	public static ArrayList<RendereredTask> draw(Graphics g, Task task, TimeBlock timeBlock, View view, JPanel canvas) {
		ArrayList<Rectangle> rectangles = new ArrayList<>();

		LocalDate taskBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime taskBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate taskEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime taskEndTime = timeBlock.getEnd().toLocalTime();
		
		Dimension clientArea = canvas.getSize();
		int caWidth = clientArea.width - 1;
		int caHeight = clientArea.height - 1;
		
		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23,59,59,999);
		
		int daysSpanning = timeBlock.daysSpaning();
		
		if (daysSpanning == 0){
			rectangles.add(new Rectangle(
				DateToPixels.getHorizontalPositionFromDate(taskBeginDate , caWidth , view), 
				DateToPixels.getVerticalPositionFromTime(taskBeginTime , caHeight), 
				DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskBeginDate , caWidth , view), 
				DateToPixels.getHeightFromInterval(taskBeginTime, taskEndTime, caHeight, view)
				));
		} else {
			rectangles.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(taskBeginTime, caHeight), 
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskBeginDate , caWidth , view), 
					DateToPixels.getHeightFromInterval(taskBeginTime, endOfDay, caHeight, view)
					));
			rectangles.add( new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(taskEndDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight), 
					DateToPixels.getHorizontalPositionFromDate(taskEndDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskEndDate, caWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, taskEndTime, caHeight, view)
					));
		}
		
		if (daysSpanning > 1){
			for (int i=1; i<daysSpanning; i++){
			rectangles.add(new Rectangle(
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(i) , caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight), 
					DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(i).plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(taskBeginDate.plusDays(i) , caWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, endOfDay, caHeight, view)
					));
			}
		}

		for (Rectangle rect: rectangles){
//			if (task.isEvent()){
			if (true) {
//				Color color = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, 0.3f, 0.6f));
//				e.setColor(new Color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.5f));
//			} else {
				Color color = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, 0.6f, 0.8f));
				g.setColor(new Color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.5f));
			}
			
			//task before now should be very transparent
//			if (timeBlock.getEnd().isBeforeNow()){
//				Color color = e.getColor();
//				e.setColor(new Color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.1f));
//			}
			
			g.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 2, 2);
			
			g.setColor(new Color(0f, 0f, 0f, 1f));
			
//			if (timeBlock.getEnd().isBeforeNow()){
//				Color color = e.getColor();
//				e.setColor(new Color(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.4f));
//			}
			
			g.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 2, 2);
	
//			e.setFont(new Font("Segoe UI", Font.PLAIN, 11));
			
			if (rect.height > 40){
				g.drawString(task.getTitle().substring(0, Math.min(13, task.getTitle().length())), rect.x+5, rect.y+15);
				g.drawString(taskBeginTime.toString("HH:mm") + "-" + taskEndTime.toString("HH:mm"), rect.x+5, rect.y+30);
			} else {
				g.drawString(task.getTitle(), rect.x+5, rect.y+15);
			}
		}
		
//		e.gc.setAlpha(255);
		
		ArrayList<RendereredTask> rt = new ArrayList<>();
		for (Rectangle r: rectangles){
			RendereredTask rtt = new RendereredTask();
			rtt.setRectangle(r);
			rtt.setTask(task);
			rtt.setTimeBlock(timeBlock);
			rt.add(rtt);
		}
		
		return rt;
	}
}
