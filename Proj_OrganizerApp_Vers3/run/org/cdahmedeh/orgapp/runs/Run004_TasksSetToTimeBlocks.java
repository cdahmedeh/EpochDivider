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
		task.setTitle("Some Title");
		
		task.assignToTimeBlock(new TimeBlock());
		task.assignToTimeBlock(new TimeBlock(new DateTime().plusDays(3)));
		
		System.out.println(task.toString());
	}
}
