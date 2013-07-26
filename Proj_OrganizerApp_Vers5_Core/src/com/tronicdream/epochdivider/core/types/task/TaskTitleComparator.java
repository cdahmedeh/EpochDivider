package com.tronicdream.epochdivider.core.types.task;

import java.util.Comparator;

public class TaskTitleComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
}