package org.cdahmedeh.orgapp.swingui.task;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.nio.file.DirectoryStream.Filter;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;

import org.cdahmedeh.orgapp.swingui.components.DateEntryCellEditor;
import org.cdahmedeh.orgapp.swingui.components.DateEntryCellRenderer;
import org.cdahmedeh.orgapp.swingui.notification.LoadTaskListPanelRequest;
import org.cdahmedeh.orgapp.swingui.notification.SelectedContextChangedNotification;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
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
	
	/**
	 * Create the panel.
	 */
	public TaskListPanel(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		
		setPreferredSize(new Dimension(TaskListPanelDefaults.DEFAULT_TASK_PANEL_WIDTH, TaskListPanelDefaults.DEFAULT_TASK_PANEL_HEIGHT));
		setLayout(new BorderLayout());
		
		createTaskListTable();
	}
	
	private void postInit() {
		prepareTaskListTableModel();
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
		EventList<Task> taskEventList = new BasicEventList<>();
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
	}

}
