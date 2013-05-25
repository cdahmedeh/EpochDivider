package org.cdahmedeh.orgapp.types.category;

import java.util.HashMap;
import java.util.Random;

import org.cdahmedeh.orgapp.containers.TaskContainer;
import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.eclipse.swt.graphics.RGB;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Context {

	/* ---- Identifier ---- */

	private static int idCounter = 0;
	private int id = idCounter++;
	public int getId() {return id;}

	
	/* ---- Constructs ---- */
	
	public Context(String name) {this.setName(name);}
	
	
	/* ---- Main Data ---- */
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = MiscHelper.safeTrim(name);}

	private int color = new Random().nextInt(255);
	public int getColor(){return color;}
	public void setColor(int color) {this.color = color;}
	
	public boolean isSelectable() {return true;}
	
	private HashMap<View, Duration> goals = new HashMap<>();
	public Duration getGoal(View view){return goals.get(view) == null ? Duration.ZERO : goals.get(view);}
	public void setGoal(View date, Duration duration) {goals.put(date, duration);}

	
	/* ---- Reader Methods ---- */
	
	/**
	 * Gives the total duration of all tasks that end after 'since' and end
	 * before 'until'. 
	 * 
	 * TimeBlocks that are within until are not counted.
	 * TimeBlocks that are within since are counted partially
	 * 
	 * @param since
	 * @param until
	 * @param taskContainer
	 * @return
	 */
	public Duration getDurationPassedSince(DateTime since, DateTime until, TaskContainer taskContainer){
		Duration duration = Duration.ZERO;
		for (Task task: taskContainer.getTasksWithContext(this).getAllTasks()){
			duration = duration.plus(task.getDurationPassedSince(since, until));
		}
		return duration;
	}
}