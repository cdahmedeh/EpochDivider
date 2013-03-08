package org.cdahmedeh.orgapp.types.task;

public class TaskCopier {
	public static void copy(Task source, Task target){
		target.setTitle(				source.getTitle());
		
		target.setMutability( 			source.getMutability());
		target.setPriority( 		source.getPriority());
		
		target.setDurationToComplete(	source.getDurationToComplete());
		target.setDueDate( 				source.getDueDate());
		
		//TODO: Timeblocks
	}
}
