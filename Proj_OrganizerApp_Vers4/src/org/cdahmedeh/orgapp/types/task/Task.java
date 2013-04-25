package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.tools.MiscHelper;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.NoContextContext;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;

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
	
	public TaskProgressInfo getTaskProgressInfo(){
		return new TaskProgressInfo(
				getTotalPassed(DateTime.now()),
				getTotalScheduled(),
				getEstimate()
			);
	}
	
	private Duration getTotalScheduled(){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: getAllTimeBlocks()) duration = duration.plus(timeBlock.getDuration());
		return duration;
	}
	
	private Duration getTotalPassed(DateTime moment){
		Duration duration = Duration.ZERO;
		for (TimeBlock timeBlock: getAllTimeBlocks()) if (timeBlock.getEnd().isBefore(moment)) duration = duration.plus(timeBlock.getDuration());
		return duration;
	}
	
	
}
