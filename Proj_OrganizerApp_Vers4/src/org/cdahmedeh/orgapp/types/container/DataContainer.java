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
	// --- contexts ---
	private ArrayList<Context> contexts;
	public void loadContexts(ArrayList<Context> contexts) {this.contexts = contexts;}

	public ArrayList<Context> getContexts() {
		return contexts;
	}
	
	public ArrayList<Context> getSelectableContexts() {
		ArrayList<Context> contextsList = new ArrayList<>();
		for (Context context: contexts) if (context.isSelectable()) contextsList.add(context);
		return contextsList;
	}
	
	private Context selectedContext = new AllContextsContext();
	public Context getSelectedContext() {return selectedContext;}
	public void setSelectedContext(Context selectedContext) {this.selectedContext = selectedContext;}
	
	// --- tasks ---	
	private ArrayList<Task> tasks;
	public void loadTasks(ArrayList<Task> tasks) {this.tasks = tasks;}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	
	// --- view ---
	private View view;
	public View getView() {return view;}
	public void setView(View view) {this.view = view;}
}
