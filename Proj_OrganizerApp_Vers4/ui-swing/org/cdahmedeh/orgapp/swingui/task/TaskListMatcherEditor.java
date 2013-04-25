package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.DueThisViewContext;
import org.cdahmedeh.orgapp.types.context.DueTodayContext;
import org.cdahmedeh.orgapp.types.context.DueTomorrowContext;
import org.cdahmedeh.orgapp.types.context.NoDueDateContext;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.LocalDate;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;

public class TaskListMatcherEditor extends AbstractMatcherEditor<Task>{
	private Context selectedContext;
	public Context getSelectedContext() {return selectedContext;}
	public void setSelectedContext(Context selectedContext) {this.selectedContext = selectedContext;}

	private View selectedView;
	public View getSelectedView() {return selectedView;}
	public void setSelectedView(View selectedView) {this.selectedView = selectedView;}
	
	public void contextChangedNotify() {this.fireChanged(matcher);}
	
	private Matcher<Task> matcher;
	public TaskListMatcherEditor() {matcher = new MatcherImplementation();}



	private final class MatcherImplementation implements Matcher<Task> {
		@Override
		public boolean matches(Task item) {
			//If the selected context is All Contexts, then don't filter anything.
			if (selectedContext instanceof AllContextsContext) return true;
			if (selectedContext instanceof NoDueDateContext) return !item.isDue();
			if (selectedContext instanceof DueTodayContext) return item.isDueToday();
			if (selectedContext instanceof DueTomorrowContext) return item.isDueTomorrow();
			if (selectedContext instanceof DueThisViewContext) return item.isDueWithinView(selectedView);
			if (item.getContext().equals(selectedContext)) return true;
			return false;
		}
	}
}
