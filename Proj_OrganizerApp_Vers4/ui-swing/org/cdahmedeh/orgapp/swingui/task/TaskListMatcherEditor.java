package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.DueTodayContext;
import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.LocalDate;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;

public class TaskListMatcherEditor extends AbstractMatcherEditor<Task>{
	private Context selectedContext;
	public Context getSelectedContext() {return selectedContext;}
	public void setSelectedContext(Context selectedContext) {this.selectedContext = selectedContext;}

	public void contextChangedNotify() {this.fireChanged(matcher);}
	
	private Matcher<Task> matcher;
	public TaskListMatcherEditor() {matcher = new MatcherImplementation();}

	private final class MatcherImplementation implements Matcher<Task> {
		@Override
		public boolean matches(Task item) {
			//If the selected context is All Contexts, then don't filter anything.
			if (selectedContext instanceof AllContextsContext) return true;

			if (selectedContext instanceof DueTodayContext) {
				if (item.getDue() != null && item.getDue().toLocalDate().isEqual(LocalDate.now())){
					return true;
				} else {
					return false;
				}
			}

			if (item.getContext().equals(selectedContext)) return true;
			
			return false;
		}
	}
}
