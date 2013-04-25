package org.cdahmedeh.orgapp.swingui.task;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.table.TableColumn;

import org.cdahmedeh.orgapp.swingui.components.DateEntryCellEditor;
import org.cdahmedeh.orgapp.swingui.components.DateEntryCellRenderer;
import org.cdahmedeh.orgapp.swingui.components.DurationCellEditor;
import org.cdahmedeh.orgapp.swingui.components.DurationCellRenderer;
import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.RefreshTaskListRequest;
import org.cdahmedeh.orgapp.swingui.notification.SelectedContextChangedNotification;
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
	public TaskListPanel(DataContainer dataContainer, EventBus eventBus) {super(dataContainer, eventBus);}
	
	@Override
	protected Object getEventRecorder() {
		return new Object(){
			@Subscribe public void changedSelectedContext(SelectedContextChangedNotification notification){
				taskListMatcherEditor.setSelectedContext(dataContainer.getSelectedContext());
				taskListMatcherEditor.setSelectedView(dataContainer.getView());
				taskListMatcherEditor.matcherChangedNotify();
				taskListTable.repaint(); //TODO: temporary call to fix redraw bug
			}
			@Subscribe public void refreshTaskList(RefreshTaskListRequest request) {
				refreshTaskListTreeTable();
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
	
	@Override
	protected void windowInit() {
		setPreferredSize(new Dimension(TaskListPanelDefaults.DEFAULT_TASK_PANEL_WIDTH, TaskListPanelDefaults.DEFAULT_TASK_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		createTaskListTable();
		createToolbar();
	}

	@Override
	protected void postWindowInit() {
		prepareTaskListTableModel();
		prepareTaskListTableRendersAndEditors();
		adjustTaskListTableColumnWidths();
		setupTaskDragAndDrop();
	}

	private void createTaskListTable() {
		taskListPane = new JScrollPane();
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
		taskListMatcherEditor = new TaskListMatcherEditor();
		FilterList<Task> filteredByContextList = new FilterList<>(taskEventList, taskListMatcherEditor);

		//Prepare sorting
		SortedList<Task> sortedTaskList = new SortedList<>(filteredByContextList, null);
		
		taskListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(sortedTaskList, taskTableFormat);
		taskListTable.setModel(taskListTableModel);
		
		TableComparatorChooser<Task> taskListSortChooser = TableComparatorChooser.install(taskListTable, sortedTaskList, TableComparatorChooser.SINGLE_COLUMN);
		
	}

	private void prepareTaskListTableRendersAndEditors() {
		//Prepare context cell editor auto-complete support
		final EventList<Context> contextEventList = new BasicEventList<>();
		contextEventList.addAll(dataContainer.getSelectableContexts());
		
		AutoCompleteCellEditor<Context> contextTableCellEditor = AutoCompleteSupport.createTableCellEditor(contextEventList);
		
		//Setup context cell editor
		TableColumn contextColumn = taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_CONTEXT);
		contextColumn.setCellEditor(contextTableCellEditor);
		
		//Setup renderer and editor for due date column
		TableColumn dueDateColumn = taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_DUE);
		dueDateColumn.setCellRenderer(new DateEntryCellRenderer());
		dueDateColumn.setCellEditor(new DateEntryCellEditor(new JTextField()));
		
		//Setup renderer and editor for estimate column
		TableColumn estimateColumn = taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_PROGRESS);
		estimateColumn.setCellRenderer(new DurationCellRenderer());
		estimateColumn.setCellEditor(new DurationCellEditor(new JTextField()));
	}
	
	private void adjustTaskListTableColumnWidths() {
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_COMPLETED).setMaxWidth(20);
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_TITLE).setPreferredWidth(200);
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_CONTEXT).setPreferredWidth(50);
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_DUE).setPreferredWidth(50);
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_PROGRESS).setPreferredWidth(50);
		
	}

	private void setupTaskDragAndDrop() {
		taskListTable.setDragEnabled(true);
		taskListTable.setTransferHandler(new TransferHandler("Task"){
			@Override
			public int getSourceActions(JComponent c) {
				return COPY_OR_MOVE;
			}
			@Override
			protected Transferable createTransferable(JComponent c) {
				//TODO: There might be a better to get the selected task.
				return new TaskTransferable(taskListTableModel.getElementAt(taskListTable.getSelectedRow()));
			}
		});
	}

	int showEvents = 0;
	
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.SOUTH);
		
		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		final JButton switchBetweenTasksAndEventsButton = ToolbarHelper.createToolbarButton(toolbar, "Switch to Events", TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/events.png")); 
		ToolbarHelper.createToolbarSeperator(toolbar);
		JButton addTaskButton = ToolbarHelper.createToolbarButton(toolbar, "Add", TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/add.png")); 
		
		final String[] labels = new String[]{"Switch to Events", "Switch back to Tasks"};
		final String[] icons = new String[]{"/org/cdahmedeh/orgapp/imt/icons/events.png", "/org/cdahmedeh/orgapp/imt/icons/tasks.png"};
		
		switchBetweenTasksAndEventsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEvents = showEvents == 0 ? 1 : 0;
				switchBetweenTasksAndEventsButton.setText(labels[showEvents]);
				switchBetweenTasksAndEventsButton.setIcon(new ImageIcon(TaskListPanel.class.getResource(icons[showEvents])));
				taskListMatcherEditor.setShowEvents(showEvents == 1);
				taskListMatcherEditor.matcherChangedNotify();
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
	
	private void refreshTaskListTreeTable() {
		//TODO: Investigate proper method to refresh GlazedLists tables.
		taskEventList.clear();
		taskEventList.addAll(dataContainer.getTasks());
	}
	
	private void addNewTaskToTaskListTable() {
		//If we are already editing a task, then just set focus to the editor.
		if (taskListTable.isEditing()){
			taskListTable.getEditorComponent().requestFocus();
			return;
		}
		
		//Create a new task.
		Task newTask = new Task("");
		
		//Set the context to the currently selected Context.
		if (dataContainer.getSelectedContext().isSelectable()){
			newTask.setContext(dataContainer.getSelectedContext());
		}
		
		//Add new task to the dataContainer and refresh task list table.
		dataContainer.getTasks().add(newTask);
		refreshTaskListTreeTable();
		
		//Init. editing the title of the new tasks and focus the editor.
		taskListTable.editCellAt(taskListTable.getRowCount()-1, TaskListPanelDefaults.COLUMN_TASK_TITLE);
		
		Component editorComponent = taskListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}
	}


}
