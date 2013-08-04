package com.tronicdream.epochdivider.swingui.calendar.timeblock;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.joda.time.LocalTime;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlockOwnerInterface;
import com.tronicdream.epochdivider.swingui.calendar.CalendarConstants;
import com.tronicdream.epochdivider.swingui.helpers.GraphicsHelper;

public class TimeBlockPainter {
	public static void renderTimeBlock(Graphics g, TimeBlockOwnerInterface owner, TimeBlock timeBlock, BRectangle rect, DataContainer dataContainer, JPanel panel){
		LocalTime tBeginTime = timeBlock.getStart().toLocalTime();
		LocalTime tEndTime = timeBlock.getEnd().toLocalTime();

		//Set the color
		Color timeBlockColor = new Color(Color.HSBtoRGB(
				owner.getContext().getColor()/255f, 
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
		g.drawString(tBeginTime.toString("HH:mm") + "-" + tEndTime.toString("HH:mm"), rect.x + 5, rect.y + 15);
		GraphicsHelper.drawCroppedAndWrappedString(g, owner.getTitle(), rect.x+5, rect.y+30, rect.width - 5, rect.height - 35);
	}
}
