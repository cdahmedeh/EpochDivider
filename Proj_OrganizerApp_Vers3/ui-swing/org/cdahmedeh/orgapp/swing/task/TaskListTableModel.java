package org.cdahmedeh.orgapp.swing.task;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.cdahmedeh.orgapp.containers.BigContainer;
import org.cdahmedeh.orgapp.swing.components.ProgressInfo;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class TaskListTableModel implements TableModel {
	private final BigContainer bigContainer;

	public TaskListTableModel(BigContainer bigContainer) {
		this.bigContainer = bigContainer;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Task task = bigContainer.getTaskContainer().getTasksWithContext(bigContainer.getCurrentContext()).getAllTasks().get(rowIndex);
		if (columnIndex == 0) {
			task.setCompleted((boolean) aValue);
		}
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return columnIndex == 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Task task = bigContainer.getTaskContainer().getTasksWithContext(bigContainer.getCurrentContext()).getAllTasks().get(rowIndex);
		switch(columnIndex){
		case 0:
			return task.isCompleted();
		case 1:	
			return task.getTitle() + " (" + task.getContext().getName() + ")";
		case 2:
			return task.getDueDate();
		case 3:
			ProgressInfo progressInfo = new ProgressInfo();
			progressInfo.first = 					
					task.getDurationPassed(DateTime.now()).getStandardHours();
			progressInfo.second = task.getDurationScheduled(bigContainer.getCurrentView().getEndDate().plusDays(1).toDateTime(LocalTime.MIDNIGHT)).getStandardHours();
			progressInfo.third = task.getEstimate().getStandardHours();
			progressInfo.color = task.getContext().getColor();
			return progressInfo;
		case 4:
			return task.getFirstTimeBlockAfterInstant(DateTime.now());
		default:
			return "";
		}
	}

	@Override
	public int getRowCount() {
		return bigContainer.getTaskContainer().getTasksWithContext(bigContainer.getCurrentContext()).getAllTasks().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex){
		case 0: return "";
		case 1: return "Task";
		case 2: return "Due";
		case 3: return "Progress";
		case 4: return "Next Scheduled";
		default: return "";
		}
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return Boolean.class;
		}
		return String.class;
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
	}
}