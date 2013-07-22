package com.tronicdream.core.container;

import java.util.List;

import org.joda.time.DateTime;

import com.tronicdream.core.type.category.Category;
import com.tronicdream.core.type.context.Context;
import com.tronicdream.core.type.event.Event;
import com.tronicdream.core.type.timeblock.TimeBlock;

public class DataContainer {

	/* - Main Data Lists - */

	private List<TimeBlock> timeBlocks;

	private List<Event> events;
	private List<Context> eventContexts;
	private List<Category> eventCategories;

	
	/* - Easy Modifiers - */

	public TimeBlock emTimeBlockNew(DateTime startTime, DateTime endTime) {
		TimeBlock timeBlock = new TimeBlock(startTime, endTime);
		timeBlocks.add(timeBlock);
		return timeBlock;
	}

	public Event emEventNew() {
		Event event = new Event();
		events.add(event);
		return event;
	}
}
