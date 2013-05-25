package org.cdahmedeh.orgapp.ui;
import org.cdahmedeh.orgapp.calendar.View;
import org.cdahmedeh.orgapp.context.AllContextsContext;
import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.context.NoContextContext;
import org.cdahmedeh.orgapp.task.Recurrence;
import org.cdahmedeh.orgapp.task.RecurrenceFrequency;
import org.cdahmedeh.orgapp.task.Task;
import org.cdahmedeh.orgapp.task.TaskContainer;
import org.cdahmedeh.orgapp.tools.ObjectWriterSaver;
import org.cdahmedeh.orgapp.ui.calendar.CalendarComposite;
import org.cdahmedeh.orgapp.ui.context.ContextList;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.cdahmedeh.orgapp.ui.task.TaskList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.eventbus.EventBus;

import swing2swt.layout.BorderLayout;

public class MainWindow {

	protected Shell shell;

	static long time = System.currentTimeMillis();
	
	public static void main(String[] args) {
		System.out.println((System.currentTimeMillis()-time) + " main");
		
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		System.out.println((System.currentTimeMillis()-time) + " start open()"); time = System.currentTimeMillis();
		Display display = Display.getDefault();
		System.out.println((System.currentTimeMillis()-time) + " after get display and before create contents"); time = System.currentTimeMillis();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public int selectedViewNumber = 1;
	
	protected void createContents() {
		System.out.println((System.currentTimeMillis()-time) + " start createContents()"); time = System.currentTimeMillis();
				
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		
		shell.setLayout(new BorderLayout());
		
		System.out.println((System.currentTimeMillis()-time) + " after shell.setLayout(..)"); time = System.currentTimeMillis();
		
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------
		
		final Context context = new Context("Main", null);
		
		context.addSubContext(new AllContextsContext("All", context));
		context.addSubContext(new NoContextContext("Unsorted", context));
	
		Context essentials = new Context("Essentials", context);
		Context faith = new Context("Faith", context);
		Context university = new Context("University", context);
		Context transportation = new Context("Transportation", context);
		
		context.addSubContext(essentials);
		context.addSubContext(faith);
		context.addSubContext(university);
		context.addSubContext(transportation);
		
		final TaskContainer task = new TaskContainer();
		
		Task sleep = new Task("Sleep", task);
		sleep.setContext(essentials);
		sleep.setScheduled(new DateTime(2013, 01, 01, 23, 00));
		sleep.setDuration(new Duration(7*DateTimeConstants.MILLIS_PER_HOUR));
		
		Recurrence sleepRecurrence = new Recurrence();
		sleepRecurrence.setFreq(RecurrenceFrequency.DAILY);
		sleep.setRecurrence(sleepRecurrence);
		
		task.addTask(sleep);

		Task faithTask = new Task("Faith", task);
		faithTask.setContext(faith);
		faithTask.setScheduled(new DateTime(2013, 01, 01, 6, 00));
		faithTask.setDuration(new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE));
		
		Recurrence faithRecurrence = new Recurrence();
		faithRecurrence.setFreq(RecurrenceFrequency.DAILY);
		faithTask.setRecurrence(faithRecurrence);
		
		task.addTask(faithTask);
		
		Task catchingUp = new Task("Catching Up", task);
		catchingUp.setContext(essentials);
		catchingUp.setScheduled(new DateTime(2013, 01, 01, 6, 30));
		catchingUp.setDuration(new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE));
		
		Recurrence catchingUpRecurrence = new Recurrence();
		catchingUpRecurrence.setFreq(RecurrenceFrequency.DAILY);
		catchingUp.setRecurrence(catchingUpRecurrence);
		
		task.addTask(catchingUp);
		
		Recurrence universityRecurrence = new Recurrence();
		universityRecurrence.setFreq(RecurrenceFrequency.WEEKLY);
		universityRecurrence.setUntil(new LocalDate(2013, 4, 9));
		
		Task drive1 = new Task("Drive", task);
		drive1.setContext(transportation);
		drive1.setScheduled(new DateTime(2013, 01, 07, 9, 00));
		drive1.setDuration(new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE));
		drive1.setRecurrence(universityRecurrence);
		task.addTask(drive1);
		
		Task universityCourse1 = new Task("CSI2101 LEC", task);
		universityCourse1.setContext(university);
		universityCourse1.setScheduled(new DateTime(2013, 01, 07, 10, 00));
		universityCourse1.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse1.setRecurrence(universityRecurrence);
		task.addTask(universityCourse1);
		
		Task universityCourse2 = new Task("CSI2532 LAB", task);
		universityCourse2.setContext(university);
		universityCourse2.setScheduled(new DateTime(2013, 01, 07, 17, 00));
		universityCourse2.setDuration(new Duration(180*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse2.setRecurrence(universityRecurrence);
		task.addTask(universityCourse2);
		
		Task drive2 = new Task("Drive", task);
		drive2.setContext(transportation);
		drive2.setScheduled(new DateTime(2013, 01, 07, 19, 30));
		drive2.setDuration(new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE));
		drive2.setRecurrence(universityRecurrence);
		task.addTask(drive2);
		
		Task drive3 = new Task("Drive", task);
		drive3.setContext(transportation);
		drive3.setScheduled(new DateTime(2013, 01, 8, 10, 30));
		drive3.setDuration(new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE));
		drive3.setRecurrence(universityRecurrence);
		task.addTask(drive3);
		
		Task universityCourse3 = new Task("CSI2532 LEC", task);
		universityCourse3.setContext(university);
		universityCourse3.setScheduled(new DateTime(2013, 01, 8, 11, 30));
		universityCourse3.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse3.setRecurrence(universityRecurrence);
		task.addTask(universityCourse3);
		
		Task universityCourse4 = new Task("PHI2794 LEC", task);
		universityCourse4.setContext(university);
		universityCourse4.setScheduled(new DateTime(2013, 01, 8, 13, 00));
		universityCourse4.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse4.setRecurrence(universityRecurrence);
		task.addTask(universityCourse4);
		
		Task universityCourse5 = new Task("CSI2532 LEC", task);
		universityCourse5.setContext(university);
		universityCourse5.setScheduled(new DateTime(2013, 01, 8, 14, 30));
		universityCourse5.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse5.setRecurrence(universityRecurrence);
		task.addTask(universityCourse5);
		
		Task universityCourse6 = new Task("CSI2101 TUT", task);
		universityCourse6.setContext(university);
		universityCourse6.setScheduled(new DateTime(2013, 01, 8, 17, 30));
		universityCourse6.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse6.setRecurrence(universityRecurrence);
		task.addTask(universityCourse6);
		
		Task drive4 = new Task("Drive", task);
		drive4.setContext(transportation);
		drive4.setScheduled(new DateTime(2013, 01, 8, 19, 00));
		drive4.setDuration(new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE));
		drive4.setRecurrence(universityRecurrence);
		task.addTask(drive4);
		
		Task drive5 = new Task("Drive", task);
		drive5.setContext(transportation);
		drive5.setScheduled(new DateTime(2013, 01, 9, 7, 30));
		drive5.setDuration(new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE));
		drive5.setRecurrence(universityRecurrence);
		task.addTask(drive5);
		
		Task universityCourse7 = new Task("CSI2101 LEC", task);
		universityCourse7.setContext(university);
		universityCourse7.setScheduled(new DateTime(2013, 01, 9, 8, 30));
		universityCourse7.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse7.setRecurrence(universityRecurrence);
		task.addTask(universityCourse7);
		
		Task drive6 = new Task("Drive", task);
		drive6.setContext(transportation);
		drive6.setScheduled(new DateTime(2013, 01, 9, 10, 00));
		drive6.setDuration(new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE));
		drive6.setRecurrence(universityRecurrence);
		task.addTask(drive6);
		
		Task drive7 = new Task("Drive", task);
		drive7.setContext(transportation);
		drive7.setScheduled(new DateTime(2013, 01, 10, 10, 30));
		drive7.setDuration(new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE));
		drive7.setRecurrence(universityRecurrence);
		task.addTask(drive7);
		
		Task universityCourse8 = new Task("CSI2532 LEC", task);
		universityCourse8.setContext(university);
		universityCourse8.setScheduled(new DateTime(2013, 01, 10, 11, 30));
		universityCourse8.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse8.setRecurrence(universityRecurrence);
		task.addTask(universityCourse8);
		
		Task universityCourse9 = new Task("CSI2520 TUT", task);
		universityCourse9.setContext(university);
		universityCourse9.setScheduled(new DateTime(2013, 01, 10, 14, 30));
		universityCourse9.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse9.setRecurrence(universityRecurrence);
		task.addTask(universityCourse9);
		
		Task universityCourse10 = new Task("CSI2520 LAB", task);
		universityCourse10.setContext(university);
		universityCourse10.setScheduled(new DateTime(2013, 01, 10, 16, 00));
		universityCourse10.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse10.setRecurrence(universityRecurrence);
		task.addTask(universityCourse10);
		
		Task drive8 = new Task("Drive", task);
		drive8.setContext(transportation);
		drive8.setScheduled(new DateTime(2013, 01, 10, 17, 30));
		drive8.setDuration(new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE));
		drive8.setRecurrence(universityRecurrence);
		task.addTask(drive8);
		
		Task drive9 = new Task("Drive", task);
		drive9.setContext(transportation);
		drive9.setScheduled(new DateTime(2013, 01, 11, 10, 30));
		drive9.setDuration(new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE));
		drive9.setRecurrence(universityRecurrence);
		task.addTask(drive9);
	
		Task fridayPrayer = new Task("Friday Prayer", task);
		fridayPrayer.setContext(faith);
		fridayPrayer.setScheduled(new DateTime(2013, 01, 11, 12, 00));
		fridayPrayer.setDuration(new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE));
		fridayPrayer.setRecurrence(universityRecurrence);
		task.addTask(fridayPrayer);
		
		Task universityCourse11 = new Task("PHI2794 LEC", task);
		universityCourse11.setContext(university);
		universityCourse11.setScheduled(new DateTime(2013, 01, 11, 13, 00));
		universityCourse11.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse11.setRecurrence(universityRecurrence);
		task.addTask(universityCourse11);
		
		Task universityCourse12 = new Task("CSI2520 LEC", task);
		universityCourse12.setContext(university);
		universityCourse12.setScheduled(new DateTime(2013, 01, 11, 16, 00));
		universityCourse12.setDuration(new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE));
		universityCourse12.setRecurrence(universityRecurrence);
		task.addTask(universityCourse12);
		
		Task drive10 = new Task("Drive", task);
		drive10.setContext(transportation);
		drive10.setScheduled(new DateTime(2013, 01, 11, 17, 30));
		drive10.setDuration(new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE));
		drive10.setRecurrence(universityRecurrence);
		task.addTask(drive10);
		
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------
		
		System.out.println((System.currentTimeMillis()-time) + " after creating some tasks and contexts"); time = System.currentTimeMillis();
		
//		final Task task = (Task)ObjectWriterSaver.readData("task.data");
//		final Context context = (Context)ObjectWriterSaver.readData("context.data");
		
//		Menu menu = makeMenu();

		EventBus eventBus = new EventBus();
		
		System.out.println((System.currentTimeMillis()-time) + " after event bus init"); time = System.currentTimeMillis();
		
		SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL | SWT.SMOOTH);

		System.out.println((System.currentTimeMillis()-time) + " init sashform"); time = System.currentTimeMillis();
		
		ContextList contextList = new ContextList(sashForm, SWT.BORDER, context, task);
		contextList.setEventBus(eventBus);
		
		System.out.println((System.currentTimeMillis()-time) + " created context list"); time = System.currentTimeMillis();
		
		Composite composite = new Composite(sashForm, SWT.BORDER);
		composite.setLayout(new BorderLayout());
		
		System.out.println((System.currentTimeMillis()-time) + " created first composite"); time = System.currentTimeMillis();
		
		if (selectedViewNumber == 0){
			TaskList taskList = new TaskList(composite, SWT.NONE, task, context);
			taskList.setLayoutData(BorderLayout.CENTER);
		} else if (selectedViewNumber == 1){
			SashForm calendarSash = new SashForm(composite, SWT.VERTICAL | SWT.SMOOTH);
			
			View view = new View(new LocalDate(2013, 2, 17+7), new LocalDate(2013, 3, 2), new LocalTime(11,0,0), new LocalTime(23,59,59));
			
			Composite insideComposte = new Composite(calendarSash, SWT.NONE);
			insideComposte.setLayout(new BorderLayout());

			System.out.println((System.currentTimeMillis()-time) + " empty sash"); time = System.currentTimeMillis();
			
			CalendarComposite compositeBlank = new CalendarComposite(insideComposte, SWT.NONE, Display.getDefault(), view, task, context);
			compositeBlank.setLayoutData(BorderLayout.CENTER);
			compositeBlank.setEventBus(eventBus);

			System.out.println((System.currentTimeMillis()-time) + " render calendar"); time = System.currentTimeMillis();
			
			ToolBar calendarToolbar = createCalToolBar(insideComposte, view, compositeBlank);
			calendarToolbar.setLayoutData(BorderLayout.NORTH);
						
			System.out.println((System.currentTimeMillis()-time) + " make toolbar"); time = System.currentTimeMillis();
			
			TaskList taskList = new TaskList(calendarSash, SWT.NONE, task, context);
			taskList.setEventBus(eventBus);
			taskList.setLayoutData(BorderLayout.CENTER);
				
			calendarSash.setWeights(new int[]{7,3});
		}
		
		System.out.println((System.currentTimeMillis()-time) + " finish stuff inside"); time = System.currentTimeMillis();
		
		ToolBar toolbar = createToolBar(composite);
		toolbar.setLayoutData(BorderLayout.NORTH);
				
		sashForm.setWeights(new int[]{2,7});
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);		
		
		MenuItem fileMenu = new MenuItem(menu, SWT.NONE);
		fileMenu.setText("Save");
		
		fileMenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ObjectWriterSaver.writeData("task.data", task);
				ObjectWriterSaver.writeData("context.data", context);
			}
		});
		
		System.out.println((System.currentTimeMillis()-time) + " menu and file menu"); time = System.currentTimeMillis();
	}

	private Menu makeMenu() {
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);		
		
		MenuItem fileMenu = new MenuItem(menu, SWT.NONE);
		fileMenu.setText("Save");
		
		fileMenu.addSelectionListener(new SelectionAdapter() {
			
		});
	
		return menu;
	}

	private ToolBar createCalToolBar(Composite composite, final View view, final CalendarComposite cal) {
		ToolBar toolBar = new ToolBar(composite, SWT.RIGHT); 

		ToolItem prev = createToolItem(toolBar, "Previous Week", Icons.PREV, SWT.NONE);
		prev.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.moveAmountOfDays(-DateTimeConstants.DAYS_PER_WEEK);
				cal.requeryTasks();
				cal.redrawAll();
			}
		});
		
		ToolItem next = createToolItem(toolBar, "Next Week", Icons.NEXT, SWT.NONE);
		next.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.moveAmountOfDays(DateTimeConstants.DAYS_PER_WEEK);
				cal.requeryTasks();
				cal.redrawAll();
			}
		});
		
		return toolBar;
	}
	
	private ToolBar createToolBar(Composite composite) {
		ToolBar toolBar = new ToolBar(composite, SWT.RIGHT); 
		
		ToolItem taskSelectButton = createToolItem(toolBar, "Tasks", Icons.TASKS, SWT.RADIO);
		ToolItem planSelectButton = createToolItem(toolBar, "Plan", Icons.PLAN, SWT.RADIO);
		ToolItem statsSelectButton = createToolItem(toolBar, "Statistics", Icons.STATS, SWT.RADIO);
		
		return toolBar;
	}

	public ToolItem createToolItem(ToolBar toolbar, String label, String image, int style){
		ToolItem toolItem = new ToolItem(toolbar, style);
		toolItem.setText(label);
		toolItem.setImage(SWTResourceManager.getImage(MainWindow.class, image));
		return toolItem;
	}
	
}
