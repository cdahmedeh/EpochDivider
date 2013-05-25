package org.cdahmedeh.orgapp.ui.calendar;

import java.util.ArrayList;
import java.util.HashMap;

import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.RecurringEvent;
import org.cdahmedeh.orgapp.schedule.Schedule;
import org.cdahmedeh.orgapp.ui.UIElementModifier;
import org.cdahmedeh.orgapp.ui.editor.EventEdit;
import org.cdahmedeh.orgapp.view.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class CalendarComposite extends Composite {

	protected Canvas scheduleCanvas;
	protected Canvas daylineCanvas;
	protected Canvas timelineCanvas;
	
	// Used for drag and drop.
	private UIMode uiMode = UIMode.NONE;
	private Event eventEdited = null;
	private Event eventDraged = null;
	private LocalTime startTimeBeforeDrag = null;
	private Duration timeClickedOffset = null;
	
	private HashMap<Rectangle, Event> mapRectEvent = new HashMap<>();
	ArrayList<Event> eventsInView = null;	
	Schedule schedule = null;
	View currentView = null;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CalendarComposite(Composite parent, int style, final Display display, final View currentView, final Schedule schedule) {
		super(parent, style);

		this.schedule = schedule;
		this.currentView = currentView;
		
		final Shell shell = parent.getShell();
		
		//Calendar container
		Composite calendarComposite = this;
		GridLayout calendarGridLayout = new GridLayout();
		calendarGridLayout.numColumns = 2;
		UIElementModifier.removeSpacingAndMargins(calendarGridLayout);
		calendarComposite.setLayout(calendarGridLayout);
	
		//Blank composite
		final Composite empty = new Composite(calendarComposite, SWT.NONE);
		
		GridData emptyGridLayout = new GridData(SWT.LEFT, SWT.TOP, false, false);
		emptyGridLayout.heightHint = 20;
		emptyGridLayout.widthHint = 35;
		empty.setLayoutData(emptyGridLayout);
		
		//Day-Line
		daylineCanvas = new Canvas(calendarComposite, SWT.DOUBLE_BUFFERED);
		
		GridData dayLineGl = new GridData(SWT.FILL, SWT.TOP, true, false);
		dayLineGl.heightHint = 20;
		daylineCanvas.setLayoutData(dayLineGl);
		
		daylineCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
	            GridRenderer.drawDays(e, daylineCanvas, currentView, DateTimeConstants.HOURS_PER_DAY);
			}
		});

		//Time-Line
		timelineCanvas = new Canvas(calendarComposite, SWT.DOUBLE_BUFFERED);
		
		GridData timelineGL = new GridData(SWT.FILL, SWT.FILL, false, true);
		timelineGL.widthHint = 35;
		timelineCanvas.setLayoutData(timelineGL);

		timelineCanvas.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
	            GridRenderer.drawHours(e, scheduleCanvas, currentView, DateTimeConstants.HOURS_PER_DAY);	  
			}
		});
		
		//Schedule
		scheduleCanvas = new Canvas(calendarComposite, SWT.DOUBLE_BUFFERED);
		
		scheduleCanvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
        eventsInView = schedule.getEventsWithinInterval(currentView.getInterval());
		
		scheduleCanvas.addPaintListener(new PaintListener() { 
	        public void paintControl(PaintEvent e) {
	        	e.gc.setAntialias(SWT.ON);

	        	Rectangle clientArea = scheduleCanvas.getClientArea();
 	            
	        	e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	        	e.gc.fillRectangle(clientArea); 	            
	            
	            GridRenderer.drawGrid(e, scheduleCanvas, currentView);
	            
	            mapRectEvent.clear();	            

	            for (Event event: eventsInView){
	            	ArrayList<Rectangle> rectangles = EventRenderer.draw(event, currentView, e, scheduleCanvas);
	            	for (Rectangle rectangle: rectangles){
	            		mapRectEvent.put(rectangle, event);
	        		}
	            }
	            
	            e.gc.setAlpha(255);
	            e.gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
	            
	            if (true){
	            	e.gc.drawRectangle(DateToPixels.getHorizontalPositionFromDate(new LocalDate(), scheduleCanvas.getClientArea().width, currentView),
	            						DateToPixels.getVerticalPositionFromTime(new LocalTime(), scheduleCanvas.getClientArea().height, currentView),
	            						DateToPixels.getWidthBasedOnView(scheduleCanvas.getClientArea().width, currentView),
	            						1);
	            }
	        }
	    });
		
		scheduleCanvas.addDragDetectListener(new DragDetectListener() {
			@Override
			public void dragDetected(DragDetectEvent e) {
				for (Rectangle r: mapRectEvent.keySet()){
					if (r.contains(e.x,e.y)) {
						eventDraged = mapRectEvent.get(r);
						if ((r.y+r.height)-e.y<=10) {
							uiMode = UIMode.RESIZE_BOTTOM;
						} else {
							uiMode = UIMode.DRAG;
						}
						timeClickedOffset = new Duration(eventDraged.getBegin(), PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView));
						scheduleCanvas.redraw();
						return;
					}
				}

				uiMode = UIMode.NEW_EVENT;
				Event newEventDragged = new Event("newevent", PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView), 15), PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView));
				schedule.addEvent(newEventDragged);
				eventDraged = newEventDragged;
				scheduleCanvas.redraw();
			}
		});

		scheduleCanvas.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				for (Rectangle r: mapRectEvent.keySet()){
					mapRectEvent.get(r).setSelected(false);
					scheduleCanvas.redraw();
				}
				
				if (eventDraged instanceof RecurringEvent){
//					((RecurringEvent) eventDraged).propagateBackToOriginal();
//					eventsInView = schedule.getEventsWithinInterval(currentView.getInterval());
					
					Event makeException = ((RecurringEvent) eventDraged).makeException();
					schedule.addEvent(makeException);
					eventsInView = schedule.getEventsWithinInterval(currentView.getInterval());
				}
				
				uiMode = UIMode.NONE;
				eventDraged = null;
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				
				for (Rectangle r: mapRectEvent.keySet()){
					if (r.contains(e.x,e.y)) {
						mapRectEvent.get(r).setSelected(true);
						scheduleCanvas.redraw();
					}
				}
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				for (Rectangle r: mapRectEvent.keySet()){
					if (r.contains(e.x,e.y)) {
						eventEdited = mapRectEvent.get(r);
					}
				}
				
				EventEdit supershell = new EventEdit(shell, SWT.NONE, eventEdited);
				supershell.open();
				scheduleCanvas.redraw();
			}
		});
		
		scheduleCanvas.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				if (uiMode == UIMode.DRAG){
					Duration duration = eventDraged.getDuration();
					eventDraged.setBegin(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView).minus(timeClickedOffset), 15));
					eventDraged.setEnd(eventDraged.getBegin().plus(duration));
					scheduleCanvas.redraw();
				} else if (uiMode == UIMode.RESIZE_BOTTOM) {
					eventDraged.setEnd(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView), 15));
					scheduleCanvas.redraw();
				} else if (uiMode == UIMode.NEW_EVENT){
					eventDraged.setEnd(PixelsToDate.roundToMins(PixelsToDate.getTimeFromPosition(e.x, e.y, scheduleCanvas.getClientArea(), currentView), 15));
					eventsInView = schedule.getEventsWithinInterval(currentView.getInterval());
					scheduleCanvas.redraw();
				}
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void refresh() {
		scheduleCanvas.redraw();
		timelineCanvas.redraw();
		daylineCanvas.redraw();
		eventsInView = schedule.getEventsWithinInterval(currentView.getInterval());
	}

}
