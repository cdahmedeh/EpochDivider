package org.cdahmedeh.orgapp.context;
import java.io.Serializable;
import java.util.ArrayList;

import org.cdahmedeh.orgapp.task.Task;
import org.cdahmedeh.orgapp.task.TaskContainer;
import org.eclipse.swt.graphics.RGB;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

public class Context implements Serializable {
	private static final long serialVersionUID = -6470628602208724622L;
	
	public Context(String name, Context parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public boolean isVisible(){return true;}
	
	private String name = "";
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	private Duration goal = null;
	public Duration getGoal() {return goal;}
	public void setGoal(Duration goal) {this.goal = goal;}
	
	private Context parent = null;
	public Context getParent() {return parent;}
	public void setParent(Context parent) {this.parent = parent;}
	
	private RGB color = null;
	public RGB getColor() {
		if (color == null) {
			int r = Math.abs(this.toString().hashCode()*this.toString().trim().toUpperCase().hashCode()*this.toString().length() % 360);
			return new RGB((float)r, 0.1f, 0.9f);
		}
		return color;
	}
	public void setColor(RGB color) {
		this.color = color;
	}
	
	private ArrayList<Context> subContexts = new ArrayList<>();
	public ArrayList<Context> getSubContexts() {return subContexts;}
	public void addSubContext(Context context) {subContexts.add(context);}
	public void removeSubContext(Context context) {subContexts.remove(context);}
	
	public ArrayList<Context> getAllSubContexts(){
		ArrayList<Context> contexts = new ArrayList<>();
		
		for (Context context: this.getSubContexts()){
			if (context.isVisible()){
				contexts.add(context);
			}
			contexts.addAll(context.getAllSubContexts());
		}
		
		return contexts;
	}
	
	public static Context fromString(String text, Context rootContext) {
		for (Context context: rootContext.getAllSubContexts()){
			if (text.equals(context.toString())){
				return context;
			}
		}
		
		return null;
	}
	
	public int countTotalHours(TaskContainer tasks, int days){
		TaskContainer filterByContext = tasks.filterByContext(this).generateReccurence(new LocalDate().plusWeeks(6), null);
		
		Duration acc = new Duration(0);
		
		for (Task task: filterByContext.getTasks()){
			if (task.getScheduled() == null) continue;
			if (days != -1 && task.getScheduled().isAfter(new DateTime().plusDays(days))) continue;
			
			acc = acc.plus(task.getDuration());
		}
		
		return (int) acc.getStandardHours();
	}
	public int countTotalHours(TaskContainer tasks){
		return countTotalHours(tasks, -1);
	}
	
	@Override
	public String toString() {
		return parent == null ? name : parent.toString() + " > " + name;
	}
}
