package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;
import java.util.Random;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.DueThisViewContext;
import org.cdahmedeh.orgapp.types.context.DueTodayContext;
import org.cdahmedeh.orgapp.types.context.DueTomorrowContext;
import org.cdahmedeh.orgapp.types.context.NoContextContext;
import org.cdahmedeh.orgapp.types.context.NoDueDateContext;
import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

/**
 * Contains method to generate some test data.
 * 
 * @author Ahmed El-Hajjar
 */
public class TestDataGenerator {
	
	/**
	 * Generates an instance of DataContainer with some example data.
	 */
	public static DataContainer generateDataContainer(){
		DataContainer dataContainer = new DataContainer();
	
		//Set the view for the calendar.
		View view = new View(new LocalDate(2013, 04, 21), new LocalDate(2013, 04, 27));
		dataContainer.setView(view);
		
		//Generate some contexts
		ArrayList<Context> contextList = new ArrayList<>();
		
		//Default contexts
		contextList.add(new AllContextsContext());
		contextList.add(new NoContextContext());
		
		contextList.add(new DueTodayContext());
		contextList.add(new DueTomorrowContext());
		contextList.add(new DueThisViewContext());
		contextList.add(new NoDueDateContext());
		
		Context faithContext = new Context("Faith");
		faithContext.setGoal(view, Duration.standardMinutes((long) (5.5*60)));
		contextList.add(faithContext);
		
		Context sleepContext = new Context("Sleep");
		sleepContext.setGoal(view, Duration.standardMinutes((long) (49*60)));
		contextList.add(sleepContext);
		
		Context relaxingContext = new Context("Relaxing");
		relaxingContext.setGoal(view, Duration.standardMinutes((long) (3.5*60)));
		contextList.add(relaxingContext);
		
		Context exerciseContext = new Context("Exercise");
		exerciseContext.setGoal(view, Duration.standardMinutes((long) (2.5*60)));
		contextList.add(exerciseContext);
		
		Context studyContext = new Context("Study");
		studyContext.setGoal(view, Duration.standardMinutes((long) (19*60)));
		contextList.add(studyContext);
	
		Context coursesContext = new Context("Courses");
		coursesContext.setGoal(view, Duration.standardMinutes((long) (21*60)));
		contextList.add(coursesContext);
		
		Context miscContext = new Context("Misc");
		miscContext.setGoal(view, Duration.standardMinutes((long) (1*60)));
		contextList.add(miscContext);
	
		Context projectContext = new Context("Projects");
		projectContext.setGoal(view, Duration.standardMinutes((long) (15*60)));
		contextList.add(projectContext);
		
		Context readingContext = new Context("Reading");
		readingContext.setGoal(view, Duration.standardMinutes((long) (8*60)));
		contextList.add(readingContext);
		
		//Generate some tasks
		ArrayList<Task> taskList = new ArrayList<>();
		
		Task task01 = new Task("Clean car");
		task01.setContext(miscContext);
		task01.setDue(DateTime.now().toDateMidnight().plusDays(1).toDateTime());
		task01.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,25,13,30)));
		task01.setEstimate(Duration.standardHours(3));
		taskList.add(task01);
		
		Task task02 = new Task("Data persistence prototype for Epoch Divider");
		task02.setContext(projectContext);
		task02.setDue(new DateTime(2013, 04, 30, 15, 00));
		task02.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,17,00)));
		task02.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,23,00),new DateTime(2013,04,23,15,15)));
		task02.setEstimate(Duration.standardHours(40));
		taskList.add(task02);
		
		Task task03 = new Task("Read - Art of Unix Programming");
		task03.setContext(readingContext);
		task03.setEstimate(Duration.standardHours(15));
		taskList.add(task03);
		
		Task task04 = new Task("Test Contexts with No Contexts");
		task04.setContext(null);
		taskList.add(task04);
		
		//Put the task and context lists into the container.
		dataContainer.loadContexts(contextList);
		dataContainer.loadTasks(taskList);
		
		return dataContainer;
	}
	
	/**
	 * Generate an instance of dataContainer with plenty of data to stress test
	 * the application. The amount of data is somewhat realistic.
	 */
	public static DataContainer generateDataContainerWithLotsOfData(){
		DataContainer dataContainer = new DataContainer();
		
		//Set the view for the calendar.
		View view = new View(new LocalDate(2013, 04, 21), new LocalDate(2013, 04, 27));
		dataContainer.setView(view);
		
		//Generate some contexts
		ArrayList<Context> contextList = new ArrayList<>();
		
		//Default contexts
		contextList.add(new AllContextsContext());
		contextList.add(new NoContextContext());
		
		contextList.add(new DueTodayContext());
		contextList.add(new DueTomorrowContext());
		contextList.add(new DueThisViewContext());
		contextList.add(new NoDueDateContext());
		
		//Generate 100 contexts.
		for (int i = 0; i < 100; i++) {
			Context context = new Context("Context " + i);
			context.setGoal(view, Duration.standardMinutes(new Random().nextInt(10*60)));
			contextList.add(context);
		}
		
		//Generate some 2000 tasks
		ArrayList<Task> taskList = new ArrayList<>();
		
		for (int i = 0; i < 2000; i++) {
			Task task = new Task("Task " + i);
			task.setEstimate(Duration.standardMinutes(new Random().nextInt(20*60)));
			while (task.getContext().equals(new NoContextContext()) || !task.getContext().isSelectable()){
				task.setContext(contextList.get(new Random().nextInt(contextList.size())));
			}
			if (new Random().nextInt(5) == 0) {
				task.setDue(DateTime.now().plusMinutes(new Random().nextInt(1000)*30));
			}
			if (new Random().nextInt(3) == 0) {
				task.setTitle("Event " + i);
				task.setEvent(true);
			}
			task.setCompleted(new Random().nextBoolean());
			taskList.add(task);
		}
		
		//Assign 300 timeblocks
		for (int i = 0; i < 400; i++) {
			taskList.get(new Random().nextInt(taskList.size())).assignToTimeBlock(new TimeBlock(view.getStartDate().toDateTimeAtStartOfDay().plusMinutes(new Random().nextInt(1000)*30), Duration.standardMinutes((new Random().nextInt(12)+1)*30)));
		}
		
		//Put the task and context lists into the container.
		dataContainer.loadContexts(contextList);
		dataContainer.loadTasks(taskList);
		
		return dataContainer;
		
	}
}
