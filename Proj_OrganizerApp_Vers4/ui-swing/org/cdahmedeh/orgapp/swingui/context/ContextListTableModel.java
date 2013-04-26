package org.cdahmedeh.orgapp.swingui.context;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.cdahmedeh.orgapp.tools.DateReference;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TripleDurationInfo;
import org.joda.time.Duration;

public class ContextListTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 3929344797261889916L;

	private ArrayList<Context> contexts;
	private ArrayList<Task> tasks;
	private View view;
	
	public ContextListTableModel(DataContainer dataContainer) {
		updateReferences(dataContainer);
		this.fireTableDataChanged();
	}

	public void updateReferences(DataContainer dataContainer) {
		this.contexts = dataContainer.getContexts();
		this.tasks = dataContainer.getTasks();
		this.view = dataContainer.getView();
	}
	
	@Override
	public int getRowCount() {
		return contexts.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
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
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Context context = contexts.get(rowIndex);
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_CONTEXT_COLOR:
			return context.getColor();
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			return context.getName();
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			return new TripleDurationInfo(
					context.getDurationPassedSince(view.getStartDate().toDateTimeAtStartOfDay(), DateReference.getNow(), tasks), 
					context.getDurationScheduled(view.getStartDate().toDateTimeAtStartOfDay(), view.getEndDate().plusDays(1).toDateTimeAtStartOfDay(), tasks), 
					context.getGoal(view));
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Context context = contexts.get(rowIndex);
		switch(columnIndex){
		case ContextListPanelDefaults.COLUMN_CONTEXT_NAME:
			if (aValue instanceof String){
				context.setName((String) aValue);	
			}
			break;
		case ContextListPanelDefaults.COLUMN_CONTEXT_PROGRESS:
			if (aValue == null){
				context.setGoal(view, Duration.ZERO);
			} else if (aValue instanceof TripleDurationInfo){
				context.setGoal(view, ((TripleDurationInfo)aValue).getEstimate());
			}
			break;
		}
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	public Context getContextAtRow(int rowIndex){
		return contexts.get(rowIndex);
	}
}
