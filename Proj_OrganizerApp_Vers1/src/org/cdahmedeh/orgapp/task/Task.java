package org.cdahmedeh.orgapp.task;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.cdahmedeh.orgapp.context.AllContextsContext;
import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.context.NoContextContext;
import org.cdahmedeh.orgapp.ui.UIConstants;
import org.eclipse.swt.graphics.RGB;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Duration;

public class Task implements Serializable {
	private static final long serialVersionUID = -5543601631970851962L;
	
	private static int idCounter = 0; 
	
	public Task(String name, TaskContainer parent) {
		this.id = Task.idCounter++; 
		this.declared = new DateTime();
		this.name = name;
		this.parent = parent;
	}

	private int id = -1;
	public int getId() {return id;}
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	private TaskContainer parent = null;
	public TaskContainer getParent() {return parent;}
	public void setParent(TaskContainer parent) {this.parent = parent;}
	
	private ArrayList<SubTask> subTasks = new ArrayList<>();
	public ArrayList<SubTask> getSubTasks() {return subTasks;}
	public void addSubTask(SubTask task) {subTasks.add(task);}
	public void removeSubTask(SubTask task) {subTasks.remove(task);}
	
	private Context context = null;
	public Context getContext() {return context;}
	public void setContext(Context context) {this.context = context;}
	
	private DateTime due = null;
	public DateTime getDue() {return due;}
	public void setDue(DateTime due) {this.due = due;}
	
	private DateTime scheduled = null;
	public DateTime getScheduled() {return scheduled;}
	public void setScheduled(DateTime scheduled) {this.scheduled = scheduled;}
	
	private DateTime declared = null;
	public DateTime getDeclared() {return declared;}
	public void setDeclared(DateTime declared) {this.declared = declared;}
	
	private Duration duration = null;
	public Duration getDuration() {return duration;}
	public void setDuration(Duration duration) {this.duration = duration;}
	
	private boolean completed = false;
	public boolean isCompleted() {return completed;}
	public void setCompleted(boolean completed) {this.completed = completed;}
	
	private Priority priority = Priority.NONE;
	public Priority getPriority() {return priority;}
	public void setPriority(Priority priority) {this.priority = priority;}
	
	private Recurrence recurrence = null;
	public Recurrence getRecurrence() {return recurrence;}
	public void setRecurrence(Recurrence recurrence) {this.recurrence = recurrence;}
	public boolean hasRecurence() {return recurrence != null;}
	
	private RGB color = null;
	public RGB getColor() {
		if (color == null){
			if (context == null || context.getColor() == null){
				return UIConstants.taskDefaultColor;
			} else {
				return context.getColor();
			}
		}
		return color;
	}
	public void setColor(RGB color) {
		this.color = color;
	}
	
	public int daysSpaning() {
		return Days.daysBetween(
				scheduled.toDateMidnight(), 
				scheduled.plus(duration).toDateMidnight()
			).getDays();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task){
			return ((Task) obj).getId() == this.id;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return parent == null ? name : parent.toString() + " > " + name;
	}
	
	public void split(int amount){
//		for (int i = 0; i<amount; i++){
//			Task subtask = new Task(this.getName(), this);
//			this.addSubTask(subtask);			
//		}
	}
	
	public Task copy(){
		Task copy = new Task(this.getName(), this.getParent());
		
		Task.easyCopy(copy, this);
		
		return copy;
	}
	
	protected static void easyCopy(Task target, Task source){
		//TODO: copy subtasks?
		
		target.setName(source.getName());
		target.setContext(source.getContext());
		
		//TODO: should dates be recreated?
		target.setDue(source.getDue());
		target.setScheduled(source.getScheduled());
		target.setDeclared(source.getDeclared());
		target.setDuration(source.getDuration());
		
		target.setCompleted(source.isCompleted());
		target.setPriority(source.getPriority());
		
		//TODO: how to copy reccurence?
		target.setRecurrence(source.getRecurrence().copy());
	}
}
