package org.cdahmedeh.orgapp.swingui.task;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;

import org.cdahmedeh.orgapp.swingui.components.DateEntryCellEditor;
import org.cdahmedeh.orgapp.swingui.components.DateEntryCellRenderer;
import org.cdahmedeh.orgapp.swingui.components.DurationCellEditor;
import org.cdahmedeh.orgapp.swingui.components.TripleDurationCellRenderer;
import org.cdahmedeh.orgapp.swingui.helpers.TableHelper;
import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.ContextsChangedNotification;
import org.cdahmedeh.orgapp.swingui.notification.SelectedContextChangedNotification;
import org.cdahmedeh.orgapp.swingui.notification.TaskListPanelPostInitCompleteNotification;
import org.cdahmedeh.orgapp.swingui.notification.TasksChangedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import ca.odell.glazedlists.swing.AutoCompleteSupport.AutoCompleteCellEditor;
import ca.odell.glazedlists.swing.GlazedListsSwing;
import ca.odell.glazedlists.swing.TableComparatorChooser;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class TaskListPanel extends CPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	public TaskListPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus, "Task List");}
	
	@Override
	protected Object getEventRecorder() {
		return new Object(){
			@Subscribe public void selectedContextChanged(SelectedContextChangedNotification notification){
				taskListMatcherEditor.matcherChangedNotify();
				taskListTable.repaint(); //TODO: Temporary call to repaint() to fix redraw bug.
			}
			@Subscribe public void taskListChanged(TasksChangedNotification notification) throws Exception {
				refreshTaskListTable();
			}
			@Subscribe public void contextListChanged(ContextsChangedNotification notification){
				refreshContextAutoComplete();
			}
		};
	}
	
	// - Components -
	private JScrollPane taskListPane;
	private JTable taskListTable;

	// - Listeners -
	private TaskListMatcherEditor taskListMatcherEditor;
	private EventList<Task> taskEventList;
	private AdvancedTableModel<Task> taskListTableModel;
	private EventList<Context> contextEventList;
	
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(TaskListPanelConstants.DEFAULT_TASK_PANEL_WIDTH, TaskListPanelConstants.DEFAULT_TASK_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		setBorder(UIManager.getBorder("ScrollPane.border"));
		
		createTaskListTable();
		createToolbar();
	}

	@Override
	protected void postWindowInit() {
		prepareTaskListTableModel();
		prepareTaskListTableRendersAndEditors();
		adjustTaskListTableColumnWidths();
		setupTaskDragAndDrop();
		createRightClickMenu();
		
		//Needed to let the context list know when to select the default context.
		eventBus.post(new TaskListPanelPostInitCompleteNotification());
	}

	private void createTaskListTable() {
		taskListPane = new JScrollPane();
		taskListPane.setBorder(BorderFactory.createEmptyBorder());
		add(taskListPane, BorderLayout.CENTER);
		
		taskListTable = new JTable();
		taskListTable.setFillsViewportHeight(true);
		taskListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		taskListPane.setViewportView(taskListTable);
	}

	private void prepareTaskListTableModel() {
		//Fill Event list with Tasks
		TableFormat<Task> taskTableFormat = new TaskListTableFormat();
		taskEventList = new BasicEventList<>();
		taskEventList.addAll(dataContainer.getTasks());
		
		//Prepare Matcher for filtering by the Context that is selected by the user
		taskListMatcherEditor = new TaskListMatcherEditor(dataContainer);
		FilterList<Task> filteredByContextList = new FilterList<>(taskEventList, taskListMatcherEditor);

		//Prepare sorting
		SortedList<Task> sortedTaskList = new SortedList<>(filteredByContextList, null);
		
		taskListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(sortedTaskList, taskTableFormat);
		taskListTable.setModel(taskListTableModel);
		
		TableComparatorChooser.install(taskListTable, sortedTaskList, TableComparatorChooser.SINGLE_COLUMN);
	}

	private void prepareTaskListTableRendersAndEditors() {
		//Setup auto-completed support for Context editor
		contextEventList = new BasicEventList<>();
		contextEventList.addAll(dataContainer.getSelectableContexts());
		
		AutoCompleteCellEditor<Context> contextTableCellEditor = AutoCompleteSupport.createTableCellEditor(contextEventList);
		
		//Setup context cell editor
		TableColumn contextColumn = taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_CONTEXT);
		contextColumn.setCellEditor(contextTableCellEditor);
		
		//Setup renderer and editor for due date column
		TableColumn dueDateColumn = taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_DUE);
		dueDateColumn.setCellRenderer(new DateEntryCellRenderer());
		dueDateColumn.setCellEditor(new DateEntryCellEditor(new JTextField()));
		
		//Setup renderer and editor for estimate column
		TableColumn estimateColumn = taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_PROGRESS);
		estimateColumn.setCellRenderer(new TripleDurationCellRenderer());
		estimateColumn.setCellEditor(new DurationCellEditor(new JTextField()));
	}
	
	private void adjustTaskListTableColumnWidths() {
		taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_COMPLETED).setMaxWidth(20);
		taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_TITLE).setPreferredWidth(200);
		taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_CONTEXT).setPreferredWidth(50);
		taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_DUE).setPreferredWidth(50);
		taskListTable.getColumnModel().getColumn(TaskListPanelConstants.COLUMN_TASK_PROGRESS).setPreferredWidth(50);
		
	}

	private void setupTaskDragAndDrop() {
		taskListTable.setDragEnabled(true);
		taskListTable.setTransferHandler(new TransferHandler("Task"){
			private static final long serialVersionUID = -750648214860216598L;
			
			@Override
			public int getSourceActions(JComponent c) {
				return COPY_OR_MOVE;
			}
			@Override
			protected Transferable createTransferable(JComponent c) {
				return new TaskTransferable(getSelectedTaskInTable());
			}
		});
	}

	private void createRightClickMenu() {
		final JPopupMenu popupMenu = new JPopupMenu();
		
		//Setup popup menu for the Task List Table. 
		TableHelper.setupPopupMenu(taskListTable, popupMenu);
		
		JMenuItem removeTaskMenuItem = new JMenuItem("Delete Task");
		removeTaskMenuItem.setIcon(new ImageIcon(TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/delete.gif")));
		popupMenu.add(removeTaskMenuItem);
		
		removeTaskMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Task selectedTaskInTable = getSelectedTaskInTable();
				if (selectedTaskInTable != null){
					dataContainer.emTaskRemove(selectedTaskInTable);
					eventBus.post(new TasksChangedNotification());
				}
			}
		});
	}
	
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBackground(taskListTable.getBackground());
		add(toolbar, BorderLayout.SOUTH);

		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		
		final JToggleButton showCompletedTasks = ToolbarHelper.createToolbarToggleButton(toolbar, "Show Completed", TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/completed.gif"));
		showCompletedTasks.setBackground(taskListTable.getBackground());
		
		ToolbarHelper.createToolbarSeperator(toolbar);
		
		final JButton addTaskButton = ToolbarHelper.createToolbarButton(toolbar, "Add", TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/add.gif"));
		addTaskButton.setBackground(taskListTable.getBackground());
		
		//ToolBar button actions
		showCompletedTasks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataContainer.setShowCompleted(showCompletedTasks.isSelected());
				taskListMatcherEditor.matcherChangedNotify();
				taskListTable.repaint(); //TODO: temporary call to fix redraw bug
			}
		});
		
		addTaskButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewTaskToTaskListTable();
			}
		});
	}

	
	// -- non-sequential methods --
	
	private void refreshTaskListTable() {
		//TODO: Investigate proper method to refresh GlazedLists tables.
		taskEventList.clear();
		taskEventList.addAll(dataContainer.getTasks());
		
		taskListTable.repaint(); //TODO: temporary call to fix redraw bug
	}
	
	private void refreshContextAutoComplete(){
		//TODO: Investigate proper method to refresh GlazedLists lists.
		contextEventList.clear();
		contextEventList.addAll(dataContainer.getSelectableContexts());
	}
	
	private void addNewTaskToTaskListTable() {
		//If we are already editing a task, then just set focus to the editor.
		if (taskListTable.isEditing()){
			taskListTable.getEditorComponent().requestFocus();
			return;
		}
		
		//Create a new task.
		dataContainer.emTaskCreate();
		
		//Refresh task list table and notify others.
		eventBus.post(new TasksChangedNotification());

		//Init. editing the title of the new tasks and focus the editor.
		taskListTable.editCellAt(taskListTable.getRowCount()-1, TaskListPanelConstants.COLUMN_TASK_TITLE);
		
		Component editorComponent = taskListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}
	}

	private Task getSelectedTaskInTable() {
		//TODO: There might be a better way to get the selected task.
		return taskListTableModel.getElementAt(taskListTable.getSelectedRow());
	}
}
