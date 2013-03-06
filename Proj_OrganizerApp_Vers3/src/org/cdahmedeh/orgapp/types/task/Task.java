package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.Duration;

public class Task{
	private	String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title != null ? title.trim() : "";}
	
	private Mutability mutability = TypeConstants.DEFAULT_MUTABILITY;
	public Mutability getMutability() {return mutability;}
	public void setMutability(Mutability mutability) {this.mutability = mutability != null ? mutability : TypeConstants.DEFAULT_MUTABILITY;}
	
	private Permissibility permissibility = TypeConstants.DEFAULT_PERMISSIBILITY;
	public Permissibility getPermissibility() {return permissibility;}
	public void setPermissibility(Permissibility permissibility) {this.permissibility = permissibility != null ? permissibility : TypeConstants.DEFAULT_PERMISSIBILITY;}
		
	private Duration durationToComplete = new Duration(0);
	public Duration getDurationToComplete() {return durationToComplete;}
	public void setDurationToComplete(Duration durationToComplete) {this.durationToComplete = durationToComplete;}
	
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<>();
	public boolean isAssignedToTimeBlock() {return !timeBlocks.isEmpty();}
	public void assignToTimeBlock(TimeBlock timeBlock) {this.timeBlocks.add(timeBlock);}
	
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
