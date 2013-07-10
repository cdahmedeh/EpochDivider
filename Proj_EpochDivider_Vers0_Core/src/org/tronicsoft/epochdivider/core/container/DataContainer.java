package org.tronicsoft.epochdivider.core.container;

import java.util.ArrayList;
import java.util.List;

import org.tronicsoft.epochdivider.core.type.event.Event;
import org.tronicsoft.epochdivider.core.type.timeblock.TimeBlock;

public class DataContainer {
	// =-- Main Data Lists --= //
	private List<Event> events = new ArrayList<Event>();
	public List<Event> getEvents() {return events;}
	
	// =-- Easy Modifiers --= //
	
	public void emEventCreate(String eventTitle, TimeBlock timeBlock){
		Event newEvent = new Event();
		
		newEvent.setTitle(eventTitle);
		newEvent.setTimeBlock(timeBlock);
		
		events.add(newEvent);
	}
}
