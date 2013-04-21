package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

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
		}
		return "";
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 2;
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
		}
		
		return baseObject;
	}

}
