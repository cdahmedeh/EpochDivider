package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.calendar.View;
import org.cdahmedeh.orgapp.types.container.DataContainer;
import org.cdahmedeh.orgapp.types.context.AllContextsContext;
import org.cdahmedeh.orgapp.types.context.Context;
import org.cdahmedeh.orgapp.types.context.DueTodayContext;
import org.cdahmedeh.orgapp.types.context.DueTomorrowContext;
import org.cdahmedeh.orgapp.types.context.NoContextContext;
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
}
