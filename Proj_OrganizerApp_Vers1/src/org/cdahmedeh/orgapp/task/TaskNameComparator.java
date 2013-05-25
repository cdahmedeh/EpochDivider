package org.cdahmedeh.orgapp.task;

import java.util.Comparator;

public class TaskNameComparator implements Comparator<Task> {

	private boolean order = true;

	public TaskNameComparator(boolean order) {
		this.order = order;
	}
	
	@Override
	public int compare(Task o1, Task o2) {
		return order ? o1.getName().compareToIgnoreCase(o2.getName()) : o2.getName().compareToIgnoreCase(o1.getName());
	}

}
