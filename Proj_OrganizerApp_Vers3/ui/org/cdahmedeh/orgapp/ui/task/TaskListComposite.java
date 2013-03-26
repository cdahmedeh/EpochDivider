package org.cdahmedeh.orgapp.ui.task;

import java.util.HashMap;

import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.category.NoContext;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.ui.category.CategoryListComposite;
import org.cdahmedeh.orgapp.ui.helpers.ComponentFactory;
import org.cdahmedeh.orgapp.ui.helpers.ComponentModifier;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.cdahmedeh.orgapp.ui.notify.CategoryChangedNotification;
import org.cdahmedeh.orgapp.ui.notify.TaskAddWithDialogRequest;
import org.cdahmedeh.orgapp.ui.notify.TaskEditRequest;
import org.cdahmedeh.orgapp.ui.notify.TaskQuickAddNotification;
import org.cdahmedeh.orgapp.ui.notify.TasksModifiedNotification;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;
import org.joda.time.DateTime;

import swing2swt.layout.BorderLayout;

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
			treeTasksList.removeAll();
			fillTaskTree();
		}
		@Subscribe public void contextChanged(CategoryChangedNotification notify){
			treeTasksList.removeAll();
			category = notify.getCategory();
			fillTaskTree();
		}
	}
	
	private TaskContainer taskContainer = null;
	private Context category = new NoContext();
	
	private Tree treeTasksList;
	
	private boolean showEvents = false;
		
	private HashMap<TreeItem, Task> mapTreeItemTask = new HashMap<>();
	
	public TaskListComposite(Composite parent, int style, TaskContainer taskContainer, boolean showEvents) {
		super(parent, style);
		
		this.taskContainer = taskContainer;
		
		this.showEvents = showEvents;
		
		this.setLayout(new BorderLayout());
		
		makeTaskTree();
		treeTasksList.setLayoutData(BorderLayout.CENTER);

		Composite bottomBar = makeBottomBar();
		bottomBar.setLayoutData(BorderLayout.SOUTH);
		
		setupDragFrom();
	}

	private void makeTaskTree() {
		treeTasksList = new Tree(this, SWT.CHECK);
		treeTasksList.setHeaderVisible(true);
		
		TreeColumn clmTitle = ComponentFactory.generateTreeColumn(treeTasksList, "Task", 290);
		TreeColumn clmDueDate = ComponentFactory.generateTreeColumn(treeTasksList, "Due Date", 100);
		TreeColumn clmTotalPassed = ComponentFactory.generateTreeColumn(treeTasksList, "Progress", 100);
		TreeColumn clmNextScheduled = ComponentFactory.generateTreeColumn(treeTasksList, "Next", 100);
		
		treeTasksList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				eventBus.post(new TaskEditRequest(mapTreeItemTask.get(treeTasksList.getSelection()[0])));
			}
		});
	}
	
	private void fillTaskTree() {
		mapTreeItemTask.clear();
		for (Task task: taskContainer.getTasksWithContext(category).getTasksWithEvents(showEvents).getAllTasks()){
			TreeItem itmTask = new TreeItem(treeTasksList, SWT.NONE);
			itmTask.setText(new String[]{
					task.getTitle() + 
					" (" + task.getContext().getName() + ")",
					task.getDueDate() == null ? "" : task.getDueDate().toString(),
					task.getDurationPassed(DateTime.now()).getStandardHours() + " | " +
					task.getDurationScheduled(DateTime.now()).getStandardHours() + " | " + //TODO: should be end of view
					task.getEstimate().getStandardHours(),
					task.getFirstTimeBlock() == null ? "" : task.getFirstTimeBlock().toString()
					});
			if (task.isEvent()){
				itmTask.setBackground(SWTResourceManager.getColor(TaskListConstants.taskImmutableDefaultBackgroundColor));
				itmTask.setForeground(SWTResourceManager.getColor(TaskListConstants.taskImmutableDefaulForegroundColor));
			}
			mapTreeItemTask.put(itmTask, task);
		}
	}
	
	private Composite makeBottomBar() {
		Composite bottomBarComposite = new Composite(this, SWT.NONE);
		GridLayout gridLayout = new GridLayout(2, false);
		ComponentModifier.removeSpacingAndMargins(gridLayout);
		bottomBarComposite.setLayout(gridLayout);
		
		final Text text = new Text(bottomBarComposite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ToolBar bottomBar = new ToolBar(bottomBarComposite, SWT.FLAT | SWT.RIGHT);
		
		ToolItem buttonAddTask = new ToolItem(bottomBar, SWT.NONE);
		buttonAddTask.setText("Add");
		buttonAddTask.setImage(SWTResourceManager.getImage(CategoryListComposite.class, Icons.ADD));
		buttonAddTask.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (text.getText().equals("")) {
					eventBus.post(new TaskAddWithDialogRequest());
				} else {
					eventBus.post(new TaskQuickAddNotification(text.getText()));
				}
				text.setText("");
			}
		});
		
		if (showEvents == false) {
			final ToolItem buttonCompletedTasks = new ToolItem(bottomBar, SWT.CHECK);
			buttonCompletedTasks.setText("Show Completed");
			buttonCompletedTasks.setImage(SWTResourceManager.getImage(CategoryListComposite.class, Icons.COMPLETED));
		} else {
			final ToolItem buttonCompletedTasks = new ToolItem(bottomBar, SWT.CHECK);
			buttonCompletedTasks.setText("Show Passed");
			buttonCompletedTasks.setImage(SWTResourceManager.getImage(CategoryListComposite.class, Icons.IMMUTABLE));
		}
		
		return bottomBarComposite;
	}
	
	private void setupDragFrom(){
		final DragSource source = new DragSource(treeTasksList, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
		source.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		source.addDragListener(new DragSourceAdapter(){
			@Override
			public void dragStart(DragSourceEvent event) {
				event.doit = true;
			}
			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = String.valueOf(mapTreeItemTask.get(treeTasksList.getSelection()[0]).getId());
			}
		});
	}
}