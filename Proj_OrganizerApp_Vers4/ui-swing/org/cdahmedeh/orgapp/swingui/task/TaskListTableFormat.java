package org.cdahmedeh.orgapp.swingui.task;

import java.util.Comparator;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskProgressInfo;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

public class TaskListTableFormat implements AdvancedTableFormat<Task>, WritableTableFormat<Task> {

	@Override
	public Object getColumnValue(Task baseObject, int column) {
		switch(column){
		case TaskListPanelDefaults.COLUMN_TASK_COMPLETED:
			return baseObject.isCompleted();
		case TaskListPanelDefaults.COLUMN_TASK_TITLE:
			return baseObject.getTitle();
		case TaskListPanelDefaults.COLUMN_TASK_CONTEXT:
			Context context = baseObject.getContext();
			if (context != null){
				//toString() used to show title of Context.
				return context;				
			}
			break;
		case TaskListPanelDefaults.COLUMN_TASK_DUE:
			DateTime dueDate = baseObject.getDue();
			if (dueDate != null){
				//Cell Renderer parses the date. 
				return dueDate;				
			}
			break;
		case TaskListPanelDefaults.COLUMN_TASK_PROGRESS:
			TaskProgressInfo taskProgressInfo = baseObject.getTaskProgressInfo();
			if (taskProgressInfo != null){
				//Cell Renderer parses the duration. 
				return taskProgressInfo;				
			}
			break;
		}
		
		return "";
	}
	
	@Override
	public Task setColumnValue(Task baseObject, Object editedValue, int column) {
		switch(column){
		case TaskListPanelDefaults.COLUMN_TASK_COMPLETED:
			baseObject.setCompleted((boolean) editedValue);
			break;
		case TaskListPanelDefaults.COLUMN_TASK_TITLE:
			baseObject.setTitle((String) editedValue);
			break;
		case TaskListPanelDefaults.COLUMN_TASK_CONTEXT:
			if (editedValue instanceof Context){
				baseObject.setContext((Context)editedValue);
			}
			break;
		case TaskListPanelDefaults.COLUMN_TASK_DUE:
			if (editedValue == null){
				baseObject.setDue(null);
			} else if (editedValue instanceof DateTime){
				baseObject.setDue((DateTime)editedValue);
			}
			break;
		case TaskListPanelDefaults.COLUMN_TASK_PROGRESS:
			if (editedValue == null){
				baseObject.setEstimate(Duration.ZERO);
			} else if (editedValue instanceof TaskProgressInfo){
				baseObject.setEstimate(((TaskProgressInfo)editedValue).getEstimate());
			}
			break;
		}
		
		return baseObject;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column){
		case TaskListPanelDefaults.COLUMN_TASK_COMPLETED:
			return "";
		case TaskListPanelDefaults.COLUMN_TASK_TITLE:
			return "Task";
		case TaskListPanelDefaults.COLUMN_TASK_CONTEXT:
			return "Context";
		case TaskListPanelDefaults.COLUMN_TASK_DUE:
			return "Due";
		case TaskListPanelDefaults.COLUMN_TASK_PROGRESS:
			return "Progress (Completed/Scheduled/Estimate)";
		}
		return "";
	}
	
	@Override
	public Class<?> getColumnClass(int column) {
		switch(column){
		case TaskListPanelDefaults.COLUMN_TASK_COMPLETED:
			return Boolean.class;
		case TaskListPanelDefaults.COLUMN_TASK_TITLE:
			return String.class;
		case TaskListPanelDefaults.COLUMN_TASK_CONTEXT:
			return String.class;
		case TaskListPanelDefaults.COLUMN_TASK_DUE:
			return String.class;
		case TaskListPanelDefaults.COLUMN_TASK_PROGRESS:
			return String.class;
		}
		return null;
	}
	
	@Override
	public boolean isEditable(Task baseObject, int column) {
		return true;
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Comparator<Task> getColumnComparator(int column) {
		return null;
	}
}
