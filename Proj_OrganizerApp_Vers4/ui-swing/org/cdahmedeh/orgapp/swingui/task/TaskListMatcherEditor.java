package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.DueThisViewContext;
import org.cdahmedeh.orgapp.types.context.DueTodayContext;
import org.cdahmedeh.orgapp.types.context.DueTomorrowContext;
import org.cdahmedeh.orgapp.types.context.NoDueDateContext;
import org.cdahmedeh.orgapp.types.task.Task;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;

public class TaskListMatcherEditor extends AbstractMatcherEditor<Task>{
	private Context selectedContext;
	private View selectedView;
	private boolean showEvents = false;;
	private boolean showCompleted = false;;
	
	public void matcherChangedNotify(DataContainer dataContainer) {
		selectedContext = dataContainer.getSelectedContext();
		selectedView = dataContainer.getView();
		showEvents = dataContainer.getShowEvents();
		showCompleted = dataContainer.getShowCompleted();
		
		this.fireChanged(matcher);
	}
	
	private Matcher<Task> matcher;
	public TaskListMatcherEditor() {matcher = new MatcherImplementation();}

	private final class MatcherImplementation implements Matcher<Task> {
		@Override
		public boolean matches(Task item) {
			//If the selected context is All Contexts, then don't filter anything.
			return matchByContext(item) && matchByIsEvent(item) && matchByIsCompleted(item);
		}

		private boolean matchByContext(Task item) {
			if (selectedContext instanceof AllContextsContext) return true;
			if (selectedContext instanceof NoDueDateContext) return !item.isDue();
			if (selectedContext instanceof DueTodayContext) return item.isDueToday();
			if (selectedContext instanceof DueTomorrowContext) return item.isDueTomorrow();
			if (selectedContext instanceof DueThisViewContext) return item.isDueWithinView(selectedView);
			if (item.getContext().equals(selectedContext)) return true;
			return false;
		}
		
		private boolean matchByIsEvent(Task item){
			return item.isEvent() == showEvents;
		}
		
		private boolean matchByIsCompleted(Task item){
			return !(!showCompleted && item.isCompleted());
		}
	}
}
