package org.cdahmedeh.orgapp.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

public class UIElementFactory {
	public static ToolItem createToolItem(ToolBar toolbar, String label, String image){
		ToolItem toolItem = new ToolItem(toolbar, SWT.PUSH);
		toolItem.setText(label);
		toolItem.setImage(SWTResourceManager.getImage(image));
		return toolItem;
	}
}
