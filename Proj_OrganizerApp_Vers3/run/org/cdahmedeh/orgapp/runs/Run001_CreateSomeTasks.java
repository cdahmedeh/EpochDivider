package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.types.task.Task;

import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

/**
 * Creating some tasks.
 * 
 * @author cdahmedeh
 */
public class Run001_CreateSomeTasks {
	public static void main(String[] args) {
		Task task1 = new Task("Work");
		task1.setDurationToComplete(new Duration(new Duration(DateTimeConstants.MILLIS_PER_HOUR * 2)));
		
		Task task2 = new Task("More Work");
		task2.setDurationToComplete(new Duration(new Duration(DateTimeConstants.MILLIS_PER_HOUR * 1)));
	}
}
