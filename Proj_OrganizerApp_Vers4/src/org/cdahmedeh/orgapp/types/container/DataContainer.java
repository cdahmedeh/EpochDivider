package org.cdahmedeh.orgapp.types.container;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.task.Task;

/**
 * In memory version of data references used by main UI. 
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	private ArrayList<Context> contexts;
	public ArrayList<Context> getContexts() {return contexts;}
	public void setContexts(ArrayList<Context> contexts) {this.contexts = contexts;}
	
	public ArrayList<Context> getSelectableContexts() {
		ArrayList<Context> contextsList = new ArrayList<>();
		for (Context context: contexts) if (context.isSelectable()) contextsList.add(context);
		return contextsList;
	}
	
	private ArrayList<Task> tasks;
	public ArrayList<Task> getTasks() {return tasks;}
	public void setTasks(ArrayList<Task> tasks) {this.tasks = tasks;}
	
}
