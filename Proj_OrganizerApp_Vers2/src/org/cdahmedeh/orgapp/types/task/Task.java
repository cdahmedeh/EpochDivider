package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;
import java.util.Collections;

import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.category.Context;
import org.cdahmedeh.orgapp.types.category.NoContext;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.cdahmedeh.orgapp.types.time.TimeBlockOrderer;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Represents a task.
 * 
 * @author ahmed
 */
public class Task{

	/* ---- Identifier ---- */
	
	private static int idCounter = 0;
	private int id = idCounter++;
	public int getId() {return id;}

	
	/* ---- Constructs ---- */
	
	public Task(String title) {this.setTitle(title);}
	
	
	/* ---- Main Data ---- */
	
	private	String title = "";
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = MiscHelper.safeTrim(title);}
	
	private Context context = new NoContext();
	public Context getContext() {return context;}
	public void setContext(Context context) {this.context = context == null ? new NoContext() : context;}
	
	private boolean event = false;
	public boolean isEvent() {return event;}
	public void setEvent(boolean event) {this.event = event;}
	
	private Duration estimate = Duration.ZERO;
	public Duration getEstimate() {return estimate;}
	public void setEstimate(Duration durationToComplete) {this.estimate = durationToComplete;}
	
	private DateTime dueDate = null;
	public boolean hasDueDate() {return this.dueDate != null;}
	public DateTime getDueDate() {return dueDate;}
	public void setDueDate(DateTime dueDate) {this.dueDate = dueDate;}
	
	private ArrayList<TimeBlock> timeBlocks = new ArrayList<>();
	public void assignToTimeBlock(TimeBlock timeBlock) {this.timeBlocks.add(timeBlock);}
	public ArrayList<TimeBlock> getAllTimeBlocks() {return timeBlocks;}

	
	/* ---- Reader Methods ---- */
	
	/**
	 * Finds the earliest timeblock that starts after 'instant'. 
	 * 
	 * @param instant 
	 * @return
	 */
	public TimeBlock getFirstTimeBlockAfterInstant(DateTime instant) {
		if (timeBlocks.isEmpty()) {
			return null;
		}
		Collections.sort(timeBlocks, new TimeBlockOrderer());
		if (timeBlocks.get(0).getStart().isAfter(instant)) {
			return timeBlocks.get(0);
		} else {
			for (TimeBlock timeBlock: timeBlocks){
				if (timeBlock.getStart().isAfter(instant)) {
					return timeBlock;
				}
			}
		}
		return null;
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
	 * Gives the total duration of all timeblocks that end before 'until'.
	 * 
	 * TimeBlocks that are within until are not counted.
	 * 
	 * @param since
	 * @param until
	 * @return
	 */
	public Duration getDurationPassed(DateTime until){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: timeBlocks) {
			if (timeBlock.getEnd().isBefore(until)) {
				duration = duration.plus(timeBlock.getDuration());
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
}
