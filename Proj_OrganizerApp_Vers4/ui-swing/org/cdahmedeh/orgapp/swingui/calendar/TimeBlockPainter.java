package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class TimeBlockPainter {
	public static ArrayList<RendereredTimeBlock> draw(Graphics g, Task task, TimeBlock timeBlock, View view, JPanel panel) {
		ArrayList<RendereredTimeBlock> rtbs = new ArrayList<>();

		LocalDate tBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate tEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();
		
		int caWidth = panel.getWidth() - 1;
		int caHeight = panel.getHeight() - 1;
		
		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23,59,59,999);
		
		int daysSpanning = timeBlock.daysSpaning();
		
		//Create the rectangles dimensions that will be rendered for the TimeBlock.
		if (daysSpanning == 0){
			rtbs.add(new RendereredTimeBlock(
				DateToPixels.getHorizontalPositionFromDate(tBeginDate , caWidth , view), 
				DateToPixels.getVerticalPositionFromTime(tBeginTime , caHeight), 
				DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate , caWidth , view), 
				DateToPixels.getHeightFromInterval(tBeginTime, tEndTime, caHeight, view)
				));
		} else {
			rtbs.add(new RendereredTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(tBeginTime, caHeight), 
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate , caWidth , view), 
					DateToPixels.getHeightFromInterval(tBeginTime, endOfDay, caHeight, view) + 1 //TODO: +1 temp
					));
			rtbs.add( new RendereredTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tEndDate, caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight), 
					DateToPixels.getHorizontalPositionFromDate(tEndDate.plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(tEndDate, caWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, tEndTime, caHeight, view)
					));
		}
		
		if (daysSpanning > 1){
			for (int i=1; i<daysSpanning; i++){
			rtbs.add(new RendereredTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i) , caWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, caHeight), 
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i).plusDays(1), caWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i) , caWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, endOfDay, caHeight, view) + 1 //TODO: +1 temp
					));
			}
		}

		//Render the rectangles
		for (RendereredTimeBlock rect: rtbs){
			//Set the background color
			Color timeBlockColor = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, CalendarConstants.TIMEBLOCK_SATURATION, CalendarConstants.TIMEBLOCK_BRIGHTNESS));
			g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_OPACITY));
			
			//Fill the block
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			
			//Set the border color and draw the border.
			g.setColor(CalendarConstants.TIMEBLOCK_BORDER_COLOR);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
	
			//Draw text
			//TODO: Fancy cropping and wrapping routine.
			g.setColor(CalendarConstants.TIMEBLOCK_TEXT_COLOR);
			g.drawString(task.getTitle().substring(0, Math.min(13, task.getTitle().length())), rect.x+5, rect.y+15);
			g.drawString(tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x+5, rect.y+30);
		}
		
		//Assign the data (task and timeBlock reference) to the rectangles.
		for (RendereredTimeBlock r: rtbs){
			r.setTask(task);
			r.setTimeBlock(timeBlock);
		}
		
		return rtbs;
	}
}
