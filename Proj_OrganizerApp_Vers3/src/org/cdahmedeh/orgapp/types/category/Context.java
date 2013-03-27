package org.cdahmedeh.orgapp.types.category;

import java.util.HashMap;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.task.TaskContainer;
import org.eclipse.swt.graphics.RGB;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Context {
	public Context(String name) {this.setName(name); id = idCounter++; color = new RGB((float)id*20%255, 0.10f, 1f);}

	public boolean isSelectable() {return true;}
	
	private static int idCounter = 0;
	private int id = -1;
	public int getId() {return id;}

	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name != null ? name.trim() : "";}

	private RGB color = null; //TODO: hue as int instead of RGB
	public RGB getColor(){return color;}
	public void setColor(RGB color) {this.color = color;}
	
	private HashMap<View, Duration> goals = new HashMap<>();
	public Duration getGoal(View view){return goals.get(view) == null ? Duration.ZERO : goals.get(view);}
	public void setGoal(View date, Duration duration) {goals.put(date, duration);}
	
	public Duration getDurationPassedSince(DateTime since, DateTime until, TaskContainer taskContainer){
		Duration duration = Duration.ZERO;
		for (Task task: taskContainer.getTasksWithContext(this).getAllTasks()){
			duration = duration.plus(task.getDurationPassedSince(since, until));
		}
		return duration;
	}
}