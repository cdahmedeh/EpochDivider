package com.tronicdream.epochdivider.core.types.task;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.tronicdream.epochdivider.core.tools.DateReference;
import com.tronicdream.epochdivider.core.tools.MiscHelper;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.context.UnsortedTasksContext;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;
import com.tronicdream.epochdivider.core.types.timeblock.TripleDurationInfo;
import com.tronicdream.epochdivider.core.types.view.View;

/**
 * Data type class for one Task. 
 * 
 * @author Ahmed El-Hajjar
 */
public class Task {
	
	/* - Primary Fields - */
	
	private int id = -1;
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	private String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = MiscHelper.safeTrim(title);}
	
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
	
	
	/* - Relationships - */

	private Context context = new UnsortedTasksContext();
	public void setContext(Context context) {this.context = context == null ? new UnsortedTasksContext() : context;}
	public Context getContext() {return context;}
	
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<>();
	public ArrayList<TimeBlock> getTimeBlocks() {return timeBlocks;}
	public void setTimeBlocks(ArrayList<TimeBlock> timeBlocks) {this.timeBlocks = timeBlocks == null ? this.timeBlocks : timeBlocks;} //TODO: FIXME!
	public void addTimeBlock(TimeBlock timeBlock) {this.timeBlocks.add(timeBlock);}
	
	
	/* - Object API Methods - */
	
	@Override
	public String toString() {
		return this.getTitle();
	}
	
	
	/* - Reader methods - */
	
	/**
	 * True if task is due today. 
	 */
	public boolean isDueToday(){
		return (this.isDue() && this.getDue().toLocalDate().isEqual(DateReference.getToday()));
	}
	
	/**
	 * True if task is due tomorrow. 
	 */
	public boolean isDueTomorrow(){
		return (this.isDue() && this.getDue().toLocalDate().isEqual(DateReference.getToday().plusDays(1)));
	}
	
	/**
	 * True if task due date is within the view. 
	 */
	public boolean isDueWithinView(View view){
		return (this.isDue() && view.getInterval().contains(this.getDue()));
	}
	
	/**
	 * Returns the total duration of all time blocks that are assigned to this
	 * task. TimeBlocks that end after the due date of the task are NOT counted.
	 */
	private Duration getTotalScheduled(){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: getTimeBlocks()){
			//TimeBlocks after due date DON'T COUNT.
			if (this.isDue() && timeBlock.getEnd().isAfter(this.getDue())) {
				continue;
			}
			duration = duration.plus(timeBlock.getDuration());
		}
		return duration;
	}
	
	/**
	 * Returns the total duration of all time blocks that end before "moment" 
	 * that are assigned to this task. TimeBlocks that end after the due date
	 * are INDEED counted.
	 */
	private Duration getTotalPassed(DateTime moment){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: getTimeBlocks()) if (timeBlock.getEnd().isBefore(moment)) duration = duration.plus(timeBlock.getDuration());
		return duration;
	}

	/**
	 * Returns a TripleDurationInfo instance with the following information
	 * 			getTotalPassed(DateReference.getNow())
	 *			getTotalScheduled()
	 *			getEstimate()
	 */
	public TripleDurationInfo getProgressInfo(){
		return new TripleDurationInfo(
				getTotalPassed(DateReference.getNow()),
				getTotalScheduled(),
				getEstimate()
			);
	}
	
	/**
	 * Gives the total duration of all TimeBlocks that end after 'since' and end
	 * before 'until'. 
	 * 
	 * TimeBlocks that are within until are not counted.
	 * TimeBlocks that are within since are counted partially
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
	 * Gives the total duration of all TimeBlocks that start within 'since' and
	 * that end within 'until'.
	 * 
	 * TimeBlocks that are within since and until are counted partially.
	 * 
	 * TODO: Optimize and cleanup.
	 */
	public Duration getDurationScheduled(DateTime since, DateTime until){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: timeBlocks) {
			//TimeBlocks ending after due date DON'T COUNT.
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
			} else if (timeBlock.getStart().isBefore(until) && timeBlock.getEnd().isAfter(since) ) {
				duration = duration.plus(new Duration(since, until));
			}
		}
		return duration;
	}
}
