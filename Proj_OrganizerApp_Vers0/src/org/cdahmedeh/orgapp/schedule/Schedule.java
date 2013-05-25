package org.cdahmedeh.orgapp.schedule;

import java.util.ArrayList;

import org.joda.time.Interval;

public class Schedule {
	private ArrayList<Event> events = new ArrayList<>();
	public void addEvent(Event event){
		this.events.add(event);
	}
	
	private ArrayList<Task> tasks = new ArrayList<>();
	public void addTask(Task task){
		this.tasks.add(task);
	}
	public ArrayList<Task> getTasks(){
		return this.tasks;
	}
	
	public ArrayList<Event> getEventsWithinInterval(Interval interval){
		ArrayList<Event> eventsInInterval = new ArrayList<>();
		ArrayList<Event> reccurentEvents = new ArrayList<>();
		
		for (Event event: events){
			if (interval.contains(event.getBegin()) || interval.contains(event.getEnd())) {
				if (event.getRecurrence() == null){
					eventsInInterval.add(event);
				} else {
					reccurentEvents.addAll(Recurrence.generateRecurrentInstances(event, interval));
				}
			}
		}
		
		eventsInInterval.addAll(reccurentEvents);
		
		return eventsInInterval;
	}
	
	@Override
	public String toString() {
		return events.toString();
	}
}
