package org.cdahmedeh.orgapp.swingui.contextlist;

import org.cdahmedeh.orgapp.types.context.Category;
import org.cdahmedeh.orgapp.types.context.Context;

import de.sciss.treetable.j.TreeColumnModel;
import de.sciss.treetable.j.event.TreeColumnModelListener;

public class ContextListTreeColumnModel implements TreeColumnModel {
	@Override
	public String getColumnName(int column) {
		switch(column){
		case ContextListConstants.COLUMN_NAME_INDEX: return ContextListConstants.COLUMN_NAME_LABEL;
		default: return "";
		}
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof Category){
			return ((Category) node).getName();
		} else if (node instanceof Context) {
			return ((Context) node).getName();
		} else {
			return null;			
		}
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {
		if (value instanceof String) {
			value = ((String) value).trim();
			if (node instanceof Category){
				((Category) node).setName((String) value);
			} else if (node instanceof Context) {
				((Context) node).setName((String) value);
			}	
		}
	}

	@Override public boolean isCellEditable(Object node, int column) {return true;}
	
	@Override public int getColumnCount() {return 1;}
	@Override public int getHierarchicalColumn() {return 0;}
	
	@Override public Class<?> getColumnClass(int column) {return String.class;}
	
	@Override public void addTreeColumnModelListener(TreeColumnModelListener l) {}
	@Override public void removeTreeColumnModelListener(TreeColumnModelListener l) {}

}
