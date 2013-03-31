package org.cdahmedeh.orgapp.ui.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class ComponentFactory {
	public static TableColumn generateTreeColumn(Table tree, String text, int width) {
		TableColumn treeColumn = new TableColumn(tree, SWT.NONE);
		treeColumn.setText(text);
		treeColumn.setWidth(width);
		return treeColumn;
	}
	
	public static TreeColumn generateTreeColumn2(Tree tree, String text, int width) {
		TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText(text);
		treeColumn.setWidth(width);
		return treeColumn;
	}
}
