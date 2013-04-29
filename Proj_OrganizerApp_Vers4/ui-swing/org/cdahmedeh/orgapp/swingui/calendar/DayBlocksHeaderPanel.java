package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.TasksChangedNotification;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class DayBlocksHeaderPanel extends CPanel {
	private static final long serialVersionUID = -1931689507788390417L;
	public DayBlocksHeaderPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}

	@Override protected Object getEventRecorder() {return new Object(){
		@Subscribe public void tasksUpdated(TasksChangedNotification notification){
			repaint();
		}
	};}
		
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(CalendarConstants.DAYBLOCKS_HEADER_DEFAULT_WIDTH, CalendarConstants.DAYBLOCKS_HEADER_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.DAYBLOCKS_HEADER_BACKGROUND_COLOR);
	}

	@Override
	protected void postWindowInit() {}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		//Draw Grid
		GridPainter.drawDateLines(g, 
				this.getWidth(), 
				this.getHeight(), 
				CalendarConstants.DAYBLOCKS_HEADER_GRID_COLOR, 
				dataContainer.getView(), 
				false
		);
		
		//Draw a line on Top and Bottom of Panel
		g.drawLine(0, 0, this.getWidth(), 0);
		g.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1);
		
		//Get all tasks with due dates in this view.
		ArrayList<Task> tasksInView = new ArrayList<>();
		View currentView = dataContainer.getView();
		
		for (Task task: dataContainer.getTasks()) {
			if (task.isDue() && currentView.getInterval().contains(task.getDue())){
				tasksInView.add(task);
			}
		}
		
		ArrayList<RendereredTimeBlock> rtbs = new ArrayList<>();
		
		int caWidth = getWidth() - 1;
		int caHeight = getHeight() - 1;
		
		for (Task task: tasksInView) {
			LocalDate tBeginDate = task.getDue().toLocalDate();
			
			RendereredTimeBlock rtb = new RendereredTimeBlock(
					DateToPixels.getHorizontalPositionFromDate(tBeginDate , caWidth , currentView), 
					0, 
					DateToPixels.getHorizontalPositionFromDate(tBeginDate.plusDays(1), caWidth , currentView) - DateToPixels.getHorizontalPositionFromDate(tBeginDate , caWidth , currentView), 
					caHeight
					);
			rtbs.add(rtb);
			rtb.setTask(task);

		}
		
		for (RendereredTimeBlock rtb: rtbs){
			Color timeBlockColor = new Color(Color.HSBtoRGB(rtb.getTask().getContext().getColor()/255f, CalendarConstants.TIMEBLOCK_SATURATION, CalendarConstants.TIMEBLOCK_BRIGHTNESS));
			g.setColor(new Color(timeBlockColor.getRed()/255f, timeBlockColor.getGreen()/255f, timeBlockColor.getBlue()/255f, CalendarConstants.TIMEBLOCK_OPACITY));
			g.fillRect(rtb.x, rtb.y, rtb.width, rtb.height);
			g.setColor(CalendarConstants.TIMEBLOCK_BORDER_COLOR);
			g.drawRect(rtb.x, rtb.y, rtb.width, rtb.height);
			g.setColor(CalendarConstants.TIMEBLOCK_TEXT_COLOR);
			TimeBlockPainter.drawString(g, rtb.getTask().getTitle(), rtb.x+5, rtb.y+15, rtb.width, rtb.height);
		}
	}
}
