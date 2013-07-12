package org.cdahmedeh.orgapp.types.task;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.DueThisViewContext;
import org.cdahmedeh.orgapp.types.context.DueTodayContext;
import org.cdahmedeh.orgapp.types.context.DueTomorrowContext;
import org.cdahmedeh.orgapp.types.context.NoDueDateContext;

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
