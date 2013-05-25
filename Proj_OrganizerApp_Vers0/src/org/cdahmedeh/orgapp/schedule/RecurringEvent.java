package org.cdahmedeh.orgapp.schedule;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class RecurringEvent extends Event {

	private Event event;
	private LocalDate positionedDate;

	public RecurringEvent(String title, DateTime begin, DateTime end, Event originalEvent) {
		super(title, begin, end);
		this.event = originalEvent;
		this.positionedDate = begin.toLocalDate();
	}
	
	public void propagateBackToOriginal(){
		//Only change the time.
		event.setBegin(event.getBegin().toLocalDate().toDateTime(this.getBegin().toLocalTime()));
		event.setEnd(event.getBegin().plus(this.getDuration()));
	}

	public Event makeException(){
		Recurrence recurrence = event.getRecurrence();
		recurrence.addException(positionedDate);
		return new Event(this.getTitle(), new DateTime(this.getBegin()), new DateTime(this.getEnd()));
	}
	
	@Override
	public Category getCategory() {
		return event.getCategory();
	}
}
