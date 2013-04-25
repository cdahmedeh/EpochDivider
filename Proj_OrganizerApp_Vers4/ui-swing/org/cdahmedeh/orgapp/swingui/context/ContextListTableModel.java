package org.cdahmedeh.orgapp.swingui.context;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.TaskProgressInfo;
import org.joda.time.Duration;

public class ContextListTableModel implements TableModel {
	private ArrayList<Context> contexts;

	public ContextListTableModel(DataContainer dataContainer) {
		this.contexts = dataContainer.getContexts();
	}
	
	@Override
	public int getRowCount() {
		return contexts.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return "Context";
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return "Progress";
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
		case ContextListPanelDefaults.COLUMN_CONTEXT_COLOR:
			return context.getColor();
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return context.getName();
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return new TaskProgressInfo(Duration.ZERO, Duration.standardHours(3), Duration.standardHours(10));
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
			break;
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

	public Context getContextAtRow(int rowIndex){
		return contexts.get(rowIndex);
	}
}
