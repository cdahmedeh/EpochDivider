package org.cdahmedeh.orgapp.swingui.task;

import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskMatcher;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;

public class TaskListMatcherEditor extends AbstractMatcherEditor<Task>{
	private DataContainer dataContainer;

	private Matcher<Task> matcher;
	public TaskListMatcherEditor(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		matcher = new TaskListMatcher();
	}
	
	public void matcherChangedNotify() {this.fireChanged(matcher);}
	
	private final class TaskListMatcher implements Matcher<Task> {
		@Override
		public boolean matches(Task item) {
			return TaskMatcher.matches(item, 
					dataContainer.getSelectedContext(), 
					dataContainer.getView(), 
					dataContainer.getShowType(), 
					dataContainer.getShowCompleted());
		}
	}
}
