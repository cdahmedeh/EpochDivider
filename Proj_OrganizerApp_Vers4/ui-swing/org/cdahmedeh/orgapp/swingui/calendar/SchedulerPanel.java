package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.google.common.eventbus.EventBus;

public class SchedulerPanel extends CPanel {
	private static final long serialVersionUID = 3673536421097243610L;
	public SchedulerPanel(final DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	@Override protected Object getEventRecorder() {return new Object(){};}

	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(50, 1000));
		setBackground(new Color(255, 255, 255));
		
		enabledClickingOnTimeBlock();
	}

	@Override
	protected void postWindowInit() {
		drawTasks = true;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);
		
		//Draw grid in the background
		GridPainter.drawTimeLines(g, this.getWidth(), this.getHeight(), CalendarConstants.CALENDAR_COLOR_DARK, CalendarConstants.CALENDAR_COLOR_LIGHT, CalendarConstants.CALENDAR_MINUTES_RESOLUTION, false);
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), CalendarConstants.CALENDAR_COLOR_DARK, dataContainer.getView(), false);
		
		//Draw the time-blocks for all tasks.
		//TODO: Optimization, draw only those within view.
		if (drawTasks){
			rendersTask.clear();
			for (Task task: dataContainer.getTasks()){
				for (TimeBlock timeBlock: task.getAllTimeBlocks())
					rendersTask.addAll(TimeBlockPainter.draw(g, task, timeBlock, dataContainer.getView(), this));
			}
		}
	}
	
	// -- Data --
	private boolean drawTasks = false;
	private CalendarUIMode uiMode = CalendarUIMode.NONE;
	private ArrayList<RendereredTask> rendersTask = new ArrayList<>();
	
	private Duration timeClickedOffset = null; 
	private TimeBlock timeBlockSelected = null;
	
	private void enabledClickingOnTimeBlock() {
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//First check that nothing else is going on.
				if (uiMode == CalendarUIMode.NONE){
					//Are we clicking on a task time-block, if so, then start dragging.
					RendereredTask clickedTimeBlock = getClickedTimeBlock(e.getX(), e.getY());
					if (clickedTimeBlock != null) {
						timeBlockSelected = clickedTimeBlock.getTimeBlock();
						if (isNearTop(e, clickedTimeBlock)){
							uiMode = CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK;
						} else if (isNearBottom(e, clickedTimeBlock)){
							uiMode = CalendarUIMode.RESIZE_TOP_TIMEBLOCK;
						} else {
							uiMode = CalendarUIMode.MOVE_TIMEBLOCK;
							timeClickedOffset = new Duration(timeBlockSelected.getStart(), PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, dataContainer.getView()));							
						}
					}
				} 
				//Moving a timeblock
				else if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK) {
					DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, dataContainer.getView());
					timeBlockSelected.moveStart(PixelsToDate.roundToMins(timeFromMouse.minus(timeClickedOffset), 15));
					repaint();
				}
				//Resize the bottom of the timeblock
				else if (uiMode == CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK) {
					DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, dataContainer.getView());
					timeBlockSelected.setEnd(PixelsToDate.roundToMins(timeFromMouse, 15));
					repaint();
				}
				//Resize the bottom of the timeblock
				else if (uiMode == CalendarUIMode.RESIZE_TOP_TIMEBLOCK) {
					DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, dataContainer.getView());
					timeBlockSelected.setStart(PixelsToDate.roundToMins(timeFromMouse, 15));
					repaint();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				RendereredTask clickedTimeBlock = getClickedTimeBlock(e.getX(), e.getY());
				if (getClickedTimeBlock(e.getX(), e.getY()) != null) {
					if (isNearTop(e, clickedTimeBlock)){
						setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					} else if (isNearBottom(e, clickedTimeBlock)){
						setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
					} else {
						setCursor(new Cursor(Cursor.MOVE_CURSOR));
					}
				} else {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("end");
				//If we we're dragging, and we release, then, stop dragging.
				if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK || uiMode == CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK || uiMode == CalendarUIMode.RESIZE_TOP_TIMEBLOCK){
					uiMode = CalendarUIMode.NONE;
					timeClickedOffset = null;
					timeBlockSelected = null;
				}
			}
		});
	}
	
	// --- Helpers ---
	private RendereredTask getClickedTimeBlock(int x, int y){
		for (RendereredTask rt: rendersTask){
			if (rt.clickedWithin(x, y)) {
				return rt;
			}
		}
		return null;
	}
	
	private boolean isNearBottom(MouseEvent e, RendereredTask clickedTimeBlock) {
		return e.getY()-clickedTimeBlock.getRectangle().y < 5;
	}

	private boolean isNearTop(MouseEvent e, RendereredTask clickedTimeBlock) {
		return e.getY()-clickedTimeBlock.getRectangle().y > clickedTimeBlock.getRectangle().height-10;
	}
}
