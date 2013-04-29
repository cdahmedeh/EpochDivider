package org.cdahmedeh.orgapp.swingui.context;

import java.util.ArrayList;
import java.util.Comparator;

import org.cdahmedeh.orgapp.tools.DateReference;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TripleDurationInfo;
import org.joda.time.Duration;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

public class ContextListTableFormat implements AdvancedTableFormat<Context>, WritableTableFormat<Context> {
	private ArrayList<Task> tasks;
	private View view;
	
	public ContextListTableFormat(DataContainer dataContainer) {
		updateReferences(dataContainer);
	}

	public void updateReferences(DataContainer dataContainer) {
		this.tasks = dataContainer.getTasks();
		this.view = dataContainer.getView();
	}
	
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int column) {
		switch(column){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return "Context";
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return "Progress";
		}
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public Object getColumnValue(Context baseObject, int column) {
		switch(column){
		case ContextListPanelDefaults.COLUMN_CONTEXT_COLOR:
			return baseObject.getColor();
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return baseObject.getName();
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return new TripleDurationInfo(
					baseObject.getDurationPassedSince(view.getStartDate().toDateTimeAtStartOfDay(), DateReference.getNow(), tasks), 
					baseObject.getDurationScheduled(view.getStartDate().toDateTimeAtStartOfDay(), view.getEndDate().plusDays(1).toDateTimeAtStartOfDay(), tasks), 
					baseObject.getGoal(view));
		}
		return "";	
	}

	@Override
	public boolean isEditable(Context baseObject, int column) {
		return true;
	}

	@Override
	public Context setColumnValue(Context baseObject, Object editedValue,
			int column) {
		switch(column){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			if (editedValue instanceof String){
				baseObject.setName((String) editedValue);	
			}
			break;
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			if (editedValue == null){
				baseObject.setGoal(view, Duration.ZERO);
			} else if (editedValue instanceof TripleDurationInfo){
				baseObject.setGoal(view, ((TripleDurationInfo)editedValue).getEstimate());
			}
			break;
		}
		return baseObject;
	}

	@Override
	public Comparator<?> getColumnComparator(int column) {
		return null;
	}
}
