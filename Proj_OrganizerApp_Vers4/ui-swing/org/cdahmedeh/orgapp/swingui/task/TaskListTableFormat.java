package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

public class TaskListTableFormat implements TableFormat<Task>, WritableTableFormat<Task> {

	@Override
	public Object getColumnValue(Task baseObject, int column) {
		switch(column){
		case TaskListPanelDefaults.COLUMN_TASK_TITLE:
			return baseObject.getTitle();
		case TaskListPanelDefaults.COLUMN_TASK_CONTEXT:
			Context context = baseObject.getContext();
			if (context != null){
				//toString() used to show value.
				return context;				
			}
			break;
		case TaskListPanelDefaults.COLUMN_TASK_DUE:
			DateTime dueDate = baseObject.getDue();
			if (dueDate != null){
				//Renderer does the parsing
				return dueDate;				
			}
			break;
		case TaskListPanelDefaults.COLUMN_TASK_ESTIMATE:
			Duration estimate = baseObject.getEstimate();
			if (estimate != null){
				//Renderer does the parsing
				return estimate;				
			}
			break;
		}
		
		return "";
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column){
		case TaskListPanelDefaults.COLUMN_TASK_TITLE:
			return "Task";
		case TaskListPanelDefaults.COLUMN_TASK_CONTEXT:
			return "Context";
		case TaskListPanelDefaults.COLUMN_TASK_DUE:
			return "Due";
		case TaskListPanelDefaults.COLUMN_TASK_ESTIMATE:
			return "Estimate";
		}
		return "";
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public boolean isEditable(Task baseObject, int column) {
		return true;
	}

	@Override
	public Task setColumnValue(Task baseObject, Object editedValue, int column) {
		switch(column){
		case TaskListPanelDefaults.COLUMN_TASK_TITLE:
			baseObject.setTitle((String) editedValue);
		case TaskListPanelDefaults.COLUMN_TASK_CONTEXT:
			if (editedValue instanceof Context){
				baseObject.setContext((Context)editedValue);
			}
		case TaskListPanelDefaults.COLUMN_TASK_DUE:
			if (editedValue == null){
				baseObject.setDue(null);
			} else if (editedValue instanceof DateTime){
				baseObject.setDue((DateTime)editedValue);
			}
		}
		
		return baseObject;
	}

}
