package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

public class TaskContainer {
	public ArrayList<Task> tasks = new ArrayList<>();
	public void addTask(Task task) {tasks.add(task);}
	public ArrayList<Task> getAllTasks() {return tasks;} //TODO: Should we clone?
}
