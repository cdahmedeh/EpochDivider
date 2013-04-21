package org.cdahmedeh.orgapp.swingui.task;

import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import org.cdahmedeh.orgapp.swingui.notification.LoadTaskListPanelRequest;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.AdvancedTableModel;
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
	}
	
	// - Components -
	private JScrollPane taskListPane;
	private JTable taskListTable;

	// - Data -
	private DataContainer dataContainer;
	
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
		
		AdvancedTableModel<Task> taskListTableModel = GlazedListsSwing.eventTableModelWithThreadProxyList(taskEventList, taskTableFormat);
		taskListTable.setModel(taskListTableModel);
	}

}
