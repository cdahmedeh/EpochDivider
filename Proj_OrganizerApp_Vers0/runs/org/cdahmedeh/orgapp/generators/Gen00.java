package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.schedule.Category;
import org.cdahmedeh.orgapp.schedule.Event;
import org.cdahmedeh.orgapp.schedule.Recurrence;
import org.cdahmedeh.orgapp.schedule.RecurrenceType;
import org.cdahmedeh.orgapp.schedule.RecurringEvent;
import org.cdahmedeh.orgapp.schedule.Schedule;
import org.cdahmedeh.orgapp.schedule.Task;
import org.cdahmedeh.orgapp.view.View;
import org.eclipse.swt.graphics.RGB;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class Gen00 {

	public static Schedule generateSchedule() {
		Schedule schedule = new Schedule();
		
//		Event event1 = new Event("event1", new DateTime(2013, 2, 17, 12, 00), new DateTime(2013, 2, 17, 13, 0));
//		Event event2 = new Event("event2", new DateTime(2013, 2, 18, 12, 30), new DateTime(2013, 2, 18, 13, 0));
//		Event event3 = new Event("event3", new DateTime(2011, 2, 19, 12, 30), new DateTime(2011, 2, 19, 13, 0));
//		Event event4 = new Event("event4", new DateTime(2013, 2, 19, 8, 30), new DateTime(2013, 2, 19, 16, 0));
//		Event event5 = new Event("event5", new DateTime(2013, 2, 20, 12, 30), new DateTime(2013, 2, 20, 13, 0));
//		Event event6 = new Event("event6", new DateTime(2013, 2, 21, 13, 00), new DateTime(2013, 2, 21, 15, 0));
		
		Event sleep1 = new Event("Sleep", new DateTime(2013, 2, 18, 23, 00), new DateTime(2013, 2, 19, 5, 30));
		
		Recurrence sleepRecurrence = new Recurrence();
		sleepRecurrence.setType(RecurrenceType.DAILY);
		sleep1.setRecurrence(sleepRecurrence);
		sleep1.setCategory(new Category("Sleep", new RGB(200, 200, 255)));
		
		schedule.addEvent(sleep1);
		
		View view = new View(new LocalDate(2013, 2, 17), new LocalDate(2013, 2, 17+6));
		
//		return schedule.getEventsWithinInterval(view.getInterval());
		
		Task task1 = new Task();
		task1.setTaskName("Task1");
//		task1.setDescription("project.task1");
		
		Task task2 = new Task();
		task2.setTaskName("Task2");
//		task2.setDescription("project.task2");
		
		schedule.addTask(task1);
		schedule.addTask(task2);
		
		return schedule;
	}

	public static View getView() {
		// TODO Auto-generated method stub
		return new View(new LocalDate(2013, 2, 17), new LocalDate(2013, 2, 17+6));
	}

	public static Schedule generateTasks() {
		Schedule schedule = new Schedule();
		
		Task task1 = new Task();
		task1.setTaskName("Task1");
//		task1.setDescription("project.task1");
		
		Task task2 = new Task();
		task2.setTaskName("Task2");
//		task2.setDescription("project.task2");
		
		schedule.addTask(task1);
		schedule.addTask(task2);
		
		return schedule;
	}

}
