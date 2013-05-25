package org.cdahmedeh.orgapp.ui.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class ElementFactory {
	public static TreeColumn makeSortableTreeColumn(Tree parentTree, String label, int width){
		TreeColumn column = new TreeColumn(parentTree, SWT.NONE);
		column.setWidth(width);
		column.setText(label);
		return column;
	}
}
