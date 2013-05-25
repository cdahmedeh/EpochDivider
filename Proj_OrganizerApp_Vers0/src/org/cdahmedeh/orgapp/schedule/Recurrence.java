package org.cdahmedeh.orgapp.schedule;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.view.View;
import org.joda.time.Interval;
import org.joda.time.LocalDate;

public class Recurrence {
	private RecurrenceType type = RecurrenceType.NONE;
	public RecurrenceType getType() {
		return type;
	}
	public void setType(RecurrenceType type) {
		this.type = type;
	}
	
	//Except these days
	private ArrayList<LocalDate> exceptions = new ArrayList<>();
	public ArrayList<LocalDate> getExceptions() {
		return exceptions;
	}
	public void addException(LocalDate date){
		this.exceptions.add(date);
	}
	
	public static ArrayList<RecurringEvent> generateRecurrentInstances(Event event, Interval interval){
		ArrayList<RecurringEvent> rec = new ArrayList<>();
		
		LocalDate begin = event.getBegin().toLocalDate();
		LocalDate end = interval.getEnd().toLocalDate();
		
		Recurrence recurrence = event.getRecurrence();

		if (recurrence.getType() == RecurrenceType.DAILY){
			do{
				for (LocalDate ex: recurrence.getExceptions()){
					if (ex.isEqual(begin)){
						begin = begin.plusDays(1);
						continue;
					}
				}
				RecurringEvent newEvent = new RecurringEvent(
						event.getTitle(), 
						begin.toDateTime(event.getBegin().toLocalTime()), 
						begin.toDateTime(event.getBegin().toLocalTime()).plus(event.getDuration()), 
						event
						);
				rec.add(newEvent);
				begin = begin.plusDays(1);
			}while(!begin.isAfter(end));
		}
		
		return rec;
	}
}
