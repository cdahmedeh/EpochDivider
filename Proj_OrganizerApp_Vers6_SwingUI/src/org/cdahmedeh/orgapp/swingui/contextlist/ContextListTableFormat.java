package org.cdahmedeh.orgapp.swingui.contextlist;

import org.cdahmedeh.orgapp.types.context.Context;

import ca.odell.glazedlists.gui.TableFormat;

public class ContextListTableFormat implements TableFormat<Context> {

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int column) {
		return "Context";
	}

	@Override
	public Object getColumnValue(Context baseObject, int column) {
		return baseObject.getName();
	}

}
