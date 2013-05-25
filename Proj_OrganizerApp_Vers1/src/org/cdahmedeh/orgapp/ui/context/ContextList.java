package org.cdahmedeh.orgapp.ui.context;
import java.util.HashMap;

import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.task.Task;
import org.cdahmedeh.orgapp.task.TaskContainer;
import org.cdahmedeh.orgapp.ui.helpers.ElementFactory;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.cdahmedeh.orgapp.ui.notification.ContextChangedEvent;
import org.cdahmedeh.orgapp.ui.notification.TaskChangedEvent;
import org.cdahmedeh.orgapp.ui.task.TaskList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import swing2swt.layout.BorderLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


public class ContextList extends Composite{
	@Override protected void checkSubclass() {}
	
	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		eventBus.register(new EventRecorder());
	}
	class EventRecorder{
		@Subscribe public void refreshTasks(TaskChangedEvent event){
			refreshContextTree(rootContext, contextTree);
		}
	}

	private HashMap<TreeItem, Context> mapTreeItemContext = new HashMap<>();
	private TaskContainer rootTask = null;
	private Tree contextTree = null;
	private Context rootContext = null;
	
	public ContextList(Composite parent, int style, final Context rootContext, final TaskContainer task) {
		super(parent, style);
		
		this.rootContext = rootContext;
		this.rootTask = task;
		
		this.setLayout(new BorderLayout());
		
		final Tree contextTree = makeContextTree(rootContext);
		contextTree.setLayoutData(BorderLayout.CENTER);
		
		Composite buttonBar = makeButtonBar(this, rootContext, contextTree);
		buttonBar.setLayoutData(BorderLayout.SOUTH);
	}

	private Tree makeContextTree(final Context rootContext) {
		final Tree contextTree = new Tree(this, SWT.NONE);
		contextTree.setHeaderVisible(true);
		
		setupDragAndDrop(rootContext, contextTree);
		
		TreeColumn nameColumn = ElementFactory.makeSortableTreeColumn(contextTree, "Context", 100);
		TreeColumn weekTimeColumn = ElementFactory.makeSortableTreeColumn(contextTree, "Week", 35);
		TreeColumn totalTimeColumn = ElementFactory.makeSortableTreeColumn(contextTree, "Total", 35);
		TreeColumn goalTimeColumn = ElementFactory.makeSortableTreeColumn(contextTree, "Goal", 35);
		
		refreshContextTree(rootContext, contextTree);
		
		contextTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Context selectedContext = mapTreeItemContext.get(contextTree.getSelection()[0]);
				eventBus.post(new ContextChangedEvent(selectedContext));
			}
		});
		
		contextTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Context selectedContext = mapTreeItemContext.get(contextTree.getSelection()[0]);
				editContextDialog(selectedContext);
				refreshContextTree(rootContext, contextTree);
				eventBus.post(new ContextChangedEvent(selectedContext));
			}
		});
		
		return contextTree;
	}

	public void setupDragAndDrop(final Context rootContext, final Tree contextTree) {
		DropTarget target = new DropTarget(contextTree, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
		target.setTransfer(new Transfer[]{TextTransfer.getInstance()});
		target.addDropListener(new DropTargetAdapter(){
			@Override
			public void drop(DropTargetEvent event) {
				for (Task task: rootTask.getTasks()){
					if (task.getId() == (Integer.parseInt((String) event.data))){
						task.setContext(Context.fromString(mapTreeItemContext.get(event.item).toString(), rootContext));
						eventBus.post(new TaskChangedEvent());
					}
				}
			}
		});
	}

	private void refreshContextTree(final Context contexts, final Tree contextTree) {
		this.contextTree = contextTree;
		contextTree.removeAll();
		fillTreeWithContext(contextTree, contexts);
	}

	private void fillTreeWithContext(Tree contextTree, Context contexts) {
		for (Context context: contexts.getSubContexts()){
			TreeItem treeRow = new TreeItem(contextTree, SWT.NONE);
			fillTreeRowWithData(context, treeRow);
			mapTreeItemContext.put(treeRow, context);
			fillTreeItemWithContext(treeRow, context);
		}
	}
	
	private void fillTreeItemWithContext(TreeItem treeItem, Context contexts) {
		for (Context context: contexts.getSubContexts()){
			TreeItem treeRow = new TreeItem(treeItem, SWT.NONE);
			fillTreeRowWithData(context, treeRow);
			mapTreeItemContext.put(treeRow, context);
			fillTreeItemWithContext(treeRow, context);
		}
	}

	public void fillTreeRowWithData(Context context, TreeItem treeRow) {
		treeRow.setText(new String[]{
				context.getName(),
				String.valueOf(context.countTotalHours(rootTask, 7)),
				String.valueOf(context.countTotalHours(rootTask)),
				context.getGoal() != null ? String.valueOf(context.getGoal().getStandardHours()) : "0"
		});
	}
	
	private Composite makeButtonBar(Composite parent, final Context rootContext, final Tree contextTree) {
		ToolBar toolbar = new ToolBar(parent, SWT.FLAT | SWT.RIGHT | SWT.RIGHT_TO_LEFT);
		
		ToolItem addButton = new ToolItem(toolbar, SWT.NONE);
		addButton.setImage(SWTResourceManager.getImage(ContextList.class, Icons.ADD));
		addButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addNewContextDialog(rootContext);
				refreshContextTree(rootContext, contextTree);
			}
		});
		
		return toolbar;
	}
	
	private void addNewContextDialog(Context rootContext) {
		Context newContext = new Context("", rootContext);
		editContextDialog(newContext);
		rootContext.addSubContext(newContext);
	}

	public void editContextDialog(Context editedContext) {
		ContextEditor contextEditor = new ContextEditor(getShell(), SWT.NONE, editedContext);
		contextEditor.open();
	}
	
}
