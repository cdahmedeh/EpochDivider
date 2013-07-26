package com.tronicdream.epochdivider.core.container;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeConstants;

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

import core.tronicdream.epochdivider.core.types.event.Event;

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
 * @author Ahmed El-Hajjar
 */
public class DataContainer {
	
	/* - Constructs - */
	
	public DataContainer() {
		taskContexts = this.generateDefaultContexts();
		tasks = new ArrayList<>();
		timeBlocks = new ArrayList<>();
		view = new View(DateReference.getToday().withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(7), DateReference.getToday().withDayOfWeek(DateTimeConstants.SUNDAY).plusDays(-1));
		selectedTaskContext = taskContexts.get(0);
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
	
	
	/* - Main UI States - */
	
	private View view;
	public View getView() {return view;}
	public void setView(View view) {this.view = view;}
	
	private Context selectedTaskContext;
	public Context getSelectedTaskContext() {return selectedTaskContext;}
	public void setSelectedTaskContext(Context selectedTaskContext) {this.selectedTaskContext = selectedTaskContext;}
	
	
	/* - Secondary UI States - */
	
	private boolean dimPast = false;
	public boolean getDimPast() {return dimPast;}
	public void setDimPast(boolean dimPast) {this.dimPast = dimPast;}

	private boolean showCompleted = false;;
	public boolean getShowCompleted() {return showCompleted;}
	public void setShowCompleted(boolean showCompleted) {this.showCompleted = showCompleted;}
	
	
	
	public ArrayList<Context> generateDefaultContexts() {
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

	
	
	// -- Readers --
	
	

	/**
	 * Get all contexts that can be assigned to a task.
	 */
	public ArrayList<Context> getSelectableContexts() {
		ArrayList<Context> contextsList = new ArrayList<>();
		for (Context context: taskContexts) if (context.isSelectable()) contextsList.add(context);
		return contextsList;
	}

	
	
	// -- Helpers --
	/**
	 * Replaces data within this container with the data of another one.
	 * TODO: Keep updated
	 */
	public void replace(DataContainer dataContainer) {
		this.taskContexts = dataContainer.getTaskContexts();
		this.tasks = dataContainer.getTasks();
		this.selectedTaskContext = dataContainer.getSelectedTaskContext();
		this.view = dataContainer.getView();
		this.showCompleted = dataContainer.getShowCompleted();
		this.dimPast = dataContainer.getDimPast();
	}
	
	// -- Easy Modifiers --
	
	/**
	 * Creates a new context with a blank name.
	 */
	public void createNewBlankContext() {
		this.getTaskContexts().add(new Context(""));		
	}
	
	/**
	 * Move 'context' to 'index' and return the new index of the context. 
	 */
	public int moveContextToRowAndGiveNewIndex(Context context, int index) {
		int indexOf = this.getTaskContexts().indexOf(context);
		this.getTaskContexts().remove(context);
		int newIndexForMovedContext = indexOf >= index ? index : index -1;
		this.getTaskContexts().add(newIndexForMovedContext , context);
		return newIndexForMovedContext;
	}
	
	/**
	 * Set the context of the provided task. 
	 */
	public boolean emTaskSetContext(Task task, Context context) {
		if (context.isSelectable()){
			task.setContext(context);
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Checks if the context can be assigned to a task.
	 */
	public boolean emContextIsAssignable(Context context) {
		return context != null && context.isSelectable();
	}
	
	/**
	 * Create a new blank task. 
	 * 
	 *  asEvent determines if the task will be set as an event or not.
	 *  TODO: Fix comment
	 */
	public void emTaskCreate(){
		//Create a new task.
		Task newTask = new Task("");
		
		//Set the context to the currently selected Context.
		if (getSelectedTaskContext().isSelectable()){
			newTask.setContext(getSelectedTaskContext());
		}
		
		//Add new task to the dataContainer and refresh task list table.
		getTasks().add(newTask);
	}
	
	/**
	 * Remove task
	 */
	public void emTaskRemove(Task task) {
		getTasks().remove(task);
	}
	
	/**
	 * Assigns a new timeBlock to the supplied task and returns it. 
	 */
	public TimeBlock assignNewTimeBlockToTask(Task task) {
		TimeBlock timeBlock = new TimeBlock();
		timeBlocks.add(timeBlock);
		timeBlock.setOwner(task);
		task.assignToTimeBlock(timeBlock);
		return timeBlock;
	}
	
	/**
	 * Creates a new task with the supplied context, and generates a new 
	 * timeBlock for that new task and returns the timeBlock.
	 */
	public TimeBlock createNewTaskAndTimeBlockWithContext(Context context) {
		Task task = new Task(context.getName());
		this.getTasks().add(task);
		TimeBlock timeBlock = new TimeBlock();
		task.assignToTimeBlock(timeBlock);
		task.setContext(context);
		timeBlocks.add(timeBlock);
		timeBlock.setOwner(task);
		return timeBlock;
	}
	
	/**
	 * Move view by days
	 * @param days
	 */
	public void moveViewByAmountOfDays(int days) {
		this.view.moveAmountOfDays(days);
	}

}
