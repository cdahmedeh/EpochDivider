package com.tronicdream.epochdivider.swingui.calendar.timeblock;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayDeque;

import javax.swing.JPanel;

import org.joda.time.LocalTime;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.tools.MiscHelper;
import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;
import com.tronicdream.epochdivider.swingui.calendar.CalendarConstants;

public class TimeBlockPainter {
	public static void renderTimeBlock(Graphics g, Task task, TimeBlock timeBlock, BRectangle rect, DataContainer dataContainer, JPanel panel){
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();

		//Set the color
		Color timeBlockColor = new Color(Color.HSBtoRGB(
				task.getContext().getColor()/255f, 
				CalendarConstants.TIMEBLOCK_TASK_SATURATION, 
				CalendarConstants.TIMEBLOCK_TASK_BRIGHTNESS
		));
		
		//Set the opacity
		g.setColor(new Color(
				timeBlockColor.getRed()/255f, 
				timeBlockColor.getGreen()/255f, 
				timeBlockColor.getBlue()/255f, 
				CalendarConstants.TIMEBLOCK_OPACITY
		));
		
		//Fill the block
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		
		//Set the border color and draw the border.
		g.setColor(CalendarConstants.TIMEBLOCK_BORDER_COLOR);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);

		//Draw text
		g.setColor(CalendarConstants.TIMEBLOCK_TEXT_COLOR);
		drawString(g, tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm") + task.getTitle(), rect.x+5, rect.y+15, rect.width - 5, rect.height - 20);
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
