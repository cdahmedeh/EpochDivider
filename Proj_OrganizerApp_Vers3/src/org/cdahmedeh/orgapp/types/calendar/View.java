package org.cdahmedeh.orgapp.types.calendar;

import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class View {
	
	/* ---- Constructs ---- */
	
	public View(LocalDate startDate, LocalDate endDate, LocalTime firstHour, LocalTime lastHour) {
		this.startDate = startDate; this.endDate = endDate;
		this.firstHour = firstHour;	this.lastHour = lastHour;
	}
	
	
	/* ---- Main Data ---- */
	
	private	LocalDate	startDate						= null;
	public	LocalDate	getStartDate()					{return startDate;}
	public	void		setStartDate(LocalDate start)	{this.startDate = start;}
	
	private	LocalDate	endDate							= null;
	public	LocalDate	getEndDate()					{return endDate;}
	public	void		setEndDate(LocalDate end)		{this.endDate = end;}
	
	private	LocalTime	firstHour						= null;
	public	LocalTime	getFirstHour()					{return firstHour;}
	public	void		setFirstHour(LocalTime first)	{this.firstHour = first;}
	
	private	LocalTime	lastHour						= null;
	public	LocalTime	getLastHour()					{return lastHour;}
	public	void		setLastHour(LocalTime last)		{this.lastHour = last;}
	
	
	/* ---- Compare Methods ---- */
	
	@Override
	public int hashCode() {
		return (startDate.toString() + endDate.toString()).hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}
	
	
	/* ---- Reader Methods ---- */
	
	public int getNumberOfDaysVisible(){
		return 1 + Days.daysBetween(startDate, endDate).getDays();
	}

	public int getNumberOfHoursVisible(){
		return Hours.hoursBetween(firstHour, lastHour).getHours();
	}
	
	public Interval getDateInterval() {
		return new Interval(startDate.toDateTimeAtStartOfDay(), endDate.toDateTimeAtStartOfDay().plusDays(1));
	}
	
	
	/* ---- Manipulate Methods ---- */
	
	public void moveAmountOfDays(int days){
		this.setStartDate(this.getStartDate().plusDays(days));
		this.setEndDate(this.getEndDate().plusDays(days));
	}

}