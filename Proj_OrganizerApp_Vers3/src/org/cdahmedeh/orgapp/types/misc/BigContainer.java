package org.cdahmedeh.orgapp.types.misc;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.category.ContextContainer;
import org.cdahmedeh.orgapp.types.task.TaskContainer;

public class BigContainer {
	public BigContainer(TaskContainer taskContainer, ContextContainer contextContainer, View currentView) {
		this.taskContainer = taskContainer;
		this.contextContainer = contextContainer;
		this.currentView = currentView;
	}
	
	private TaskContainer taskContainer = null;
	public TaskContainer getTaskContainer() {return taskContainer;}
	
	private ContextContainer contextContainer = null;
	public ContextContainer getContextContainer() {return contextContainer;}
	
	private View currentView = null;
	public View getCurrentView() {return currentView;}
}
