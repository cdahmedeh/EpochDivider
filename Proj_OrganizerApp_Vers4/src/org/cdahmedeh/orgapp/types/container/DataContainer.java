package org.cdahmedeh.orgapp.types.container;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;

/**
 * In memory version of data references used by main UI. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	// -- Data and Loaders --
	private ArrayList<Context> contexts;
	public void loadContexts(ArrayList<Context> contexts) {this.contexts = contexts;}
	
	private ArrayList<Task> tasks;
	public void loadTasks(ArrayList<Task> tasks) {this.tasks = tasks;}

	// -- UI States --
	private View view;
	public View getView() {return view;}
	public void setView(View view) {this.view = view;}
	
	private Context selectedContext = new AllContextsContext();
	public Context getSelectedContext() {return selectedContext;}
	public void setSelectedContext(Context selectedContext) {this.selectedContext = selectedContext;}
	
	private boolean showEvents = false;;
	public boolean getShowEvents() {return showEvents;}
	public void setShowEvents(boolean showEvents) {this.showEvents = showEvents;}
	
	private boolean showCompleted = false;;
	public boolean getShowCompleted() {return showCompleted;}
	public void setShowCompleted(boolean showCompleted) {this.showCompleted = showCompleted;}
	
	private boolean dimPast = false;
	public boolean getDimPast() {return dimPast;}
	public void setDimPast(boolean dimPast) {this.dimPast = dimPast;}
	
	// -- Readers --
	
	/**
	 * Get all contexts that exist.
	 */
	public ArrayList<Context> getContexts() {
		return contexts;
	}
	
	/**
	 * Set all contexts that exist.
	 */
	public void setContexts(ArrayList<Context> contexts) {
		this.contexts = contexts;
	}

	/**
	 * Get all contexts that can be assigned to a task.
	 */
	public ArrayList<Context> getSelectableContexts() {
		ArrayList<Context> contextsList = new ArrayList<>();
		for (Context context: contexts) if (context.isSelectable()) contextsList.add(context);
		return contextsList;
	}

	/**
	 * Get all tasks that exist.
	 */
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	/**
	 * Set all tasks that exist.
	 */
	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	
	// -- Helpers --
	/**
	 * Replaces data within this container with the data of another one.
	 * TODO: Keep updated
	 */
	public void replace(DataContainer dataContainer) {
		this.contexts = dataContainer.getContexts();
		this.tasks = dataContainer.getTasks();
		this.selectedContext = dataContainer.getSelectedContext();
		this.view = dataContainer.getView();
		this.showEvents = dataContainer.getShowEvents();
		this.showCompleted = dataContainer.getShowCompleted();
		this.dimPast = dataContainer.getDimPast();
	}
	
	// -- Easy Modifiers --
	
	/**
	 * Creates a new context with a blank name.
	 */
	public void createNewBlankContext() {
		this.getContexts().add(new Context(""));		
	}
	
	/**
	 * Move 'context' to 'index' and return the new index of the context. 
	 */
	public int moveContextToRowAndGiveNewIndex(Context context, int index) {
		int indexOf = this.getContexts().indexOf(context);
		this.getContexts().remove(context);
		int newIndexForMovedContext = indexOf >= index ? index : index -1;
		this.getContexts().add(newIndexForMovedContext , context);
		return newIndexForMovedContext;
	}
	
	/**
	 * Set the context of the provided task. 
	 */
	public boolean setTaskToContext(Task task, Context context) {
		if (context.isSelectable()){
			task.setContext(context);
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Checks if the context can be assigned to a task.
	 */
	public boolean dropSupported(Context context) {
		return context != null && context.isSelectable();
	}
	
	/**
	 * Create a new blank task. 
	 * 
	 *  asEvent determines if the task will be set as an event or not.
	 */
	public void createNewBlankTask(boolean asEvent){
		//Create a new task.
		Task newTask = new Task("");
		
		//Set the context to the currently selected Context.
		if (getSelectedContext().isSelectable()){
			newTask.setContext(getSelectedContext());
		}
		
		//Set the task to event if we are in event mode.
		newTask.setEvent(asEvent);
		
		//Add new task to the dataContainer and refresh task list table.
		getTasks().add(newTask);
	}
	
	/**
	 * Remove task
	 */
	public void removeTask(Task task) {
		getTasks().remove(task);
	}
	
	/**
	 * Assigns a new timeBlock to the supplied task and returns it. 
	 */
	public TimeBlock assignNewTimeBlockToTask(Task task) {
		TimeBlock timeBlock = new TimeBlock();
		task.assignToTimeBlock(timeBlock);
		return timeBlock;
	}
	
	/**
	 * Creates a new task with the supplied context, and generates a new 
	 * timeBlock for that new task and returns the timeBlock.
	 */
	public TimeBlock createNewTaskAndTimeBlockWithContext(Context context) {
		Task task = new Task(context.getName());
		this.getTasks().add(task);
		TimeBlock timeBlock = new TimeBlock();
		task.assignToTimeBlock(timeBlock);
		task.setContext(context);
		return timeBlock;
	}
	
	/**
	 * Move view by days
	 * @param days
	 */
	public void moveViewByAmountOfDays(int days) {
		this.view.moveAmountOfDays(days);
	}
}
