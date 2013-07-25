package com.tronicdream.epochdivider.types.task;

import java.util.Comparator;

import org.joda.time.DateTime;

public class TaskDueDateComparator implements Comparator<DateTime> {
	@Override
	public int compare(DateTime o1, DateTime o2) {
		if (o1 == null && o2 == null) return 0;
		if (o1 == null) return 1;
		if (o2 == null) return -1;
		return o1.compareTo(o2);
	}
}
