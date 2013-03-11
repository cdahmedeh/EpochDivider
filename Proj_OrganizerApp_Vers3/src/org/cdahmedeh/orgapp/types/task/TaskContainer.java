package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

public class TaskContainer {
	public ArrayList<Task> tasks = new ArrayList<>();
	public void addTask(Task task) {tasks.add(task);}
	public ArrayList<Task> getAllTasks() {return tasks;}
	
	public Task getTaskFromId(int id){
		for (Task task: tasks) if (task.getId() == id) return task;
		return null;
	}
}
