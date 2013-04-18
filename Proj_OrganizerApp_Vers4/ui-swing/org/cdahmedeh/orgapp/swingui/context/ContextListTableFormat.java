package org.cdahmedeh.orgapp.swingui.context;

import org.cdahmedeh.orgapp.types.context.Context;

import ca.odell.glazedlists.gui.TableFormat;

public class ContextListTableFormat implements TableFormat<Context> {
	@Override public int getColumnCount() {return 1;}

	@Override
	public Object getColumnValue(Context baseObject, int column) {
		switch (column){
		case ContextListPanelDefaults.COLUMN_NAME: 
			return baseObject.getName();
		default: 
			return "";
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column){
		case ContextListPanelDefaults.COLUMN_NAME:
			return "Context";
		default: 
			return "";
		}
	}
}
