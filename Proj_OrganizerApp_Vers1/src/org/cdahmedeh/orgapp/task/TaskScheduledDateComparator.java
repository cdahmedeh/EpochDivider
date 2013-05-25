package org.cdahmedeh.orgapp.task;

import java.util.Comparator;

public class TaskScheduledDateComparator implements Comparator<Task> {

	private boolean order = true;

	public TaskScheduledDateComparator(boolean order) {
		this.order = order;
	}
	
	@Override
	public int compare(Task o1, Task o2) {
		if (o1.getScheduled() == null && o2.getScheduled() == null) return 0;
		if (o1.getScheduled() == null) return -1;
		if (o2.getScheduled() == null) return 1;
		return order ? o1.getScheduled().compareTo(o2.getScheduled()) : o2.getScheduled().compareTo(o1.getScheduled());
	}

}
