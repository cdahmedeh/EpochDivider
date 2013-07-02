package org.cdahmedeh.orgapp.swingui.calendar.timeblock;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.swingui.calendar.CalendarConstants;
import org.cdahmedeh.orgapp.tools.DateReference;
import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskType;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.LocalTime;

public class TimeBlockPainter {
	public static void renderTimeBlock(Graphics g, Task task, TimeBlock timeBlock, BRectangle rect, DataContainer dataContainer, JPanel panel){
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();
		
		//Render the rectangles
			//Set the background color
			//Events should be much darker
			if (!(task.getType() == TaskType.EVENT)){
				Color timeBlockColor = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, CalendarConstants.TIMEBLOCK_SATURATION, CalendarConstants.TIMEBLOCK_BRIGHTNESS));
				g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_OPACITY));
			} else {
				Color timeBlockColor = new Color(Color.HSBtoRGB(task.getContext().getColor()/255f, CalendarConstants.TIMEBLOCK_EVENT_SATURATION, CalendarConstants.TIMEBLOCK_EVENT_BRIGHTNESS));
				g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_OPACITY));
			}
			
			//Timeblocks that passed should be lighter
			if (dataContainer.getDimPast() && timeBlock.getEnd().isBefore(DateReference.getNow())){
				Color timeBlockColor = g.getColor();
				g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_PASSED_OPACITY));
			}
			
			//Fill the block
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			
			//Set the border color and draw the border.
			g.setColor(CalendarConstants.TIMEBLOCK_BORDER_COLOR);
	/**/	if (dataContainer.getDimPast() && timeBlock.getEnd().isBefore(DateReference.getNow())){
				g.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
	/**/	}
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
	
			//Draw text
			//TODO: Fancy cropping and wrapping routine.
			g.setColor(CalendarConstants.TIMEBLOCK_TEXT_COLOR);
			
	/**/	if (dataContainer.getDimPast() && timeBlock.getEnd().isBefore(DateReference.getNow())){
				g.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
	/**/	}
			
//			g.drawString(task.getTitle().substring(0, Math.min(12, task.getTitle().length())), rect.x+5, rect.y+15);
//			if (rect.height > 30) g.drawString(tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x+5, rect.y+30);
			drawString(g, task.getTitle() + " " + tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x+5, rect.y+15, rect.width - 5, rect.height - 20);
//			if (rect.height < 30) g.drawString(tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x+5, rect.y+30);
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
				
				if (nextWordWidth + spaceWidth >= width && lineLength == 0) {
					g.drawString(drawCroppedString(fontMetrics, nextWord, width), xPrintPos+lineLength, yPrintPos);
					break;
				} else if (lineLength + nextWordWidth + spaceWidth < width) {
					g.drawString(nextWord, xPrintPos+lineLength, yPrintPos);
				} else {
					text.push(nextWord);
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
