package org.cdahmedeh.orgapp.containers;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.category.AllContexts;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.category.NoContext;
import org.cdahmedeh.orgapp.types.task.Task;

public class TaskContainer {
	public ArrayList<Task> tasks = new ArrayList<>();
	public void addTask(Task task) {tasks.add(task);}
	public ArrayList<Task> getAllTasks() {return tasks;}
	
	public Task getTaskFromId(int id){
		for (Task task: tasks) if (task.getId() == id) return task;
		return null;
	}
	
	public TaskContainer getTasksWithContext(Context context){
		if (context instanceof AllContexts) return this;
		else{
			TaskContainer taskC = new TaskContainer();
			if (context instanceof NoContext) {
				for (Task task: tasks) if (task.getContext() == null || task.getContext() instanceof NoContext) taskC.addTask(task);
			} else {
				for (Task task: tasks) if (task.getContext() == context) taskC.addTask(task);
			}
			return taskC;
		}
	}
	
	public TaskContainer getTasksWithEvents(boolean withEvents){
		if (withEvents) return this;
		else{
			TaskContainer taskC = new TaskContainer();
			for (Task task: tasks) if (!task.isEvent()) taskC.addTask(task);
			return taskC;
		}
	}
}
