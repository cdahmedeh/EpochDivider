package org.cdahmedeh.orgapp.task;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class RecurringTask extends Task {

	private Task actual;

	public RecurringTask(String name, TaskContainer parent, Task actual) {
		super(name, parent);
		this.actual = actual;
	}
	
	public void recurrencePropagateAll() {
		DateTime scheduled2 = actual.getScheduled();
		Recurrence recurrence2 = actual.getRecurrence();
		this.easyCopy(actual, this);
		actual.setScheduled(scheduled2.withTime(this.getScheduled().getHourOfDay(), this.getScheduled().getMinuteOfHour(), this.getScheduled().getSecondOfMinute(), this.getScheduled().getMillisOfSecond()));
		actual.setRecurrence(recurrence2);
	}
	
	public void recurrencePropagateForward() {
		LocalDate until = actual.getRecurrence().getUntil();
				
		actual.getRecurrence().setUntil(this.getScheduled().toLocalDate().minusDays(1));
//		actual.getRecurrence().addException(this.getScheduled().toLocalDate().minusDays(1));
		
		Task copy = this.copy();
		actual.getParent().addTask(copy);
		
		Recurrence rec = new Recurrence();
		rec.setAmount(5);
		rec.setFreq(RecurrenceFrequency.DAILY);
		rec.setUntil(until);
//		copy.setRecurrence(rec);
	}
	
	public void recurrenceMakeException(LocalDate ex) {
		//TODO: exception could be a certain instance of date 
		actual.getRecurrence().addException(ex);
		Task copy = this.copy();
		copy.setRecurrence(null);
		actual.getParent().addTask(copy);
	}
	
}