package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.cdahmedeh.orgapp.ui.helpers.ComponentModifier;
import org.eclipse.swt.SWT;
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
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class CalendarComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	protected Canvas calendarCanvas;
	protected Canvas daylineCanvas;
	protected Canvas timelineCanvas;

	private HashMap<TimeBlock, Task> timeBlockTaskMap = new HashMap<>();
	private HashMap<Rectangle, TimeBlock> rectangleTimeBlockMap = new HashMap<>();
	
	private TaskContainer taskContainer = null;
	private View currentView =  new View(new LocalDate(), new LocalDate().plusDays(7), new LocalTime(12, 0, 0), new LocalTime(23, 59, 59, 999));
	
	public CalendarComposite(Composite parent, int style, TaskContainer taskContainer) {
		super(parent, style);
		
		this.taskContainer = taskContainer;
	
		fillTimeBlockTaskMap();
		prepareGridLayout();
		makeSpacer();
		makeDayLine();
		makeTimeLine();
		makeCalendar();
		makeCalendarScrollable();
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
	        	e.gc.setAntialias(SWT.ON);
	            GridRenderer.drawTimeGrid(e, calendarCanvas, currentView);
	            
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
	
	public void redrawAllCanvas() {
		calendarCanvas.redraw();
		timelineCanvas.redraw();
		daylineCanvas.redraw();
	}
}