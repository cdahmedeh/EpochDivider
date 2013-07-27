package com.tronicdream.epochdivider.core.container;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.tronicdream.epochdivider.core.tools.DateReference;
import com.tronicdream.epochdivider.core.types.context.Context;
import com.tronicdream.epochdivider.core.types.event.Event;
import com.tronicdream.epochdivider.core.types.task.Task;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlock;
import com.tronicdream.epochdivider.core.types.timeblock.TimeBlockConstants;
import com.tronicdream.epochdivider.core.types.view.View;

/**
 * A {@link DataContainer} instance contains all the necessary data for one
 * user in Epoch Divider. In other words, it is an in memory model of the a 
 * user database. It contains the following:
 * 
 *  - Lists of all the users data such as tasks, events and contexts.
 *  - Instances of UI states such as currently selected context.
 * 
 * All data modification is expected to be done through the DataContainer with
 * the methods prefixed with em.
 * 
 * Reading filtering data is done through the reader methods prefixed with rd.
 * 
 * Methods related to data validation are prefixed with vl.
 * 
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	
	/* - Constructs and Replacements - */
	
	public DataContainer() {
		timeBlocks = new ArrayList<>();
		
		tasks = new ArrayList<>();
		taskContexts = DataContainerHelper.generateDefaultTaskContexts();

		events = new ArrayList<>();
		eventContexts = DataContainerHelper.generateDefaultTaskContexts();
		
		view = new View();
		selectedTaskContext = taskContexts.get(0);
		
		dimPast = false;
		showCompleted = false;
	}
	
	public void replace(DataContainer dataContainer) {
		timeBlocks = dataContainer.getTimeBlocks();
		
		tasks = dataContainer.getTasks();
		taskContexts = dataContainer.getTaskContexts();

		events = dataContainer.getEvents();
		eventContexts = dataContainer.getEventContexts();
		
		view = dataContainer.getView();
		selectedTaskContext = dataContainer.getSelectedTaskContext();

		dimPast = dataContainer.getDimPast();
		showCompleted = dataContainer.getShowCompleted();
	}

	
	/* - Main Data Lists - */

	private List<TimeBlock> timeBlocks;
	public List<TimeBlock> getTimeBlocks() {return timeBlocks;}
	public void setTimeBlocks(List<TimeBlock> timeBlocks) {this.timeBlocks = timeBlocks;}
	
	private List<Task> tasks;
	public List<Task> getTasks() {return tasks;}
	public void setTasks(List<Task> tasks) {this.tasks = tasks;}
	
	private List<Context> taskContexts;
	public List<Context> getTaskContexts() {return taskContexts;}
	public void setTaskContexts(List<Context> taskContexts) {this.taskContexts = taskContexts;}

	private List<Event> events;
	public List<Event> getEvents() {return events;}
	public void setEvents(List<Event> events) {this.events = events;}
	
	private List<Context> eventContexts;
	public List<Context> getEventContexts() {return eventContexts;}
	public void setEventContexts(List<Context> eventContexts) {this.eventContexts = eventContexts;}
	
	
	/* - Id Counters for Main Data - */
	
	private int timeBlockIdCounter = 0;
	public int getTimeBlockIdCounter() {return timeBlockIdCounter;}
	public void setTimeBlockIdCounter(int timeBlockIdCounter) {this.timeBlockIdCounter = timeBlockIdCounter;}

	private int taskIdCounter = 0;
	public int gettaskIdCounter() {return taskIdCounter;}
	public void settaskIdCounter(int taskIdCounter) {this.taskIdCounter = taskIdCounter;}
	
	private int taskContextIdCounter = 0;
	public int gettaskContextIdCounter() {return taskContextIdCounter;}
	public void settaskContextIdCounter(int taskContextIdCounter) {this.taskContextIdCounter = taskContextIdCounter;}
	
	private int eventIdCounter = 0;
	public int getEventIdCounter() {return eventIdCounter;}
	public void setEventIdCounter(int eventIdCounter) {this.eventIdCounter = eventIdCounter;}
	
	private int eventContextIdCounter = 0;
	public int geteventContextIdCounter() {return eventContextIdCounter;}
	public void seteventContextIdCounter(int eventContextIdCounter) {this.eventContextIdCounter = eventContextIdCounter;}
	
	
	/* - Main UI States - */
	
	private View view;
	public View getView() {return view;}
	public void setView(View view) {this.view = view;}
	
	private Context selectedTaskContext;
	public Context getSelectedTaskContext() {return selectedTaskContext;}
	public void setSelectedTaskContext(Context selectedTaskContext) {this.selectedTaskContext = selectedTaskContext;}
	
	
	/* - Secondary UI States - */
	
	private boolean dimPast;
	public boolean getDimPast() {return dimPast;}
	public void setDimPast(boolean dimPast) {this.dimPast = dimPast;}

	private boolean showCompleted;
	public boolean getShowCompleted() {return showCompleted;}
	public void setShowCompleted(boolean showCompleted) {this.showCompleted = showCompleted;}
	
	
	/* - Readers - */

	public ArrayList<Context> rdSelectableTaskContexts() {
		ArrayList<Context> contextsList = new ArrayList<>();
		for (Context context: taskContexts) if (context.isSelectable()) contextsList.add(context);
		return contextsList;
	}

	
	/* - Easy Modifiers - */
	
	public TimeBlock emTimeBlockNew(DateTime startTime, DateTime endTime) {
		TimeBlock timeBlock = new TimeBlock();
		
		timeBlock.setId(timeBlockIdCounter++);
		timeBlock.setStart(startTime);
		timeBlock.setEnd(endTime);
		
		timeBlocks.add(timeBlock);
		
		return timeBlock;
	}

	public Task emTaskNew(){
		Task task = new Task();
	
		task.setId(taskContextIdCounter++);
		task.setTitle("");
		
		// Set the task context to the currently selected one only if it can
		// be assigned to the task.
		if (getSelectedTaskContext().isSelectable()){
			task.setContext(getSelectedTaskContext());
		}
		
		tasks.add(task);
		
		return task;
	}
	
	public boolean emTaskRemove(Task task) {
		return tasks.remove(task);
	}

	public boolean emTaskSetContext(Task task, Context context) {
		if (context.isSelectable()){
			task.setContext(context);
			return true;
		} else {
			return false;
		}
	}
	
	public TimeBlock emTaskSetNewTimeBlock(Task task) {
		TimeBlock timeBlock = emTimeBlockNew(DateReference.getNow(), DateReference.getNow().plus(TimeBlockConstants.DEFAULT_DURATION));
		
		task.assignToTimeBlock(timeBlock);
		timeBlock.setOwner(task);

		return timeBlock;
	}
	
	public Context emTaskContextNew() {
		Context context = new Context("");

		context.setId(taskContextIdCounter++);
		
		taskContexts.add(context);
		
		return context;
	}

	public int emTaskContextMoveToIndex(Context context, int index) {
		int indexOf = this.getTaskContexts().indexOf(context);
		this.getTaskContexts().remove(context);
		int newIndexForMovedContext = indexOf >= index ? index : index -1;
		this.getTaskContexts().add(newIndexForMovedContext , context);
		return newIndexForMovedContext;
	}
	
	public Event emEventNew() {
		Event event = new Event();
		
		event.setId(eventIdCounter++);
		
		events.add(event);
		
		return event;
	}
	
	public void emEventSetTimeBlock(Event event, TimeBlock timeBlock) {
		event.setTimeBlock(timeBlock);
		timeBlock.setOwner(event);
	}
	
	
	/* - Validation methods - */
	
	public boolean vlContextIsAssignable(Context context) {
		return context != null && context.isSelectable();
	}

	
	/* - Easy State Changers - */
	
	public void csViewMoveByDays(int days) {
		view.moveAmountOfDays(days);
	}

}
