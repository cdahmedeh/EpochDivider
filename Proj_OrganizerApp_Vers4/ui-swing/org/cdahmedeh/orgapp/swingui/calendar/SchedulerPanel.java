package org.cdahmedeh.orgapp.swingui.calendar;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.TransferHandler;

import org.cdahmedeh.orgapp.swingui.helpers.GraphicsHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.ContextsChangedNotification;
import org.cdahmedeh.orgapp.swingui.notification.TasksChangedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class SchedulerPanel extends CPanel {
	private static final long serialVersionUID = 3673536421097243610L;
	public SchedulerPanel(final DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	
	@Override protected Object getEventRecorder() {return new Object(){
		@Subscribe public void tasksUpdated(TasksChangedNotification notification){
			repaint();
		}
	};}

	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(CalendarConstants.SCHEDULER_DEFAULT_WIDTH, CalendarConstants.SCHEDULER_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.SCHEDULER_BACKGROUND_COLOR);
		
		enabledCalendarMouseActions();
		setupDragFrom();
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
		GridPainter.drawTimeLines(g, this.getWidth(), this.getHeight(), CalendarConstants.SCHEDULER_GRID_HOUR_COLOR, CalendarConstants.SCHEDULER_GRID_MINUTE_COLOR, CalendarConstants.SCHEDULER_MINUTES_RESOLUTION, false);
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), CalendarConstants.SCHEDULER_GRID_HOUR_COLOR, dataContainer.getView(), false);
		
		//Draw the time-blocks for all tasks.
		//TODO: Optimization, draw only those within view.
		if (drawTasks){
			renderedTimeBlocks.clear();
			
			ArrayList<RenderedTimeBlock> rtbs = new ArrayList<>();
			
			for (Task task: dataContainer.getTasks()){
				for (TimeBlock timeBlock: task.getAllTimeBlocks())
					rtbs.addAll(TimeBlockGenerator.produceRenderedTimeBlocksForTask(task, timeBlock, dataContainer, this));
			}
			
			renderedTimeBlocks = TimeBlockGenerator.processIntersections(dataContainer, rtbs);
			
			for (RenderedTimeBlock rtb: renderedTimeBlocks){
				TimeBlockPainter.renderTimeBlock(g, rtb, dataContainer, this);
			}
		}
		
		//Draw the current time line.
		GridPainter.drawCurrentTime(g, this.getWidth(), this.getHeight(), dataContainer.getView());
	}
	
	// -- Data --
	private boolean drawTasks = false;
	private ArrayList<RenderedTimeBlock> renderedTimeBlocks = new ArrayList<>();

	// -- Drag and drop variables ---
	private CalendarUIMode uiMode = CalendarUIMode.NONE;
	private Duration timeClickedOffset = null; 
	private TimeBlock timeBlockSelected = null;
	
	private void enabledCalendarMouseActions() {
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//Support for moving and resizing TimeBlocks.
				if (uiMode == CalendarUIMode.NONE){
					RenderedTimeBlock clickedTimeBlock = getClickedTimeBlock(e.getX(), e.getY());
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
				} else if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK) {
					DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, dataContainer.getView());
					timeBlockSelected.moveStart(PixelsToDate.roundToMins(timeFromMouse.minus(timeClickedOffset), 15));
					repaint();
				} else if (uiMode == CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK) {
					DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, dataContainer.getView());
					timeBlockSelected.setEnd(PixelsToDate.roundToMins(timeFromMouse, 15));
					repaint();
				} else if (uiMode == CalendarUIMode.RESIZE_TOP_TIMEBLOCK) {
					DateTime timeFromMouse = PixelsToDate.getTimeFromPosition(e.getX(), e.getY(), getWidth()-1, getHeight()-1, dataContainer.getView());
					timeBlockSelected.setStart(PixelsToDate.roundToMins(timeFromMouse, 15));
					repaint();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				//Support for changing mouse cursor depending on position over a TimeBlock.
				RenderedTimeBlock clickedTimeBlock = getClickedTimeBlock(e.getX(), e.getY());
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
				//If we we're dragging, and we release, then, stop dragging and
				//tell everyone that data has changed.
				if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK || uiMode == CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK || uiMode == CalendarUIMode.RESIZE_TOP_TIMEBLOCK){
					endDragging();
				}
			}
		});
	}
	
	private void setupDragFrom() {
		this.setTransferHandler(new TransferHandler("Calendar"){
			private static final long serialVersionUID = -2911907527149438848L;

			@Override
			public boolean canImport(TransferSupport support) {
				//If the data that is coming in is a Task, then start dragging
				//right away. canImport is being used so that the TimeBlock
				//is visible as soon as the pointer enters the Calendar area.
				try {
					if (uiMode == CalendarUIMode.NONE) {
						if (support.getTransferable().isDataFlavorSupported(new DataFlavor(Task.class, "Task"))){
							//Get the task being dragged
							Task task = (Task) support.getTransferable().getTransferData(new DataFlavor(Task.class, "Task"));
							if (task == null) return false;
							
							//Add a new TimeBlock to the Task and start dragging it.
							TimeBlock timeBlock = dataContainer.assignNewTimeBlockToTask(task);
							timeBlockSelected = timeBlock;
							uiMode = CalendarUIMode.MOVE_TIMEBLOCK;
							timeClickedOffset = Duration.ZERO;
							repaint();
							return true;
						} else if (support.getTransferable().isDataFlavorSupported(new DataFlavor(Context.class, "Context"))){
							//Get the context being dragged
							Context context = (Context) support.getTransferable().getTransferData(new DataFlavor(Context.class, "Context"));
							if (context == null) return false;
							
							TimeBlock timeBlock = dataContainer.createNewTaskAndTimeBlockWithContext(context);
							timeBlockSelected = timeBlock;
							uiMode = CalendarUIMode.MOVE_TIMEBLOCK;
							timeClickedOffset = Duration.ZERO;
							repaint();
							return true;
						}
					}
					else if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK) {
						DateTime timeFromMouse = PixelsToDate.getTimeFromPosition((int)support.getDropLocation().getDropPoint().getX(), (int)support.getDropLocation().getDropPoint().getY(), getWidth()-1, getHeight()-1, dataContainer.getView());
						timeBlockSelected.moveStart(PixelsToDate.roundToMins(timeFromMouse.minus(timeClickedOffset), 15));
						repaint();
						return true;
					}
				} catch (UnsupportedFlavorException | IOException e) {
					return false;
				}
				return false;
			}
			
			@Override
			public boolean importData(TransferSupport support) {
				if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK || uiMode == CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK || uiMode == CalendarUIMode.RESIZE_TOP_TIMEBLOCK){
					endDragging();
					return true;
				}
				return false;
			}
		});
	}
	
	// --- Helpers ---
	private RenderedTimeBlock getClickedTimeBlock(int x, int y){
		for (RenderedTimeBlock rt: renderedTimeBlocks){
			if (rt.isWithin(x, y)) {
				return rt;
			}
		}
		return null;
	}
	
	private boolean isNearBottom(MouseEvent e, RenderedTimeBlock clickedTimeBlock) {
		return e.getY()-clickedTimeBlock.y < 5;
	}

	private boolean isNearTop(MouseEvent e, RenderedTimeBlock clickedTimeBlock) {
		return e.getY()-clickedTimeBlock.y > clickedTimeBlock.height-10;
	}
	
	private void endDragging() {
		uiMode = CalendarUIMode.NONE;
		repaint();
		timeClickedOffset = null;
		timeBlockSelected = null;
		eventBus.post(new TasksChangedNotification());
		eventBus.post(new ContextsChangedNotification());
	}
}
