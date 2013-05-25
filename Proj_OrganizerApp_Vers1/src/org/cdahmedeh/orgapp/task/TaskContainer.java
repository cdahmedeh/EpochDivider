package org.cdahmedeh.orgapp.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.cdahmedeh.orgapp.context.AllContextsContext;
import org.cdahmedeh.orgapp.context.Context;
import org.cdahmedeh.orgapp.context.NoContextContext;
import org.joda.time.LocalDate;

public class TaskContainer {
	private ArrayList<Task> tasks = new ArrayList<>();
	public ArrayList<Task> getTasks() {return tasks;}
	public void addTask(Task task) {tasks.add(task);}
	public void removeTask(Task task) {tasks.remove(task);}
	
	public TaskContainer filterByContext(Context context){
		if (context instanceof AllContextsContext){
			return this;
		}
		
		ArrayList<Task> tasks = new ArrayList<>();
		
		if (context instanceof NoContextContext){
			for (Task task: this.getTasks()){
				if (task.getContext() == null){
					tasks.add(task);
				}
			}
		} else {
			for (Task task: this.getTasks()){
				if (task.getContext() == context){
					tasks.add(task);
				}
			}	
		}
		
		TaskContainer task = new TaskContainer();
		
		for (Task t: tasks){
			task.addTask(t);
		}
		
		return task;
	}
	public TaskContainer sortWith(Comparator<? super Task> comparable) {
		if (comparable == null) return this;
		
		Collections.sort(this.getTasks(), comparable);
		
		ArrayList<Task> tasks = new ArrayList<>();
		
		for (Task task: this.getTasks()){
			tasks.add(task);
		}

		TaskContainer task = new TaskContainer();
		
		for (Task t: tasks){
			task.addTask(t);
		}
		
		return task;
	}
	
	public TaskContainer generateReccurence(LocalDate lookAheadMax, LocalDate lookBeforeMin){
		TaskContainer newRoot = new TaskContainer();
		
		newRoot.getTasks().addAll(this.getTasks());

		ArrayList<Task> rts = new ArrayList<>();
		ArrayList<Task> rem = new ArrayList<>();
		
		for (Task insideT: newRoot.getTasks()){
			if (!insideT.hasRecurence()) continue;
			if (insideT.getRecurrence().getFreq() != RecurrenceFrequency.NONE){
				for (int i = 0; i < insideT.getRecurrence().getAmount() || insideT.getRecurrence().getAmount() == 0; i++){
					RecurringTask rt = new RecurringTask(insideT.getName(), insideT.getParent(), insideT);
					
					rem.add(insideT);
					
					insideT.easyCopy(rt, insideT);
					if (insideT.getRecurrence().getFreq() == RecurrenceFrequency.DAILY){
						rt.setScheduled(insideT.getScheduled().plusDays(i*insideT.getRecurrence().getMult()));
					} else if (insideT.getRecurrence().getFreq() == RecurrenceFrequency.WEEKLY){
						rt.setScheduled(insideT.getScheduled().plusWeeks(i*insideT.getRecurrence().getMult()));
					} else if (insideT.getRecurrence().getFreq() == RecurrenceFrequency.MONTHLY){
						rt.setScheduled(insideT.getScheduled().plusMonths(i*insideT.getRecurrence().getMult()));
					} else if (insideT.getRecurrence().getFreq() == RecurrenceFrequency.YEARLY){
						rt.setScheduled(insideT.getScheduled().plusYears(i*insideT.getRecurrence().getMult()));
					}
					if(insideT.getRecurrence().endsUntil() && rt.getScheduled().toLocalDate().isAfter(insideT.getRecurrence().getUntil())){
						break;
					}
					if(lookBeforeMin != null && rt.getScheduled().toLocalDate().isBefore(lookBeforeMin)){
						continue;
					}
					if(rt.getScheduled().toLocalDate().isAfter(lookAheadMax)){
						break;
					}
					if (insideT.getRecurrence().isException(rt.getScheduled().toLocalDate())) {
						continue;
					}
					rt.setDuration(insideT.getDuration());
					rts.add(rt);
				}
			}
		}
		
		newRoot.getTasks().addAll(rts);
		
		newRoot.getTasks().removeAll(rem);
		
		return newRoot;
	}
	
}
