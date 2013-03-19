package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.category.NoContext;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Task{
	public Task(String title) {this.setTitle(title); id = idCounter++;}
	
	private static int idCounter = 0;
	private int id = -1;
	public int getId() {return id;}
	
	private	String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title != null ? title.trim() : "";}
	
	private Context context = new NoContext();
	public Context getContext() {return context;}
	public void setContext(Context context) {this.context = context == null ? new NoContext() : context;}
	
	private boolean event = false;
	public boolean isEvent() {return event;}
	public void setEvent(boolean event) {this.event = event;}
	
	private Duration estimate = new Duration(0);
	public Duration getEstimate() {return estimate;}
	public void setEstimate(Duration durationToComplete) {this.estimate = durationToComplete;}
	
	private DateTime dueDate = null;
	public boolean hasDueDate() {return this.dueDate != null;}
	public DateTime getDueDate() {return dueDate;}
	public void setDueDate(DateTime dueDate) {this.dueDate = dueDate;}
	
	//TODO: Ordering
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<>();
	public void assignToTimeBlock(TimeBlock timeBlock) {this.timeBlocks.add(timeBlock);}
	public TimeBlock getFirstTimeBlock() {return timeBlocks.isEmpty() ? null : timeBlocks.get(0);}
	public ArrayList<TimeBlock> getAllTimeBlocks() {return timeBlocks;}
	
	public Duration getDurationPassed(DateTime until){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: timeBlocks) {
			if (timeBlock.getEnd().isBefore(until)) {
				duration = duration.plus(timeBlock.getDuration());
			}
		}
		return duration;
	}
	
	public Duration getDurationScheduled(DateTime until){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: timeBlocks) {
			if (timeBlock.getEnd().isBefore(until)) {
				duration = duration.plus(timeBlock.getDuration());
			} else if (timeBlock.getStart().isBefore(until)) {
				duration = duration.plus(new Duration(timeBlock.getStart(),until));
			}
		}
		return duration;
	}
	
	@Override
	public String toString() {return this.getTitle();}
}
