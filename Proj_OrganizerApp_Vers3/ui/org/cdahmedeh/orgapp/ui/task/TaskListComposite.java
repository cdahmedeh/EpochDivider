package org.cdahmedeh.orgapp.ui.task;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.ui.category.CategoryListComposite;
import org.cdahmedeh.orgapp.ui.helpers.ComponentFactory;
import org.cdahmedeh.orgapp.ui.helpers.ComponentModifier;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.cdahmedeh.orgapp.ui.notify.TasksModifiedNotification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class TaskListComposite extends Composite {
	@Override protected void checkSubclass() {}

	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new EventRecorder());
	}
	class EventRecorder{
		@Subscribe public void tasksModified(TasksModifiedNotification notify){
			treeTasksList.clearAll(true);
			fillTaskTree();
		}
	}
	
	private TaskContainer taskContainer = null;
	private Tree treeTasksList;
	
	public TaskListComposite(Composite parent, int style, TaskContainer taskContainer) {
		super(parent, style);
		
		this.taskContainer = taskContainer;
		
		this.setLayout(new BorderLayout());
		
		makeTaskTree();
		treeTasksList.setLayoutData(BorderLayout.CENTER);

		Composite bottomBar = makeBottomBar();
		bottomBar.setLayoutData(BorderLayout.SOUTH);
	}

	private void makeTaskTree() {
		treeTasksList = new Tree(this, SWT.CHECK);
		treeTasksList.setHeaderVisible(true);
		
		TreeColumn clmTitle = ComponentFactory.generateTreeColumn(treeTasksList, "Task", 300);
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
	
	private Composite makeBottomBar() {
		Composite bottomBarComposite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		ComponentModifier.removeSpacingAndMargins(gridLayout);
		bottomBarComposite.setLayout(gridLayout);
		
		Text text = new Text(bottomBarComposite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ToolBar bottomBar = new ToolBar(bottomBarComposite, SWT.FLAT | SWT.RIGHT);
		
		ToolItem buttonAddTask = new ToolItem(bottomBar, SWT.NONE);
		buttonAddTask.setText("Add");
		buttonAddTask.setImage(SWTResourceManager.getImage(CategoryListComposite.class, Icons.ADD));
		return bottomBarComposite;
	}
}