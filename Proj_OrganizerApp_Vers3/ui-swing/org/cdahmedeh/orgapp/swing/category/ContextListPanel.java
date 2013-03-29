package org.cdahmedeh.orgapp.swing.category;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.task.Task;

public class ContextListPanel extends JPanel {
	private BigContainer bigContainer;
	
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ContextListPanel(final BigContainer bigContainer) {
		this.bigContainer = bigContainer;
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		table = new JTable();
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
				ArrayList<Context> allContexts = bigContainer.getContextContainer().getAllContexts();
				Context context = allContexts.get(rowIndex);
				switch(columnIndex){
				case 0:
					return context.getName();
				}
				return context.getName();
			}
			
			@Override
			public int getRowCount() {
				return bigContainer.getContextContainer().getAllContexts().size();
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				// TODO Auto-generated method stub
				return "Context";
			}
			
			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return 1;
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
