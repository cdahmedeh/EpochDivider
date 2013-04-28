package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.tools.DateReference;
import org.cdahmedeh.orgapp.tools.MiscHelper;
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
			//Events should be much darker
			if (!task.isEvent()){
				Color timeBlockColor = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, CalendarConstants.TIMEBLOCK_SATURATION, CalendarConstants.TIMEBLOCK_BRIGHTNESS));
				g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_OPACITY));
			} else {
				Color timeBlockColor = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, CalendarConstants.TIMEBLOCK_EVENT_SATURATION, CalendarConstants.TIMEBLOCK_EVENT_BRIGHTNESS));
				g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_OPACITY));
			}
			
//			//Timeblocks that passed should be lighter
//			if (timeBlock.getEnd().isBefore(DateReference.getNow())){
//				Color timeBlockColor = g.getColor();
//				g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_PASSED_OPACITY));
//			}
			
			//Fill the block
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			
			//Set the border color and draw the border.
			g.setColor(CalendarConstants.TIMEBLOCK_BORDER_COLOR);
//	/**/	if (timeBlock.getEnd().isBefore(DateReference.getNow())){
//				g.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
//	/**/	}
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
	
			//Draw text
			//TODO: Fancy cropping and wrapping routine.
			g.setColor(CalendarConstants.TIMEBLOCK_TEXT_COLOR);
			
//	/**/	if (timeBlock.getEnd().isBefore(DateReference.getNow())){
//				g.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
//	/**/	}
			
//			g.drawString(task.getTitle().substring(0, Math.min(12, task.getTitle().length())), rect.x+5, rect.y+15);
//			if (rect.height > 30) g.drawString(tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x+5, rect.y+30);
			drawString(g, task.getTitle() + " " + tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x+5, rect.y+15, rect.width - 5, rect.height - 20);
//			if (rect.height < 30) g.drawString(tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x+5, rect.y+30);
		}
		
		//Assign the data (task and timeBlock reference) to the rectangles.
		for (RendereredTimeBlock r: rtbs){
			r.setTask(task);
			r.setTimeBlock(timeBlock);
		}
		
		return rtbs;
	}
	
	//From: http://stackoverflow.com/questions/400566/full-justification-with-a-java-graphics-drawstring-replacement
	//TODO: Improve, slow, not loop proof.
	//TODO: What if a word is just too long?
	public static void drawString(Graphics g, String s, int x, int y, int width, int height)
	{
		String[] split = s.split(" ");
		ArrayDeque<String> text = MiscHelper.toArrayDeque(split);
		FontMetrics fontMetrics = g.getFontMetrics();

		int yPrintPos = y;
		int spaceWidth = fontMetrics.stringWidth(" ");
		
		while (!text.isEmpty()){
			int xPrintPos = x;
			int lineLength = 0;
			
			while (!text.isEmpty()) {
				String nextWord = text.pop();
				
				int nextWordWidth = fontMetrics.stringWidth(nextWord);
				
				System.out.println(nextWordWidth);
				System.out.println(width);
				
				if (nextWordWidth + spaceWidth >= width && lineLength == 0) {
					System.out.println("Word too long: " + nextWord);
					g.drawString(drawCroppedString(fontMetrics, nextWord, width), xPrintPos+lineLength, yPrintPos);
					break;
				} else if (lineLength + nextWordWidth + spaceWidth < width) {
					g.drawString(nextWord, xPrintPos+lineLength, yPrintPos);
				} else {
					text.push(nextWord);
					System.out.println("Word next line: " + nextWord);
					break;
				}
				
				lineLength += nextWordWidth + spaceWidth;
			}
			
			yPrintPos += fontMetrics.getHeight();
			
			if (yPrintPos-y > height) break;
		}
	}

	private static String drawCroppedString(FontMetrics metrics, String nextWord, int width) {
		if (nextWord.isEmpty() || metrics.stringWidth(nextWord) < width){
			return nextWord;
		} else {
			return drawCroppedString(metrics, nextWord.substring(0, nextWord.length()-1), width);
		}
	}
}
