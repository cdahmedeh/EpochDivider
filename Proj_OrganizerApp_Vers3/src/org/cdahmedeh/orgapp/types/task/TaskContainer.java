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
	
	public ArrayList<Task> getTasksWithCategory(Category category){
		if (category instanceof AllCategories) return getAllTasks();
		else{
			ArrayList<Task> taskC = new ArrayList<>();
			if (category instanceof NoCategory) {
				for (Task task: tasks) if (task.getCategory() == null || task.getCategory() instanceof NoCategory) taskC.add(task);
			} else {
				for (Task task: tasks) if (task.getCategory() == category) taskC.add(task);
			}
			return taskC;
		}
	}
}
