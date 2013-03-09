package org.cdahmedeh.orgapp.ui.main;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.ui.task.TaskListComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

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
		taskContainer.addTask(new Task("Test"));
		taskContainer.addTask(new Task("Test"));
		
		TaskListComposite taskListComposite = new TaskListComposite(shell, SWT.NONE, taskContainer);
	}
}