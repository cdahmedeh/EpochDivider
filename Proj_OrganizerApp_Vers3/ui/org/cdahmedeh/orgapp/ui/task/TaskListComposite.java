package org.cdahmedeh.orgapp.ui.task;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.ui.helpers.ComponentFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;

public class TaskListComposite extends Composite {
	@Override protected void checkSubclass() {}
	
	private TaskContainer taskContainer = null;
	private Tree treeTasksList;
	
	public TaskListComposite(Composite parent, int style, TaskContainer taskContainer) {
		super(parent, style);
		
		this.taskContainer = taskContainer;
		
		this.setLayout(new BorderLayout());
		
		makeTaskTree();
		treeTasksList.setLayoutData(BorderLayout.CENTER);
		
		fillTaskTree();
		
		ToolBar bottomBar = makeBottomBar();
		bottomBar.setLayoutData(BorderLayout.SOUTH);
	}

	private void makeTaskTree() {
		treeTasksList = new Tree(this, SWT.CHECK);
		treeTasksList.setHeaderVisible(true);
		
		TreeColumn clmTitle = ComponentFactory.generateTreeColumn(treeTasksList, "Title", 300);
		TreeColumn clmDuration = ComponentFactory.generateTreeColumn(treeTasksList, "Duration", 100);
		TreeColumn clmDueDate = ComponentFactory.generateTreeColumn(treeTasksList, "Due Date", 100);
	}
	
	private void fillTaskTree() {
		for (Task task: taskContainer.getAllTasks()){
			TreeItem itmTask = new TreeItem(treeTasksList, SWT.NONE);
			itmTask.setText(new String[]{
					task.getTitle(), 
					task.getDurationToComplete().getStandardHours() + " hr",
					task.getDueDate() == null ? "" : task.getDueDate().toString()
					});
		}
	}
	
	private ToolBar makeBottomBar() {
		ToolBar bottomBar = new ToolBar(this, SWT.NONE);
		
		ToolItem buttonAddTask = new ToolItem(bottomBar, SWT.NONE);
		buttonAddTask.setText("Add");
		return bottomBar;
	}
}