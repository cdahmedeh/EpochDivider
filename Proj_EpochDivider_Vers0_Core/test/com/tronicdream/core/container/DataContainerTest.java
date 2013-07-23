package com.tronicdream.core.container;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

import com.tronicdream.core.type.event.Event;
import com.tronicdream.core.type.timeblock.TimeBlock;

public class DataContainerTest {

	@Test
	public void testEmTimeBlockNew() {
		DataContainer dataContainer = new DataContainer();
		DateTime startTime = new DateTime(234209842L);
		DateTime endTime = new DateTime(9342342342L);
		TimeBlock newTimeBlock = dataContainer.emTimeBlockNew(startTime, endTime);

		// Id should be set (not -1).
		assertEquals(0, newTimeBlock.getId());
		
		// TimeBlock should have times set correctly
		assertEquals(startTime, newTimeBlock.getStartTime());
		assertEquals(endTime, newTimeBlock.getEndTime());
		
		// Event should be added to the end of the events list.
		List<TimeBlock> timeBlocks = dataContainer.getTimeBlocks();
		assertEquals(1, timeBlocks.size());
		assertEquals(newTimeBlock, timeBlocks.get(timeBlocks.size()-1));
	}

	@Test
	public void testEmEventNew() {
		DataContainer dataContainer = new DataContainer();
		Event newEvent = dataContainer.emEventNew();

		// Id should be set (not -1).
		assertEquals(0, newEvent.getId());
		
		// Event should be completely blank
		assertEquals("", newEvent.getTitle());
		assertNull(newEvent.getContext());
		assertNull(newEvent.getTimeBlock());
		
		// Event should be added to the end of the events list.
		List<Event> events = dataContainer.getEvents();
		assertEquals(1, events.size());
		assertEquals(newEvent, events.get(events.size()-1));
	}

	@Test
	public void testEmEventSetTimeBlock() {
		DataContainer dataContainer = new DataContainer();
		Event event = new Event();
		TimeBlock timeBlock = new TimeBlock();
		dataContainer.emEventSetTimeBlock(event, timeBlock);
		
		//Event should have TimeBlock
		assertEquals(timeBlock, event.getTimeBlock());
		
		//TimeBlock should be owned by event
		assertEquals(event, timeBlock.getOwner());
	}

}
