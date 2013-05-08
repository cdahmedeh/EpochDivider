package org.cdahmedeh.orgapp.types.container;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

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
	
	
	public boolean setTaskToContext(Task task, Context context) {
		if (context.isSelectable()){
			task.setContext(context);
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean dropSupported(Context context) {
		return context != null && context.isSelectable();
	}
}
