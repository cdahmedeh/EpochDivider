package org.cdahmedeh.orgapp.swingui.contextlist;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.types.context.Context;

public class ContextListTableModel implements TableModel {

	private ArrayList<Context> contexts;

	public ContextListTableModel(ArrayList<Context> contexts) {
		this.contexts = contexts;
	}

	@Override public void addTableModelListener(TableModelListener arg0) {}
	@Override public void removeTableModelListener(TableModelListener arg0) {}
	@Override public Class<?> getColumnClass(int arg0) {return String.class;}
	@Override public int getColumnCount() {return 1;}
	@Override public String getColumnName(int arg0) {return "Context";}
	@Override public int getRowCount() {return contexts.size();}
	@Override public Object getValueAt(int arg0, int arg1) {return contexts.get(arg0).getName();}
	@Override public boolean isCellEditable(int arg0, int arg1) {return false;}
	@Override public void setValueAt(Object arg0, int arg1, int arg2) {}

}
