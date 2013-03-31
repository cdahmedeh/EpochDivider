package org.cdahmedeh.orgapp.swing.category;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.LocalTime;

public class ContextListPanel extends JPanel {
	private BigContainer bigContainer;
	
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ContextListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		this.setPreferredSize(new Dimension(200, 200));
		
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
				Context context = bigContainer.getContextContainer().getAllContexts().get(rowIndex);
				switch(columnIndex){
				case 0:	
					return context.getName();
				case 1:
					return context.getDurationPassedSince(bigContainer.getCurrentView().getStartDate().toDateTime(LocalTime.MIDNIGHT), bigContainer.getCurrentView().getEndDate().toDateTime(LocalTime.MIDNIGHT), bigContainer.getTaskContainer()).getStandardHours() + " | " + context.getGoal(bigContainer.getCurrentView()).getStandardHours();
				default:
					return "";
				}
			}
			
			@Override
			public int getRowCount() {
				return bigContainer.getContextContainer().getAllContexts().size();
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				switch(columnIndex){
				case 0: return "Context";
				case 1: return "Progress";
				default: return "";
				}
			}
			
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 2;
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
