package com.tronicdream.epochdivider.swingui.taskcontextlist;

import java.util.Comparator;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.timeblock.TripleDurationInfo;
import com.tronicdream.epochdivider.core.types.view.View;

import org.joda.time.Duration;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

public class TaskContextListTableFormat implements AdvancedTableFormat<Context>, WritableTableFormat<Context> {
	private DataContainer dataContainer;

	public TaskContextListTableFormat(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
	}

	@Override
	public String getColumnName(int column) {
		switch(column){
		case TaskContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return TaskContextListPanelDefaults.COLUMN_CONTEXT_LABEL;
		case TaskContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return TaskContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS_LABEL;
		}
		return "";
	}

	@Override
	public Object getColumnValue(Context baseObject, int column) {
		switch(column){
		case TaskContextListPanelDefaults.COLUMN_CONTEXT_COLOR:
			return baseObject.getColor();
		case TaskContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return baseObject.getName();
		case TaskContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return baseObject.getProgress(dataContainer.getTasks(), dataContainer.getView());
		}
		return "";
	}
	
	@Override
	public Context setColumnValue(Context baseObject, Object editedValue, int column) {
		switch(column){
		case TaskContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			if (editedValue instanceof String){
				baseObject.setName((String) editedValue);	
			}
			break;
		}
		return baseObject;
	}
	
	@Override
	public int getColumnCount() {
		return 3;
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
}
