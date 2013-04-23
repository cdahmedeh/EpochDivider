package org.cdahmedeh.orgapp.swingui.task;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import org.cdahmedeh.orgapp.swingui.components.DateEntryCellEditor;
import org.cdahmedeh.orgapp.swingui.components.DateEntryCellRenderer;
import org.cdahmedeh.orgapp.swingui.components.DurationCellRenderer;
import org.cdahmedeh.orgapp.swingui.notification.LoadTaskListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.SelectedContextChangedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.AdvancedTableModel;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import ca.odell.glazedlists.swing.AutoCompleteSupport.AutoCompleteCellEditor;
import ca.odell.glazedlists.swing.GlazedListsSwing;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class TaskListPanel extends JPanel {
	private static final long serialVersionUID = -8250528552031443184L;
	
	// - EventBus -
	private EventBus eventBus;
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
		this.eventBus.register(new EventRecorder());
	}
	
	class EventRecorder{
		@Subscribe public void loadTaskListPanel(LoadTaskListPanelRequest request) {
			postInit();
		}
		@Subscribe public void changedSelectedContext(SelectedContextChangedNotification notification){
			contextMatcherEditor.setContext(notification.getContext());
			contextMatcherEditor.contextChangedNotify();
			taskListTable.repaint(); //TODO: temp. to fix redraw bug.
		}
	}
	
	// - Components -
	private JScrollPane taskListPane;
	private JTable taskListTable;

	// - Data -
	private DataContainer dataContainer;
	
	// - Listeners -
	private ContextMatcherEditor contextMatcherEditor;
	private EventList<Task> taskEventList;
	
	/**
	 * Create the panel.
	 */
	public TaskListPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(TaskListPanelDefaults.DEFAULT_TASK_PANEL_WIDTH, TaskListPanelDefaults.DEFAULT_TASK_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		createTaskListTable();
		createToolbar();
	}
	
	private void postInit() {
		prepareTaskListTableModel();
		adjustTaskListTableColumnWidths();
	}

	private void createTaskListTable() {
		taskListPane = new JScrollPane();
		add(taskListPane, BorderLayout.CENTER);
		
		taskListTable = new JTable();
		taskListTable.setFillsViewportHeight(true);
		taskListPane.setViewportView(taskListTable);
	}

	/**
	 * Set the Table Model for the Task List Table.
	 */
	private void prepareTaskListTableModel() {
		TableFormat<Task> taskTableFormat = new TaskListTableFormat();
		taskEventList = new BasicEventList<>();
		taskEventList.addAll(dataContainer.getTasks());
		
		contextMatcherEditor = new ContextMatcherEditor();
		FilterList<Task> filteredByContextList = new FilterList<>(taskEventList, contextMatcherEditor);
		contextMatcherEditor.contextChangedNotify(); //TODO: ensure that list is filtered the first time
		
		AdvancedTableModel<Task> taskListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(filteredByContextList, taskTableFormat);
		taskListTable.setModel(taskListTableModel);
		
		//Context edit autocomplete support
		final EventList<Context> contextEventList = new BasicEventList<>();
		contextEventList.addAll(dataContainer.getSelectableContexts());
		
		AutoCompleteCellEditor<Context> contextTableCellEditor = AutoCompleteSupport.createTableCellEditor(contextEventList);
		
		TableColumn contextColumn = taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_CONTEXT);
		contextColumn.setCellEditor(contextTableCellEditor);
		
		//Setup renderer and editor for due date column
		TableColumn dueDateColumn = taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_DUE);
		dueDateColumn.setCellRenderer(new DateEntryCellRenderer());
		dueDateColumn.setCellEditor(new DateEntryCellEditor(new JTextField()));
		
		//Setup renderer and editor for estimate column
		TableColumn estimateColumn = taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_ESTIMATE);
		estimateColumn.setCellRenderer(new DurationCellRenderer());
	}
	
	private void adjustTaskListTableColumnWidths() {
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_TITLE).setPreferredWidth(200);
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_CONTEXT).setPreferredWidth(50);
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_DUE).setPreferredWidth(50);
		taskListTable.getColumnModel().getColumn(TaskListPanelDefaults.COLUMN_TASK_ESTIMATE).setPreferredWidth(50);
		
	}
	
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		add(toolbar, BorderLayout.SOUTH);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		toolbar.add(horizontalGlue);
				
		JButton addContextButton = new JButton("Add Task");
		addContextButton.setIcon(new ImageIcon(TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/imt/icons/add.png")));
		toolbar.add(addContextButton);
		
		addContextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewTaskToTaskListTable();
			}
		});
	}

	//non-sequential methods 
	private void addNewTaskToTaskListTable() {
		//make sure that we are not already editing something
		if (taskListTable.isEditing()){
			taskListTable.getEditorComponent().requestFocus();
			return;
		}
		
		Task newTask = new Task("");
		//TODO: stop hacking
		if (contextMatcherEditor.getContext().isSelectable()){
			newTask.setContext(contextMatcherEditor.getContext());
		}
		dataContainer.getTasks().add(newTask);
		refreshContextListTreeTable();
		
		taskListTable.editCellAt(taskListTable.getRowCount()-1, TaskListPanelDefaults.COLUMN_TASK_TITLE);
		
		Component editorComponent = taskListTable.getEditorComponent();
		if (editorComponent != null) {
			editorComponent.requestFocus();
		}
	}

	private void refreshContextListTreeTable() {
		//TODO: investigate method to refresh
		taskEventList.clear();
		taskEventList.addAll(dataContainer.getTasks());
	}
	
}
