package org.cdahmedeh.orgapp.runs;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.Schedule;
import org.cdahmedeh.orgapp.view.View;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class Run00 {
	public static void main(String[] args) {
		Schedule schedule = new Schedule();
		
		Event event1 = new Event("event1", new DateTime(2013, 1, 12, 12, 30), new DateTime(2013, 1, 12, 13, 0));
		Event event2 = new Event("event2", new DateTime(2013, 1, 19, 12, 30), new DateTime(2013, 1, 20, 13, 0));
		Event event3 = new Event("event3", new DateTime(2011, 1, 14, 12, 30), new DateTime(2011, 1, 14, 13, 0));
		
		schedule.addEvent(event1);
		schedule.addEvent(event2);
		schedule.addEvent(event3);
		
		View view = new View(new LocalDate(2013, 1, 11), new LocalDate(2013, 1, 19));
		
		ArrayList<Event> eventsInInterval = schedule.getEventsWithinInterval(view.getInterval());
		
		System.out.println(eventsInInterval);
	}
}
