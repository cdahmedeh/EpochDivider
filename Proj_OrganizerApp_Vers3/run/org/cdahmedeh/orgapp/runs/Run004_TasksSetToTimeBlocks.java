package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.types.task.Task;
import org.cdahmedeh.orgapp.types.time.TimeBlock;
import org.joda.time.DateTime;

/**
 * Creating some tasks and assigning them to some time blocks.
 * 
 * @author cdahmedeh
 */
public class Run004_TasksSetToTimeBlocks {
	public static void main(String[] args) throws Exception {
		Task task = new Task();
		task.setTitle("Two blocks");
		
		task.assignToTimeBlock(new TimeBlock());
		task.assignToTimeBlock(new TimeBlock(new DateTime().plusDays(3)));
		
		System.out.println(task.toString());
		
		Task task2 = new Task();
		task2.setTitle("No Time");
		
		System.out.println(task2.toString());
		
		Task task3 = new Task();
		task3.setTitle("One block");
		
		task3.assignToTimeBlock(new TimeBlock(new DateTime().plusDays(1), new DateTime().plusDays(1).plusHours(1)));
		
		System.out.println(task3.toString());
	}
}
