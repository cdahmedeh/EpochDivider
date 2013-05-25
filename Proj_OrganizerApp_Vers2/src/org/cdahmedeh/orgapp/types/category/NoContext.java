package org.cdahmedeh.orgapp.types.category;

import org.cdahmedeh.orgapp.containers.ContextContainer;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

public class NoContext extends Context {
	public NoContext() {super("Unsorted");}
	@Override public boolean isSelectable() {return false;}
	
	public Duration getGoal(View view, ContextContainer contextContainer) {
		Duration duration = Duration.ZERO;
		for (Context context: contextContainer.getAllContexts()){
			duration = duration.plus(context.getGoal(view));
		}
		return new Duration(DateTimeConstants.MILLIS_PER_WEEK).minus(duration);
	}
}
