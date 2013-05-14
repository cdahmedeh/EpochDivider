package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JPanel;

import org.cdahmedeh.orgapp.tools.DateReference;
import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

public class TimeBlockPainter {
	public static ArrayList<RenderedTimeBlock> produceRenderedTimeBlocksForTask(Task task, TimeBlock timeBlock, DataContainer dataContainer, JPanel panel){
		ArrayList<RenderedTimeBlock> rtbs = new ArrayList<>();
		View view = dataContainer.getView();
		
		int panelWidth = panel.getWidth() - 1;
		int panelHeight = panel.getHeight() - 1;
		
		LocalTime midnight = new LocalTime(0, 0);
		LocalTime endOfDay = new LocalTime(23,59,59,999);
		
		LocalDate tBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate tEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();
		
		int daysSpanning = timeBlock.daysSpaning();
		
		//Create the rectangles dimensions that will be rendered for the TimeBlock.
		if (daysSpanning == 0){
			rtbs.add(new RenderedTimeBlock(
				DateToPixels.getHorizontalPositionFromDate(tBeginDate , panelWidth , view), 
				DateToPixels.getVerticalPositionFromTime(tBeginTime , panelHeight), 
				DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate , panelWidth , view), 
				DateToPixels.getHeightFromInterval(tBeginTime, tEndTime, panelHeight, view)
				));
		} else {
			rtbs.add(new RenderedTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate, panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(tBeginTime, panelHeight), 
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate , panelWidth , view), 
					DateToPixels.getHeightFromInterval(tBeginTime, endOfDay, panelHeight, view) + 1 //TODO: +1 temp
					));
			rtbs.add( new RenderedTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tEndDate, panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, panelHeight), 
					DateToPixels.getHorizontalPositionFromDate(tEndDate.plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tEndDate, panelWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, tEndTime, panelHeight, view)
					));
		}
		
		if (daysSpanning > 1){
			for (int i=1; i<daysSpanning; i++){
			rtbs.add(new RenderedTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i) , panelWidth, view), 
					DateToPixels.getVerticalPositionFromTime(midnight, panelHeight), 
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i).plusDays(1), panelWidth , view) - DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(i) , panelWidth , view), 
					DateToPixels.getHeightFromInterval(midnight, endOfDay, panelHeight, view) + 1 //TODO: +1 temp
					));
			}
		}

		//Assign the data (task and timeBlock reference) to the rectangles.
		for (RenderedTimeBlock r: rtbs){
			r.setTask(task);
			r.setTimeBlock(timeBlock);
		}
		
		return rtbs;
	}
	
	public static void renderTimeBlock(Graphics g, RenderedTimeBlock rect, DataContainer dataContainer, JPanel panel){
		TimeBlock timeBlock = rect.getTimeBlock();
		Task task = rect.getTask();
		
		LocalDate tBeginDate = timeBlock.getStart().toLocalDate();
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalDate tEndDate = timeBlock.getEnd().toLocalDate();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();
		
		//Render the rectangles
			//Set the background color
			//Events should be much darker
			if (!task.isEvent()){
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

	public static ArrayList<RenderedTimeBlock> processIntersections(
			ArrayList<RenderedTimeBlock> renderedTimeBlocks) {
		
//		ArrayListMultimap<RenderedTimeBlock, RenderedTimeBlock> mmm = ArrayListMultimap.create();
//		
//		for (RenderedTimeBlock rtb1: renderedTimeBlocks) {
//			mmm.put(rtb1, rtb1);
//			for (RenderedTimeBlock rtb2: renderedTimeBlocks) {
//				if (rtb1.intersects(rtb2.x+1, rtb2.y+1, rtb2.width-2, rtb2.height-2) && !mmm.keySet().contains(rtb2)){
//					mmm.put(rtb1, rtb2);
//				}
//			}	
//		}
//		
//		for (RenderedTimeBlock rk: mmm.keySet()){
//			List<RenderedTimeBlock> set = mmm.get(rk);
//			int i = 0; for (RenderedTimeBlock rv: set){
//				rv.width = rv.width/set.size();
//				rv.x += i*rv.width;
//			i++;}
//		}
		
		return renderedTimeBlocks;
	}
}
