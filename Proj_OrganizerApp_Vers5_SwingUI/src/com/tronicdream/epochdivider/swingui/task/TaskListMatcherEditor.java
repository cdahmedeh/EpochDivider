package com.tronicdream.epochdivider.swingui.task;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.core.types.task.TaskMatcher;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;

/**
 * GlazedLists AbstractMatcherEditor implementation for filtering tasks on the
 * task list table.  
 * 
 * @author Ahmed El-Hajjar
 */
public class TaskListMatcherEditor extends AbstractMatcherEditor<Task> {
	private DataContainer dataContainer;
	private Matcher<Task> matcher;

	public TaskListMatcherEditor(DataContainer dataContainer) {
		this.dataContainer = dataContainer;
		this.matcher = new TaskListMatcher();
	}

	public void matcherChangedNotify() {
		this.fireChanged(matcher);
	}

	private final class TaskListMatcher implements Matcher<Task> {
		@Override
		public boolean matches(Task item) {
			return TaskMatcher.matches(item, dataContainer);
		}
	}
}
