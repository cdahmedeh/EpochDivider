package org.cdahmedeh.orgapp.autofinder;

import java.util.ArrayList;
import java.util.TreeMap;

import org.cdahmedeh.orgapp.generators.Gen00;
import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.EventType;
import org.cdahmedeh.orgapp.schedule.Schedule;
import org.cdahmedeh.orgapp.view.View;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class BlankSpaceFinder {
	public static void main(String[] args) {
		getEventsForBlank(Gen00.generateSchedule(), Gen00.getView());
		
	}

	public static ArrayList<Event> getEventsForBlank(Schedule schedule, View view) {
		ArrayList<Event> events = new ArrayList<>();
		
		DateTime now = new DateTime();
		
		TreeMap<Integer, Event> collisionMap = new TreeMap<>();
		
		LocalDate localDate = new LocalDate(view.getStart());
		
		LocalTime timeCount = new LocalTime(0, 0, 0);
		
		do{
		do{
			for (Event event: schedule.getEventsWithinInterval(view.getInterval())){
				if (event.getInterval().contains(localDate.toDateTime(timeCount))){
					collisionMap.put(new LocalTime(timeCount).get(DateTimeFieldType.minuteOfDay()), event);
				}
			}
			
			timeCount = timeCount.plusMinutes(30);
		} while(!timeCount.isEqual(new LocalTime(0, 0, 0)));
		
		timeCount = new LocalTime(0, 0, 0);
		
		do{
			if (collisionMap.get(timeCount.get(DateTimeFieldType.minuteOfDay())) == null 
//					&& collisionMap.get(timeCount.plusMinutes(29).get(DateTimeFieldType.minuteOfDay())) == null
					){
				System.out.println(timeCount);
				Event eventnew = new Event("empty", localDate.toDateTime(timeCount), localDate.toDateTime(timeCount).plusMinutes(30));
				eventnew.setEventType(EventType.MUTABLE_ASSIGNED);
				events.add(eventnew);
			}

			timeCount = timeCount.plusMinutes(30);
		} while(!timeCount.isEqual(new LocalTime(0, 0, 0)));
		
			localDate = localDate.plusDays(1);
			collisionMap.clear();
		} while(!localDate.isAfter(view.getEnd()));
		
		return events;
	}
}
