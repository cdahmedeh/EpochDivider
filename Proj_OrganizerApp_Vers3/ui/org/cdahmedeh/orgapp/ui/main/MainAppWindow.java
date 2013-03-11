package org.cdahmedeh.orgapp.ui.main;

import org.cdahmedeh.orgapp.types.category.Category;
import org.cdahmedeh.orgapp.types.category.CategoryContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.cdahmedeh.orgapp.ui.calendar.CalendarComposite;
import org.cdahmedeh.orgapp.ui.category.CategoryListComposite;
import org.cdahmedeh.orgapp.ui.notify.TasksModifiedNotification;
import org.cdahmedeh.orgapp.ui.task.TaskListComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.google.common.eventbus.EventBus;

public class MainAppWindow {
	protected Shell shell;

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
		
		CategoryContainer categoryContainer = new CategoryContainer();
		categoryContainer.addCategory(new Category("Essentials"));
		categoryContainer.addCategory(new Category("University"));
		
		EventBus eventBus = new EventBus();
		
		TaskContainer taskContainer = new TaskContainer();
		Task task = new Task("Test");
//		task.assignToTimeBlock(new TimeBlock());
		task.assignToTimeBlock(new TimeBlock(new DateTime(2013, 03, 12, 12, 00), new DateTime(2013, 03, 13, 12, 00)));
		taskContainer.addTask(task);
		taskContainer.addTask(new Task("Test"));
		
		SashForm fullSashForm = new SashForm(shell, SWT.SMOOTH | SWT.HORIZONTAL);
		
		CategoryListComposite taskListComposite2 = new CategoryListComposite(fullSashForm, SWT.BORDER, categoryContainer);
		
		SashForm rightSashForm = new SashForm(fullSashForm, SWT.SMOOTH | SWT.VERTICAL);

		fullSashForm.setWeights(new int[]{2,7});
		
		CalendarComposite calendarComposite = new CalendarComposite(rightSashForm, SWT.BORDER, taskContainer);
		TaskListComposite taskListComposite = new TaskListComposite(rightSashForm, SWT.BORDER, taskContainer);
		
		taskListComposite.setEventBus(eventBus);
		
		eventBus.post(new TasksModifiedNotification());
	}
}