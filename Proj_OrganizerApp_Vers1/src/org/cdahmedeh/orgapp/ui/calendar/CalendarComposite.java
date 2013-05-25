package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;
import java.util.HashMap;

import org.cdahmedeh.orgapp.calendar.View;
import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.task.RecurringTask;
import org.cdahmedeh.orgapp.task.Task;
import org.cdahmedeh.orgapp.task.TaskContainer;
import org.cdahmedeh.orgapp.ui.UIConstants;
import org.cdahmedeh.orgapp.ui.helpers.ElementModifier;
import org.cdahmedeh.orgapp.ui.notification.TaskChangedEvent;
import org.cdahmedeh.orgapp.ui.task.TaskEditor;
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
import org.eclipse.swt.graphics.RGB;
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
		eventBus.register(new EventRecorder());
	}
	
	private Display display = Display.getDefault();
	
	protected Canvas scheduleCanvas;
	protected Canvas daylineCanvas;
	protected Canvas timelineCanvas;
	
	// Used for drag and drop.
	private UIMode uiMode = UIMode.NONE;
	private Task eventEdited = null;
	private Task eventDraged = null;
	private Duration timeClickedOffset = null;
	private LocalDate startDateBeforeDrag = null;
	
	private HashMap<Rectangle, Task> mapRectEvent = new HashMap<>();
	private TaskContainer tasks = null;
	private View currentView = null;
	private Context rootContext = null;
	private ArrayList<Task> subTasks = null;
	
	public CalendarComposite(Composite parent, int style, final Display display, final View currentView, final TaskContainer tasks, final Context rootContext) {
		super(parent, style);

		this.tasks = tasks;
		this.currentView = currentView;
		this.rootContext = rootContext;
		
		GridLayout calendarGridLayout = new GridLayout();
		ElementModifier.removeSpacingAndMargins(calendarGridLayout);
		calendarGridLayout.numColumns = 2;
		this.setLayout(calendarGridLayout);
	
		drawSpacer();
		drawDayline();
		drawTimeLine();
		drawSchedule();
		
		setupTaskDrag();
		setupScrollbar();
		setupDragAndDrop();
	}


	public void drawSpacer() {
		final Composite empty = new Composite(this, SWT.NONE);
		GridData emptyGridLayout = new GridData(SWT.LEFT, SWT.TOP, false, false);
		emptyGridLayout.heightHint = UIConstants.dayLineHeight;
		emptyGridLayout.widthHint = UIConstants.timeLineWidth;
		empty.setLayoutData(emptyGridLayout);
		empty.setBackground(SWTResourceManager.getColor(UIConstants.lineBackgroundColor));
	}
	
	public void drawDayline() {
		daylineCanvas = new Canvas(this, SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
		daylineCanvas.setBackground(SWTResourceManager.getColor(UIConstants.lineBackgroundColor));
		
		GridData dayLineGl = new GridData(SWT.FILL, SWT.TOP, true, false);
		dayLineGl.heightHint = UIConstants.dayLineHeight;
		daylineCanvas.setLayoutData(dayLineGl);
		
		daylineCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
	            GridRenderer.drawDays(e, daylineCanvas, currentView);
			}
		});
	}
	
	public void drawTimeLine() {
		timelineCanvas = new Canvas(this, SWT.DOUBLE_BUFFERED);
		timelineCanvas.setBackground(SWTResourceManager.getColor(UIConstants.lineBackgroundColor));
		
		GridData timelineGL = new GridData(SWT.FILL, SWT.FILL, false, true);
		timelineGL.widthHint = UIConstants.timeLineWidth;
		timelineCanvas.setLayoutData(timelineGL);

		timelineCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
	            GridRenderer.drawHours(e, timelineCanvas, currentView);	  
			}
		});
	}
	
	public void drawSchedule() {
		requeryTasks();
		
		scheduleCanvas = new Canvas(this, SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
		scheduleCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		scheduleCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		scheduleCanvas.addPaintListener(new PaintListener() { 
			public void paintControl(PaintEvent e) {
	        	e.gc.setAntialias(SWT.ON);
	            mapRectEvent.clear();
	            GridRenderer.drawTimeGrid(e, scheduleCanvas, currentView);
	            
				for (Task task: subTasks){
	            	for (Rectangle rectangle: TaskRenderer.draw(task, currentView, e, scheduleCanvas)){
	            		mapRectEvent.put(rectangle, task);
	        		}
	            }
				
				GridRenderer.drawCurrentTime(display, scheduleCanvas, currentView, e);
	        }
	    });
	}

	public void setupTaskDrag() {
		scheduleCanvas.addDragDetectListener(new DragDetectListener() {
			@Override
			public void dragDetected(DragDetectEvent e) {
				for (Rectangle r: mapRectEvent.keySet()){ if (r.contains(e.x,e.y)) {
					eventDraged = mapRectEvent.get(r);
					if ((r.y+r.height)-e.y<=10) {
						uiMode = UIMode.RESIZE_BOTTOM;
					} else {						
						uiMode = UIMode.DRAG;
					}
					timeClickedOffset = new Duration(eventDraged.getScheduled(), PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView));
					startDateBeforeDrag = eventDraged.getScheduled().toLocalDate();
					scheduleCanvas.redraw();
					return;
				}}

				uiMode = UIMode.NEW_EVENT;
				Task newTask = new Task("new task", tasks);
				newTask.setScheduled(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView), 15));
				eventDraged = newTask;
				tasks.addTask(newTask);
		        requeryTasks();
				scheduleCanvas.redraw();
			}
		});

		scheduleCanvas.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				if (eventDraged != null){
					uiMode = UIMode.NONE;
					scheduleCanvas.redraw();
					if (eventDraged instanceof RecurringTask){
						((RecurringTask)eventDraged).recurrenceMakeException(startDateBeforeDrag);
					}
					eventDraged = null;
					eventBus.post(new TaskChangedEvent());
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				for (Rectangle r: mapRectEvent.keySet()){
					if (r.contains(e.x,e.y)) {
						eventEdited = mapRectEvent.get(r);
					}
				}
				if (eventEdited !=null){
					TaskEditor taskEditor = new TaskEditor(getShell(), SWT.NONE, eventEdited, rootContext);
					taskEditor.open();
					eventBus.post(new TaskChangedEvent());
				}
			}
		});
		
		scheduleCanvas.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				if (uiMode == UIMode.DRAG){
					Duration duration = eventDraged.getDuration();
					eventDraged.setScheduled(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView).minus(timeClickedOffset), 15));
					eventDraged.setDuration(duration);
					scheduleCanvas.redraw();
				} else if (uiMode == UIMode.RESIZE_BOTTOM) {
					eventDraged.setDuration(new Duration(eventDraged.getScheduled(), (PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView), 15))));
					scheduleCanvas.redraw();
				} else if (uiMode == UIMode.NEW_EVENT){
					eventDraged.setDuration(new Duration(eventDraged.getScheduled(), (PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView), 15))));
					scheduleCanvas.redraw();
				}
			}
		});
	}
	
	public void setupScrollbar() {
		final ScrollBar scroll = scheduleCanvas.getVerticalBar();
		scroll.setValues(12, 0, currentView.getNumberOfHoursVisible(), 1, 1, 1);
		scroll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int selection = scroll.getSelection();
				currentView.setFirstHour(new LocalTime(selection, 0, 0));
				currentView.setLastHour(new LocalTime(selection+12, 59, 59));
				redrawAll();
			}
		});
	}

	public void setupDragAndDrop() {
		DropTarget target = new DropTarget(scheduleCanvas, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
		target.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		target.addDropListener(new DropTargetAdapter(){
			@Override
			public void drop(DropTargetEvent event) {
				for (Task task: tasks.getTasks()){
					if (task.getId() == (Integer.parseInt((String) event.data))){
						eventDraged = task;
						eventBus.post(new TaskChangedEvent());
						uiMode = UIMode.DRAG;

						eventDraged.setScheduled(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(event.x, event.y, scheduleCanvas.getClientArea(), currentView), 15));
						
						if (task.getDuration() == null) task.setDuration(new Duration(2*DateTimeConstants.MILLIS_PER_HOUR));
						scheduleCanvas.redraw();
						
						//TODO: temp
						eventBus.post(new TaskChangedEvent());
					}
				}
			}
		});
	}	
	
	public void requeryTasks() {
		subTasks = tasks.generateReccurence(currentView.getEndDate(), currentView.getStartDate()).getTasks();
	}
	
	public void redrawAll() {
		scheduleCanvas.redraw();
		timelineCanvas.redraw();
		daylineCanvas.redraw();
	}
		
	class EventRecorder{
		@Subscribe public void refreshTasks(TaskChangedEvent event){
	        requeryTasks();
			redrawAll();
		}
	}
}
