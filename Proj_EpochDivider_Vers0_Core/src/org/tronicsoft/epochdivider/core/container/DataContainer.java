package org.tronicsoft.epochdivider.core.container;

import java.util.ArrayList;
import java.util.List;

import org.tronicsoft.epochdivider.core.type.event.Event;
import org.tronicsoft.epochdivider.core.type.timeblock.TimeBlock;

public class DataContainer {
	// =-- Main Data Lists --= //
	private List<Event> events = new ArrayList<Event>();

	
	// =-- Easy Modifiers --= //
	
	public void emEventCreate(String eventTitle, TimeBlock timeBlock){
		events.add(new Event(eventTitle));
	}
}
