package org.cdahmedeh.orgapp.swingui.task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;

public class ContextMatcherEditor extends AbstractMatcherEditor<Task>{
	private Context context;
	private Matcher<Task> matcher;

	public ContextMatcherEditor() {
		matcher = new MatcherImplementation();
	}

	public void contextChangedNotify() {
		this.fireChanged(matcher);
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	private final class MatcherImplementation implements Matcher<Task> {
		@Override
		public boolean matches(Task item) {
			return item.getContext() == context;
		}
	}
}
