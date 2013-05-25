package org.cdahmedeh.orgapp.schedule;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;

public class Event {
	
	public Event(String title, DateTime begin, DateTime end) {
		this.title = title;
		this.begin = begin;
		this.end = end;

		if (end.isBefore(begin)) {
			this.end = begin;
		}
	}
	public Event(String title, DateTime begin, Duration duration) {
		this.title = title;
		this.begin = begin;
		this.end = begin.plus(duration);
	}
	public Event() {
		this.title = "";
		this.begin = new DateTime();
		this.end = new DateTime().plus(Defaults.DEFAULT_DURATION);

		if (end.isBefore(begin)) {
			this.end = begin;
		}
	}
	
	private String title = null;
	public void setTitle(String text) {
		this.title = text;
	}
	public String getTitle() {
		return this.title;
	}

	private DateTime begin = null;
	public DateTime getBegin() {
		return begin;
	}
	public void setBegin(DateTime begin) {
		this.begin = begin;
	}	
	
	private DateTime end = null;
	public DateTime getEnd() {
		return end;
	}
	public void setEnd(DateTime end) {
		this.end = end;

		if (end.isBefore(begin)) {
			this.end = begin;
		}
	}
	
	private Category category = null;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	private Recurrence recurrence = null;
	public Recurrence getRecurrence() {
		return recurrence;
	}
	public void setRecurrence(Recurrence recurrence) {
		this.recurrence = recurrence;
	}
	
	private EventType eventType = EventType.IMMUTABLE_NOTASK;
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	private boolean selected = false;
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Interval getInterval() {
		return new Interval(begin, end);
	}
	public Duration getDuration() {
		return new Duration(begin, end);
	}
	public int daysSpaning() {
		return Days.daysBetween(begin.toDateMidnight(), end.toDateMidnight())
				.getDays();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(this.title);
		sb.append(" ");
		sb.append(this.begin);
		sb.append(" ");
		sb.append(this.end);

		return sb.toString();
	}
}
