package org.cdahmedeh.orgapp.types.task;

import java.util.ArrayList;

import org.cdahmedeh.orgapp.types.recurrence.Recurrence;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.LocalDate;

public class RecurrentTaskTemplate extends Task {
	private Recurrence recurrence = null;
	public Recurrence getRecurrence() {return recurrence;}
	public void setRecurrence(Recurrence recurrence) {this.recurrence = recurrence;}

	private ArrayList<RecurrentTaskInstance> instances = new ArrayList<>();
	
	public ArrayList<RecurrentTaskInstance> generateRecurrentTasks(LocalDate startRange, LocalDate endRange){
		ArrayList<RecurrentTaskInstance> rTasks = new ArrayList<>();
		
		//TODO: Very experimental, start and end range currently useless.
		//TODO: Clean me up please.
		for (LocalDate date: recurrence.generateRecurrenceDateIterable()){
			RecurrentTaskInstance rTaskI = new RecurrentTaskInstance();
			TimeBlock firstTimeBlock = this.getFirstTimeBlock();
			TaskCopier.copy(this, rTaskI);
			rTaskI.clearAssignedTimeBlocks();
			rTaskI.setTemplate(this); //TODO: Make in constructor
			rTaskI.assignToTimeBlock(new TimeBlock(date.toDateTime(firstTimeBlock.getStart().toLocalTime())));
			rTasks.add(rTaskI);
			instances.add(rTaskI);
		}
		
		return rTasks;
	}
}
