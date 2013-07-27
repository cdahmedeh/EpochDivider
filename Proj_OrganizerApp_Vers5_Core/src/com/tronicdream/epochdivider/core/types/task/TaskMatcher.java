package com.tronicdream.epochdivider.core.types.task;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.context.AllTasksContext;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.context.DueThisViewContext;
import com.tronicdream.epochdivider.core.types.context.DueTodayContext;
import com.tronicdream.epochdivider.core.types.context.DueTomorrowContext;
import com.tronicdream.epochdivider.core.types.context.NoDueDateContext;
import com.tronicdream.epochdivider.core.types.view.View;

/**
 * For matching task to certain criteria. 
 * 
 * @author Ahmed El-Hajjar
 */
public class TaskMatcher {
	public static boolean matchByContext(Task item, Context context, View view) {
		if (context instanceof AllTasksContext) return true;
		if (context instanceof NoDueDateContext) return !item.isDue();
		if (context instanceof DueTodayContext) return item.isDueToday();
		if (context instanceof DueTomorrowContext) return item.isDueTomorrow();
		if (context instanceof DueThisViewContext) return item.isDueWithinView(view);
		if (item.getContext().equals(context)) return true;
		return false;
	}
	
	public static boolean matchByIsCompleted(Task item, boolean showCompleted){
		return !(!showCompleted && item.isCompleted());
	}

	public static boolean matches(Task item, DataContainer dataContainer) {
		return matchByContext(item, dataContainer.getSelectedTaskContext(), dataContainer.getView()) && matchByIsCompleted(item, dataContainer.getShowCompleted());
	}
}
