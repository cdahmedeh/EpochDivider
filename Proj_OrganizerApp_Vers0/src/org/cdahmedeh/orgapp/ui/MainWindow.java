package org.cdahmedeh.orgapp.ui;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.autofinder.BlankSpaceFinder;
import org.cdahmedeh.orgapp.generators.Gen00;
import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.Schedule;
import org.cdahmedeh.orgapp.ui.calendar.CalendarComposite;
import org.cdahmedeh.orgapp.ui.editor.EventEdit;
import org.cdahmedeh.orgapp.view.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.joda.time.DateTime;

import swing2swt.layout.BorderLayout;

public class MainWindow {
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	// Current view and events.
	private final Schedule schedule = Gen00.generateSchedule();
	private final View currentView = Gen00.getView();	
	
	// UI Component References
	protected Shell shell;
	protected Display display;
	protected CalendarComposite calendarComposite;
	
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("Calendar Application");
		shell.setLayout(new BorderLayout());
		
		buildMenu();
		createToolbar();
	
		SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL | SWT.SMOOTH);
		
		calendarComposite = new CalendarComposite(sashForm, SWT.NONE, display, currentView, schedule);
		createSideBar(sashForm);
		
		sashForm.setWeights(new int[]{100,20});
	}
	
	public Menu buildMenu() {
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("File");
		
		Menu menu_1 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_1);
		
		MenuItem quit = new MenuItem(menu_1, SWT.NONE);
		quit.setText("Quit");
		
		return menu;
	}

	public ToolBar createToolbar() {
		ToolBar toolbar = new ToolBar(shell, SWT.RIGHT);
		toolbar.setLayoutData(BorderLayout.NORTH);
	
		ToolItem quickAdd 			= UIElementFactory.createToolItem(toolbar, "Quick Add", 	"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/add.png");
		ToolItem autoAssign 		= UIElementFactory.createToolItem(toolbar, "Auto Assign", 	"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/view-restore.png");
		ToolItem seperator1 		= new ToolItem(toolbar, SWT.SEPARATOR);
		ToolItem scheduleButton 	= UIElementFactory.createToolItem(toolbar, "Schedule", 		"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/calendar.png");
		ToolItem taskButton 		= UIElementFactory.createToolItem(toolbar, "Task", 			"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/task.png");
		ToolItem seperator2 		= new ToolItem(toolbar, SWT.SEPARATOR);
		ToolItem previousWeek 		= UIElementFactory.createToolItem(toolbar, "Previous week",	"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/go-previous.png");
		ToolItem nextWeek 			= UIElementFactory.createToolItem(toolbar, "Next week", 	"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/go-next.png");
		ToolItem seperator3 		= new ToolItem(toolbar, SWT.SEPARATOR);
		ToolItem day 				= UIElementFactory.createToolItem(toolbar, "Day", 			"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/day.png");
		ToolItem week 				= UIElementFactory.createToolItem(toolbar, "Week", 			"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/week.png");
		ToolItem seperator4 		= new ToolItem(toolbar, SWT.SEPARATOR);
		ToolItem scList 			= UIElementFactory.createToolItem(toolbar, "Agenda", 		"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/agenda.png");
		ToolItem tdList 			= UIElementFactory.createToolItem(toolbar, "Todo List", 	"/home/ahmed/Projects/organizer-app/Sources/Vers0/icons/todolist.png");
		
		autoAssign.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ArrayList<Event> events = BlankSpaceFinder.getEventsForBlank(schedule, currentView);
				
				for (Event event: events){
					schedule.addEvent(event);
				}
				
				calendarComposite.refresh();
			}
		});
		
		scheduleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Event newEvent = new Event();
				new EventEdit(shell, SWT.NONE, newEvent).open();
				schedule.addEvent(newEvent);
				calendarComposite.refresh();
		}
		});
		
		previousWeek.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentView.moveAmountOfDays(-7);
				calendarComposite.refresh();

			}
		});
		
		nextWeek.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentView.moveAmountOfDays(7);
				calendarComposite.refresh();
			}
		});
		
		return toolbar;
	}
	
	private Composite createSideBar(SashForm sashForm) {
		Composite sidebar = new Composite(sashForm, SWT.NONE);
		sidebar.setLayout(new BorderLayout());
		
		TaskList tasklist = new TaskList(sidebar, SWT.NONE, schedule);
		tasklist.setLayoutData(BorderLayout.CENTER);
		
		return sidebar;
	}	
}
