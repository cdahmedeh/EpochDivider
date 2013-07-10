package org.tronicsoft.epochdivider.core.type.event;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.joda.time.DateTime;
import org.junit.Test;
import org.tronicsoft.epochdivider.core.type.timeblock.TimeBlock;

public class EventTest {

	@Test
	public void testEventIdsShouldIncrementInOrder() throws Exception {
		//Reset id counter back to 0 using Java Reflection
		Field idCounterField = Event.class.getDeclaredField("idCounter");
		idCounterField.setAccessible(true);
		idCounterField.set(null, 0);

		assertEquals(0, new Event().getId());
		assertEquals(1, new Event().getId());
		assertEquals(2, new Event().getId());
		assertEquals(3, new Event().getId());
		assertEquals(4, new Event().getId());
	}

	@Test
	public void testSetAndGetEventName() {
		Event testEvent = new Event();

		testEvent.setTitle("Event");
		assertEquals("Event", testEvent.getTitle());

		testEvent.setTitle("Test Event");
		assertEquals("Test Event", testEvent.getTitle());

		testEvent.setTitle("SomeEvent");
		assertEquals("SomeEvent", testEvent.getTitle());

		testEvent.setTitle("   SomeEvent");
		assertEquals("   SomeEvent", testEvent.getTitle());

		testEvent.setTitle("   SomeEve  nn   tt   ");
		assertEquals("   SomeEve  nn   tt   ", testEvent.getTitle());

		testEvent.setTitle("");
		assertEquals("", testEvent.getTitle());

		testEvent.setTitle(" ");
		assertEquals(" ", testEvent.getTitle());

		testEvent.setTitle("  ");
		assertEquals("  ", testEvent.getTitle());
	}

	@Test
	public void testSettingEventWithNullNameShouldSetNameToBlankString(){
		Event testEvent = new Event();

		testEvent.setTitle(null);
		assertEquals("", testEvent.getTitle());
	}
	
	@Test
	public void testBasicSetAndGetForTimeBlock(){
		Event testEvent = new Event();
		
		TimeBlock timeBlock = new TimeBlock(DateTime.now(), DateTime.now());
		testEvent.setTimeBlock(timeBlock);
		
		assertEquals(timeBlock, testEvent.getTimeBlock());
	}

}
