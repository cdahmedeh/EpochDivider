package org.cdahmedeh.orgapp.view;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Days;
import org.joda.time.Interval;

public class View {
	private LocalDate start = null;
	private LocalDate end = null;
	
	public View(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}
	
	public int getNumberOfDaysVisible(){
		return 1 + Days.daysBetween(start, end).getDays();
	}

	public Interval getInterval() {
		return new Interval(start.toDateTimeAtStartOfDay(), end.toDateTimeAtStartOfDay().plusDays(1));
	}

	public LocalDate getStart() {
		return start;
	}
	
	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}
	
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	
	public void moveAmountOfDays(int days){
		this.setStart(this.getStart().plusDays(days));
		this.setEnd(this.getEnd().plusDays(days));
	}
}
