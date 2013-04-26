package org.cdahmedeh.orgapp.swingui.task;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
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
import org.cdahmedeh.orgapp.swingui.components.TripleDurationCellRenderer;
import org.cdahmedeh.orgapp.swingui.helpers.ToolbarHelper;
import org.cdahmedeh.orgapp.swingui.main.CPanel;
import org.cdahmedeh.orgapp.swingui.notification.RefreshTaskListRequest;
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
				refreshTaskListTable();
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
	
	// - States -
	private int showEvents = 0; //0 for showing only tasks, and 1 for only events. 
	
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
		createRightClickMenu();
		
		//Needed to let the context list know when to select the default context.
		eventBus.post(new TaskListPanelPostInitCompleteNotification());
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
		
//		TableComparatorChooser<Task> taskListSortChooser = 
		TableComparatorChooser.install(taskListTable, sortedTaskList, TableComparatorChooser.SINGLE_COLUMN);
		
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
		estimateColumn.setCellRenderer(new TripleDurationCellRenderer());
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
		taskListTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3){
					//Select the row where the user clicked
					//Credit: http://stackoverflow.com/questions/3558293/java-swing-jtable-right-click-menu-how-do-i-get-it-to-select-aka-highlight-t					
					int r = taskListTable.rowAtPoint(e.getPoint());
					if (r >= 0 && r < taskListTable.getRowCount()) {
						taskListTable.setRowSelectionInterval(r, r);
						popupMenu.show(taskListTable, e.getX(), e.getY());
					} else {
						taskListTable.clearSelection();
					}
				}
			}
		});
		
		JMenuItem removeTaskMenuItem = new JMenuItem("Delete Task");
		removeTaskMenuItem.setIcon(new ImageIcon(TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/remove.png")));
		popupMenu.add(removeTaskMenuItem);
		
		removeTaskMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Task selectedTaskInTable = getSelectedTaskInTable();
				if (selectedTaskInTable != null){
					dataContainer.getTasks().remove(selectedTaskInTable);
					refreshTaskListTable();
				}
			}
		});
	}
	
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.SOUTH);

		final String[] labelsForSwitcher = new String[]{"Switch to Events", "Switch back to Tasks"};
		final String[] iconsForSwitcher = new String[]{"/org/cdahmedeh/orgapp/imt/icons/events.png", "/org/cdahmedeh/orgapp/imt/icons/tasks.png"};
		
		ToolbarHelper.createToolbarHorizontalGlue(toolbar);
		final JButton switchBetweenTasksAndEventsButton = ToolbarHelper.createToolbarButton(toolbar, labelsForSwitcher[showEvents], TaskListPanel.class.getResource(iconsForSwitcher[showEvents])); 
		ToolbarHelper.createToolbarSeperator(toolbar);
		JButton addTaskButton = ToolbarHelper.createToolbarButton(toolbar, "Add", TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/add.png")); 
		
		switchBetweenTasksAndEventsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showEvents = showEvents == 0 ? 1 : 0;
				switchBetweenTasksAndEventsButton.setText(labelsForSwitcher[showEvents]);
				switchBetweenTasksAndEventsButton.setIcon(new ImageIcon(TaskListPanel.class.getResource(iconsForSwitcher[showEvents])));
				taskListMatcherEditor.setShowEvents(showEvents == 1);
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
		
		//Let other knows that the table is update.
		eventBus.post(new TasksChangedNotification());
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
		
		//Set the task to event if we are in event mode.
		newTask.setEvent(showEvents == 1);
		
		//Add new task to the dataContainer and refresh task list table.
		dataContainer.getTasks().add(newTask);
		refreshTaskListTable();
		
		//Init. editing the title of the new tasks and focus the editor.
		taskListTable.editCellAt(taskListTable.getRowCount()-1, TaskListPanelDefaults.COLUMN_TASK_TITLE);
		
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
