package org.cdahmedeh.orgapp.swingui.contextlist;

import java.util.ArrayList;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.cdahmedeh.orgapp.types.context.Category;
import org.cdahmedeh.orgapp.types.context.Context;

public class ContextListTreeModel implements TreeModel {
	private ArrayList<Category> categories;

	public ContextListTreeModel(ArrayList<Category> categories) {
		this.categories = categories;
	}

	@Override
	public Object getChild(Object arg0, int arg1) {
		if (arg0 instanceof ArrayList<?>){
			return ((ArrayList<?>) arg0).get(arg1);
		} else if (arg0 instanceof Category){
			return ((Category) arg0).getContextAtIndex(arg1);
		} else {
			return null;			
		}
	}

	@Override
	public int getChildCount(Object arg0) {
		if (arg0 instanceof ArrayList<?>){
			return ((ArrayList<?>) arg0).size();
		} else if (arg0 instanceof Category){
			return ((Category) arg0).getContextsAmount();
		} else {
			return 0;			
		}
	}

	@Override
	public int getIndexOfChild(Object arg0, Object arg1) {
		if (arg0 instanceof ArrayList<?>){
			return ((ArrayList<?>) arg0).indexOf(arg1);
		} else if (arg0 instanceof Category){
			return ((Category) arg0).getIndexOfContext((Context) arg1);
		} else {
			return 0;			
		}
	}

	@Override public Object getRoot() {return categories;}
	@Override public boolean isLeaf(Object arg0) {return arg0 instanceof Context;}

	@Override public void addTreeModelListener(TreeModelListener arg0) {}
	@Override public void removeTreeModelListener(TreeModelListener arg0) {}
	@Override public void valueForPathChanged(TreePath arg0, Object arg1) {}
}
