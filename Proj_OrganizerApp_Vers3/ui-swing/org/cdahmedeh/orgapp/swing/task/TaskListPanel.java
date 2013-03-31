package org.cdahmedeh.orgapp.swing.task;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class TaskListPanel extends JPanel {
	private BigContainer bigContainer;
	
	private JTable table;

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
		table.setShowVerticalLines(false);
		table.setModel(new TableModel() {
			
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Task task = bigContainer.getTaskContainer().getAllTasks().get(rowIndex);
				switch(columnIndex){
				case 0:	
					return task.getTitle();
				case 1:
					return task.getDueDate();
				case 2:
					return task.getDurationPassed(DateTime.now()) + " | " +
						task.getDurationScheduled(bigContainer.getCurrentView().getEndDate().plusDays(1).toDateTime(LocalTime.MIDNIGHT)) + " | " +
						task.getEstimate();
				case 3:
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
				case 0: return "Task";
				case 1: return "Due";
				case 2: return "Progress";
				case 3: return "Next Scheduled";
				default: return "";
				}
			}
			
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 4;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return String.class;
			}
			
			@Override
			public void addTableModelListener(TableModelListener arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		scrollPane.setViewportView(table);

	}

}
