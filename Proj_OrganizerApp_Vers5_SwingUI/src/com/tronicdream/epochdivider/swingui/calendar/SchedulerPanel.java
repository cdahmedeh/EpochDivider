package com.tronicdream.epochdivider.swingui.calendar;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.TransferHandler;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;
import com.tronicdream.epochdivider.swingui.calendar.timeblock.BRectangle;
import com.tronicdream.epochdivider.swingui.calendar.timeblock.TimeBlockIntersectionHandler;
import com.tronicdream.epochdivider.swingui.calendar.timeblock.TimeBlockPainter;
import com.tronicdream.epochdivider.swingui.calendar.timeblock.TimeBlockRender;
import com.tronicdream.epochdivider.swingui.helpers.GraphicsHelper;
import com.tronicdream.epochdivider.swingui.main.CPanel;
import com.tronicdream.epochdivider.swingui.notification.ContextsChangedNotification;
import com.tronicdream.epochdivider.swingui.notification.TasksChangedNotification;

public class SchedulerPanel extends CPanel {
	private static final long serialVersionUID = 3673536421097243610L;
	public SchedulerPanel(final DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus, "Scheduler");}
	
	@Override protected Object getEventRecorder() {return new Object(){
		@Subscribe public void tasksUpdated(TasksChangedNotification notification){
			generateTimeBlockRenders();
			repaint();
		}
		@Subscribe public void contextsUpdated(ContextsChangedNotification notification){
			generateTimeBlockRenders();
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
	private HashMap<TimeBlock, TimeBlockRender> renderedTimeBlocks = new HashMap<>();
	private CalendarUIMode uiMode = CalendarUIMode.NONE;
	
	private TimeBlockRender tbrSelected = null;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		GraphicsHelper.enableDefaultAASettings(g);

		generateTimeBlockRenders();
		drawGrid(g);
		if (drawTasks) {
//			processIntersections();
			drawTimeBlocks(g);
		}
		drawCurrentTimeLine(g);
	}
	
	private void generateTimeBlockRenders() {
		if (renderedTimeBlocks == null) return;
		renderedTimeBlocks.clear();

			for (TimeBlock timeBlock: dataContainer.getTimeBlocks()) {
				TimeBlockRender e = new TimeBlockRender(timeBlock, dataContainer.getView(), this.getWidth(), this.getHeight());
				e.generateRectangles();
				renderedTimeBlocks.put(timeBlock, e);
//				}
			}
	}
	
	private void drawGrid(Graphics g) {
		//Draw grid in the background
		GridPainter.drawTimeLines(g, this.getWidth(), this.getHeight(), CalendarConstants.SCHEDULER_GRID_HOUR_COLOR, CalendarConstants.SCHEDULER_GRID_MINUTE_COLOR, CalendarConstants.SCHEDULER_MINUTES_RESOLUTION, false);
		GridPainter.drawDateLines(g, this.getWidth(), this.getHeight(), CalendarConstants.SCHEDULER_GRID_HOUR_COLOR, dataContainer.getView(), false);
	}

	private void drawTimeBlocks(Graphics g) {
		//Draw the time-blocks for all tasks.
		for (TimeBlockRender rtb: renderedTimeBlocks.values()){ 
			for (BRectangle rect: rtb.getRects()){
				TimeBlockPainter.renderTimeBlock(g, rtb.getOwner(), rtb.getTimeBlock(), rect, dataContainer, this);
			}
		}
	}

	private void processIntersections() {
		ArrayList<BRectangle> rects = new ArrayList<>();
		for (TimeBlockRender rtb: renderedTimeBlocks.values()){ 
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
						uiMode = CalendarUIMode.ADJUST_TIMEBLOCK;
					}
				} else if (uiMode == CalendarUIMode.ADJUST_TIMEBLOCK) {
					tbrSelected.move(e.getX(), e.getY());
					repaint();
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (uiMode == CalendarUIMode.ADJUST_TIMEBLOCK){
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
							TimeBlock timeBlock = dataContainer.emTaskSetNewTimeBlock(task);
							generateTimeBlockRenders();
							tbrSelected = renderedTimeBlocks.get(timeBlock);
							uiMode = CalendarUIMode.ADJUST_TIMEBLOCK;
							tbrSelected.forceMove();
							repaint();
							return true;
						}
					}
					else if (uiMode == CalendarUIMode.ADJUST_TIMEBLOCK) {
						Point dropPoint = support.getDropLocation().getDropPoint();
						tbrSelected.move((int)dropPoint.getX(), (int)dropPoint.getY());
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
				if (uiMode == CalendarUIMode.ADJUST_TIMEBLOCK){
					endDragging();
					return true;
				}
				return false;
			}
		});
	}
	
	// --- Helpers ---
	private TimeBlockRender getClickedTimeBlock(int x, int y){
		for (TimeBlockRender rt: renderedTimeBlocks.values()){
			if (rt.pointerWithin(x, y)) {
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
