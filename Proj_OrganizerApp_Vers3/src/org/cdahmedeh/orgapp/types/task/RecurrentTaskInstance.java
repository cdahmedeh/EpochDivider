package org.cdahmedeh.orgapp.types.task;

public class RecurrentTaskInstance extends Task{
	protected RecurrentTaskTemplate template = null;
	public RecurrentTaskTemplate getTemplate() {return template;}
	public void setTemplate(RecurrentTaskTemplate template) {this.template = template;}
}
