package org.cdahmedeh.orgapp.swingui.context;

import java.util.Comparator;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.time.TripleDurationInfo;
import org.joda.time.Duration;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

public class ContextListTableFormat implements AdvancedTableFormat<Context>, WritableTableFormat<Context> {
	private DataContainer dataContainer;

	public ContextListTableFormat(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int column) {
		return "";
	}

	@Override public Class<?> getColumnClass(int columnIndex) { 
		return String.class;
	}

	@Override
	public boolean isEditable(Context baseObject, int column) {
		return true;
	}

	@Override
	public Comparator<?> getColumnComparator(int column) {
		return null;
	}
	
	@Override
	public Object getColumnValue(Context baseObject, int column) {
		switch(column){
		case ContextListPanelDefaults.COLUMN_CONTEXT_COLOR:
			return baseObject.getColor();
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return baseObject.getName();
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return baseObject.getProgress(dataContainer.getTasks(), dataContainer.getView());
		}
		return "";
	}
	
	@Override
	public Context setColumnValue(Context baseObject, Object editedValue, int column) {
		switch(column){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			if (editedValue instanceof String){
				baseObject.setName((String) editedValue);	
			}
			break;
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			View view = dataContainer.getView();
			if (editedValue == null){
				baseObject.setGoal(view, Duration.ZERO);
			} else if (editedValue instanceof TripleDurationInfo){
				baseObject.setGoal(view, ((TripleDurationInfo)editedValue).getEstimate());
			}
			break;
		}
		return baseObject;
	}
}
