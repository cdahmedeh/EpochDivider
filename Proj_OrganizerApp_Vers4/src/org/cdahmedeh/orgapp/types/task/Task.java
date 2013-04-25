package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.NoContextContext;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

/**
 * Data type class for one Task. 
 * 
 * @author Ahmed El-Hajjar
 */
public class Task {
	
	/* ---- Constructs ---- */
	
	public Task(String title) {this.setTitle(title);}
	
	
	/* ---- Main Data ---- */
	
	private String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = MiscHelper.safeTrim(title);}
	
	private Context context = new NoContextContext();
	public void setContext(Context context) {this.context = context == null ? new NoContextContext() : context;}
	public Context getContext() {return context;}
	
	private DateTime due = null;
	public boolean isDue() {return due != null;}
	public DateTime getDue() {return due;}
	public void setDue(DateTime due) {this.due = due;}

	private Duration estimate = Duration.ZERO;
	public Duration getEstimate() {return estimate;}
	public void setEstimate(Duration estimate) {this.estimate = estimate;}
	
	private boolean completed = false;
	public boolean isCompleted() {return completed;}
	public void setCompleted(boolean completed) {this.completed = completed;}
	
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<>();
	public void assignToTimeBlock(TimeBlock timeBlock) {this.timeBlocks.add(timeBlock);}
	public ArrayList<TimeBlock> getAllTimeBlocks() {return timeBlocks;}
	
	
	/* ---- Reader methods ---- */
	
	public boolean isDueToday(){
		return (this.isDue() && this.getDue().toLocalDate().isEqual(LocalDate.now()));
	}
	
	public boolean isDueTomorrow(){
		return (this.isDue() && this.getDue().toLocalDate().isEqual(LocalDate.now().plusDays(1)));
	}
	
	public boolean isDueWithinView(View view){
		return (this.isDue() && view.getInterval().contains(this.getDue()));
	}
	
	public TaskProgressInfo getTaskProgressInfo(){
		return new TaskProgressInfo(
				getTotalPassed(DateTime.now()),
				getTotalScheduled(),
				getEstimate()
			);
	}
	
	private Duration getTotalScheduled(){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: getAllTimeBlocks()){
			//Timeblocks after due date DON'T COUNT.
			if (this.getDue() != null && timeBlock.getEnd().isAfter(this.getDue())) continue;
			
			duration = duration.plus(timeBlock.getDuration());
		}
		return duration;
	}
	
	private Duration getTotalPassed(DateTime moment){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: getAllTimeBlocks()) if (timeBlock.getEnd().isBefore(moment)) duration = duration.plus(timeBlock.getDuration());
		return duration;
	}
	
	/**
	 * Gives the total duration of all timeblocks that end after 'since' and end
	 * before 'until'. 
	 * 
	 * TimeBlocks that are within until are not counted.
	 * TimeBlocks that are within since are counted partially
	 * 
	 * @param since
	 * @param until
	 * @return
	 */
	public Duration getDurationPassedSince(DateTime since, DateTime until){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: timeBlocks) {
			if (timeBlock.getEnd().isBefore(until)) {
				if (timeBlock.getStart().isAfter(since)){
					duration = duration.plus(timeBlock.getDuration());
				} else if (timeBlock.getEnd().isAfter(since)){
					duration = duration.plus(new Duration(since, timeBlock.getEnd()));
				}
			}
		}
		return duration;
	}
	
	/**
	 * Gives the total duration of all timeblocks that end within 'until'.
	 * 
	 * TimeBlocks that are within until are counted partially.
	 * 
	 * @param since
	 * @param until
	 * @return
	 */
	public Duration getDurationScheduled(DateTime since, DateTime until){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: timeBlocks) {
			//Timeblocks after due date DON'T COUNT.
			if (this.getDue() != null && timeBlock.getEnd().isAfter(this.getDue())) continue;
			if (timeBlock.getEnd().isAfter(since) && timeBlock.getEnd().isBefore(until)){
				if (timeBlock.getStart().isAfter(since)){
					duration = duration.plus(timeBlock.getDuration());
				} else {
					duration = duration.plus(new Duration(since, timeBlock.getEnd()));
				}
			} else if (timeBlock.getStart().isBefore(until) && timeBlock.getStart().isAfter(since)){
				if (timeBlock.getEnd().isBefore(until)){
					duration = duration.plus(timeBlock.getDuration());
				} else {
					duration = duration.plus(new Duration(timeBlock.getStart(), until));
				}
			}
		}
		//TODO: Doesn't count the timeblock that spans the whole view (need to fix that).
		return duration;
	}
}
