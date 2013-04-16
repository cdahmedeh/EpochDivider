package org.cdahmedeh.orgapp.swing.task;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.components.ProgressCellRenderer;
import org.cdahmedeh.orgapp.swing.components.ProgressInfo;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.ui.icons.Icons;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

public class TaskListPanel extends JPanel {
	private static final long serialVersionUID = -8836485029464886832L;

	//Data
	private BigContainer bigContainer;
	
	//Components
	private JTable taskListTable;
	private JTextField addTaskTextField;

	/**
	 * Create the panel.
	 */
	public TaskListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		createTaskListTable();
		scrollPane.setViewportView(taskListTable);

		allowDragFromTaskListTable();
		
		createBottomBar();
	}

	private void createTaskListTable() {
		taskListTable = new JTable();
		taskListTable.setFillsViewportHeight(true);
		taskListTable.setShowHorizontalLines(false);
		taskListTable.setShowVerticalLines(true);
		taskListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		taskListTable.setModel(new TaskListTableModel(bigContainer));
		
		taskListTable.getColumnModel().getColumn(TaskListColumns.COMPLETED).setPreferredWidth(30);
		taskListTable.getColumnModel().getColumn(TaskListColumns.COMPLETED).setMaxWidth(30);
		taskListTable.getColumnModel().getColumn(TaskListColumns.TITLE).setPreferredWidth(150);
		
		taskListTable.getColumnModel().getColumn(TaskListColumns.PROGRESS).setCellRenderer(new ProgressCellRenderer());
	}
	
	private void allowDragFromTaskListTable() {
		taskListTable.setDragEnabled(true);
		
		taskListTable.setTransferHandler(new TransferHandler(){
			@Override
			public int getSourceActions(JComponent c) {
				return MOVE;
			}
						
			@Override
			protected Transferable createTransferable(JComponent c) {
				if (c instanceof JTable){
					Task selected = bigContainer.getTaskContainer().getAllTasks().get((((JTable) c).getSelectedRow()));
					return new StringSelection(String.valueOf(selected.getId()));
				}
//				return super.createTransferable(c);
				return new StringSelection("");
			}
			
		});
	}
	
	private void createBottomBar() {
		JToolBar bottomBar = new JToolBar();
		bottomBar.setFloatable(false);
		add(bottomBar, BorderLayout.SOUTH);
		
		addTaskTextField = new JTextField();
		bottomBar.add(addTaskTextField);
		addTaskTextField.setColumns(10);
		
		JButton addTaskButton = new JButton("Add");
		addTaskButton.setIcon(new ImageIcon(TaskListPanel.class.getResource(Icons.ADD)));
		bottomBar.add(addTaskButton);
	}
}