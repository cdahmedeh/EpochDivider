package org.cdahmedeh.orgapp.ui;

import java.util.HashMap;

import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.Schedule;
import org.cdahmedeh.orgapp.schedule.Task;
import org.cdahmedeh.orgapp.ui.editor.TaskEdit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import swing2swt.layout.BorderLayout;

public class TaskList extends Composite {

	private HashMap<TableItem, Task> mapItemTask = new HashMap<>();
	
	public TaskList(final Composite parent, int style, final Schedule schedule) {
		super(parent, style);
		
		this.setLayout(new BorderLayout());
		this.setLayoutData(BorderLayout.CENTER);
		
		final Table taskTable = new Table(this, SWT.CHECK);
		taskTable.setHeaderVisible(true);
		
		TableColumn taskNameColumn = new TableColumn(taskTable, SWT.NONE);
		taskNameColumn.setWidth(100);
		
		fillTableWithTasks(schedule, taskTable);
		
		Button addTaskButton = new Button(this, SWT.NONE);
		addTaskButton.setLayoutData(BorderLayout.SOUTH);
		addTaskButton.setText("Add Task");
		
		addTaskButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Task newTask = new Task();
				newTask.setTaskName("");
//				newTask.setDescription("");
				TaskEdit te = new TaskEdit(parent.getShell(), SWT.NONE, newTask);
				te.open();
				schedule.addTask(newTask);
				fillTableWithTasks(schedule, taskTable);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		taskTable.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				TableItem selection = taskTable.getSelection()[0];
				Task selectedTask = mapItemTask.get(selection);
				
				System.out.println(selectedTask);
				
				TaskEdit te = new TaskEdit(parent.getShell(), SWT.NONE, selectedTask);
				te.open();
				fillTableWithTasks(schedule, taskTable);
			}
		});
	}

	private void fillTableWithTasks(final Schedule schedule, Table taskTable) {
		taskTable.removeAll();
		
		for (Task task: schedule.getTasks()){
			TableItem row = new TableItem(taskTable, SWT.NONE);
			mapItemTask.put(row, task);
			row.setText(task.getTaskName());
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
