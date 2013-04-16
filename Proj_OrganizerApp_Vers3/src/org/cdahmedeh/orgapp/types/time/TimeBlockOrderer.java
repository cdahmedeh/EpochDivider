package org.cdahmedeh.orgapp.types.time;

import java.util.Comparator;

/**
 * Comparator for TimeBlock. TimeBlocks that start earlier are first. 
 * 
 * @author ahmed
 */
public class TimeBlockOrderer implements Comparator<TimeBlock>{
	@Override
	public int compare(TimeBlock o1, TimeBlock o2) {
		if (o1 == null && o2 == null) return 0;
		if (o1 == null) return -1;
		if (o2 == null) return 1;
		return o1.getStart().compareTo(o2.getStart());
	}
}
