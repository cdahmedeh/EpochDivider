package com.tronicdream.epochdivider.core.types.context;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.tronicdream.epochdivider.core.tools.DateReference;
import com.tronicdream.epochdivider.core.tools.MiscHelper;
import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.core.types.timeblock.TripleDurationInfo;
import com.tronicdream.epochdivider.core.types.view.View;

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
	
	private int id = -1;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = MiscHelper.safeTrim(name);}
	
	public boolean isSelectable() {return true;}
	
	private static int colorCounter = 0;
	private int color = 25*colorCounter++ % 255;
	public int getColor() {return color;}
	public void setColor(int color) {this.color = color;}
	
	/* ---- Reader methods ---- */
	
	/**
	 * Gives the total duration of all tasks that end after 'since' and end
	 * before 'until'. 
	 * 
	 * TimeBlocks that are within until are not counted.
	 * TimeBlocks that are within since are counted partially
	 */
	public Duration getDurationPassedSince(DateTime since, DateTime until, List<Task> list){
		Duration duration = Duration.ZERO;
		for (Task task: list){
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
	public Duration getDurationScheduled(DateTime since, DateTime until, List<Task> list){
		Duration duration = Duration.ZERO;
		for (Task task: list){
			if (task.getContext().equals(this)){
				duration = duration.plus(task.getDurationScheduled(since, until));
			}
		}
		return duration;
	}
	
	public Duration getTotalEstimates(List<Task> list){
		Duration duration = Duration.ZERO;
		for (Task task: list){
			if (task.getContext().equals(this)){
				duration = duration.plus(task.getEstimate());
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
	public TripleDurationInfo getProgress(List<Task> list, View view) {
		return new TripleDurationInfo(
				this.getDurationPassedSince(view.getStartDate().toDateTimeAtStartOfDay(), DateReference.getNow(), list), 
				this.getDurationScheduled(view.getStartDate().toDateTimeAtStartOfDay(), view.getEndDate().plusDays(1).toDateTimeAtStartOfDay(), list), 
				this.getTotalEstimates(list));
	}
	
	
}
