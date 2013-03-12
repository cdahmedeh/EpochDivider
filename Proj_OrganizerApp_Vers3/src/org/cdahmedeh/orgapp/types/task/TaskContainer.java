package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.category.AllCategories;
import org.cdahmedeh.orgapp.types.category.Category;
import org.cdahmedeh.orgapp.types.category.NoCategory;

public class TaskContainer {
	public ArrayList<Task> tasks = new ArrayList<>();
	public void addTask(Task task) {tasks.add(task);}
	public ArrayList<Task> getAllTasks() {return tasks;}
	
	public Task getTaskFromId(int id){
		for (Task task: tasks) if (task.getId() == id) return task;
		return null;
	}
	
	public TaskContainer getTasksWithCategory(Category category){
		if (category instanceof AllCategories) return this;
		else{
			TaskContainer taskC = new TaskContainer();
			if (category instanceof NoCategory) {
				for (Task task: tasks) if (task.getCategory() == null || task.getCategory() instanceof NoCategory) taskC.addTask(task);
			} else {
				for (Task task: tasks) if (task.getCategory() == category) taskC.addTask(task);
			}
			return taskC;
		}
	}
	
	public TaskContainer getTasksImmutable(boolean withImmutable){
		if (withImmutable) return this;
		else{
			TaskContainer taskC = new TaskContainer();
			for (Task task: tasks) if (task.getMutability() == Mutability.MUTABLE) taskC.addTask(task);
			return taskC;
		}
	}
}
