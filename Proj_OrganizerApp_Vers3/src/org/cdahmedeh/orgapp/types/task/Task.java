package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Task{
	public Task(String title) {this.setTitle(title);}
	
	private	String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title != null ? title.trim() : "";}
	
	private Mutability mutability = TypeConstants.DEFAULT_MUTABILITY;
	public Mutability getMutability() {return mutability;}
	public void setMutability(Mutability mutability) {this.mutability = mutability != null ? mutability : TypeConstants.DEFAULT_MUTABILITY;}
	
	private Priority priority = TypeConstants.DEFAULT_PRIORITY;
	public Priority getPriority() {return priority;}
	public void setPriority(Priority priority) {this.priority = priority != null ? priority : TypeConstants.DEFAULT_PRIORITY;}
		
	private Duration durationToComplete = new Duration(0);
	public Duration getDurationToComplete() {return durationToComplete;}
	public void setDurationToComplete(Duration durationToComplete) {this.durationToComplete = durationToComplete;}
	
	private DateTime dueDate = null;
	public boolean hasDueDate() {return this.dueDate != null;}
	public DateTime getDueDate() {return dueDate;}
	public void setDueDate(DateTime dueDate) {this.dueDate = dueDate;}
	
	//TODO: Ordering
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<>();
	public boolean isAssignedToTimeBlock() {return !timeBlocks.isEmpty();}
	public boolean hasOnlyOneTimeBlock() {return timeBlocks.size() == 1;}
	public void clearAssignedTimeBlocks() {this.timeBlocks.clear();}
	public void assignToTimeBlock(TimeBlock timeBlock) {this.timeBlocks.add(timeBlock);}
	public TimeBlock getFirstTimeBlock() {return timeBlocks.get(0);}
	public ArrayList<TimeBlock> getAllTimeBlocks() {return timeBlocks;} //TODO: copy?
	
	public Duration getTotalScheduledDuration() {
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: timeBlocks) duration = duration.plus(timeBlock.getDuration()); 
		return duration;	
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getTitle());
		sb.append(" Duration: ");
		sb.append(this.getDurationToComplete().getStandardMinutes());
		sb.append(" mins");
		
		sb.append(" Scheduled Total: ");
		sb.append(this.getTotalScheduledDuration().getStandardMinutes());
		sb.append(" mins");
		
		for (TimeBlock timeBlock: timeBlocks){
			sb.append("\n");
			sb.append("\t");
			sb.append(timeBlock.toString());
		}
		
		return sb.toString();
	}
}
