package org.cdahmedeh.orgapp.types.context;

import java.util.Comparator;

public class ContextByCategoryComparator implements Comparator<Context> {
	@Override
	public int compare(Context o1, Context o2) {
		return o1.getCategory().getName().compareTo(o2.getCategory().getName());
	}

}
