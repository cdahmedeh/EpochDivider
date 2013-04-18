package org.cdahmedeh.orgapp.swingui.context;

import java.util.ArrayList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.ContextCategory;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class ContextListTreeTableModel implements TreeTableModel {
	private ArrayList<ContextCategory> contextCategoriesList;

	public ContextListTreeTableModel(ArrayList<ContextCategory> contextCategoriesList) {
		this.contextCategoriesList = contextCategoriesList;
	}

	@Override
	public Object getRoot() {
		return contextCategoriesList;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if(parent instanceof ContextCategory){
			return ((ContextCategory) parent).getContexts().get(index);
		} else if(parent instanceof ArrayList<?>){
			return ((ArrayList<Context>) parent).get(index);
		}
		return "";
	}

	@Override
	public int getChildCount(Object parent) {
		if(parent instanceof ContextCategory){
			return ((ContextCategory) parent).getContexts().size();
		} else if(parent instanceof ArrayList<?>){
			return ((ArrayList<Context>) parent).size();
		}
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		return node instanceof Context;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int column) {
		switch(column){
		case ContextListPanelDefaults.COLUMN_NAME:
			return "Context";
		default:
			return "";
		}
	}

	@Override
	public int getHierarchicalColumn() {
		return 0;
	}

	@Override
	public Object getValueAt(Object node, int column) {
		if (node instanceof Context){
			Context context = (Context)node;
			switch(column){
			case ContextListPanelDefaults.COLUMN_NAME:
				return context.getName();
			}
		} else if(node instanceof ContextCategory){
			ContextCategory contextCategory = (ContextCategory)node;
			switch(column){
			case ContextListPanelDefaults.COLUMN_NAME: //TODO
				return contextCategory.getName();
			}
		} else if(node instanceof ArrayList<?>){
			return "";
		}
		return "";
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		return false;
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {
	}

}
