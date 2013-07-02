package org.cdahmedeh.orgapp.generators;

import java.util.ArrayList;
import java.util.Random;

import org.cdahmedeh.orgapp.tools.DateReference;
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
import org.cdahmedeh.orgapp.types.task.TaskType;
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
		task01.setDue(DateReference.getNow().toDateMidnight().plusDays(1).toDateTime());
		task01.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,25,13,30)));
		task01.setEstimate(Duration.standardHours(3));
		taskList.add(task01);
		
		Task task02 = new Task("Data persistence prototype for Epoch Divider");
		task02.setContext(projectContext);
		task02.setDue(new DateTime(2013, 04, 30, 15, 00));
		task02.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,17,00)));
		task02.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,23,15,00),new DateTime(2013,04,23,23,15)));
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
	 * Generate an instance of dataContainer with data designed to stress 
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
				task.setDue(DateReference.getNow().plusMinutes(new Random().nextInt(1000)*30));
			}
			if (new Random().nextInt(3) == 0) {
				task.setTitle("Event " + i);
				task.setType(TaskType.EVENT);
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
	
	/**
	 * Generate an instance of dataContainer with data designed to stress 
	 * the application. The amount of data is not realistic.
	 */
	public static DataContainer generateDataContainerWithTooMuchData(){
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
		
		//Generate 300 contexts.
		for (int i = 0; i < 300; i++) {
			Context context = new Context("Context " + i);
			context.setGoal(view, Duration.standardMinutes(new Random().nextInt(10*60)));
			contextList.add(context);
		}
		
		//Generate some 50000 tasks
		ArrayList<Task> taskList = new ArrayList<>();
		
		for (int i = 0; i < 50000; i++) {
			Task task = new Task("Task " + i);
			task.setEstimate(Duration.standardMinutes(new Random().nextInt(20*60)));
			while (task.getContext().equals(new NoContextContext()) || !task.getContext().isSelectable()){
				task.setContext(contextList.get(new Random().nextInt(contextList.size())));
			}
			if (new Random().nextInt(5) == 0) {
				task.setDue(DateReference.getNow().plusMinutes(new Random().nextInt(1000)*30));
			}
			if (new Random().nextInt(3) == 0) {
				task.setTitle("Event " + i);
				task.setType(TaskType.EVENT);
			}
			task.setCompleted(new Random().nextBoolean());
			taskList.add(task);
		}
		
		//Assign 7000 timeblocks
		for (int i = 0; i < 7000; i++) {
			taskList.get(new Random().nextInt(taskList.size())).assignToTimeBlock(new TimeBlock(view.getStartDate().toDateTimeAtStartOfDay().plusMinutes(new Random().nextInt(1000)*30), Duration.standardMinutes((new Random().nextInt(12)+1)*30)));
		}
		
		//Put the task and context lists into the container.
		dataContainer.loadContexts(contextList);
		dataContainer.loadTasks(taskList);
		
		return dataContainer;
		
	}
	
	/**
	 * Generate an instance of dataContainer with data designed to stress 
	 * the application. The amount of data is not realistic.
	 */
	public static DataContainer generateDataContainerWithTooMuchDataSpreadOut(){
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
		
		//Generate 300 contexts.
		for (int i = 0; i < 300; i++) {
			Context context = new Context("Context " + i);
			context.setGoal(view, Duration.standardMinutes(new Random().nextInt(10*60)));
			contextList.add(context);
		}
		
		//Generate some 50000 tasks
		ArrayList<Task> taskList = new ArrayList<>();
		
		for (int i = 0; i < 50000; i++) {
			Task task = new Task("Task " + i);
			task.setEstimate(Duration.standardMinutes(new Random().nextInt(20*60)));
			while (task.getContext().equals(new NoContextContext()) || !task.getContext().isSelectable()){
				task.setContext(contextList.get(new Random().nextInt(contextList.size())));
			}
			if (new Random().nextInt(5) == 0) {
				task.setDue(DateReference.getNow().plusMinutes(new Random().nextInt(1000)*30));
			}
			if (new Random().nextInt(3) == 0) {
				task.setTitle("Event " + i);
				task.setType(TaskType.EVENT);
			}
			task.setCompleted(new Random().nextBoolean());
			taskList.add(task);
		}
		
		//Assign 7000 timeblocks
		for (int i = 0; i < 7000; i++) {
			taskList.get(new Random().nextInt(taskList.size())).assignToTimeBlock(new TimeBlock(view.getStartDate().toDateTimeAtStartOfDay().plusMinutes(new Random().nextInt(100000)*30), Duration.standardMinutes((new Random().nextInt(12)+1)*30)));
		}
		
		//Put the task and context lists into the container.
		dataContainer.loadContexts(contextList);
		dataContainer.loadTasks(taskList);
		
		return dataContainer;
		
	}
	
	/**
	 * Generate an instance of dataContainer with data designed to stress 
	 * the calendar renderer.
	 */
	public static DataContainer generateDataContainerForStressingCalendarPainter(){
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
		
		//Generate 1 contexts.
		Context context = new Context("Context");
		contextList.add(context);
		
		//Generate 1 task.
		ArrayList<Task> taskList = new ArrayList<>();
		
		Task task = new Task("Task");
		taskList.add(task);
		
		//Assign 350 timeblocks to that task
		for (int i = 0; i < 350; i++) {
			task.assignToTimeBlock(new TimeBlock(view.getStartDate().toDateTimeAtStartOfDay().plusMinutes(i*30), Duration.standardMinutes(30)));
		}
		
		//Put the task and context lists into the container.
		dataContainer.loadContexts(contextList);
		dataContainer.loadTasks(taskList);
		
		return dataContainer;

	}
	
	/**
	 * Generate an instance of dataContainer with data that resembles a
	 * real schedule of a certain person.
	 */
	public static DataContainer generateDataContainerWithAhmedsData(){
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
		faithContext.setGoal(view, Duration.standardMinutes((long) (5*60)));
		contextList.add(faithContext);
		
		Context sleepContext = new Context("Sleep");
		sleepContext.setGoal(view, Duration.standardMinutes((long) (49*60)));
		contextList.add(sleepContext);
		
		Context catchingUpContext = new Context("Catching Up");
		catchingUpContext.setGoal(view, Duration.standardMinutes((long) (3.5*60)));
		contextList.add(catchingUpContext);
		
		Context relaxingContext = new Context("Relaxing");
		relaxingContext.setGoal(view, Duration.standardMinutes((long) (3.5*60)));
		contextList.add(relaxingContext);
		
		Context exerciseContext = new Context("Exercise");
		exerciseContext.setGoal(view, Duration.standardMinutes((long) (2.5*60)));
		contextList.add(exerciseContext);
		
		Context studyContext = new Context("Study");
		studyContext.setGoal(view, Duration.standardMinutes((long) (0*60)));
		contextList.add(studyContext);
	
		Context coursesContext = new Context("Courses");
		coursesContext.setGoal(view, Duration.standardMinutes((long) (0*60)));
		contextList.add(coursesContext);
		
		Context workContext = new Context("Work");
		workContext.setGoal(view, Duration.standardMinutes((long) (40*60)));
		contextList.add(workContext);
		
		Context transportContext = new Context("Transportation");
		transportContext.setGoal(view, Duration.standardMinutes((long) (11*60)));
		contextList.add(transportContext);
		
		Context miscContext = new Context("Misc");
		miscContext.setGoal(view, Duration.standardMinutes((long) (1.5*60)));
		contextList.add(miscContext);
	
		Context choresContext = new Context("Chores");
		choresContext.setGoal(view, Duration.standardMinutes((long) (2*60)));
		contextList.add(choresContext);
		
		Context tinkeringContext = new Context("Tinkering");
		tinkeringContext.setGoal(view, Duration.standardMinutes((long) (4.5*60)));
		contextList.add(tinkeringContext);
		
		Context gamingContext = new Context("Gaming");
		gamingContext.setGoal(view, Duration.standardMinutes((long) (7*60)));
		contextList.add(gamingContext);
		
		Context techReadingContext = new Context("Tech Reading");
		techReadingContext.setGoal(view, Duration.standardMinutes((long) (4*60)));
		contextList.add(techReadingContext);
		
		Context religionReadingContext = new Context("Religion Reading");
		religionReadingContext.setGoal(view, Duration.standardMinutes((long) (4*60)));
		contextList.add(religionReadingContext);
		
		Context projectEpochDividerContext = new Context("Epoch Divider");
		projectEpochDividerContext.setGoal(view, Duration.standardMinutes((long) (13*60)));
		contextList.add(projectEpochDividerContext);
		
		Context projectOrbitHubContext = new Context("OrbitHub");
		projectOrbitHubContext.setGoal(view, Duration.standardMinutes((long) (5*60)));
		contextList.add(projectOrbitHubContext);
		
		Context projectIslamWebContext = new Context("Islam Web Portal");
		projectIslamWebContext.setGoal(view, Duration.standardMinutes((long) (3*60)));
		contextList.add(projectIslamWebContext);
		
		Context projectMisc = new Context("Other Projects");
		projectMisc.setGoal(view, Duration.standardMinutes((long) (0*60)));
		contextList.add(projectMisc);		
		
		ArrayList<Task> taskList = new ArrayList<>();
		
		//Generate some events
		for (int i=0; i<8; i++){
		Task sleepEvent = new Task("Sleep");
		sleepEvent.setContext(sleepContext);
		sleepEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,20,23,00).plusDays(i),new DateTime(2013,04,21,6,00).plusDays(i)));
		sleepEvent.setType(TaskType.EVENT);
		taskList.add(sleepEvent);
		
		Task faithEvent = new Task("Faith");
		faithEvent.setContext(faithContext);
		faithEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,21,6,00).plusDays(i),new DateTime(2013,04,21,6,30).plusDays(i)));
		faithEvent.setType(TaskType.EVENT);
		taskList.add(faithEvent);
		
		Task catchingUpEvent = new Task("Catching Up");
		catchingUpEvent.setContext(catchingUpContext);
		catchingUpEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,21,6,30).plusDays(i),new DateTime(2013,04,21,7,00).plusDays(i)));
		catchingUpEvent.setType(TaskType.EVENT);
		taskList.add(catchingUpEvent);
		}
		
		for (int i=0; i<5; i++){
			Task workEvent = new Task("Work");
			workEvent.setContext(workContext);
			if (i<3) {
				workEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,9,00).plusDays(i),new DateTime(2013,04,22,17,00).plusDays(i)));
			} else if (i==3) {
				workEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,8,00).plusDays(i),new DateTime(2013,04,22,17,00).plusDays(i)));
			} else if (i==4) {
				workEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,8,00).plusDays(i),new DateTime(2013,04,22,12,00).plusDays(i)));
				workEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,14,00).plusDays(i),new DateTime(2013,04,22,17,00).plusDays(i)));
			}
			workEvent.setType(TaskType.EVENT);
			taskList.add(workEvent);
			
			Task driveFirstEvent = new Task("Drive");
			driveFirstEvent.setContext(transportContext);
			if (i<3) {
				driveFirstEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,8,00).plusDays(i),new DateTime(2013,04,22,9,00).plusDays(i)));
			} else {
				driveFirstEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,7,00).plusDays(i),new DateTime(2013,04,22,8,00).plusDays(i)));
			}
			driveFirstEvent.setType(TaskType.EVENT);
			taskList.add(driveFirstEvent);
			
			Task driveLastEvent = new Task("Drive");
			driveLastEvent.setContext(transportContext);
			driveLastEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,22,17,00).plusDays(i),new DateTime(2013,04,22,18,00).plusDays(i)));
			driveLastEvent.setType(TaskType.EVENT);
			taskList.add(driveLastEvent);
		}
		
		Task faithEvent = new Task("Friday Prayer");
		faithEvent.setContext(faithContext);
		faithEvent.assignToTimeBlock(new TimeBlock(new DateTime(2013,04,26,12,00),new DateTime(2013,04,26,14,00)));
		faithEvent.setType(TaskType.EVENT);
		taskList.add(faithEvent);
		
		//Generate some tasks
		Task task01 = new Task("Refactor code for Epoch Divider");
		task01.setContext(projectEpochDividerContext);
//		task01.setDue(DateReference.getNow().toDateMidnight().plusDays(1).toDateTime());
		task01.setEstimate(Duration.standardHours(10));
		taskList.add(task01);
		
		Task task02 = new Task("Add contexts grouping");
		task02.setContext(projectEpochDividerContext);
//		task02.setDue(DateReference.getNow().toDateMidnight().plusDays(1).toDateTime());
		task02.setEstimate(Duration.standardHours(3));
		taskList.add(task02);
		
		Task task02a = new Task("Review Websites List");
		task02a.setContext(projectIslamWebContext);
//		task02.setDue(DateReference.getNow().toDateMidnight().plusDays(1).toDateTime());
		task02a.setEstimate(Duration.standardHours(3));
		taskList.add(task02a);
		
		Task task02b = new Task("Review Server Settings");
		task02b.setContext(projectOrbitHubContext);
//		task02.setDue(DateReference.getNow().toDateMidnight().plusDays(1).toDateTime());
		task02b.setEstimate(Duration.standardHours(3));
		taskList.add(task02b);
		
		Task task03 = new Task("The Art of Unix Programming - Eric S. Raymond");
		task03.setContext(techReadingContext);
		task03.setEstimate(Duration.standardHours(20));
		taskList.add(task03);
		
		Task task04 = new Task("The Quran - Saheeh International Translation");
		task04.setContext(religionReadingContext);
		task04.setEstimate(Duration.standardHours(60));
		taskList.add(task04);
		
		Task task05 = new Task("Kitab At-Tawhid - ibn Abd Al-Wahhab");
		task05.setContext(religionReadingContext);
		task05.setEstimate(Duration.standardHours(10));
		taskList.add(task05);

		Task task06 = new Task("Milestones - Sayyid Qutb");
		task06.setContext(religionReadingContext);
		task06.setEstimate(Duration.standardHours(30));
		taskList.add(task06);
		
		//Put the task and context lists into the container.
		dataContainer.loadContexts(contextList);
		dataContainer.loadTasks(taskList);
		
		return dataContainer;
	}

	public static ArrayList<Context> generateDefaultContexts() {
		//Generate some contexts
		ArrayList<Context> contextList = new ArrayList<>();
		
		//Default contexts
		contextList.add(new AllContextsContext());
		contextList.add(new NoContextContext());
		
		contextList.add(new DueTodayContext());
		contextList.add(new DueTomorrowContext());
		contextList.add(new DueThisViewContext());
		contextList.add(new NoDueDateContext());
		
		return contextList;
	}
	
}
