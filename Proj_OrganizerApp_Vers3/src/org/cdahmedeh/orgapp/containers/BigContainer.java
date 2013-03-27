package org.cdahmedeh.orgapp.containers;

import org.cdahmedeh.orgapp.types.calendar.View;

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
