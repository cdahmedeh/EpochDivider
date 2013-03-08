package org.cdahmedeh.orgapp.types.task;

public class RecurrentTaskInstance extends Task{
	public RecurrentTaskInstance(String title) {
		super(title);
	}

	protected RecurrentTaskTemplate template = null;
	public RecurrentTaskTemplate getTemplate() {return template;}
	public void setTemplate(RecurrentTaskTemplate template) {this.template = template;}
	
	public void propogateForward(){
		
	}
	
	public void propogateToAll(){
		
	}
	
	public void seperate(){
		template.getRecurrence().addExceptions(this.getFirstTimeBlock().getStart().toLocalDate());
		
		FormerlyRecurrentTaskInstance taskInstance = new FormerlyRecurrentTaskInstance("");
		
		TaskCopier.copy(this, taskInstance);
		
		taskInstance.setTemplate(this.template);
		
		//where does it go?
		template.formers.add(taskInstance);
	}
}
