package com.tronicdream.epochdivider.generators;

import java.util.ArrayList;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.tronicdream.epochdivider.core.container.DataContainer;
import com.tronicdream.epochdivider.core.tools.DateReference;
import com.tronicdream.epochdivider.core.types.context.AllContextsContext;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.context.DueThisViewContext;
import com.tronicdream.epochdivider.core.types.context.DueTodayContext;
import com.tronicdream.epochdivider.core.types.context.DueTomorrowContext;
import com.tronicdream.epochdivider.core.types.context.NoContextContext;
import com.tronicdream.epochdivider.core.types.context.NoDueDateContext;
import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;
import com.tronicdream.epochdivider.core.types.view.View;

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
		View view = new View();
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
		contextList.add(faithContext);
		
		Context sleepContext = new Context("Sleep");
		contextList.add(sleepContext);
		
		Context relaxingContext = new Context("Relaxing");
		contextList.add(relaxingContext);
		
		Context exerciseContext = new Context("Exercise");
		contextList.add(exerciseContext);
		
		Context studyContext = new Context("Study");
		contextList.add(studyContext);
	
		Context coursesContext = new Context("Courses");
		contextList.add(coursesContext);
		
		Context miscContext = new Context("Misc");
		contextList.add(miscContext);
	
		Context projectContext = new Context("Projects");
		contextList.add(projectContext);
		
		Context readingContext = new Context("Reading");
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
		dataContainer.setTaskContexts(contextList);
		dataContainer.setTasks(taskList);
		
		return dataContainer;
	}
	
	/**
	 * Generate an instance of dataContainer with data designed to stress 
	 * the application. The amount of data is somewhat realistic.
	 */
	public static DataContainer generateDataContainerWithLotsOfData(){
		DataContainer dataContainer = new DataContainer();
		
		//Set the view for the calendar.
		View view = new View();
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
			}
			task.setCompleted(new Random().nextBoolean());
			taskList.add(task);
		}
		
		//Assign 300 timeblocks
		for (int i = 0; i < 400; i++) {
			taskList.get(new Random().nextInt(taskList.size())).assignToTimeBlock(new TimeBlock(view.getStartDate().toDateTimeAtStartOfDay().plusMinutes(new Random().nextInt(1000)*30), Duration.standardMinutes((new Random().nextInt(12)+1)*30)));
		}
		
		//Put the task and context lists into the container.
		dataContainer.setTaskContexts(contextList);
		dataContainer.setTasks(taskList);
		
		return dataContainer;
		
	}
	
	/**
	 * Generate an instance of dataContainer with data designed to stress 
	 * the application. The amount of data is not realistic.
	 */
	public static DataContainer generateDataContainerWithTooMuchData(){
		DataContainer dataContainer = new DataContainer();
		
		//Set the view for the calendar.
		View view = new View();
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
			}
			task.setCompleted(new Random().nextBoolean());
			taskList.add(task);
		}
		
		//Assign 7000 timeblocks
		for (int i = 0; i < 7000; i++) {
			taskList.get(new Random().nextInt(taskList.size())).assignToTimeBlock(new TimeBlock(view.getStartDate().toDateTimeAtStartOfDay().plusMinutes(new Random().nextInt(1000)*30), Duration.standardMinutes((new Random().nextInt(12)+1)*30)));
		}
		
		//Put the task and context lists into the container.
		dataContainer.setTaskContexts(contextList);
		dataContainer.setTasks(taskList);
		
		return dataContainer;
		
	}
	
	/**
	 * Generate an instance of dataContainer with data designed to stress 
	 * the application. The amount of data is not realistic.
	 */
	public static DataContainer generateDataContainerWithTooMuchDataSpreadOut(){
		DataContainer dataContainer = new DataContainer();
		
		//Set the view for the calendar.
		View view = new View();
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
			}
			task.setCompleted(new Random().nextBoolean());
			taskList.add(task);
		}
		
		//Assign 7000 timeblocks
		for (int i = 0; i < 7000; i++) {
			taskList.get(new Random().nextInt(taskList.size())).assignToTimeBlock(new TimeBlock(view.getStartDate().toDateTimeAtStartOfDay().plusMinutes(new Random().nextInt(100000)*30), Duration.standardMinutes((new Random().nextInt(12)+1)*30)));
		}
		
		//Put the task and context lists into the container.
		dataContainer.setTaskContexts(contextList);
		dataContainer.setTasks(taskList);
		
		return dataContainer;
		
	}
	
	/**
	 * Generate an instance of dataContainer with data designed to stress 
	 * the calendar renderer.
	 */
	public static DataContainer generateDataContainerForStressingCalendarPainter(){
		DataContainer dataContainer = new DataContainer();
		
		//Set the view for the calendar.
		View view = new View();
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
		dataContainer.setTaskContexts(contextList);
		dataContainer.setTasks(taskList);
		
		return dataContainer;

	}
	
	/**
	 * Generate an instance of dataContainer with data that resembles a
	 * real schedule of a certain person.
	 */
	public static DataContainer generateDataContainerWithAhmedsData(){
		DataContainer dataContainer = new DataContainer();
	
		//Set the view for the calendar.
		View view = new View();
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
		
		Context exerciseContext = new Context("Exercise");
		contextList.add(exerciseContext);
		
		Context studyContext = new Context("Study");
		contextList.add(studyContext);
	
		Context miscContext = new Context("Misc");
		contextList.add(miscContext);
	
		Context choresContext = new Context("Chores");
		contextList.add(choresContext);
		
		Context tinkeringContext = new Context("Tinkering");
		contextList.add(tinkeringContext);
		
		Context gamingContext = new Context("Gaming");
		contextList.add(gamingContext);
		
		Context techReadingContext = new Context("Tech Reading");
		contextList.add(techReadingContext);
		
		Context religionReadingContext = new Context("Religion Reading");
		contextList.add(religionReadingContext);
		
		Context projectEpochDividerContext = new Context("Epoch Divider");
		contextList.add(projectEpochDividerContext);
		
		Context projectOrbitHubContext = new Context("OrbitHub");
		contextList.add(projectOrbitHubContext);
		
		Context projectIslamWebContext = new Context("Islam Web Portal");
		contextList.add(projectIslamWebContext);
		
		Context projectMisc = new Context("Other Projects");
		contextList.add(projectMisc);		
		
		ArrayList<Task> taskList = new ArrayList<>();
		
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
		dataContainer.setTaskContexts(contextList);
		dataContainer.setTasks(taskList);
		
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
