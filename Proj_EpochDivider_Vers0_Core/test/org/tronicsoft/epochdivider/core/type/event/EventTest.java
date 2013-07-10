package org.tronicsoft.epochdivider.core.type.event;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Test;

public class EventTest {

	@Test
	public void testEventIdsShouldIncrementInOrder() throws Exception {
		//Reset id counter back to 0 using Java Reflection
		Field idCounterField = Event.class.getDeclaredField("idCounter");
		idCounterField.setAccessible(true);
		idCounterField.set(null, 0);

		assertEquals(0, new Event("").getId());
		assertEquals(1, new Event("").getId());
		assertEquals(2, new Event("").getId());
		assertEquals(3, new Event("").getId());
		assertEquals(4, new Event("").getId());
	}

	@Test
	public void testCreatingEventsWithNameShouldSetName() {
		assertEquals("Event", new Event("Event").getTitle());
		assertEquals("Test Event", new Event("Test Event").getTitle());
		assertEquals("SomeEvent", new Event("SomeEvent").getTitle());
		assertEquals("   SomeEvent", new Event("   SomeEvent").getTitle());
		assertEquals("   SomeEve  nn   tt   ", new Event("   SomeEve  nn   tt   ").getTitle());
		assertEquals("", new Event("").getTitle());
		assertEquals(" ", new Event(" ").getTitle());
		assertEquals("  ", new Event("  ").getTitle());
	}

	@Test
	public void testCreatingEventWithNullNameShouldSetNameToBlankString(){
		assertEquals("", new Event(null).getTitle());
	}

	@Test
	public void testSetAndGetEventName() {
		Event testEvent = new Event("");

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
		Event testEvent = new Event("Event");

		testEvent.setTitle(null);
		assertEquals("", testEvent.getTitle());
	}

}
