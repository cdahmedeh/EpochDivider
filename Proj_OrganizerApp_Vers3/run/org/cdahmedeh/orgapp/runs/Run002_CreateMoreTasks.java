package org.cdahmedeh.orgapp.runs;

import org.cdahmedeh.orgapp.types.task.Task;
import org.joda.time.DateTimeConstants;
import org.joda.time.Duration;

/**
 * More tasks testing.
 * 
 * @author cdahmedeh
 */
public class Run002_CreateMoreTasks {
	public static void main(String[] args) {
		Task task1 = new Task("Finish Assignment");
		task1.setDurationToComplete(new Duration(DateTimeConstants.MILLIS_PER_MINUTE * 15));
	}
}
