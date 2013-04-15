package org.cdahmedeh.orgapp.swing.task;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.category.ProgressCellRenderer;
import org.cdahmedeh.orgapp.swing.category.ProgressInfo;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class TaskListPanel extends JPanel {
	private BigContainer bigContainer;
	
	private JTable table;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public TaskListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setShowHorizontalLines(false);

		table.setModel(new TableModel() {
			
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				Task task = bigContainer.getTaskContainer().getAllTasks().get(rowIndex);
				if (columnIndex == 0) {
					task.setCompleted((boolean) aValue);
				}
			}
			
			@Override
			public void removeTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return columnIndex == 0;
			}
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Task task = bigContainer.getTaskContainer().getAllTasks().get(rowIndex);
				switch(columnIndex){
				case 0:
					return task.isCompleted();
				case 1:	
					return task.getTitle() + " (" + task.getContext().getName() + ")";
				case 2:
					return task.getDueDate();
				case 3:
					ProgressInfo progressInfo = new ProgressInfo();
					progressInfo.first = 					
							task.getDurationPassed(DateTime.now()).getStandardHours();
					progressInfo.second = task.getDurationScheduled(bigContainer.getCurrentView().getEndDate().plusDays(1).toDateTime(LocalTime.MIDNIGHT)).getStandardHours();
					progressInfo.third = task.getEstimate().getStandardHours();
					progressInfo.color = task.getContext().getColor();
					return progressInfo;
				case 4:
					return task.getFirstTimeBlockAfterInstant(DateTime.now());
				default:
					return "";
				}
			}
			
			@Override
			public int getRowCount() {
				return bigContainer.getTaskContainer().getAllTasks().size();
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				switch(columnIndex){
				case 0: return "";
				case 1: return "Task";
				case 2: return "Due";
				case 3: return "Progress";
				case 4: return "Next Scheduled";
				default: return "";
				}
			}
			
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 5;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				if (columnIndex == 0) {
					return Boolean.class;
				}
				return String.class;
			}
			
			@Override
			public void addTableModelListener(TableModelListener arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		

		
		scrollPane.setViewportView(table);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.SOUTH);
		
		textField = new JTextField();
		toolBar.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.setIcon(new ImageIcon(TaskListPanel.class.getResource("/org/cdahmedeh/orgapp/ui/icons/add.png")));
		toolBar.add(btnNewButton);
		
		table.getColumnModel().getColumn(3).setCellRenderer(new ProgressCellRenderer());


	}
}