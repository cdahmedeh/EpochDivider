package org.cdahmedeh.orgapp.swingui.context;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.types.context.Context;

public class ContextListTableModel implements TableModel {
	private ArrayList<Context> contexts;

	public ContextListTableModel(ArrayList<Context> contexts) {
		this.contexts = contexts;
	}
	
	@Override
	public int getRowCount() {
		return contexts.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return "Context";
		}
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Context context = contexts.get(rowIndex);
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return context.getName();
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Context context = contexts.get(rowIndex);
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			if (aValue instanceof String){
				context.setName((String) aValue);	
			}
		}
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

}
