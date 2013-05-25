package org.cdahmedeh.orgapp.schedule;

import javax.xml.datatype.Duration;

import org.joda.time.DateTime;

public class RecurrenceTask {
	private String taskName = "";
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	private Recurrence recurrence = null;
	public Recurrence getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}	

	private DateTime due = null;
	public DateTime getEnd() {
		return due;
	}
	public void setEnd(DateTime end) {
		this.due = end;
	}	

	private Duration duration = null;
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	private Context context = null;
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
}
