package org.cdahmedeh.orgapp.swingui.context;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.types.context.Context;

public class ContextListTableModel implements TableModel {
	private ArrayList<Context> contextList;

	public ContextListTableModel(ArrayList<Context> contextList) {
		this.contextList = contextList;
	}

	@Override
	public int getRowCount() {
		return contextList.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_NAME:
			return "Context";
		default:
			return "";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Context context = contextList.get(rowIndex);
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_NAME:
			return context.getName();
		default:
			return "";
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}

	@Override public void addTableModelListener(TableModelListener l) {}
	@Override public void removeTableModelListener(TableModelListener l) {}
}
