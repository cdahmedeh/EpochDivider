package org.cdahmedeh.orgapp.types.task;

public class RecurrentTaskInstance extends Task{
	private RecurrentTaskTemplate template = null;
	public RecurrentTaskTemplate getTemplate() {return template;}
	public void setTemplate(RecurrentTaskTemplate template) {this.template = template;}
}
