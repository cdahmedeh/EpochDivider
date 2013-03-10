package org.cdahmedeh.orgapp.ui.calendar;

import java.util.HashMap;
import java.util.Map.Entry;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class CalendarComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	private TaskContainer taskContainer = null;
	private HashMap<TimeBlock, Task> timeBlockTaskMap = new HashMap<>();
	
	public CalendarComposite(Composite parent, int style, TaskContainer taskContainer) {
		super(parent, style);
		
		this.taskContainer = taskContainer;
		
		this.setLayout(new FillLayout());
		
		fillTimeBlockTaskMap();
		
		makeCalendar();
	}

	public void fillTimeBlockTaskMap() {
		for (Task task: taskContainer.getAllTasks()){
			for (TimeBlock timeBlock: task.getAllTimeBlocks()){
				timeBlockTaskMap.put(timeBlock, task);
			}
		}
	}
	
	public void makeCalendar() {
		final View currentView = new View(new LocalDate(), new LocalDate().plusDays(7), LocalTime.MIDNIGHT, new LocalTime(13, 59, 59));
		
		final Canvas calendarCanvas = new Canvas(this, SWT.V_SCROLL);
		calendarCanvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		calendarCanvas.addPaintListener(new PaintListener() { 
			public void paintControl(PaintEvent e) {
	        	e.gc.setAntialias(SWT.ON);
	            GridRenderer.drawTimeGrid(e, calendarCanvas, currentView);
	            
	            for (Entry<TimeBlock, Task> entry : timeBlockTaskMap.entrySet()){
	            	TimeBlockRenderer.draw(entry.getKey(), entry.getValue(), currentView, e, calendarCanvas);
				}
	            
				GridRenderer.drawCurrentTime(Display.getDefault(), calendarCanvas, currentView, e);
	        }
	    });
	}
}