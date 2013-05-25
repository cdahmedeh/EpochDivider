package org.cdahmedeh.orgapp.ui.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class ComponentFactory {
	public static TreeColumn generateTreeColumn(Tree tree, String text, int width) {
		TreeColumn treeColumn = new TreeColumn(tree, SWT.NONE);
		treeColumn.setText(text);
		treeColumn.setWidth(width);
		return treeColumn;
	}
}
