package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.misc.BigContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.cdahmedeh.orgapp.ui.helpers.ComponentModifier;
import org.cdahmedeh.orgapp.ui.notify.TasksModifiedNotification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class CalendarComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new EventRecorder());
	}
	class EventRecorder{
		@Subscribe public void tasksModified(TasksModifiedNotification notify){
			redrawAllCanvas();
		}
	}
	
	protected Canvas calendarCanvas;
	protected Canvas daylineCanvas;
	protected Canvas timelineCanvas;

	private HashMap<TimeBlock, Task> timeBlockTaskMap = new HashMap<>();
	private HashMap<Rectangle, TimeBlock> rectangleTimeBlockMap = new HashMap<>();
	
	private CalendarUIMode uiMode = CalendarUIMode.NONE;
	private Duration timeClickedOffset = null;
	private TimeBlock timeBlockDragged = null;
		
	private TaskContainer taskContainer = null;
	private View currentView =  null;
	
	public CalendarComposite(Composite parent, int style, BigContainer bigContainer) {
		super(parent, style);
		
		this.taskContainer = bigContainer.getTaskContainer();
		this.currentView = bigContainer.getCurrentView();
		
		fillTimeBlockTaskMap();
		prepareGridLayout();
		makeSpacer();
		makeDayLine();
		makeTimeLine();
		makeCalendar();
		makeCalendarScrollable();
		makeCalendarZoomable();
		makeCalendarBlocksDraggable();
		setupDrop();
	}

	public void fillTimeBlockTaskMap() {
		for (Task task: taskContainer.getAllTasks()){
			for (TimeBlock timeBlock: task.getAllTimeBlocks()){
				timeBlockTaskMap.put(timeBlock, task);
			}
		}
	}
	
	public void prepareGridLayout() {
		GridLayout calendarGridLayout = new GridLayout();
		ComponentModifier.removeSpacingAndMargins(calendarGridLayout);
		calendarGridLayout.numColumns = 2;
		this.setLayout(calendarGridLayout);
	}
	
	public void makeSpacer() {
		final Composite empty = new Composite(this, SWT.NONE);
		GridData emptyGridLayout = new GridData(SWT.LEFT, SWT.TOP, false, false);
		emptyGridLayout.heightHint = CalendarUIConstants.dayLineHeight;
		emptyGridLayout.widthHint = CalendarUIConstants.timeLineWidth;
		empty.setLayoutData(emptyGridLayout);
		empty.setBackground(SWTResourceManager.getColor(CalendarUIConstants.lineBackgroundColor));
	}
	
	public void makeDayLine() {
		daylineCanvas = new Canvas(this, SWT.V_SCROLL);
		daylineCanvas.setBackground(SWTResourceManager.getColor(CalendarUIConstants.lineBackgroundColor));
		
		GridData dayLineGl = new GridData(SWT.FILL, SWT.TOP, true, false);
		dayLineGl.heightHint = CalendarUIConstants.dayLineHeight;
		daylineCanvas.setLayoutData(dayLineGl);
		
		daylineCanvas.addPaintListener(new PaintListener() {
		@Override public void paintControl(PaintEvent e) {GridRenderer.drawDays(e, daylineCanvas, currentView);}});
	}
	
	public void makeTimeLine() {
		timelineCanvas = new Canvas(this, SWT.NONE);
		timelineCanvas.setBackground(SWTResourceManager.getColor(CalendarUIConstants.lineBackgroundColor));
		
		GridData timelineGL = new GridData(SWT.FILL, SWT.FILL, false, true);
		timelineGL.widthHint = CalendarUIConstants.timeLineWidth;
		timelineCanvas.setLayoutData(timelineGL);

		timelineCanvas.addPaintListener(new PaintListener() {
		@Override public void paintControl(PaintEvent e) {GridRenderer.drawHours(e, timelineCanvas, currentView);}});
	}
	
	public void makeCalendar() {
		calendarCanvas = new Canvas(this, SWT.V_SCROLL);
		calendarCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		calendarCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			
		calendarCanvas.addPaintListener(new PaintListener() { 
			public void paintControl(PaintEvent e) {
				//TODO: antialias is SLOW
//	        	e.gc.setAntialias(SWT.ON);
	            GridRenderer.drawTimeGrid(e, calendarCanvas, currentView);
	            
	            rectangleTimeBlockMap.clear();
	            	            
	            for (Entry<TimeBlock, Task> entry : timeBlockTaskMap.entrySet()){
	            	ArrayList<Rectangle> rectangles = TimeBlockRenderer.draw(entry.getKey(), entry.getValue(), currentView, e, calendarCanvas);
	            	for (Rectangle rectangle: rectangles){
	            		rectangleTimeBlockMap.put(rectangle, entry.getKey());
	            	}
				}
	            
				GridRenderer.drawCurrentTime(Display.getDefault(), calendarCanvas, currentView, e);
	        }
	    });
	}

	public void makeCalendarScrollable() {
		final ScrollBar calendarVBar = calendarCanvas.getVerticalBar();
		calendarVBar.setValues(
			currentView.getFirstHour().getHourOfDay(), 0, 
			24 - currentView.getNumberOfHoursVisible(), 1, 1, 1);
		
		calendarVBar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = calendarVBar.getSelection();
				currentView.setLastHour(new LocalTime(selection+currentView.getNumberOfHoursVisible(),59,59,999));
				currentView.setFirstHour(new LocalTime(selection, 0, 0));
				redrawAllCanvas();
			}
		});
	}
	
	public void makeCalendarZoomable() {
//		calendarCanvas.addMouseWheelListener(new MouseWheelListener() {
//			@Override
//			public void mouseScrolled(MouseEvent e) {
//				if (e.stateMask == SWT.MOD1){
//					currentView.zoomInMinutes(e.count > 0 ? -60: 60);
//					redrawAllCanvas();
//				}
//			}
//		});
	}
	
	private void makeCalendarBlocksDraggable() {
		calendarCanvas.addDragDetectListener(new DragDetectListener() {
			@Override
			public void dragDetected(DragDetectEvent e) {
				for (Rectangle r: rectangleTimeBlockMap.keySet()){ //TODO: Use Entry set.
					if (r.contains(e.x,e.y)) {
						if ((r.y+r.height)-e.y<=10) {
							uiMode = CalendarUIMode.RESIZE_BOTTOM;
						} else {
							uiMode = CalendarUIMode.DRAG;
						}
						timeBlockDragged = rectangleTimeBlockMap.get(r);
						timeClickedOffset  = new Duration(timeBlockDragged.getStart(), PixelsToDate.getTimeFromPosition(e.x, e.y, calendarCanvas.getClientArea(), currentView));
					}
				}
			}
		});
		
		calendarCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				uiMode = CalendarUIMode.NONE;
				eventBus.post(new TasksModifiedNotification());
				calendarCanvas.redraw();
			}
		});
		
		calendarCanvas.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				if (uiMode == CalendarUIMode.DRAG){
					Duration duration = timeBlockDragged.getDuration();
					timeBlockDragged.setStart(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, calendarCanvas.getClientArea(), currentView).minus(timeClickedOffset), 15));
					timeBlockDragged.setEnd(timeBlockDragged.getStart().plus(duration));
					calendarCanvas.redraw();
				} else if (uiMode == CalendarUIMode.RESIZE_BOTTOM) {
					timeBlockDragged.setEnd(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, calendarCanvas.getClientArea(), currentView), 15));
					calendarCanvas.redraw();
				} 
				
//				//TODO: temp cursor method
//				if (uiMode == CalendarUIMode.NONE){
//				for (Rectangle r: rectangleTimeBlockMap.keySet()){ //TODO: Use Entry set.
//					if (r.contains(e.x,e.y)) {
//						if ((r.y+r.height)-e.y<=10) {
//							calendarCanvas.getShell().setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_SIZES));
//							return;
//						} else {
//							calendarCanvas.getShell().setCursor(new Cursor(Display.getCurrent(), SWT.NONE));
//						}
//					} else {
//					}
//				}
//				calendarCanvas.getShell().setCursor(new Cursor(Display.getCurrent(), SWT.NONE));
//				}
			}
		});
	}
	
	public void setupDrop(){
		DropTarget target = new DropTarget(calendarCanvas, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
		target.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		target.addDropListener(new DropTargetAdapter(){
			@Override
			public void drop(DropTargetEvent event) {
				Task selectedTask = taskContainer.getTaskFromId(Integer.valueOf((String) event.data));
				TimeBlock newTimeBlock = new TimeBlock();
				selectedTask.assignToTimeBlock(newTimeBlock);
				timeBlockDragged = newTimeBlock;
				timeClickedOffset = new Duration(15*DateTimeConstants.MILLIS_PER_MINUTE);
//				eventBus.post(new TasksModifiedNotification());
				//TODO: test only
				timeBlockTaskMap.clear();
				fillTimeBlockTaskMap();
				redrawAllCanvas();
				uiMode = CalendarUIMode.DRAG;
//				System.out.println(selectedTask.getTitle());
			}
		});
	}
	
	public void redrawAllCanvas() {
		calendarCanvas.redraw();
		timelineCanvas.redraw();
		daylineCanvas.redraw();
	}
}