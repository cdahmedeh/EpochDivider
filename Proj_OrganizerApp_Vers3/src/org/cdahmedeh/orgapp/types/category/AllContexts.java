package org.cdahmedeh.orgapp.types.category;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.joda.time.Duration;

public class AllContexts extends Context {
	public AllContexts() {super("All Contexts");}
	@Override public boolean isSelectable() {return false;}
	
	public Duration getGoal(View view, ContextContainer contextContainer) {
		Duration duration = Duration.ZERO;
		for (Context context: contextContainer.getAllContexts()){
			duration = duration.plus(context.getGoal(view));
		}
		return duration;
	}
}
