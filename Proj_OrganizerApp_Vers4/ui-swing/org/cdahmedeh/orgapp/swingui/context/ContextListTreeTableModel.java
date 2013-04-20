package org.cdahmedeh.orgapp.swingui.context;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.cdahmedeh.orgapp.types.context.Context;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class ContextListTreeTableModel implements TreeTableModel {
	/* As a reminder, the structure of this Tree looks like this:
	 * 
	 * ArrayList<ContextCategory> (root)
	 *  |
	 *  |- ContextCategory("University")
	 *  |   |
	 *  |   |- Context("Study")
	 *  |   |- Context("Course")
	 *  |
	 *  |- ContextCategory("Projects")
	 *  |- ContextCategory("Essentials")
	 */

	private Context rootContext;

	public ContextListTreeTableModel(Context contextCategoriesList) {
		this.rootContext = contextCategoriesList;
	}

	@Override 
	public Object getRoot() {
		return rootContext;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((Context) parent).getSubContexts().get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((Context) parent).getSubContexts().size();
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((Context) node).getSubContexts().isEmpty();
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
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int column) {
		switch(column){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
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
			case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
				return context.getName();
			}
		}
		return "";
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		return true;
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {
		if (!(value instanceof String)) return;
		if (node instanceof Context){
			Context context = (Context)node;
			switch(column){
			case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
				context.setName((String)value);
			}
		}
	}
}
