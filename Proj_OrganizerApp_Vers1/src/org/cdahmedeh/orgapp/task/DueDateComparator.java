package org.cdahmedeh.orgapp.task;

import java.util.Comparator;

public class DueDateComparator implements Comparator<Task> {

	private boolean order = true;

	public DueDateComparator(boolean order) {
		this.order = order;
	}
	
	@Override
	public int compare(Task o1, Task o2) {
		if (o1.getDue() == null && o2.getDue() == null) return 0;
		if (o1.getDue() == null) return 1;
		if (o2.getDue() == null) return -1;
		return order ? o1.getDue().compareTo(o2.getDue()) : o2.getDue().compareTo(o1.getDue());
	}

}
