package com.tronicdream.epochdivider.core.container;

import java.util.ArrayList;

import com.tronicdream.epochdivider.core.types.context.AllTasksContext;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.context.DueThisViewContext;
import com.tronicdream.epochdivider.core.types.context.DueTodayContext;
import com.tronicdream.epochdivider.core.types.context.DueTomorrowContext;
import com.tronicdream.epochdivider.core.types.context.UnsortedTaskContext;
import com.tronicdream.epochdivider.core.types.context.NoDueDateContext;

/**
 * Helper methods for {@link DataContainer} 
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainerHelper {
	public static ArrayList<Context> generateDefaultTaskContexts() {
		//Generate some contexts
		ArrayList<Context> contextList = new ArrayList<>();
		
		//Default contexts
		contextList.add(new AllTasksContext());
		contextList.add(new UnsortedTaskContext());
		
		contextList.add(new DueTodayContext());
		contextList.add(new DueTomorrowContext());
		contextList.add(new DueThisViewContext());
		contextList.add(new NoDueDateContext());
		
		return contextList;
	}
}
