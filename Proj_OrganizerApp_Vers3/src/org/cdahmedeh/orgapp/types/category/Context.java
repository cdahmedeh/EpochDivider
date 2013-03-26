package org.cdahmedeh.orgapp.types.category;

import java.util.HashMap;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.eclipse.swt.graphics.RGB;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

public class Context {
	public Context(String name) {this.setName(name); id = idCounter++; color = new RGB((float)id*20%255, 0.10f, 1f);}

	public boolean isVisible() {return true;}
	
	private static int idCounter = 0;
	private int id = -1;
	public int getId() {return id;}

	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name != null ? name.trim() : "";}

	private RGB color = null;
	public RGB getColor(){return color;}
	public void setColor(RGB color) {this.color = color;}
	
	private HashMap<LocalDate, Duration> goals = new HashMap<>();
	public Duration getGoal(LocalDate date){return goals.get(date);}
	public void setGoal(LocalDate date, Duration duration) {goals.put(date, duration);}
	
	public Duration getDurationPassedSince(DateTime since, DateTime until, TaskContainer taskContainer){
		Duration duration = Duration.ZERO;
		for (Task task: taskContainer.getTasksWithCategory(this).getAllTasks()){
			duration = duration.plus(task.getDurationPassedSince(since, until));
		}
		return duration;
	}
}