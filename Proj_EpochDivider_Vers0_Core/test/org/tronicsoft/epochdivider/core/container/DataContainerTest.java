package org.tronicsoft.epochdivider.core.container;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;
import org.tronicsoft.epochdivider.core.type.event.Event;
import org.tronicsoft.epochdivider.core.type.timeblock.TimeBlock;

public class DataContainerTest {
	
	@Test
	public void testemEventCreate(){
		DataContainer dataContainer = new DataContainer();
		
		String eventTitle = "HelloWorld";
		TimeBlock timeBlock = new TimeBlock(DateTime.now(), DateTime.now().plusDays(1));
		dataContainer.emEventCreate(eventTitle, timeBlock);
		
		Event event = dataContainer.getEvents().get(0);
		
		assertEquals(eventTitle, event.getTitle());
		assertEquals(timeBlock, event.getTimeBlock());
	}
	
}
