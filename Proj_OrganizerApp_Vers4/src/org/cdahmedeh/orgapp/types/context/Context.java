package org.cdahmedeh.orgapp.types.context;

import java.util.ArrayList;
import java.util.HashMap;

import org.cdahmedeh.orgapp.tools.DateReference;
import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TripleDurationInfo;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * A context represents a category that tasks can be assigned to. For example,
 * there can be a "Study" context that is assigned to every task is related to
 * studying. The idea is to help the user categorize their tasks.
 * 
 * @author Ahmed El-Hajjar
 */
public class Context {

	/* ---- Constructs ---- */
	
	public Context(String name) {this.setName(name);}
	
	
	/* ---- Main Data ---- */
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = MiscHelper.safeTrim(name);}
	
	public boolean isSelectable() {return true;}
	
	private static int colorCounter = 0;
	private int color = 25*colorCounter++ % 255;
	public int getColor() {return color;}
	public void setColor(int color) {this.color = color;}
	
	private HashMap<View, Duration> goals = new HashMap<>();
	public HashMap<View, Duration> getGoals(){return goals;}
	public Duration getGoal(View view){return goals.get(view) == null ? Duration.ZERO : goals.get(view);}
	public void setGoal(View date, Duration duration) {goals.put(date, duration);}

	
	/* ---- Reader methods ---- */
	
	/**
	 * Gives the total duration of all tasks that end after 'since' and end
	 * before 'until'. 
	 * 
	 * TimeBlocks that are within until are not counted.
	 * TimeBlocks that are within since are counted partially
	 */
	public Duration getDurationPassedSince(DateTime since, DateTime until, ArrayList<Task> taskContainer){
		Duration duration = Duration.ZERO;
		for (Task task: taskContainer){
			if (task.getContext().equals(this)){
				duration = duration.plus(task.getDurationPassedSince(since, until));
			}
		}
		return duration;
	}
	
	/**
	 * Gives the total duration of all tasks' TimeBlocks that start within 
	 * 'since' and that end within 'until'.
	 * 
	 * TimeBlocks that are within since and until are counted partially.
	 */
	public Duration getDurationScheduled(DateTime since, DateTime until, ArrayList<Task> taskContainer){
		Duration duration = Duration.ZERO;
		for (Task task: taskContainer){
			if (task.getContext().equals(this)){
				duration = duration.plus(task.getDurationScheduled(since, until));
			}
		}
		return duration;
	}
	
	
	/* ---- Object methods ---- */
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	/**
	 * Two contexts are equal if they have the same name.
	 * TODO: Investigate better comparison criteria.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Context){
			return this.getName().equals(((Context) obj).getName());	
		} else {
			return false;
		}
	}
	public TripleDurationInfo getProgress(ArrayList<Task> tasks, View view) {
		return new TripleDurationInfo(
				this.getDurationPassedSince(view.getStartDate().toDateTimeAtStartOfDay(), DateReference.getNow(), tasks), 
				this.getDurationScheduled(view.getStartDate().toDateTimeAtStartOfDay(), view.getEndDate().plusDays(1).toDateTimeAtStartOfDay(), tasks), 
				this.getGoal(view));
	}
	
	
}
