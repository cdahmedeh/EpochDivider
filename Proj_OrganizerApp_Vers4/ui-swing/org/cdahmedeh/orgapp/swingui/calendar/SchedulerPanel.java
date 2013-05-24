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

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class SchedulerPanel extends CPanel {
	private static final long serialVersionUID = 3673536421097243610L;
	public SchedulerPanel(final DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	
	@Override protected Object getEventRecorder() {return new Object(){
		@Subscribe public void tasksUpdated(TasksChangedNotification notification){
			repaint();
		}
		@Subscribe public void contextsUpdated(ContextsChangedNotification notification){
			repaint();
		}
	};}

	@Override protected void windowInit() {
		setPreferredSize(new Dimension(CalendarConstants.SCHEDULER_DEFAULT_WIDTH, CalendarConstants.SCHEDULER_DEFAULT_HEIGHT));
		setBackground(CalendarConstants.SCHEDULER_BACKGROUND_COLOR);
	}

	@Override protected void postWindowInit() {
		drawTasks = true;
		repaint();
		
		enabledCalendarMouseActions();
		setupDragFrom();
	}
	
	// -- Data --
	private boolean drawTasks = false;
	private ArrayList<TimeBlockRender> renderedTimeBlocks = new ArrayList<>();
	private CalendarUIMode uiMode = CalendarUIMode.NONE;
	
	private TimeBlockRender tbrSelected = null;
	private TimeBlockClickLocation clickLocation;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		GraphicsHelper.enableDefaultAASettings(g);

		generateTimeBlockRenders();
		drawGrid(g);
		if (drawTasks) {
			processIntersections();
			drawTimeBlocks(g);
		}
		drawCurrentTimeLine(g);
	}
	
	private void generateTimeBlockRenders() {
		if (renderedTimeBlocks == null) return;
		renderedTimeBlocks.clear();

		for (Task task: dataContainer.getTasks()){
			for (TimeBlock timeBlock: task.getAllTimeBlocks()) {
				TimeBlockRender e = new TimeBlockRender(task, timeBlock, dataContainer.getView(), this.getWidth(), this.getHeight());
				e.generateRectangles();
				renderedTimeBlocks.add(e);
			}
		}
	}
	
	private void drawGrid(Graphics g) {
		//Draw grid in the background
		GridPainter.drawTimeLines(g, this.getWidth(), this.getHeight(), CalendarConstants.SCHEDULER_GRID_HOUR_COLOR, CalendarConstants.SCHEDULER_GRID_MINUTE_COLOR, CalendarConstants.SCHEDULER_MINUTES_RESOLUTION, false);
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), CalendarConstants.SCHEDULER_GRID_HOUR_COLOR, dataContainer.getView(), false);
	}

	private void drawTimeBlocks(Graphics g) {
		//Draw the time-blocks for all tasks.
		for (TimeBlockRender rtb: renderedTimeBlocks){ 
			for (BRectangle rect: rtb.getRects()){
				TimeBlockPainter.renderTimeBlock(g, rtb.getTask(), rtb.getTimeBlock(), rect, dataContainer, this);
			}
		}
	}

	private void processIntersections() {
		ArrayList<BRectangle> rects = new ArrayList<>();
		for (TimeBlockRender rtb: renderedTimeBlocks){ 
			rects.addAll(rtb.getRects());
		}
		TimeBlockIntersectionHandler.processIntersections(rects);
	}
	
	private void drawCurrentTimeLine(Graphics g) {
		//Draw the current time line.
		GridPainter.drawCurrentTime(g, this.getWidth(), this.getHeight(), dataContainer.getView());
	}


	private void enabledCalendarMouseActions() {
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//Support for moving and resizing TimeBlocks.
				if (uiMode == CalendarUIMode.NONE){
					TimeBlockRender clickedTimeBlock = getClickedTimeBlock(e.getX(), e.getY());
					if (clickedTimeBlock != null) {
						tbrSelected = clickedTimeBlock;
						tbrSelected.setMoveOffset(e.getX(), e.getY());
						if (clickLocation == TimeBlockClickLocation.BOTTOM){
							uiMode = CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK;
						} else if (clickLocation == TimeBlockClickLocation.TOP){
							uiMode = CalendarUIMode.RESIZE_TOP_TIMEBLOCK;
						} else {
							uiMode = CalendarUIMode.MOVE_TIMEBLOCK;
						}
					}
				} else if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK) {
					tbrSelected.move(e.getX(), e.getY());
					repaint();
				} else if (uiMode == CalendarUIMode.RESIZE_BOTTOM_TIMEBLOCK) {
					tbrSelected.resizeBottom(e.getX(), e.getY());
					repaint();
				} else if (uiMode == CalendarUIMode.RESIZE_TOP_TIMEBLOCK) {
					tbrSelected.resizeTop(e.getX(), e.getY());
					repaint();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				//Support for changing mouse cursor depending on position over a TimeBlock.
				TimeBlockRender clickedTimeBlock = getClickedTimeBlock(e.getX(), e.getY());
				if (getClickedTimeBlock(e.getX(), e.getY()) != null) {
					if (clickLocation == TimeBlockClickLocation.TOP){
						setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					} else if (clickLocation == TimeBlockClickLocation.BOTTOM){
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
							TimeBlockRender timeBlockRender = new TimeBlockRender(task, timeBlock, dataContainer.getView(), getWidth(), getHeight());
							renderedTimeBlocks.add(timeBlockRender);
							tbrSelected = timeBlockRender;							
							uiMode = CalendarUIMode.MOVE_TIMEBLOCK;
							tbrSelected.resetOffset();
							repaint();
							return true;
						} else if (support.getTransferable().isDataFlavorSupported(new DataFlavor(Context.class, "Context"))){
							//Get the context being dragged
							Context context = (Context) support.getTransferable().getTransferData(new DataFlavor(Context.class, "Context"));
							if (context == null) return false;
							
							TimeBlock timeBlock = dataContainer.createNewTaskAndTimeBlockWithContext(context);
							TimeBlockRender timeBlockRender = new TimeBlockRender(new Task(""), timeBlock, dataContainer.getView(), getWidth(), getHeight());
							renderedTimeBlocks.add(timeBlockRender);
							tbrSelected = timeBlockRender;							
							uiMode = CalendarUIMode.MOVE_TIMEBLOCK;
							tbrSelected.resetOffset();
							repaint();
							return true;
						}
					}
					else if (uiMode == CalendarUIMode.MOVE_TIMEBLOCK) {
						tbrSelected.move((int)support.getDropLocation().getDropPoint().getX(), (int)support.getDropLocation().getDropPoint().getY());
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
	private TimeBlockRender getClickedTimeBlock(int x, int y){
		for (TimeBlockRender rt: renderedTimeBlocks){
			TimeBlockClickLocation pointerWithin = rt.pointerWithin(x, y);
			if (pointerWithin != TimeBlockClickLocation.NONE) {
				clickLocation = pointerWithin;
				return rt;
			}
		}
		return null;
	}

	private void endDragging() {
		uiMode = CalendarUIMode.NONE;
		tbrSelected = null;
		repaint();
		eventBus.post(new TasksChangedNotification());
		eventBus.post(new ContextsChangedNotification());
	}
}
