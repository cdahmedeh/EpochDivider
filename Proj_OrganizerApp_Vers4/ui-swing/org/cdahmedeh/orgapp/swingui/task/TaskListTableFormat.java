package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.types.task.Task;

import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

public class TaskListTableFormat implements TableFormat<Task>, WritableTableFormat<Task> {

	@Override
	public Object getColumnValue(Task baseObject, int column) {
		return baseObject.getTitle();
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return "Task";
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isEditable(Task baseObject, int column) {
		return true;
	}

	@Override
	public Task setColumnValue(Task baseObject, Object editedValue, int column) {
		baseObject.setTitle((String) editedValue);
		return baseObject;
	}

}
