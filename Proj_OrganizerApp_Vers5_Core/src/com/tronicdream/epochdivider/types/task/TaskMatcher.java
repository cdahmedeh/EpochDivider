package com.tronicdream.epochdivider.types.task;

import com.tronicdream.epochdivider.types.calendar.View;
import com.tronicdream.epochdivider.types.container.DataContainer;
import com.tronicdream.epochdivider.types.context.AllContextsContext;
import com.tronicdream.epochdivider.types.context.Context;
import com.tronicdream.epochdivider.types.context.DueThisViewContext;
import com.tronicdream.epochdivider.types.context.DueTodayContext;
import com.tronicdream.epochdivider.types.context.DueTomorrowContext;
import com.tronicdream.epochdivider.types.context.NoDueDateContext;

public class TaskMatcher {
	public static boolean matchByContext(Task item, Context context, View view) {
		if (context instanceof AllContextsContext) return true;
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
		return matchByContext(item, dataContainer.getSelectedContext(), dataContainer.getView()) && matchByIsCompleted(item, dataContainer.getShowCompleted());
	}
}
