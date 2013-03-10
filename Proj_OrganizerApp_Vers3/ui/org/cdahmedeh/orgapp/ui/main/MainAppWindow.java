package org.cdahmedeh.orgapp.ui.main;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.cdahmedeh.orgapp.ui.calendar.CalendarComposite;
import org.cdahmedeh.orgapp.ui.task.TaskListComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

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
		shell.setText("SWT Application");

		shell.setLayout(new FillLayout());
		
		TaskContainer taskContainer = new TaskContainer();
		Task task = new Task("Test");
		task.assignToTimeBlock(new TimeBlock());
		task.assignToTimeBlock(new TimeBlock(new LocalDate().toDateTime(new LocalTime(14,30,00))));
		taskContainer.addTask(task);
		taskContainer.addTask(new Task("Test"));
		
		SashForm sashForm = new SashForm(shell, SWT.SMOOTH | SWT.VERTICAL);
		
		CalendarComposite calendarComposite = new CalendarComposite(sashForm, SWT.BORDER, taskContainer);
		TaskListComposite taskListComposite = new TaskListComposite(sashForm, SWT.BORDER, taskContainer);
	}
}