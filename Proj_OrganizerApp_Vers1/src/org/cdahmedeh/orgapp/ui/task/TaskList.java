package org.cdahmedeh.orgapp.ui.task;
import java.util.Comparator;
import java.util.HashMap;

import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.task.DueDateComparator;
import org.cdahmedeh.orgapp.task.Task;
import org.cdahmedeh.orgapp.task.TaskContainer;
import org.cdahmedeh.orgapp.task.TaskScheduledDateComparator;
import org.cdahmedeh.orgapp.task.TaskNameComparator;
import org.cdahmedeh.orgapp.ui.UIConstants;
import org.cdahmedeh.orgapp.ui.helpers.ElementModifier;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.cdahmedeh.orgapp.ui.notification.ContextChangedEvent;
import org.cdahmedeh.orgapp.ui.notification.TaskChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.joda.time.LocalDate;

public class TaskList extends Composite {
	@Override protected void checkSubclass() {}
	
	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		eventBus.register(new EventRecorder());
	}
	class EventRecorder{
		@Subscribe public void changeContext(ContextChangedEvent event){
			setContextInView(event.getContext());
		}
		@Subscribe public void refreshTasks(TaskChangedEvent event){
			refreshTaskTree(rootTask);
		}
	}
	
	private HashMap<TreeItem, Task> mapTreeItemTask = new HashMap<>();
	
	private Tree taskTree = null;
	private TaskContainer rootTask = null;

	private Context selectedContext;
	private Comparator<? super Task> sortComporator = null;
	
	public TaskList(Composite parent, int style, final TaskContainer rootTask, final Context rootContext) {
		super(parent, style);
		
		this.rootTask = rootTask;
		
		this.setLayout(new BorderLayout());
		
		taskTree = makeTaskTree();
		taskTree.setLayoutData(BorderLayout.CENTER);
		
		taskTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (e.button == 2){
					Task selectedTask = mapTreeItemTask.get(taskTree.getSelection()[0]);
					selectedTask.split(5);
					eventBus.post(new TaskChangedEvent());					
				}
				else if (e.button == 3){
					Task selectedTask = mapTreeItemTask.get(taskTree.getSelection()[0]);
					rootTask.removeTask(selectedTask);
					eventBus.post(new TaskChangedEvent());
				}
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Task selectedTask = mapTreeItemTask.get(taskTree.getSelection()[0]);
				if (selectedTask != null) {
					editTaskDialog(rootContext, selectedTask);
				}
				eventBus.post(new TaskChangedEvent());
			}
		});
		
		taskTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem selectedTreeItem = taskTree.getSelection()[0];
				Task selectedTask = mapTreeItemTask.get(selectedTreeItem);
				selectedTask.setCompleted(selectedTreeItem.getChecked());
			}
		});
		
		refreshTaskTree(rootTask);
		
		Composite buttonBar = makeButtonBar(this, rootContext);
		buttonBar.setLayoutData(BorderLayout.SOUTH);
	}

	private Tree makeTaskTree() {
		final Tree taskTreeN = new Tree(this, SWT.CHECK);
		taskTreeN.setHeaderVisible(true);
		
		final DragSource source = new DragSource(taskTreeN, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
		source.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		source.addDragListener(new DragSourceAdapter(){
			@Override
			public void dragStart(DragSourceEvent event) {
				event.doit = true;
			}
			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = String.valueOf(mapTreeItemTask.get(taskTree.getSelection()[0]).getId());
			}
		});
		
		final TreeColumn nameColumn = new TreeColumn(taskTreeN, SWT.NONE);
		nameColumn.setWidth(175);
		nameColumn.setMoveable(true);
		nameColumn.setText("Task");
		nameColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				taskTreeN.setSortColumn(nameColumn);
				taskTreeN.setSortDirection(taskTreeN.getSortDirection() == SWT.DOWN ? SWT.UP : SWT.DOWN);
				sortComporator = new TaskNameComparator(taskTreeN.getSortDirection() == SWT.DOWN ? true : false);
				eventBus.post(new TaskChangedEvent());
			}
		});
		
		TreeColumn contextColumn = new TreeColumn(taskTreeN, SWT.NONE);
		contextColumn.setWidth(140);
		contextColumn.setMoveable(true);
		contextColumn.setText("Context");
		
		final TreeColumn dueColumn = new TreeColumn(taskTreeN, SWT.NONE);
		dueColumn.setWidth(120);
		dueColumn.setMoveable(true);
		dueColumn.setText("Due");
		dueColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				taskTreeN.setSortColumn(dueColumn);
				taskTreeN.setSortDirection(taskTreeN.getSortDirection() == SWT.DOWN ? SWT.UP : SWT.DOWN);
				sortComporator = new DueDateComparator(taskTreeN.getSortDirection() == SWT.DOWN ? true : false);
				eventBus.post(new TaskChangedEvent());
			}
		});
		
		final TreeColumn scheduledColumn = new TreeColumn(taskTreeN, SWT.NONE);
		scheduledColumn.setWidth(120);
		scheduledColumn.setMoveable(true);
		scheduledColumn.setText("Scheduled");
		scheduledColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				taskTreeN.setSortColumn(scheduledColumn);
				taskTreeN.setSortDirection(taskTreeN.getSortDirection() == SWT.DOWN ? SWT.UP : SWT.DOWN);
				sortComporator = new TaskScheduledDateComparator(taskTreeN.getSortDirection() == SWT.DOWN ? true : false);
				eventBus.post(new TaskChangedEvent());
			}
		});

		TreeColumn durationColumn = new TreeColumn(taskTreeN, SWT.NONE);
		durationColumn.setWidth(50);
		durationColumn.setMoveable(true);
		durationColumn.setText("Duration");
		
		return taskTreeN;
	}

	public void refreshTaskTree(final TaskContainer tasks) {
		taskTree.removeAll();
		fillTreeWithTask(tasks.filterByContext(selectedContext).generateReccurence(new LocalDate().plusMonths(6), null).sortWith(sortComporator));
	}

	private void fillTreeWithTask(TaskContainer tasks) {
		for (Task task: tasks.getTasks()){
			TreeItem treeRow = new TreeItem(taskTree, SWT.NONE);
			treeRow.setChecked(task.isCompleted());
			treeRow.setText(new String[]{
					task.getName(), 
					task.getContext() == null ? "" : task.getContext().toString(),
					task.getDue() == null ? "" : task.getDue().toString(UIConstants.DEFAULT_DATE_FORMAT),
					task.getScheduled() == null ? "" : task.getScheduled().toString(UIConstants.DEFAULT_DATE_FORMAT),
					task.getDuration() == null ? "" : task.getDuration().getStandardHours() + " hr"
			});
			
			mapTreeItemTask.put(treeRow, task);
			
			fillTreeItemWithTask(treeRow, task);
		}
	}
	
	private void fillTreeItemWithTask(TreeItem treeItem, Task tasks) {
		for (Task task: tasks.getSubTasks()){
			TreeItem treeRow = new TreeItem(treeItem, SWT.NONE);
			treeRow.setChecked(task.isCompleted());
			treeRow.setText(new String[]{
					task.getName(), 
					task.getContext() == null ? "" : task.getContext().toString(),
					task.getDue() == null ? "" : task.getDue().toString(UIConstants.DEFAULT_DATE_FORMAT),
					task.getScheduled() == null ? "" : task.getScheduled().toString(UIConstants.DEFAULT_DATE_FORMAT),
					task.getDuration() == null ? "" : task.getDuration().getStandardHours() + " hr"
			});
			
			mapTreeItemTask.put(treeRow, task);
			
			fillTreeItemWithTask(treeRow, task);
		}
	}
	
	private Composite makeButtonBar(Composite parent, final Context rootContext) {
		Composite bar = new Composite(parent, SWT.NONE);
		GridLayout barGLayout = new GridLayout(2, false);
		ElementModifier.removeSpacingAndMargins(barGLayout);
		bar.setLayout(barGLayout);
		
		final Text text = new Text(bar, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR){
					rootTask.addTask(new Task(text.getText(), rootTask));
					text.setText("");
					eventBus.post(new TaskChangedEvent());
				}
			}
		});
		
		ToolBar toolbar = new ToolBar(bar, SWT.FLAT | SWT.RIGHT |SWT.RIGHT_TO_LEFT);
		
		ToolItem addButton = new ToolItem(toolbar, SWT.NONE);
		addButton.setImage(SWTResourceManager.getImage(TaskList.class, Icons.ADD));
		addButton.setText("Add");
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addNewTaskDialog(rootContext);
				eventBus.post(new TaskChangedEvent());
			}
		});
		
		return bar;
	}
	
	private void addNewTaskDialog(Context rootContext) {
		Task newTask = new Task("", rootTask);

		if (selectedContext != null && selectedContext.isVisible()){
			newTask.setContext(selectedContext);
		}
		
		editTaskDialog(rootContext, newTask);
		rootTask.addTask(newTask);
	}

	public void editTaskDialog(Context rootContext, Task editedTask) {
		TaskEditor taskEditor = new TaskEditor(getShell(), SWT.NONE, editedTask, rootContext);
		taskEditor.open();
	}

	public void setContextInView(Context selectedContext) {
		this.selectedContext = selectedContext;
		this.refreshTaskTree(rootTask);		
	}
	

}
