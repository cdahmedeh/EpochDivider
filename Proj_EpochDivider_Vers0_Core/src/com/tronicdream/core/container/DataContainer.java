package com.tronicdream.core.container;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.tronicdream.core.type.event.Event;
import com.tronicdream.core.type.timeblock.TimeBlock;

/**
 * A {@link DataContainer} instance contains all the necessary data for one
 * user in Epoch Divider. It contains the following:
 * 
 *  - Lists of all the users data such as tasks, events and contexts.
 *  - Instances of UI states such as currently selected context.
 * 
 * All data modification is expected to be done through the DataContainer with
 * the methods prefixed with em.
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {

	/* - Main Data Lists - */

	private List<TimeBlock> timeBlocks = new ArrayList<>();
	public List<TimeBlock> getTimeBlocks() {return timeBlocks;}
	
	private List<Event> events = new ArrayList<>();
	public List<Event> getEvents() {return events;}
	
	
	/* - Id Counters - */
	
	private int timeBlockIdCounter = 0;
	private int eventIdCounter = 0;
	
	
	/* - Easy Modifiers - */

	/**
	 * Create a new TimeBlock with the provided start and end times.
	 */
	public TimeBlock emTimeBlockNew(DateTime startTime, DateTime endTime) {
		TimeBlock timeBlock = new TimeBlock();
		
		timeBlock.setId(timeBlockIdCounter++);
		timeBlock.setStartTime(startTime);
		timeBlock.setEndTime(endTime);
		
		timeBlocks.add(timeBlock);
		
		return timeBlock;
	}

	/**
	 * Create a new blank Event. 
	 */
	public Event emEventNew() {
		Event event = new Event();
		
		event.setId(eventIdCounter++);
		
		events.add(event);
		
		return event;
	}
	
	/**
	 * Assign the Event to the provided TimeBlock. 
	 */
	public void emEventSetTimeBlock(Event event, TimeBlock timeBlock) {
		event.setTimeBlock(timeBlock);
		timeBlock.setOwner(event);
	}
	
}
