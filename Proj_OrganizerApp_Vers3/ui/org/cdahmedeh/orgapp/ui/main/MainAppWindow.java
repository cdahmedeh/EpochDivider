package org.cdahmedeh.orgapp.ui.main;

import org.cdahmedeh.orgapp.types.category.AllCategories;
import org.cdahmedeh.orgapp.types.category.Category;
import org.cdahmedeh.orgapp.types.category.CategoryContainer;
import org.cdahmedeh.orgapp.types.category.NoCategory;
import org.cdahmedeh.orgapp.types.task.Mutability;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.cdahmedeh.orgapp.ui.calendar.CalendarComposite;
import org.cdahmedeh.orgapp.ui.category.CategoryListComposite;
import org.cdahmedeh.orgapp.ui.notify.ChangeTaskToCategoryRequest;
import org.cdahmedeh.orgapp.ui.notify.TaskAddWithDialogRequest;
import org.cdahmedeh.orgapp.ui.notify.TaskEditRequest;
import org.cdahmedeh.orgapp.ui.notify.TaskQuickAddNotification;
import org.cdahmedeh.orgapp.ui.notify.TasksModifiedNotification;
import org.cdahmedeh.orgapp.ui.task.TaskEditorDialog;
import org.cdahmedeh.orgapp.ui.task.TaskListComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class MainAppWindow {
	protected Shell shell;
	private EventBus eventBus;
	private TaskContainer taskContainer;
	private CategoryContainer categoryContainer;

	class EventRecorder{
		@Subscribe public void tasksModified(TaskQuickAddNotification notify){
			taskContainer.addTask(new Task(notify.getName()));
			eventBus.post(new TasksModifiedNotification());
		}
		@Subscribe public void taskModifyCategory(ChangeTaskToCategoryRequest notify){
			taskContainer.getTaskFromId(notify.getId()).setCategory(notify.getCategory());
			eventBus.post(new TasksModifiedNotification());
		}
		@Subscribe public void taskAdd(TaskAddWithDialogRequest notify){
			Task task = new Task("");
			TaskEditorDialog dialog = new TaskEditorDialog(shell, SWT.NONE, task, categoryContainer);
			dialog.open();
			taskContainer.addTask(task);
			eventBus.post(new TasksModifiedNotification());
		}
		@Subscribe public void taskEdit(TaskEditRequest notify){
			TaskEditorDialog dialog = new TaskEditorDialog(shell, SWT.NONE, notify.getTask(), categoryContainer);
			dialog.open();
			eventBus.post(new TasksModifiedNotification());
		}
	}
	
	public static void main(String[] args) {
		try {
			MainAppWindow window = new MainAppWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("Epoch Divider");

		shell.setLayout(new FillLayout());
		
		categoryContainer = new CategoryContainer();
		
		
		eventBus = new EventBus();
		
		taskContainer = new TaskContainer();
//		Task task = new Task("Test");
////		task.assignToTimeBlock(new TimeBlock());
//		task.assignToTimeBlock(new TimeBlock(new DateTime(2013, 03, 12, 12, 00), new DateTime(2013, 03, 12, 14, 00)));
//		taskContainer.addTask(task);
//		taskContainer.addTask(new Task("Test"));
		
		fill(categoryContainer, taskContainer);
		
		SashForm fullSashForm = new SashForm(shell, SWT.SMOOTH | SWT.HORIZONTAL);
		
		SashForm leftSashForm = new SashForm(fullSashForm, SWT.SMOOTH | SWT.VERTICAL);
		
		org.eclipse.swt.widgets.DateTime dateTimeWidgetThing = new org.eclipse.swt.widgets.DateTime(leftSashForm, SWT.CALENDAR);
		
		CategoryListComposite taskListComposite2 = new CategoryListComposite(leftSashForm, SWT.BORDER, categoryContainer);
		
		SashForm rightSashForm = new SashForm(fullSashForm, SWT.SMOOTH | SWT.VERTICAL);

		fullSashForm.setWeights(new int[]{2,7});
		
		CalendarComposite calendarComposite = new CalendarComposite(rightSashForm, SWT.BORDER, taskContainer);
		TaskListComposite taskListComposite = new TaskListComposite(rightSashForm, SWT.BORDER, taskContainer);
		
		calendarComposite.setEventBus(eventBus);
		taskListComposite.setEventBus(eventBus);
		taskListComposite2.setEventBus(eventBus);
	
		leftSashForm.setWeights(new int[]{4,13});
		rightSashForm.setWeights(new int[]{7,3});
		
		eventBus.post(new TasksModifiedNotification());
		
		this.eventBus.register(new EventRecorder());
	}
	
	public void fill(CategoryContainer context, TaskContainer task){
		context.addCategory(new AllCategories());
		context.addCategory(new NoCategory());
	
		Category essentials = new Category("Essentials");
		Category faith = new Category("Faith");
		Category university = new Category("University");
		Category transportation = new Category("Transportation");
		
		context.addCategory(essentials);
		context.addCategory(faith);
		context.addCategory(university);
		context.addCategory(transportation);
		context.addCategory(new Category("Projects"));
		context.addCategory(new Category("Misc"));
		context.addCategory(new Category("Gaming"));
		
		for (int i=0; i<8; i++){
		Task sleep = new Task("Sleep");
		sleep.setCategory(essentials);
		sleep.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 9+i, 23, 00),
		new Duration(7*DateTimeConstants.MILLIS_PER_HOUR)));
		
		task.addTask(sleep);

		Task faithTask = new Task("Faith");
		faithTask.setCategory(faith);
		faithTask.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 9+i, 6, 00),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		
		task.addTask(faithTask);
		
		Task catchingUp = new Task("Catching Up");
		catchingUp.setCategory(essentials);
		catchingUp.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 9+i, 6, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		
		task.addTask(catchingUp);
		}
		
		Task drive1 = new Task("Drive");
		drive1.setCategory(transportation);
		drive1.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 11, 9, 00),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive1);
//	
		for (int i = 0; i< 1; i++){
//			Task universityCourse1 = new Task("Some Thing");
//			universityCourse1.setCategory(university);
//			universityCourse1.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 9, 0, 00).plusMinutes(30*i),
//			new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
//			universityCourse1.setMutability(Mutability.MUTABLE);
//			task.addTask(universityCourse1);
			
		Task universityCourse1 = new Task("CSI2101 LEC");
		universityCourse1.setCategory(university);
		universityCourse1.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 11, 10, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		universityCourse1.setMutability(Mutability.IMMUTABLE);
		task.addTask(universityCourse1);

		Task universityCourse2 = new Task("CSI2532 LAB");
		universityCourse2.setCategory(university);
		universityCourse2.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 11, 17, 00),
		new Duration(180*DateTimeConstants.MILLIS_PER_MINUTE)));
		universityCourse2.setMutability(Mutability.IMMUTABLE);
		task.addTask(universityCourse2);
		}
//		
		Task drive2 = new Task("Drive");
		drive2.setCategory(transportation);
		drive2.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 11, 19, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive2);
		
		Task drive3 = new Task("Drive");
		drive3.setCategory(transportation);
		drive3.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 12, 10, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive3);
		
		Task universityCourse3 = new Task("CSI2532 LEC");
		universityCourse3.setCategory(university);
		universityCourse3.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 12, 11, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse3);
		
		Task universityCourse4 = new Task("PHI2794 LEC");
		universityCourse4.setCategory(university);
		universityCourse4.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 12, 13, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse4);
		
		Task universityCourse5 = new Task("CSI2532 LEC");
		universityCourse5.setCategory(university);
		universityCourse5.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 12, 14, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse5);
		
		Task universityCourse6 = new Task("CSI2101 TUT");
		universityCourse6.setCategory(university);
		universityCourse6.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 12, 17, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse6);
		
		Task drive4 = new Task("Drive");
		drive4.setCategory(transportation);
		drive4.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 12, 19, 00),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive4);
		
		Task drive5 = new Task("Drive");
		drive5.setCategory(transportation);
		drive5.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 13, 7, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive5);
		
		Task universityCourse7 = new Task("CSI2101 LEC");
		universityCourse7.setCategory(university);
		universityCourse7.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 13, 8, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse7);
		
		Task drive6 = new Task("Drive");
		drive6.setCategory(transportation);
		drive6.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 13, 10, 00),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive6);
		
		Task drive7 = new Task("Drive");
		drive7.setCategory(transportation);
		drive7.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 14, 10, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive7);
		
		Task universityCourse8 = new Task("CSI2532 LEC");
		universityCourse8.setCategory(university);
		universityCourse8.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 14, 11, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse8);
		
		Task universityCourse9 = new Task("CSI2520 TUT");
		universityCourse9.setCategory(university);
		universityCourse9.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 14, 14, 30),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse9);
		
		Task universityCourse10 = new Task("CSI2520 LAB");
		universityCourse10.setCategory(university);
		universityCourse10.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 14, 16, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse10);
		
		Task drive8 = new Task("Drive");
		drive8.setCategory(transportation);
		drive8.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 14, 17, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive8);
		
		Task drive9 = new Task("Drive");
		drive9.setCategory(transportation);
		drive9.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 15, 10, 30),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive9);
	
		Task fridayPrayer = new Task("Friday Prayer");
		fridayPrayer.setCategory(faith);
		fridayPrayer.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 15, 12, 00),
		new Duration(60*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(fridayPrayer);
		
		Task universityCourse11 = new Task("PHI2794 LEC");
		universityCourse11.setCategory(university);
		universityCourse11.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 15, 13, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse11);
		
		Task universityCourse12 = new Task("CSI2520 LEC");
		universityCourse12.setCategory(university);
		universityCourse12.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 15, 16, 00),
		new Duration(90*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(universityCourse12);
		
		Task drive10 = new Task("Drive");
		drive10.setCategory(transportation);
		drive10.assignToTimeBlock(new TimeBlock(new DateTime(2013, 3, 15, 17, 30),
		new Duration(30*DateTimeConstants.MILLIS_PER_MINUTE)));
		task.addTask(drive10);
		
		for (Task task3: taskContainer.getAllTasks()){
			task3.setMutability(Mutability.IMMUTABLE);
		}
	}
}